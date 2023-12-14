package net.mceteams.event;

import net.mceteams.functions.logsRegister;
import net.mceteams.functions.presetTemporaryDataFunction;
import net.mceteams.smpplugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.UUID;

import static net.mceteams.functions.actionBarFunction.sendActionBarMessage;

public class deathEvent implements Listener {

    private smpplugin plugin;

    public void SpectatorDeath(smpplugin plugin) {
        this.plugin = plugin;
    }

    private String formatLocation(Location location) {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String world = Objects.requireNonNull(location.getWorld()).getName();

        return "X: " + decimalFormat.format(x) + " Y: " + decimalFormat.format(y) + " Z: " + decimalFormat.format(z) + "§r§7 dans le monde  §l§6'" + world + "'";
    }

    @EventHandler
    public void death(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = p.getKiller();
        p.setGameMode(GameMode.SPECTATOR);
        e.setDroppedExp(0);

        if (killer != null) {

            String killerName = killer.getName();

            int expToAdd = p.getTotalExperience();
            int levelToAdd = p.getLevel();
            float expFraction = p.getExp();

            killer.giveExp(expToAdd); // Ajouter l'expérience totale du joueur tué
            killer.setLevel(killer.getLevel() + levelToAdd); // Ajouter les niveaux du joueur tué
            killer.setExp(expFraction); // Ajouter l'expérience fractionnaire du joueur tué

            logsRegister.addLogEntry(p, p.getName() + " has been killed at " + formatLocation(p.getLocation()) + " by " + killer.getName());
                    e.setDeathMessage("§6§l" + p.getDisplayName() + "§r§f a été tué par §c§l" + killerName + "§r§f");
            killer.sendMessage("Vous avez tué §6§l" + p.getName());
        } else {
            // Le joueur est mort d'une autre cause (chute, noyade, etc.)
            logsRegister.addLogEntry(p, p.getName() + " has died at " + formatLocation(p.getLocation()));
            e.setDeathMessage("§6§l" + p.getDisplayName() + "§r§f est mort(e)");

        }

        p.sendMessage("§6§lVous êtes mort(e)§r§7, voici la où vous êtes mort(e) §6" + formatLocation(e.getEntity().getLocation()));
        // Planifier la réapparition du joueur après 10 secondes
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        PlayerRespawnEvent.RespawnReason respawnReason = event.getRespawnReason();

        if (respawnReason == PlayerRespawnEvent.RespawnReason.DEATH) {
            Player player = event.getPlayer();
            UUID playerId = player.getUniqueId();
            presetTemporaryDataFunction.addSpectatingPlayer(playerId);

            int countdownSeconds = 10; // Temps en secondes avant la réapparition
            int countdownTicks = countdownSeconds * 20; // Conversion en ticks (20 ticks par seconde)

            new BukkitRunnable() {
                int remainingTicks = countdownTicks;

                @Override
                public void run() {
                    if (remainingTicks <= 0) {
                        respawnPlayer(player);
                        cancel(); // Annuler la tâche répétée une fois le compte à rebours terminé
                    } else {
                        UUID playerId = player.getUniqueId();
                        if (presetTemporaryDataFunction.isSpectatingPlayer(playerId)) {
                            PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1, true, false);
                            PotionEffect jump = new PotionEffect(PotionEffectType.JUMP, 999999, 199, true, false);
                            PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 999999, 199, true, false);
                            player.addPotionEffect(blind);
                            player.addPotionEffect(jump);
                            player.addPotionEffect(slow);
                        }
                        int remainingSeconds = remainingTicks / 20; // Conversion en secondes
                        String titleMessage = "Réapparition dans §6§l" + remainingSeconds + "§r seconde(s)";
                        player.sendTitle("§7Vous êtes §4§lmort§r§7(§4§le§r§7) !", titleMessage, 0, 20, 10); // Afficher le titre avec le message de réapparition


                        remainingTicks -= 1; // Réduire le nombre de ticks restants d'une seconde
                    }
                }
            }.runTaskTimer(plugin.getPlugin(), 0, 1); // Exécuter la tâche répétée toutes les 1 tick (1/20 seconde)
        }
    }

    private void respawnPlayer(Player player) {
        UUID playerId = player.getUniqueId();
        player.setGameMode(GameMode.SURVIVAL);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.removePotionEffect(PotionEffectType.JUMP);
        player.removePotionEffect(PotionEffectType.SLOW);
        Location respawnLocation = player.getBedSpawnLocation();
        if (respawnLocation == null) {
            respawnLocation = Bukkit.getWorlds().get(0).getSpawnLocation(); // Modifier avec la localisation de réapparition souhaitée
        }
        presetTemporaryDataFunction.removeSpectatingPlayer(playerId);
        player.teleport(respawnLocation);
        player.sendMessage("§7Vous êtes §a§lréapparu§r§7.");
        player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1f, 1f);
        player.setInvulnerable(true);
        int cts = 30;
        int cst = cts * 20;

        new BukkitRunnable() {
            int spawnprotect = cst;

            @Override
            public void run() {
                if(spawnprotect <= 0) {
                    sendActionBarMessage(player, "§7Protection de spawn §6§lterminée§r§7.");
                    cancel();
                } else {
                    int remainsec = spawnprotect/20;
                    String titleMessage = "§7Protection de spawn. §6§l" + remainsec + "§r§7 seconde(s) restantes";
                    sendActionBarMessage(player, titleMessage);

                    spawnprotect -= 1; // Réduire le nombre de ticks restants d'une seconde
                }
            }
        }.runTaskTimer(plugin.getPlugin(), 0, 1); // Exécuter la tâche répétée toutes les 1 tick (1/20 seconde)
        player.setInvulnerable(false);
    }
}


