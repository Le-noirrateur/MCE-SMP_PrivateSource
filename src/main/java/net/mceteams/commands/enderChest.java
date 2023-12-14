package net.mceteams.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class enderChest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 1f);
        p.sendMessage("Votre §5§lender chest§r est §2ouvert§7.");
        p.openInventory(p.getEnderChest());

        return true;
    }
}
