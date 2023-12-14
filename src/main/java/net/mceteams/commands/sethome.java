package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class sethome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

//        if (!p.getName().equals("Lenoirrateur")) {
//            p.sendMessage("§c§lMCE §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
//            return true;
//        }

        if(strings.length == 1) {
            Location plrpos = p.getLocation();

            int maxHomes = 5; // Limite de homes

            List<String> homes = dataManagementFunction.getKeys(p.getName(), "/homes/");
            if (homes.size() >= maxHomes) {
                p.sendMessage("§c§lMCE §8§l»§r §7Vous avez atteint la limite de homes.");
                return true;
            }

            String homename = strings[0];

            // Ajouter le home
            Location playerLocation = p.getLocation();
            String locationData = String.format("%.0f,%.0f,%.0f", playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
            dataManagementFunction.addData(p.getName(), "/homes/", homename, locationData);
            p.sendMessage("§c§lMCE §8§l»§r §7Le home §6§l" + homename + "§r§7 a été §2§lcréé§r§7.");
        } else {
            p.sendMessage("§c§lMCE §8§l»§r §7Veuillez specifier un nom de home.");
        }

        return true;
    }
}
