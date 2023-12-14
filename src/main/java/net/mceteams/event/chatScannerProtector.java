package net.mceteams.event;

import net.mceteams.functions.MessageSpamChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatScannerProtector implements Listener {
    MessageSpamChecker spamChecker = new MessageSpamChecker();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (spamChecker.isSpamming(player, message)) {
            event.setCancelled(true); // Annuler le message du joueur s'il envoie du spam.
            player.sendMessage("§cVous envoyez des messages trop rapidement. Veuillez ralentir.");
        }

        if (message.contains(".fr") || message.contains(".com") || message.contains("www.") || message.contains(".net") || message.contains(".xyz") || message.contains(".eu") || message.contains(".shop") || message.contains(".it") || message.contains(".es") || message.contains(".gg")) {
            // Le message contient l'une des sous-chaînes spécifiées, vous pouvez faire ce que vous voulez ici
            // Par exemple, annuler le message du joueur
            event.setCancelled(true);

            // Envoyer un message au joueur pour l'informer que le message a été annulé
            player.sendMessage("§4§lHey§r,§7 votre message a été bloqué !§r\n\n§7Veuillez ne pas faire la promotion de sites ou autre.§r\n§7Si ce n'est pas le cas votre message a été bloqué car il§r\n§7possède un élément automatiquement bloqué par le serveur...§r\n\n§7Nous vous recommandons de relire le règlement.§r\n§6§lrules.lsmp.mceteams.net§r ...");
        }
    }

    @EventHandler
    public void antiswearing(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        if (msg.equalsIgnoreCase("L") || msg.equalsIgnoreCase("ez") || msg.contains(" ez ") || msg.contains(" L ")) {
            e.setCancelled(true);
            p.sendMessage("§4§lHey§r,§7 votre message a été bloqué !§r\n\n§7Veuillez respecter les autres.§r\n§7Il n'est pas respectueux pour les autres de dire §6§l" + msg + "§r§7 ici !§r\n§7Nous tenons à ce que le serveur reste§r\n§7accessible à tout le monde.§r\n\n§7Nous vous recommandons de relire le règlement.§r\n§6§lrules.lsmp.mceteams.net§r ...");
        }
    }
}
