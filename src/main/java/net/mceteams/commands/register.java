package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class register implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        try {
            if (dataManagementFunction.loadData(p.getUniqueId().toString(), "/players/", "registered").equals("true")) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                p.sendMessage("§c§lErreur §8§l»§r Vous avez déjà enregistré un mot de passe.");
                return true;
            }

            if(strings.length == 0) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                p.sendMessage("§c§lErreur §8§l»§r Vous n'avez pas spécifié un mot de passe.");
                return true;
            }


            p.sendMessage("§fMot de passe enregistré avec §2§lsuccès§r.");
            dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "registered", "true");
            dataManagementFunction.addData(p.getUniqueId().toString(),"/players/", "password",strings[0]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
