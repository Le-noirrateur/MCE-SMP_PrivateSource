package net.mceteams.event;

import net.mceteams.functions.logsRegister;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

import java.text.DecimalFormat;
import java.util.Objects;

public class logEveryImportantActions implements Listener {

    private String formatLocation(Location location) {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String world = Objects.requireNonNull(location.getWorld()).getName();

        return " XYZ: " + decimalFormat.format(x) + " " + decimalFormat.format(y) + " " +decimalFormat.format(z) + " in " + world;
    }

    @EventHandler
    public void dropitem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + " dropped item " + e.getItemDrop().getName() + " at " + formatLocation(p.getLocation()));
    }

    @EventHandler
    public void bedEvent(PlayerBedLeaveEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + " has leaved bed at " + formatLocation(p.getLocation()));
    }

    @EventHandler
    public void getdamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            logsRegister.addLogEntry(p, p.getName() + " has been damaged by " + e.getCause().name() + " at " + formatLocation(p.getLocation()));
        }
    }

    @EventHandler
    public void getgamemode(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + "'s gamemode has been changed to " + e.getNewGameMode());
    }

    @EventHandler
    public void EnterBedEvent(PlayerBedEnterEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + " has entered bed at " + formatLocation(p.getLocation()));
    }

    @EventHandler
    public void FinishAdvancement(PlayerAdvancementDoneEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + " has finished a advancement at " + formatLocation(p.getLocation()) + " advancement : " + e.getAdvancement().getDisplay());
    }

    @EventHandler
    public void cmdsend(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String command = e.getMessage().split(" ")[0].substring(1).toLowerCase();
        String[] str = e.getMessage().substring(command.length() + 1).split(" ");
        String args = String.join(" ", str).substring(command.length() + 1);
        if(args == null ) {
            args = "error";
            logsRegister.addLogEntry(p, p.getName() + " has executed /" + command + " " + args);
        }
    }

    @EventHandler
    public void kickevent(PlayerKickEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, "\n" + p.getName() + " has been kicked\nReason : " + e.getReason());
    }

    @EventHandler
    public void tpevent(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + " has been teleported from" + formatLocation(e.getFrom()) + " to " + formatLocation(Objects.requireNonNull(e.getTo())));
    }

    @EventHandler
    public void expevent(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + "'s exp has been changed to " + e.getAmount());
    }

    @EventHandler
    public void lvlevent(PlayerLevelChangeEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + "'s levels has been changed from " + e.getOldLevel()+ " to " + e.getNewLevel());
    }

    @EventHandler
    public void plritembreakevent(PlayerItemBreakEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + "'s item " + e.getBrokenItem().getTranslationKey() + " has been destroyed" );
    }

    @EventHandler
    public void re(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + " has respawned at " + formatLocation(e.getRespawnLocation()));
    }

    @EventHandler
    public void prtlevent(PlayerPortalEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + " has traveled thought a portal from " + formatLocation(e.getFrom()) + " to " + formatLocation(Objects.requireNonNull(e.getTo())));
    }

    @EventHandler
    public void egi(EntityPickupItemEvent e) {
        if (e.getEntity()  instanceof Player p) {
            logsRegister.addLogEntry(p, p.getName() + " has taken a item " + e.getItem().getName() + " at " + formatLocation(p.getLocation()));
        }
    }

    @EventHandler
    public void plblc(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + " has placed the block " + e.getBlock().getType().name() + " at " + formatLocation(e.getBlock().getLocation()));
    }

    @EventHandler
    public void plblc(BlockBreakEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + " has destroyed the block " + e.getBlock().getType().name() + " at " + formatLocation(e.getBlock().getLocation()));
    }

    @EventHandler
    public void trg(EntityTargetEvent e) {
        Entity Targeter = e.getEntity();
        Entity p = e.getTarget();
        if(p != null) {
            if (Targeter != null) {
                logsRegister.addLogEntry((Player) p, "\n" + p.getName() + " has been targeted by " + Targeter.getName() + "\nreason : " + e.getReason());
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        String entityName = entity.getType().name();
        logsRegister.addLogEntry(player, player.getName() + " has interacted with " + entityName + " at " + formatLocation(entity.getLocation()));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block != null) {
            String blockType = block.getType().name();
            logsRegister.addLogEntry(player, player.getName() + " has interacted with " + blockType + " at " + formatLocation(block.getLocation()));
        }
    }


    @EventHandler
    public void ge(EntityPotionEffectEvent e) {
        if (e.getEntity()  instanceof Player p) {
            logsRegister.addLogEntry(p, p.getName() + " got " + e.getNewEffect() + " effect.");
        }
    }

    @EventHandler
    public void chatev(PlayerChatEvent e) {
        Player p = e.getPlayer();
        logsRegister.addLogEntry(p, p.getName() + e.getMessage());
    }
}
