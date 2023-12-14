package net.mceteams.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class banevent implements Listener{



    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_BANNED) {
            String banMessage = event.getKickMessage();
            String[] parts = banMessage.split(":");

            Player p = Bukkit.getPlayer(event.getPlayer().getUniqueId());
            if (parts.length >= 3) {
                String reason = parts[1];
                String duration = parts[2];

                // Supprimer la partie de la date
                Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})"); // Pattern de la date (AAAA-MM-JJ)
                Matcher matcher = pattern.matcher(duration);
                if (matcher.find()) {
                    duration = matcher.group(1);
                }

                event.setKickMessage("§c§lMCE §8§l»§r §7Vous avez été §4§lTEMPORAIREMENT BANNI§r§7 !\n\n§cRaison §8» §7" + reason + ".\n\n§cDurée §8» §7" + duration + ".");
            } else {
                // Message de bannissement invalide
                String reason = parts[1];
                event.setKickMessage("§c§lMCE §8§l»§r §7Vous avez été §4§lBANNI PERMANENT§r§7 !\n\n§cRaison §8» §7" + reason);
            }
        }
    }
}
