package net.mceteams.event;

import net.mceteams.functions.presetTemporaryDataFunction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class playerJoinOrDeathSecure implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (presetTemporaryDataFunction.isSpectatingPlayer(playerId)) {
            PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1, true, false);
            PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 999999, 199, true, false);
            player.addPotionEffect(blind);
            player.addPotionEffect(slow);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (presetTemporaryDataFunction.isSpectatingPlayer(playerId)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCMD(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0].substring(1).toLowerCase(); // Obtenir la commande sans le caractère '/' et en minuscules
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (presetTemporaryDataFunction.isSpectatingPlayer(playerId)) {
            if (presetTemporaryDataFunction.isloggedin(playerId)) {
//              if (!command.equalsIgnoreCase("mce_smp:login") || !command.equalsIgnoreCase("mce_smp:register") || !command.equalsIgnoreCase("login") || !command.equalsIgnoreCase("register")) {
//                  event.setCancelled(true);
//                  player.sendMessage("§cVeuillez vous connecter avec /login <mot de passe> ou avec /register <mot de passe>");
//              }
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (presetTemporaryDataFunction.isSpectatingPlayer(playerId)) {
            if (presetTemporaryDataFunction.isloggedin(playerId)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerTP(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if(presetTemporaryDataFunction.isSpectatingPlayer(playerId)) {
            event.setCancelled(true);
        }
    }
}
