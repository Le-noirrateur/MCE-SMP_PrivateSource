package net.mceteams.event;

import net.mceteams.functions.dataManagementFunction;
import net.mceteams.functions.sanctionsManagerFunction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class defaultMinecraftCommandsEdit implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0].substring(1).toLowerCase(); // Obtenir la commande sans le caractère '/' et en minuscules


        try {
            if (!dataManagementFunction.loadData(event.getPlayer().getUniqueId().toString(),"/players/", "perm").equals("admin")) {
                if (command.equalsIgnoreCase("minecraft:execute") || command.equalsIgnoreCase("execute") || command.equalsIgnoreCase("minecraft:gamerule") || command.equalsIgnoreCase("gamerule") || command.equalsIgnoreCase("minecraft:difficulty") || command.equalsIgnoreCase("difficulty") || command.equalsIgnoreCase("minecraft:locate") || command.equalsIgnoreCase("locate") || command.equalsIgnoreCase("minecraft:clone") || command.equalsIgnoreCase("clone") || command.equalsIgnoreCase("advancement") || command.equalsIgnoreCase("defaultgamemode") || command.equalsIgnoreCase("experience") || command.equalsIgnoreCase("fill") || command.equalsIgnoreCase("place") || command.equalsIgnoreCase("setblock") || command.equalsIgnoreCase("spawnpoint") || command.equalsIgnoreCase("effect") || command.equalsIgnoreCase("weather") || command.equalsIgnoreCase("teleport") || command.equalsIgnoreCase("time") || command.equalsIgnoreCase("tp") || command.equalsIgnoreCase("gamemode") || command.equalsIgnoreCase("kill") || command.equalsIgnoreCase("enchant") || command.equalsIgnoreCase("give") || command.equalsIgnoreCase("summon") || command.equalsIgnoreCase("xp") || command.equalsIgnoreCase("minecraft:advancement") || command.equalsIgnoreCase("minecraft:defaultgamemode") || command.equalsIgnoreCase("minecraft:experience") || command.equalsIgnoreCase("minecraft:fill") || command.equalsIgnoreCase("minecraft:place") || command.equalsIgnoreCase("minecraft:setblock") || command.equalsIgnoreCase("minecraft:spawnpoint") || command.equalsIgnoreCase("minecraft:effect") || command.equalsIgnoreCase("minecraft:weather") || command.equalsIgnoreCase("minecraft:teleport") || command.equalsIgnoreCase("minecraft:time") || command.equalsIgnoreCase("minecraft:tp") || command.equalsIgnoreCase("minecraft:gamemode") || command.equalsIgnoreCase("minecraft:kill") || command.equalsIgnoreCase("minecraft:enchant") || command.equalsIgnoreCase("minecraft:give") || command.equalsIgnoreCase("minecraft:summon") || command.equalsIgnoreCase("minecraft:xp")) {
                    event.getPlayer().sendMessage("§c§lMCE §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
                    event.setCancelled(true);
                } else if (command.equalsIgnoreCase("clear") || command.equalsIgnoreCase("minecraft:clear")) {
                    Player p = Bukkit.getPlayer(event.getPlayer().getName());
                    assert p != null;
                    p.sendMessage("§c§lMCE §8§l»§r §4lATTENTION§r§7, cette commande a été désactivé car elle est trop dangeureuse !");
                    event.setCancelled(true);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        if(command.equalsIgnoreCase("restart") || command.equalsIgnoreCase("spigot:restart")) {
            Player p = Bukkit.getPlayer(event.getPlayer().getName());
            assert p != null;
            if(p.isOp()) {
                Bukkit.broadcastMessage("§c§lMCE §8§l»§r §7Le serveur redémarre dans §65 secondes.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Bukkit.broadcastMessage("§c§lMCE §8§l»§r §7Le serveur redémarre dans §64 secondes.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Bukkit.broadcastMessage("§c§lMCE §8§l»§r §7Le serveur redémarre dans §63 secondes.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Bukkit.broadcastMessage("§c§lMCE §8§l»§r §7Le serveur redémarre dans §62 secondes.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Bukkit.broadcastMessage("§c§lMCE §8§l»§r §7Le serveur redémarre dans §61 secondes.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Bukkit.broadcastMessage("§c§lMCE §8§l»§r §6Le serveur redémarre.");
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.kickPlayer("§c§lMCE §8§l»§r §7Vous avez été §4§lExclus§r§7 !\n\n§cRaison §8» §7Le serveur redémarre.");
                }
                Bukkit.getServer().shutdown();
            }
        }

        if(command.equalsIgnoreCase("stop") || command.equalsIgnoreCase("minecraft:stop")) {
            Player p = Bukkit.getPlayer(event.getPlayer().getName());
            assert p != null;
            if(p.isOp()) {
                Bukkit.broadcastMessage("§c§lMCE §8§l»§r §7Arrêt serveur.");
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.kickPlayer("§c§lMCE §8§l»§r §7Vous avez été §4§lExclus§r§7 !\n\n§cRaison §8» §7Le serveur s'arrête.");
                }
                Bukkit.getServer().shutdown();
            }
        }

        if(command.equalsIgnoreCase("reload") || command.equalsIgnoreCase("bukkit:reload")) {
            Player p = Bukkit.getPlayer(event.getPlayer().getName());
            assert p != null;
            if (p.isOp()) {
                Bukkit.broadcastMessage("§c§lMCE §8§l»§r §6Le serveur reload.");
                Bukkit.getServer().reload();
            }
        }

        if(command.equalsIgnoreCase("ban-ip") || command.equalsIgnoreCase("minecraft:ban-ip")) {
            Player p = Bukkit.getPlayer(event.getPlayer().getName());
            assert p != null;
            p.sendMessage("§c§lMCE §8§l»§r§7 Cette commande est désactivé !");
            event.setCancelled(true);
        }

        if(command.equalsIgnoreCase("kick") || command.equalsIgnoreCase("minecraft:kick")) {
            event.setCancelled(true);
            String[] args = event.getMessage().substring(command.length() + 1).split(" "); // Obtenir les arguments de la commande
            // args contient maintenant les arguments de la commande ban
            // Vérifier si des arguments sont fournis
            String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
            if (args.length > 0) {
                event.setCancelled(true);
                if(args[1].equals("@a") || args[1].equals("@e")) {
                    for (Player plr : Bukkit.getOnlinePlayers()) {
                        sanctionsManagerFunction.kick(plr, reason, event.getPlayer().getName());
                    }
                }
                Player target = Bukkit.getPlayer(args[1]);


                if (target != null) {
                    sanctionsManagerFunction.kick(target, reason, event.getPlayer().getName());
                }
            } else {
                // Aucun argument fourni avec la commande
                event.getPlayer().sendMessage("§c§lMCE §8§l»§r Veuillez sélectionner le joueur.");
            }
        }

        if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("minecraft:help")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§c§lErreur §8§l»§r Commande indisponible pour le moment.");
        }

        if (command.equalsIgnoreCase("minecraft:ban") || command.equalsIgnoreCase("ban")) {
            event.setCancelled(true);
            String[] args = event.getMessage().substring(command.length() + 1).split(" "); // Obtenir les arguments de la commande
            if (args.length >= 3) {
                Player target = Bukkit.getPlayer(args[0]);
                String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

                if (target != null) {
                    sanctionsManagerFunction.ban(target, reason, event.getPlayer().getDisplayName());
                }
            } else {
                // Gérer l'absence d'arguments nécessaires
                event.getPlayer().sendMessage("La commande nécessite au moins 2 arguments.");
            }

        }
    }
}

