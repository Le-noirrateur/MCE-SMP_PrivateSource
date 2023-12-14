package net.mceteams.functions;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class administrationSystemFunction {
    public static boolean checkperm(Player player, String permission) throws FileNotFoundException {
        return chpm(player, permission);
    }

    public static boolean bypassclaim(Player player) throws FileNotFoundException {
        if(dataManagementFunction.loadData(player.getUniqueId().toString(), "/players/","claimbypass").equals("true")) {
            return true;
        }
        return false;
    }

    private static boolean chpm(Player plr, String perm) throws FileNotFoundException {
        if (perm.equals("moderator") || perm.equals("modérateur") || perm.equals("moderators") || perm.equals("moderateurs") || perm.equals("administrationSystemFunction") || perm.equals("mods")) {
            if (dataManagementFunction.loadData(plr.getUniqueId().toString(), "/players/", "perm").equals("administrationSystemFunction")) {
                return true;
            }
        }

        if (perm.equals("admin") || perm.equals("admins") || perm.equals("administrators") || perm.equals("administrator") || perm.equals("administrateurs") || perm.equals("administrateur")) {
            if (dataManagementFunction.loadData(plr.getUniqueId().toString(), "/players/", "perm").equals("admin")) {
                return true;
            }
        }

        return false;
    }

    public static void warn(String message, Boolean activated_sound, Player target) throws FileNotFoundException {
        for (Player plrs : Bukkit.getOnlinePlayers()) {
            if(chpm(plrs,"administrationSystemFunction") || chpm(plrs,"admin")) {
                plrs.sendMessage("§C§lAlerte§r §8§l»§r §6§l" + target.getName() + " ( " + target.getUniqueId() + "§r ) " + message);
                if(activated_sound) {
                    plrs.playSound(plrs.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                }
            }
        }
    }
}
