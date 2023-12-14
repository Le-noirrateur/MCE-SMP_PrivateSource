package net.mceteams.commands;


import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class tpa implements CommandExecutor {

    private final Plugin plugin; // Instance de votre plugin
    private final Map<UUID, UUID> tpaRequests = new HashMap<>();

    public tpa(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        // Vérifier les arguments et les conditions pour la commande /tpa
        if (args.length == 1) {
            String targetName = args[0]; // Nom du joueur cible
            // Vérifier si le joueur cible existe et est en ligne
            Player targetPlayer = player.getServer().getPlayer(targetName);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                player.sendMessage("§7Le joueur cible n'existe pas ou n'est pas en ligne.");
                return true;
            }

            // Stocker la demande de téléportation dans la map
            tpaRequests.put(targetPlayer.getUniqueId(), player.getUniqueId());

            player.sendMessage("§7Une demande de téléportation a été envoyée à §6§l" + targetPlayer.getName() + "§r§7.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
            targetPlayer.sendMessage("Vous avez reçu une demande téléportation de §6§l" + player.getName() + "§r\nFaite : \n§2§l/tpaccept§r pour §2§laccepter§r la demande de tp§r.\n§4§l/deny§r pour §4§lrefuser§r la demande de tp§r.");
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f);

            // Démarrer un délai de 30 secondes pour la réponse
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (tpaRequests.containsKey(targetPlayer.getUniqueId())) {
                    // Le délai est écoulé et la demande de téléportation n'a pas été répondue
                    tpaRequests.remove(targetPlayer.getUniqueId());
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.1f);
                    player.sendMessage("§7La demande de téléportation à §6§l" + targetPlayer.getName() + "§r§7 a §c§lexpiré§r§7.");
                    targetPlayer.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                    targetPlayer.sendMessage("§7La demande de téléportation de §6§l" + player.getName() + "§r§7 a §c§lexpiré§r§7.");
                }
            }, 20 * 10); // 30 secondes (20 ticks par seconde)
        } else {
            player.sendMessage("§7Veuillez §c§lindiquer§r§7 le nom d'un joueur !");
        }

        return true;
    }

    public Map<UUID, UUID> getTpaRequests() {
        return tpaRequests;
    }
}




