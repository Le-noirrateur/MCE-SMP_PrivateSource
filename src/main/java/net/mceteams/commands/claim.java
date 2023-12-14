package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import net.mceteams.functions.administrationSystemFunction;
import net.mceteams.functions.townManagementFunction;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.mceteams.functions.chuckFunction;

import java.io.FileNotFoundException;

public class claim implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        try {
            if (!administrationSystemFunction.checkperm(p,"admin")) {
                p.sendMessage("§c§lMCE §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
                return true;
            }
        } catch (FileNotFoundException e) { throw new RuntimeException(e); }

        if(townManagementFunction.getTown(p,"owner") == "null") {
            if(townManagementFunction.getTown(p, "member") == "null") {
                p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas §6§lclaim§r ce chunk car vous n'êtes pas dans une ville.");
                return true;
            } else if (!townManagementFunction.doesPerm(townManagementFunction.getTown(p, "member"),p,"claim")) {
                p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas §6§lclaim§r ce chunk car vous n'avez pas les permissions.");
                return true;
            }
        }

        try {
            chuckFunction.prepare(p);
            if (dataManagementFunction.loadData(chuckFunction.detect(p), "/chunks/", "claimed").equals("false")) {
                dataManagementFunction.addData(chuckFunction.detect(p), "/chunks/", "claimed", "true");
                p.sendMessage("§2§lSuccès §8§l»§r Vous avez §6§lclaim§r ce chunk.");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f);
                String text = dataManagementFunction.searchData("/towns/","owner",p.getUniqueId().toString());
                String townname = text.replace("[", "").replace("]", "");
                dataManagementFunction.addData(chuckFunction.detect(p), "/chunks/", "townowner", townname);
            } else {
                if(dataManagementFunction.loadData(chuckFunction.detect(p), "/chunks/", "townowner").equals(args[0])) {
                    p.sendMessage("§c§lErreur §8§l»§r Vous avez §6§ldéjà claim §rce chunk.");
                    return true;
                }
                p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas claim ce chunk car il à §4§ldéjà§r été claim par §6§l" + dataManagementFunction.loadData(chuckFunction.detect(p), "/chunks/", "townowner") + "§r !");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
            }
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
