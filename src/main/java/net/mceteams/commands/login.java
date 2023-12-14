package net.mceteams.commands;

import net.mceteams.functions.dataManagementFunction;
import net.mceteams.functions.presetTemporaryDataFunction;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.io.FileNotFoundException;
import java.util.UUID;

public class login implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        if (presetTemporaryDataFunction.isSpectatingPlayer(p.getUniqueId())) {
            if (presetTemporaryDataFunction.isloggedin(p.getUniqueId())) {
                try {
                    if (dataManagementFunction.loadData(p.getUniqueId().toString(), "/players/", "registered") == "false") {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                        p.sendMessage("§c§lErreur §8§l»§r Vous n'avez pas enregistré un mot de passe.");
                        return true;
                    }

                    if (strings.length == 0) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                        p.sendMessage("§c§lErreur §8§l»§r Vous n'avez pas spécifié un mot de passe.");
                        return true;
                    }


                    if (dataManagementFunction.loadData(p.getUniqueId().toString(), "/players/", "password").equals(strings[0])) {
                        p.sendMessage("""
                                        
                                §8§m+--------------***--------------+§r
                                                
                                §7Bienvenu(e), §b§n""" + p.getDisplayName() + """
                                §r§7 sur le serveur.
                                           
                                           
                                    §9§lDiscord §r§fdiscord.gg/9UU4GdqKmb
                                    §6§lPatreon §r§fpatreon.com/MCE_SMPPublic
                                          
                                    Faite §6§l/changelog §r§7pour obtenir
                                    les dernières mises à jours.
                                          
                                §8§m+--------------***--------------+§r
                                """);

                        UUID playerId = p.getUniqueId();
                        presetTemporaryDataFunction.removelogin(playerId);
                        presetTemporaryDataFunction.removeSpectatingPlayer(playerId);
                        p.setCanPickupItems(true);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.setFlying(false);
                        p.setWalkSpeed(0.2f);
                        p.setInvulnerable(false);
                        p.setInvisible(false);
                        p.setAllowFlight(false);
                        p.removePotionEffect(PotionEffectType.BLINDNESS);
                        p.removePotionEffect(PotionEffectType.JUMP);
                        p.removePotionEffect(PotionEffectType.SLOW);
                        Bukkit.broadcastMessage("§2§l+§r §6" + p.getDisplayName() + "§7 s'est connecté.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                        return true;
                    } else {
                        p.sendMessage("§fMot de passe §4§lincorrecte§r.");
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return true;
            } else {
                p.sendMessage("Vous êtes §2§ldéjà connecté§r.");
            }
        } else {
            p.sendMessage("Vous êtes §2§ldéjà connecté§r.");
        }
        return true;
    }
}
