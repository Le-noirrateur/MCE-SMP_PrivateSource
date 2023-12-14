package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class war implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        try {
            if (!dataManagementFunction.loadData(p.getUniqueId().toString(),"/players/", "perm").equals("admin")) {
                p.sendMessage("§c§lErreur §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
                return true;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(args[0].equals("declare")) {
            p.sendMessage("§c§lErreur §8§l»§r Il nest pas encore possible de faire des déclarations de guerre.");
            p.stopSound("custom.war");
            p.playSound(p.getLocation(), "custom.war", 1f, 1f);
//            for (Player plr : Bukkit.getOnlinePlayers()) {
//                plr.playSound(plr.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1f, 1f);
//                plr.sendMessage("""
//                        §7----------------[Annonce]----------------
//                            §l
//                            §c§lUne guerre a été déclaré§r§7.
//                            §l
//                            §7La ville de "§6§lteste1§r§7" est entré en
//                            §7guerre avec la ville "§c§lteste2§r§7"
//                            §l
//                            §7faites §eattention§r§7 en sortant de chez vous.
//                            §l
//                        §7-----------------------------------------
//                        """);
//                PotionEffect ps = PotionEffectType.BLINDNESS.createEffect(1, 1);
//                plr.addPotionEffect(ps, true);
//            }
        } else if (args[0].equals("assault")) {
            p.sendMessage("§c§lErreur §8§l»§r Il nest pas encore possible de faire des assauts.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("war")) {
            if (strings.length == 1) {
                List<String> completions = new ArrayList<>();
                completions.add("declare");
                completions.add("assault");
                // Ajoutez d'autres options ici

                return completions;
            }
        }
        return Collections.emptyList();
    }
}
