package org.dreamndelight.playerstatistics.plugin.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.dreamndelight.playerstatistics.plugin.Main;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MessageHandler {

    public static String format(@NotNull String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static Optional<String> getConfigString(String key) {
        return Optional.ofNullable(Main.getInstance().getConfig().getString(key));
    }

    public static Optional<String> formatConfigString(String key) {
        return getConfigString(key).isPresent() ? Optional.of(format(getConfigString(key).get())) : Optional.empty();
    }

    public static void sendFormatted(CommandSender sender, String key) {
        final Optional<String> message = formatConfigString(key);
        message.ifPresent(sender::sendMessage);
    }

}
