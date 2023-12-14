package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class health implements CommandExecutor {
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

        p.setHealth(20);
        p.sendMessage("§c§lMCE §8§l»§r§7 Vous avez été soigné.");


        return true;
    }
}
