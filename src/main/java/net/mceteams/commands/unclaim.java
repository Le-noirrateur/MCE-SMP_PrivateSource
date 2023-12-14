package net.mceteams.commands;

import net.mceteams.functions.administrationSystemFunction;
import net.mceteams.functions.chuckFunction;
import net.mceteams.functions.dataManagementFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class unclaim implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        try {
            if (!administrationSystemFunction.checkperm(p, "admin")) {
                p.sendMessage("§c§lMCE §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
                return true;
            }
        } catch ( FileNotFoundException e ) { throw new RuntimeException(e); }


        dataManagementFunction.addData(chuckFunction.detect(p), "/chunks/", "townowner", "");
        dataManagementFunction.addData(chuckFunction.detect(p), "/chunks/", "claimed", "false");
        return true;
    }
}
