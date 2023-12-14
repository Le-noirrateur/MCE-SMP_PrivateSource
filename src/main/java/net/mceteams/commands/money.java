package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class money implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        if(args.length == 0) {
            return true;
        }

        if(args[0].equals("amount")) {
            try {
                p.sendMessage("§c§lBanque §8§l»§r Votre solde est de : \n§6§l" + dataManagementFunction.loadData(p.getUniqueId().toString(), "/players/", "money") + " $MP");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (args[0].equals("give")) {
            p.sendMessage("§c§lBanque §8§l»§r Il n'est pas encore possible de donner de l'argent !!!");
        } else {
            p.sendMessage("§c§lBanque §8§l»§r Merci de spécifier si vous voulez donner ou voir votre solde.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("money")) {
            if (strings.length == 1) {
                List<String> completions = new ArrayList<>();
                completions.add("give");
                completions.add("amount");
                // Ajoutez d'autres options ici

                return completions;
            }
        }
        return Collections.emptyList();
    }
}
