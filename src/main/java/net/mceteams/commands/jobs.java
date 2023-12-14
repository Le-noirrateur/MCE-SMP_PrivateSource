package net.mceteams.commands;

import net.mceteams.functions.logsRegister;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class jobs implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        if (!p.getName().equals("Lenoirrateur")) {
            p.sendMessage("§c§lMCE §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
            return true;
        }

        if(strings.length == 1) {
            String subcmd = strings[0];
            if(subcmd.equals("list")) {
                p.sendMessage("§c§lMCE §8§l»§r§7 Voici la liste des jobs :\n\n   §6§lMiner§r\n\n   §c§lHunter§r\n\n   §2§lFarmer§r\n\n §k§l§7EEEEEEEE");
            } else if (subcmd.equalsIgnoreCase("leaderboard")) {
                p.sendMessage("§c§lMCE §8§l»§r§7 Le leaderboard n'est pas disponible.");
            } else {
                p.sendMessage("§c§lMCE §8§l»§r§7 §6" + subcmd + " §r§c§l n'existe pas§r§7.");
            }

            return true;
        }
        return true;
    }
}
