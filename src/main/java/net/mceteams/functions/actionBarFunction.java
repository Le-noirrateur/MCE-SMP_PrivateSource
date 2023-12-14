package net.mceteams.functions;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class actionBarFunction {
    public static void sendActionBarMessage(Player player, String message) {
        TextComponent textComponent = new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
    }
}
