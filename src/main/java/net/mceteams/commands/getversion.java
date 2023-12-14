package net.mceteams.commands;

import net.mceteams.functions.administrationSystemFunction;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class getversion implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        try {
            if (!administrationSystemFunction.checkperm(p, "admin")) {
                p.sendMessage("§c§lErreur §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
                return true;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Player targetPlayer = Bukkit.getPlayer(strings[1]);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            p.sendMessage("§c§lMCE §8§l»§r§7 Le joueur §6§l" + strings[1] + "§r§c§l n'existe pas§r§7 ou §c§ln'est pas connecté§r§7 !");
            return true;
        }

        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        p.sendMessage("La version de §6§l"+ targetPlayer.getName() + "§r est '§c§l" + version + "§r");
        return true;
    }
}
