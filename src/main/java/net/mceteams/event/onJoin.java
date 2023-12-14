package net.mceteams.event;

import net.mceteams.functions.dataManagementFunction;
import net.mceteams.functions.logsRegister;
import net.mceteams.functions.presetTemporaryDataFunction;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.FileNotFoundException;
import java.util.UUID;

import static net.mceteams.smpplugin.getPlugin;
import static org.bukkit.Bukkit.getServer;

public class onJoin implements Listener {

    @EventHandler

    public void onjoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        logsRegister.createLog(p);
        logsRegister.addLogEntry(p,"has joined the game.");
        if(getServer().hasWhitelist()) {
            if(!p.isWhitelisted()) {
                logsRegister.addLogEntry(p," has been kicked ( not white listed ).");
                p.kickPlayer("§c§lMCE §8§l»§r §7Vous avez été §4§lExclus§r§7 !\n\n§cRaison §8» §7Vous §c§ln'êtes pas §r§7dans la §6§lliste blanche§r§7 !\n\n§l§6Le serveur est en maintenance§r...");
            }
        }

        try {
            if(dataManagementFunction.loadData(p.getUniqueId().toString(), "/bans/", "status").equals("true")) {
                p.kickPlayer("§7§l---------------------------------- §4§ll'SMP §7§l---------- §6§lPunition §7§l----------------------------------§r\n§4§lVous avez été BANNI de ce serveur !§r\n\n§7Raison : §e§l" + dataManagementFunction.loadData(p.getUniqueId().toString(), "/bans/", "reason")  + "§r\n§7Temps restant : §4§l Bannissement PERMANENT.§r\n\n§9Pour §b§lrevenir sur la désision finale de ce bannissement§r§9 car vous ne la trouvez pas valable !§r\n§9Merci de passer sur §6§ltickets.mceteams.net/lsmp/appeals.§r\n\n§7-----------------------------------------------------------------------------------------------------------§r");
            }
        } catch (FileNotFoundException ex) {
            p.kickPlayer("Une erreur est survenue lors de la vérification des bannissement.");
            throw new RuntimeException(ex);
        }

        try { // Préparation des dataManagementFunction joueur.
            if (!dataManagementFunction.doesDataExist(p.getUniqueId().toString(), "/players/", "created")) {
                dataManagementFunction.saveData(p.getUniqueId().toString(), "/players/", "created",getPlugin().getDescription().getVersion());
                dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "playername", p.getName());
                dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "money", "0");
                dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "perm", "none");
                dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "password", "");
                dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "registered", "false");
                p.sendMessage("Bienvenu(e) sur le serveur,\ncomme c'est la §2§lpremière fois§r que vous vous connectez au serveur\n\nEntrez §6§l/register <mot de passe>§r afin de pouvoir commencer à jouer.");
                p.sendMessage("Comme c'est votre première connexion sur le serveur !\nVeuillez prêter attention au règlement. rules.lsmp.mceteams.net");
            }

            if (dataManagementFunction.doesDataExist(p.getUniqueId().toString(), "/players/", "created")) {
                if (!dataManagementFunction.loadData(p.getUniqueId().toString(), "/players/", "created").equals(getPlugin().getDescription().getVersion())) {
                    dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "created", getPlugin().getDescription().getVersion());
                    dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "chunklocation", "");
                    dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "playername", p.getName());
                    dataManagementFunction.addData(p.getUniqueId().toString(), "/players/","claimbypass", "false");
                }

                if (dataManagementFunction.loadData(p.getUniqueId().toString(), "/players/", "registered").equals("true")) {
                    p.sendMessage("§fBienvenu(e) sur le serveur, faite §6§l/login <votre mot de passe>§r afin de rejoindre le serveur.");
                }
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }



        // Applique-les paramètre de join par défaut.
        UUID playerId = p.getUniqueId();
        presetTemporaryDataFunction.addSpectatingPlayer(playerId);
        presetTemporaryDataFunction.addlogin(playerId);
        p.setCanPickupItems(false);
        p.setGameMode(GameMode.SPECTATOR);
        PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS,  999999, 1, true,false);
        PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 999999, 199, true, false);
        p.addPotionEffect(blind);
        p.addPotionEffect(slow);
        p.setInvulnerable(true);
        p.setInvisible(true);
        e.setJoinMessage("");
    }

}
