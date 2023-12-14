package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import net.mceteams.functions.logsRegister;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        try {
            if (!dataManagementFunction.loadData(p.getUniqueId().toString(),"/players/", "perm").equals("administrationSystemFunction")) {
                if(!dataManagementFunction.loadData(p.getUniqueId().toString(),"/players/", "perm").equals("admin")) {
                    p.sendMessage("§c§lMCE §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        if(strings.length == 0) {
            if(p.getAllowFlight()) {
                logsRegister.addLogEntry(p, p.getName() + " executed /fly (off)");
                p.sendMessage("§c§lMCE §8§l»§r§7 Vous ne pouvez plus voler.");
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BIG_FALL, 1f, 1f);
                p.setAllowFlight(false);
            } else {
                logsRegister.addLogEntry(p, p.getName() + " executed /fly (on)");
                p.sendMessage("§c§lMCE §8§l»§r§7 Vous pouvez voler.");
                p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 1f);
                p.setAllowFlight(true);
            }
        } else {
            Player Target = Bukkit.getPlayer(strings[0]);
            if(Target != null) {
                if(Target.getAllowFlight()) {
                    logsRegister.addLogEntry(p, p.getName() + " executed /fly (off) for " + Target.getName() + " ( " + Target.getUniqueId() + " ).");
                    Target.sendMessage("§c§lMCE §8§l»§r§7 Vous ne pouvez plus voler.");
                    p.sendMessage("§c§lMCE §8§l»§r§7 Vous avez interdit §6§l" + strings[0] + "§r§7 à voler.");
                    p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                    Target.playSound(Target.getLocation(), Sound.ENTITY_PLAYER_BIG_FALL, 1f, 1f);
                    Target.setAllowFlight(false);
                } else {
                    logsRegister.addLogEntry(p, p.getName() + " executed /fly (on) for " + Target.getName() + " ( " + Target.getUniqueId() + " ).");
                    Target.sendMessage("§c§lMCE §8§l»§r§7 Vous pouvez voler.");
                    p.sendMessage("§c§lMCE §8§l»§r§7 Vous avez autorisé §6§l" + strings[0] + "§r§7 à voler.");
                    p.playSound(p.getLocation(),Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1f);
                    Target.playSound(Target.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 1f);
                    Target.setAllowFlight(true);
                }
            } else {
                p.sendMessage("§c§lMCE §8§l»§r§7 Le joueur §6§l" + strings[0] + "§r§c§l n'existe pas§r§7 ou §c§ln'est pas connecté§r§7 !");
            }
        }

        return true;
    }
}
