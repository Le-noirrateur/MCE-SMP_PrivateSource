package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.mceteams.smpplugin.*;

public class admin implements CommandExecutor, TabCompleter{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        try {
            if (!dataManagementFunction.loadData(p.getUniqueId().toString(), "/players/", "perm").equals("admin")) {
                p.sendMessage("§c§lErreur §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
                return true;
            } else {

                if(strings.length == 0) {
                    p.sendMessage("§c§lErreur §8§l»§r Veuillez specifier un argument proposé par la commande.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING ,1f, 0.5f);
                    return true;
                }

                switch (strings[0]) {
                    case "add" -> {
                        if (strings.length < 0) {
                            p.sendMessage("§c§lMCE §8§l»§r Veuillez sélectionner le joueur.");
                            return true;
                        }

                        Player targetPlayer = Bukkit.getPlayer(strings[1]);
                        if (targetPlayer == null || !targetPlayer.isOnline()) {
                            p.sendMessage("§c§lMCE §8§l»§r§7 Le joueur §6§l" + strings[1] + "§r§c§l n'existe pas§r§7 ou §c§ln'est pas connecté§r§7 !");
                            return true;
                        }

                        if (strings[2].equals("admin")) {
                            if (dataManagementFunction.loadData(targetPlayer.getUniqueId().toString(), "/players/", "perm").equals("admin")) {
                                p.sendMessage("§c§lMCE §8§l»§r§7 Le joueur §6§l" + targetPlayer.getName() + "§r§7 à déjà les permissions admin.");
                                return true;
                            }
                            targetPlayer.sendMessage("§c§lMCE §8§l»§r§7 Vous avez reçu les permissions §4§ladministrateur§r§7.");
                            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);
                            p.sendMessage("§c§lMCE §8§l»§r§7 Vous avez §2§lajouté§r§7 les permissions §4§ladministrateur§r§7 à §6§l" + targetPlayer.getName() + "§r§7 !");
                            dataManagementFunction.addData(targetPlayer.getUniqueId().toString(), "/players/", "perm", "admin");
                        } else if (strings[2].equals("moderator")) {
                            if (dataManagementFunction.loadData(targetPlayer.getUniqueId().toString(), "/players/", "perm").equals("admin") || dataManagementFunction.loadData(targetPlayer.getUniqueId().toString(), "/players/", "perm").equals("administrationSystemFunction")) {
                                p.sendMessage("§c§lMCE §8§l»§r§7 Le joueur §6§l" + targetPlayer.getName() + "§r§7 à déjà des permissions.");
                                return true;
                            }
                            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);
                            targetPlayer.sendMessage("§c§lMCE §8§l»§r§7 Vous avez reçu les permissions §9§lmodérateur§r§7.");
                            p.sendMessage("§c§lMCE §8§l»§r§7 Vous avez §2§lajouté§r§7 les permissions §9§lmodérateur§r§7 à §6§l" + targetPlayer.getName() + "§r§7 !");
                            dataManagementFunction.addData(targetPlayer.getUniqueId().toString(), "/players/", "perm", "administrationSystemFunction");
                        } else {
                            p.sendMessage("§c§lMCE §8§l»§r§7 Veuillez specifier une permission.");
                        }
                    }


                    case "remove" -> {
                        Player targetPlayer = Bukkit.getPlayer(strings[1]);
                        if (targetPlayer == null || !targetPlayer.isOnline()) {
                            p.sendMessage("§c§lMCE §8§l»§r§7 Le joueur §6§l" + strings[1] + "§r§c§l n'existe pas§r§7 ou §c§ln'est pas connecté§r§7 !");
                            return true;
                        }

                        p.sendMessage("§c§lMCE §8§l»§r§7 Vous avez retiré les permissions à §6§l" + targetPlayer.getName() + "§r§7 !");
                        targetPlayer.sendMessage("§c§lMCE §8§l»§r§7 Vous avez perdu vos permissions.");
                        dataManagementFunction.addData(targetPlayer.getUniqueId().toString(), "/players/", "perm", "none");
                    }
                    case "getuuid" -> {
                        Player targetPlayer = Bukkit.getPlayer(strings[1]);
                        if (targetPlayer == null || !targetPlayer.isOnline()) {
                            p.sendMessage("§c§lMCE §8§l»§r§7 Le joueur §6§l" + strings[1] + "§r§c§l n'existe pas§r§7 ou §c§ln'est pas connecté§r§7 !");
                            return true;
                        }

                        p.sendMessage("Voici l'UUID de §6§l" + targetPlayer.getName() + "§r :\n§c" + targetPlayer.getUniqueId());
                    }

                    case "version" -> {
                        p.sendMessage("Version bukkit : \n§6§l" + Bukkit.getVersion()+ "§r\n\nVersion serveur :\n§6§l" + Bukkit.getServer().getVersion() + "§r\n\n" + getPlugin().getName() + " :\n§6§l" + getPlugin().getDescription().getVersion() + "§r");
                    }

                    case "claimbypass" -> {
                        if (dataManagementFunction.loadData(p.getUniqueId().toString(), "/players/", "claimbypass").equals("false")) {
                            dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "claimbypass", "true");
                            p.sendMessage("§c§lMCE §8§l»§r§7 Vous passez outre les claims !");
                        } else {
                            dataManagementFunction.addData(p.getUniqueId().toString(), "/players/", "claimbypass", "false");
                            p.sendMessage("§c§lMCE §8§l»§r§7 Vous ne passez plus outre les claims !");
                        }
                    }
                }
                return true;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> onTabComplete (CommandSender commandSender, Command command, String s, String[]strings){
        if (command.getName().equalsIgnoreCase("admin")) {
            if (strings.length == 1) {
                List<String> completions = new ArrayList<>();
                completions.add("add");
                completions.add("remove");
                completions.add("getuuid");
                completions.add("version");
                completions.add("claimbypass");
                // Ajoutez d'autres options ici

                return completions;
            }

            if (strings.length == 2 ) {
                if (strings[0].equals("add") || strings[0].equals("remove") || strings[0].equals("getuuid")) {
                    List<String> completions = new ArrayList<>();
                    for (Player plr : Bukkit.getOnlinePlayers()) {
                        completions.add(plr.getName());
                    }
                    return completions;
                }
            }

            if (strings.length == 3 && strings[0].equals("add")) {
                List<String> completions = new ArrayList<>();
                completions.add("admin");
                completions.add("moderator");

                return completions;
            }
        }
        return Collections.emptyList();
    }
}

