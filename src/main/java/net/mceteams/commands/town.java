package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import net.mceteams.functions.logsRegister;
import net.mceteams.functions.townManagementFunction;
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
import java.util.Objects;

public class town implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        if (!p.getName().equals("Thenoirrateur")) {
            if (!p.getName().equals("ABIZU_fluffy1")) {
                p.sendMessage("§c§lErreur §8§l»§r §7Vous §c§ln'avez pas la permission§r§7 d'utiliser cette commande !");
                return true;
            }
        }

        if(args.length == 0) {
            p.sendMessage("§c§lErreur §8§l»§r Veuillez specifier un argument proposé par la commande.");
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING ,1f, 0.5f);
            return true;
        }

        switch (args[0]) {
            case "create" -> {
                if (args.length == 1) {
                    logsRegister.addLogEntry(p, p.getName() + " didn't specified city name");
                    p.sendMessage("§c§lErreur §8§l»§r Veuillez spécifier §6§lun nom§r pour votre ville");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                    return true;
                }

                if (!dataManagementFunction.doesDataExist(args[1],"/towns/","owner")) {
                    if (dataManagementFunction.searchData("/towns/", "owner", p.getUniqueId().toString()) == null) {
                        dataManagementFunction.saveData(args[1], "/towns/", "owner", p.getUniqueId().toString()); // Création de la ville.
                        dataManagementFunction.addData(args[1], "/towns/", "money", "0"); // définition de l'argent.
                        dataManagementFunction.addData(args[1], "/towns/", "completed-quest", "0"); // définition de stats
                        dataManagementFunction.addData(args[1], "/towns/", "directory", "/towns-dataManagementFunction/" + args[1]); // Emplacement des données
                        dataManagementFunction.addData(args[1], "/towns/","entermsg","Welcome at " + args[1] + ".");
                        dataManagementFunction.addData(args[1], "/towns/", "leavemsg", "See you soon at " + args[1] + ".");
                        p.sendMessage("§6§lFélicitation§r votre ville §6§l" + args[1] + "§r §2§la été§r crée !");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                    } else {
                        p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas re-créer une ville car vous êtes déjà dans une ville.");
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1f, 1f);
                    }
                } else {
                    p.sendMessage("§c§lErreur §8§l»§r Le nom de cette ville existe déjà.");
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1f, 1f);
                }
                return true;
            }

            case "dismount" -> {
                try {
                    if (townManagementFunction.doesOwner(townManagementFunction.getTown(p,"owner"),p)) {
                        p.sendMessage("§c§lErreur §8§l»§r Vous n'êtes pas le propriétaire ou vous n'avez pas de ville.");
                    } else {

                        String texte = Objects.requireNonNull(dataManagementFunction.searchData("/towns/", "owner", p.getUniqueId().toString())).toString();
                        String townname = texte.replace("[", "").replace("]", "");
                        dataManagementFunction.deleteData(townname, "/towns/", "file");
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1f, 0.5f);
                        p.sendMessage(" Votre ville a été dissout, à bientôt.");
                        Bukkit.broadcastMessage("La ville §6§l" + townname + "§r a été§c§l dissout§r§7 !");
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            case "invite" -> {
                    if (!townManagementFunction.getTown(p, "owner").equals("null")) {
                        if (args.length < 2 || args[1] == null) {
                            p.sendMessage("§c§lErreur §8§l»§r Veuillez spécifier un joueur.");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                            break;
                        }

                        Player target = Bukkit.getPlayer(args[1]);

                        if (target != null) {
                            String texte = Objects.requireNonNull(dataManagementFunction.searchData("/towns/", "owner", p.getUniqueId().toString())).toString();
                            String townname = texte.replace("[", "").replace("]", "");
                            dataManagementFunction.addData(target.getUniqueId().toString(),"/towns/invites/", "invite_" + townname, "invited");
                            dataManagementFunction.deleteData(target.getUniqueId().toString(), "/towns/invites/","");
                            p.sendMessage("§7Vous avez invité §6§l" + target.getName() + "§r dans votre ville !");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f);
                            target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f);
                            target.sendMessage("§7Vous avez été invité a rejoindre §6§l" + townname + "§r.\n§7Faite §2§l/town join§r §6§l" + townname + "§r.\nFaite §4§l/town deny§r §6§l" + townname);
                        } else {
                            p.sendMessage("Une erreur est survenue.");
                        }
                    } else {
                        p.sendMessage("§c§lErreur §8§l»§r §7Vous §c§ln'avez pas la permission§r§7  d'inviter un joueur dans la town.");
                    }
            }

            case "join" -> {
                try {
                    if(townManagementFunction.doesOwner(args[1], p)) {
                        p.sendMessage("§c§lErreur §8§l»§r Vous §4§lne pouvez pas§r rejoindre votre ville.");
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                    }

                    if(townManagementFunction.doesMember(townManagementFunction.getTown(p,"member"),p)) {
                        p.sendMessage("§c§lErreur §8§l»§r Vous §6§lêtes déjà§r dans cette ville.");
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if (args.length == 1) {
                    logsRegister.addLogEntry(p, p.getName() + " didn't specified city name for invitation.");
                    p.sendMessage("§c§lErreur §8§l»§r Veuillez spécifier §6§lun nom§r pour votre ville");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                    return true;
                }

                try {
                    if(!dataManagementFunction.doesDataExist(p.getUniqueId().toString(), "/towns/invites", "invite_" + args[1])) {
                        logsRegister.addLogEntry(p, p.getName() + " is not invited to " + args[1] + " or the city doesn't exist.");
                        p.sendMessage("§c§lErreur §8§l»§r Vous n'êtes pas invité dans la ville §6§l" + args[1] + "§r ou cette ville n'existe pas.");
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                        return true;
                    }

                    String towndirectory = dataManagementFunction.loadData(args[1],"/towns/", "directory");

                    dataManagementFunction.saveData("member_" + p.getUniqueId(), towndirectory,"role", "member");
                    dataManagementFunction.addData("member_" + p.getUniqueId(), towndirectory, "member-id", p.getUniqueId().toString());
                    dataManagementFunction.addData("member_" + p.getUniqueId(), towndirectory, "town", args[1]);
                    dataManagementFunction.saveData("settings_" + p.getUniqueId(), towndirectory, "claim", "deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "invite","deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "build", "deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "destroy", "deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "use", "deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "tpa", "deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "perms", "deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "home", "deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "fhome", "deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "fsethome", "deny");
                    dataManagementFunction.addData("settings_" + p.getUniqueId(), towndirectory, "killplrs", "deny");

                    p.sendMessage("Vous avez rejoins la ville §6§l" + args[1] + "§r.");
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            case "set" -> {
                if(args[2].equals("enter-message")) {

                }

                if(args[2].equals("leave-message")) {

                }
            }

            case "leave" -> {

                if(!townManagementFunction.getTown(p,"owner").equals("null")) {
                    p.sendMessage("Vous devez §4§ldismount§r votre ville ou §6§lrendre quelqu'un d'autre propriétaire§r !");
                }

                if(townManagementFunction.getTown(p,"member").equals("null")) {
                    p.sendMessage("Vous §4§ln'êtes pas§r dans une ville.");
                } else {
                    dataManagementFunction.deleteData("member_" + p.getUniqueId(), "/towns-data/" +townManagementFunction.getTown(p, "member"), "file");
                    dataManagementFunction.deleteData("settings_" + p.getUniqueId(), "/towns-data/" +townManagementFunction.getTown(p, "member"), "file");
                    p.sendMessage("Vous avez §c§lquitté§r la ville §6§l" + townManagementFunction.getTown(p, "member") + "§r.");
                }
            }

            case "perms" -> {
                try {
                    if (townManagementFunction.doesOwner(townManagementFunction.getTown(p,"owner"), p)) {
                        if (args.length == 0) {
                            p.sendMessage("§c§lErreur §8§l»§r Veuillez spécifier un joueur.");
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                            break;
                        }

                        Player target = Bukkit.getPlayer(args[1]);

                        if (target == null) {
                            p.sendMessage("§c§lErreur §8§l»§r le joueur §6§l" + args[1] + " §rn'existe pas ou n'est pas connecté.");
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                            break;
                        }

                        if(target.getUniqueId() == p.getUniqueId()) {
                            p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas vous donner des permissions à vous même.");
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                            break;
                        }

                        if(dataManagementFunction.searchData("/towns/", "owner", target.getUniqueId().toString()) != null) {
                            p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas éditer les permissions du créateur de cette ville.");
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                            break;
                        }

                        try {

                            String text = dataManagementFunction.searchData("/towns/","owner",p.getUniqueId().toString());
                            String townname = text.replace("[", "").replace("]", "");

                            String ownerfac = dataManagementFunction.searchData("/towns/","owner", p.getUniqueId().toString());
                            String targetmemberloc = dataManagementFunction.loadData(ownerfac, "/towns/","directory");

                            if (dataManagementFunction.doesDataExist("member_" + target.getUniqueId(),"/towns-dataManagementFunction/" + townname, "")) {
                                p.sendMessage("§c§lErreur §8§l»§r Une erreur inconnue est survenue.");
                                p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                                break;
                            }

                            if (args.length == 2) {
                                p.sendMessage("§c§lErreur §8§l»§r Veuillez spécifier un paramètre.");
                                p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                                break;
                            }

                            if (args[2].equals("claim")) {
                                if (dataManagementFunction.loadData("settings_" + target.getUniqueId(), targetmemberloc, "claim") == null) {
                                    dataManagementFunction.saveData("settings_" + target.getUniqueId(), targetmemberloc, "claim", "deny");
                                }

                                if (args.length == 3) {
                                    p.sendMessage("§c§lErreur §8§l»§r Veuillez spécifier 'allow' ou 'deny'.");
                                    p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                                    break;
                                }

                                if (args[3].equals("allow")) {
                                    if (dataManagementFunction.loadData("settings_" + target.getUniqueId(), targetmemberloc, "claim").equals("deny")) {
                                        dataManagementFunction.addData("settings_" + target.getUniqueId(), targetmemberloc, "claim", "allow");
                                        p.sendMessage("Vous avez autorisé à §6§l" + target.getDisplayName() + "§r de claim.");
                                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);
                                    } else {
                                        p.sendMessage("§6§l" + target.getDisplayName() + "§r peux déjà claim.");
                                        p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                                    }
                                } else if (args[3].equals("deny")) {
                                    if (dataManagementFunction.loadData("settings_" + target.getUniqueId(), targetmemberloc, "claim").equals("allow")) {
                                        dataManagementFunction.addData("settings_" + target.getUniqueId(), targetmemberloc, "claim", "deny");
                                        p.sendMessage("Vous avez interdit à §6§l" + target.getDisplayName() + "§r de claim.");
                                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);
                                    } else {
                                        p.sendMessage("§6§l" + target.getDisplayName() + "§r ne peux déjà pas claim.");
                                        p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                                    }
                                } else {
                                    p.sendMessage("§c§lErreur §8§l»§r Veuillez spécifier '§2§lallow§r' ou '§4§ldeny§r'.");
                                    p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.5f);
                                }
                                return true;
                            }
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("town")) {
            if (strings.length == 1) {
                List<String> completions = new ArrayList<>();
                completions.add("create");
                completions.add("dismount");
                completions.add("perms");
                completions.add("invite");
                completions.add("join");
                completions.add("set");
                completions.add("leave");

                // Ajoutez d'autres options ici
                return completions;
            }

            if(strings.length == 2){
                List<String> completions = new ArrayList<>();
                for (Player plr : Bukkit.getOnlinePlayers()) {
                    completions.add(plr.getName());
                }
                return completions;
            }

            if(strings[0].equals("set")) {
                List<String> completions = new ArrayList<>();
                completions.add("leave-message");
                completions.add("enter-message");
            }

            if(strings[0].equals("perms")) {
                if(strings.length == 3) {
                    List<String> completions = new ArrayList<>();
                    completions.add("claim");

                    return completions;
                }
            }

            if(strings[2].equals("claim")) {
                List<String> completions = new ArrayList<>();
                completions.add("allow");
                completions.add("deny");

                return completions;
            }
        }
        return Collections.emptyList();
    }
}
