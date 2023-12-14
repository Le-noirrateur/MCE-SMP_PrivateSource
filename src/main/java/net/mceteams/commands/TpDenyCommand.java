package net.mceteams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

public class TpDenyCommand implements CommandExecutor {

    private final Plugin plugin;
    private final Map<UUID, UUID> tpaRequests;

    public TpDenyCommand(Plugin plugin, Map<UUID, UUID> tpaRequests) {
        this.plugin = plugin;
        this.tpaRequests = tpaRequests;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        // Vérifier si le joueur a une demande de téléportation en attente
        if (tpaRequests.containsKey(player.getUniqueId())) {

            // Récupérer l'UUID de l'émetteur de la demande
            UUID requesterUUID = tpaRequests.get(player.getUniqueId());

            // Supprimer la demande de téléportation de la map
            tpaRequests.remove(player.getUniqueId());

            player.sendMessage("§c§lMCE §8§l»§r§7 Vous avez §4§lrefusé§r§7 la demande de téléportation.");

            // Informer l'émetteur de la demande
            Player requester = plugin.getServer().getPlayer(requesterUUID);
            if (requester != null) {
                requester.sendMessage("§c§lMCE §8§l»§r§7 Le joueur §6§l" + player.getName() + "§r§7 a §4§lrefusé§r§7 votre demande de téléportation.");
            }
        } else {
            player.sendMessage("§c§lMCE §8§l»§r§7 Vous n'avez pas de demande de téléportation en attente.");
        }

        return true;
    }
}
