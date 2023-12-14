package net.mceteams.commands;

import net.mceteams.functions.temporaryDataFunction;
import net.mceteams.functions.dataManagementFunction;
import org.bukkit.Location;
import org.bukkit.Sound;
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

import java.io.FileNotFoundException;

public class home implements CommandExecutor, Listener {

    private final Plugin plugin;
    boolean hasMoved = false;
    int secondsLeft = 5;
    boolean hasBeenAttacked = false;

    public home(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

//        if (!p.getName().equals("Lenoirrateur")) {
//            p.sendMessage("§c§lMCE §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
//            return true;
//        }

        if(strings.length == 1) {
            String homename = strings[0];

            try {
                String gethomeData = dataManagementFunction.loadPlayerData(p, "/homes/", homename);

                if(gethomeData == null) {
                    p.sendMessage("§7Le home §6§l" + homename + "§r§c§l n'existe pas§r§7 ou §c§ln'a pas été trouvé§r§7 !");
                    return true;
                }

                p.sendMessage("§7Téléportation vers §6§l" + homename + "§r§7 !");
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);
                String[] coordinates = gethomeData.split(",");
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                double z = Double.parseDouble(coordinates[2]);

                Location homeLocation = new Location(p.getWorld(), x, y, z);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (secondsLeft > 0) {
                            if (hasMoved || hasBeenAttacked) {
                                cancel(); // Annuler le téléport si le joueur bouge ou se fait attaquer
                                hasMoved = false;
                                hasBeenAttacked = false;
                                secondsLeft = 5;
                                return;
                            }
                            p.sendMessage("§7Téléportation dans §6§l" + secondsLeft + "§r§7 seconde(s)...");
                            secondsLeft--;
                        } else {
                            secondsLeft = 5;
                            temporaryDataFunction.removeTemporaryData("home-Teleporting");
                            p.teleport(homeLocation);
                            p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1f, 1f);
                            PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 1);
                            PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 2 * 20, 10);
                            p.addPotionEffect(slow);
                            p.addPotionEffect(blind);
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0, 20);
            } catch (FileNotFoundException e) {
                return true;
            }
        } else {
            p.sendMessage("§7Veuillez specifier un nom de home.");
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

        String tempdat = (String) temporaryDataFunction.getTemporaryData("home-Teleporting");

        if (tempdat != null) {
            if (secondsLeft == 4 || secondsLeft == 3 || secondsLeft == 2 || secondsLeft == 1) {
                hasMoved = true;
                // Annuler la demande de téléportation si le joueur bouge
                temporaryDataFunction.removeTemporaryData("home-Teleporting");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1f, 1f);
                player.sendMessage("§7La demande de téléportation a été §c§lannulée§r§7 car vous avez bougé.");
            }
        }
    }


    // Événement lorsque le joueur est attaqué par un joueur ou un monstre


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        String tempdat = (String) temporaryDataFunction.getTemporaryData("home-Teleporting");
        if (event.getEntity() instanceof Player player) {
            if (tempdat != null) {
                if (secondsLeft == 4 || secondsLeft == 3 || secondsLeft == 2 || secondsLeft == 1) {
                    hasBeenAttacked = true;
                    // Annuler la demande de téléportation si le joueur est attaqué
                    temporaryDataFunction.removeTemporaryData("home-Teleporting");
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1f, 1f);
                    player.sendMessage("§7La demande de téléportation a été §c§lannulée§r§7 car vous avez été attaqué.");
                }
            }
        }
    }

}
