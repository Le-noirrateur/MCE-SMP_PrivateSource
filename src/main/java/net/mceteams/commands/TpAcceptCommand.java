package net.mceteams.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class TpAcceptCommand implements CommandExecutor, Listener {

    private final Plugin plugin;
    private final Map<UUID, UUID> tpaRequests;

    boolean hasMoved = false;
    int secondsLeft = 5;
    boolean hasBeenAttacked = false;

    public TpAcceptCommand(Plugin plugin, Map<UUID, UUID> tpaRequests) {
        this.plugin = plugin;
        this.tpaRequests = tpaRequests;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        // Vérifier si le joueur a une demande de téléportation en attente
        if (tpaRequests.containsKey(player.getUniqueId())) {
            // Récupérer l'UUID de l'émetteur de la demande
            UUID requesterUUID = tpaRequests.get(player.getUniqueId());

            // Supprimer la demande de téléportation de la ma

            // Exécuter le téléport
            // Votre code pour effectuer le téléport ici

            player.sendMessage("§7Vous avez §2§laccepté§r§7 la demande de téléportation.");


            // Informer l'émetteur de la demande
            Player requester = plugin.getServer().getPlayer(requesterUUID);

            if (requester != null) {
                requester.sendMessage("§7Le joueur §6§l" + player.getName() + "§r§7 a §2§laccepté§r§7 votre demande de téléportation.");
                //Votre logique pour le délai de 5 secondes ici
                new BukkitRunnable() {



                    @Override
                    public void run() {
                        if (secondsLeft > 0) {
                            if (hasMoved || hasBeenAttacked) {
                                cancel(); // Annuler le téléport si le joueur bouge ou se fait attaquer
                                player.sendMessage("§7La demande de téléportation a été §c§lannulée§r§7 car le joueur a §cbougé§7 ou §cété attaqué§7.");
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                                hasMoved = false;
                                hasBeenAttacked = false;
                                secondsLeft = 5;
                                return;
                            }
                            requester.sendMessage("§7Téléportation dans §6§l" + secondsLeft + "§r§7 seconde(s)...");
                            secondsLeft--;
                        } else {
                            secondsLeft = 5;
                            tpaRequests.remove(player.getUniqueId());
                            requester.teleport(player.getLocation());
                            requester.playSound(requester.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1f, 1f);
                            PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 1);
                            PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 2 * 20, 10);
                            requester.addPotionEffect(slow);
                            requester.addPotionEffect(blind);
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0, 20);
            } else {
                player.sendMessage("§7Vous n'avez pas de demande de téléportation en attente.");
            }


        }
        return true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        // Vérifier si seuls les mouvements de la souris ont été effectués
        if (to != null && from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
            return; // Ignorer le mouvement de la souris
        }

        if (tpaRequests.containsValue(player.getUniqueId())) {
            if (secondsLeft == 4 || secondsLeft == 3 || secondsLeft == 2 || secondsLeft == 1) {
                hasMoved = true;
                // Annuler la demande de téléportation si le joueur bouge
                tpaRequests.values().remove(player.getUniqueId());
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1f, 1f);
                player.sendMessage("§7La demande de téléportation a été §c§lannulée§r§7 car vous avez bougé.");
            }
        }
    }


    // Événement lorsque le joueur est attaqué par un joueur ou un monstre
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (tpaRequests.containsValue(player.getUniqueId())) {
                if (secondsLeft == 4 || secondsLeft == 3 || secondsLeft == 2 || secondsLeft == 1) {
                    hasBeenAttacked = true;
                    // Annuler la demande de téléportation si le joueur est attaqué
                    tpaRequests.values().remove(player.getUniqueId());
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1f, 1f);
                    player.sendMessage("§7La demande de téléportation a été §c§lannulée§r§7 car vous avez été attaqué.");
                }
            }
        }
    }
}
