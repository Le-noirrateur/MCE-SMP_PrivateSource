package net.mceteams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class changelogs implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage("§c§lMCE §8§l»§r §7Cette commande peut seulement être exécutée par un joueur !");
            return true;
        }

        p.sendMessage("""
                §r§6§lChanges logs§r§7
                §l
                §r§l§20.3.0 BETA > 0.7.8 BETA §8§l»
                §r§2§lAjout§r§7 de la commande /money.
                §r§2§lAjout§r§7 de la commande /changelogs.
                §r§2§lAjout§r§7 de la commande /town.
                §r§2§lAjout§r§7 de la commande /market.
                §r§2§lAjout§r§7 de la commande /jobs.
                §r§2§lAjout§r§7 de l'enregistrement de chaque action des joueurs.
                §l
                §r§6§lModification§r§7 de la commande /sethome ( possibilité d'en mettre 5. ).
                §r§6§lModification§r§7 de la commande /tpa ( animation lors du tp. ).
                §r§6§lModification§r§7 de la commande /home ( animation lors du tp. ).
                §l
                §r§a§lFix bug§r§7 de mort ( affiche enfin les coordonnée de là ou vous êtes mort. ).
                §r§a§lFix bug§r§7 des home ( peux enfin ajouter + de 1 home. ).
                §l
                §r§6§l§k***§r§9§lPréparation§6§l§k***§r§7 du système de ville ( avec claim, guerre alliances et autre. ).
                §r§6§l§k***§r§9§lPréparation§6§l§k***§r§7 du market.
                §r§6§l§k***§r§9§lPréparation§6§l§k***§r§7 des quêtes hebdomadaires.
                §r§6§l§k***§r§9§lPréparation§6§l§k***§r§7 des grades.
                §r§6§l§k***§r§9§lPréparation§6§l§k***§r§7 de la monnaie.
                §r§6§l§k***§r§9§lPréparation§6§l§k***§r§7 du site publique lsmp.mceteams.net.
                §l
                §r Fin des logs des changements !
                """);
        return true;
    }
}
