package net.mceteams.event;

import net.mceteams.functions.dataManagementFunction;
import net.mceteams.functions.logsRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.FileNotFoundException;

import static org.bukkit.Bukkit.getServer;

public class onLeave implements Listener {

    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + "has left the game.");
        try {
            if(!dataManagementFunction.loadData(p.getUniqueId().toString(), "/bans/", "status").equals("true")) {
                if(getServer().hasWhitelist()) {
                    if (p.isWhitelisted()) {
                        e.setQuitMessage("§4§l-§r §6" + p.getDisplayName() + "§7 s'est déconnecté.");
                    }
                } else {
                    e.setQuitMessage("§4§l-§r §6" + p.getDisplayName() + "§7 s'est déconnecté.");
                }
            } else {
                e.setQuitMessage("");
            }
        } catch (FileNotFoundException ex) {
            p.kickPlayer("Une erreur est survenue lors de la vérification des bannissement.");
            throw new RuntimeException(ex);
        }
    }
}
