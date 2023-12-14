package net.mceteams.functions;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MessageSpamChecker {
    private Map<Player, Long> lastMessageTimes;
    private Map<Player, Integer> messageCounts;

    public MessageSpamChecker() {
        lastMessageTimes = new HashMap<>();
        messageCounts = new HashMap<>();
    }

    public boolean isSpamming(Player player, String message) {
        long currentTime = System.currentTimeMillis();
        int maxMessageCount = 20; // Maximum de messages autorisés en une période donnée (à ajuster selon vos besoins)
        long timeThreshold = 10000; // Période de temps en millisecondes pendant laquelle les messages sont vérifiés (à ajuster selon vos besoins)

        if (lastMessageTimes.containsKey(player)) {
            long lastMessageTime = lastMessageTimes.get(player);
            int count = messageCounts.getOrDefault(player, 0);

            if (currentTime - lastMessageTime <= timeThreshold) {
                count++;

                if (count >= maxMessageCount) {
                    return true; // Spam détecté
                }

                messageCounts.put(player, count);
            } else {
                messageCounts.put(player, 1);
            }
        } else {
            messageCounts.put(player, 1);
        }

        lastMessageTimes.put(player, currentTime);
        return false; // Pas de spam détecté
    }
}
