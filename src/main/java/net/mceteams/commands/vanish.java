package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import net.mceteams.functions.logsRegister;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class vanish implements CommandExecutor {
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
            if(p.getGameMode() == GameMode.CREATIVE) {
                if (!p.isInvisible() && !p.isInvulnerable()) {
                    logsRegister.addLogEntry(p, p.getName() + " executed /vanish (on)");
                    p.sendMessage("§c§lMCE §8§l»§r§7 Vous êtes invisible.");
                    p.setInvulnerable(true);
                    p.setInvisible(true);
                    p.setCanPickupItems(false);
                } else {
                    logsRegister.addLogEntry(p, p.getName() + " executed /vanish (off)");
                    p.sendMessage("§c§lMCE §8§l»§r§7 Vous n'êtes plus invisible.");
                    p.setInvulnerable(false);
                    p.setInvisible(false);
                    p.setCanPickupItems(true);
                }
            } else if (p.getGameMode() == GameMode.ADVENTURE || p.getGameMode() == GameMode.SURVIVAL){
                if (!p.isInvisible() && !p.isInvulnerable() && !p.getAllowFlight()) {
                    logsRegister.addLogEntry(p, p.getName() + " executed /vanish (on)");
                    p.sendMessage("§c§lMCE §8§l»§r§7 Vous êtes invisible.");
                    p.setInvulnerable(true);
                    p.setInvisible(true);
                    p.setAllowFlight(true);
                    p.setCanPickupItems(false);
                } else {
                    logsRegister.addLogEntry(p, p.getName() + " executed /vanish (off)");
                    p.sendMessage("§c§lMCE §8§l»§r§7 Vous n'êtes plus invisible.");
                    p.setInvulnerable(false);
                    p.setInvisible(false);
                    p.setAllowFlight(false);
                    p.setCanPickupItems(true);
                }
            } else if (p.getGameMode() == GameMode.SPECTATOR) {
                p.sendMessage("§c§lMCE §8§l»§r§7 Vous §c§lne pouvez pas§r§7 être invisible en mode spectator.");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
