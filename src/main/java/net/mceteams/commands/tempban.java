package net.mceteams.commands;

import net.mceteams.functions.sanctionsManagerFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;


public class tempban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        if (strings.length < 3) {
            commandSender.sendMessage("§c§lMCE §8§l»§r§7 Utilisation : /tempban <joueur> <durée> <raison>");
            return true;
        }

        Player target = Bukkit.getPlayer(strings[0]);

        String reason = String.join(" ", Arrays.copyOfRange(strings, 2, strings.length));

        if (target != null) {
            sanctionsManagerFunction.tempban(target, reason, p.getName(), strings[1]);
        } else {
            p.sendMessage("§c§lMCE §8§l»§r§7 Le joueur §6§l" + strings[0] + "§r§7 n'existe pas.");
        }

        return false;
    }
}
