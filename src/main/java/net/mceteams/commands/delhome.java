package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class delhome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        if (!p.getName().equals("Thenoirrateur")) {
            p.sendMessage("§c§lMCE §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
            return true;
        }

        if(strings.length == 0) {
            p.sendMessage("§c§lErreur §8§l»§r Veuillez specifier un nom de home.");
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING ,1f, 0.5f);
            return true;
        }

        if(!dataManagementFunction.doesDataExist(p.getName(),"/homes/",strings[0])) {
            p.sendMessage("§c§lErreur §8§l»§r Ce home n'existe pas.");
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING ,1f, 0.5f);
            return true;
        }

        p.sendMessage("Les home a été supprimé avec succès");
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1f,1f);
        dataManagementFunction.deleteData(p.getName(),"/homes/", "");
        return true;
    }
}
