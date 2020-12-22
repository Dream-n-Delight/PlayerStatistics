package org.dreamndelight.playerstatistics.plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dreamndelight.playerstatistics.lib.data.PlayerStatistic;
import org.dreamndelight.playerstatistics.lib.enums.Statistic;
import org.dreamndelight.playerstatistics.plugin.Main;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

import static org.dreamndelight.playerstatistics.plugin.util.MessageHandler.sendFormatted;

public class StatsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sendFormatted(sender, "messages.error.notaplayer");
            return true;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {
            PlayerStatistic statistic = Main.getInstance().getPlayerStatistics().getStatisticsManager().getOrFetch(player);

            player.sendMessage(ChatColor.GOLD + "======== Your Statistics ========");
            player.sendMessage(ChatColor.GOLD + "Kills: " + statistic.getStatistic(Statistic.MOB_KILLS));
            player.sendMessage(ChatColor.GOLD + "Deaths: " + statistic.getStatistic(Statistic.DEATHS));
            player.sendMessage(ChatColor.GOLD + "Blocks Broken: " + statistic.getStatistic(Statistic.BLOCKS_BROKEN));
            player.sendMessage(ChatColor.GOLD + "======== Your Statistics ========");
            return true;
        }

        final Optional<UUID> uuid = Main.getInstance().getPlayerStatistics().getUserData().getUUID(args[0]);
        if (!uuid.isPresent()) {
            sendFormatted(player, "messages.error.playernotfound");
            return true;
        }

        final String username = Main.getInstance().getPlayerStatistics().getUserData().getName(uuid.get()).orElse(args[0]);

        PlayerStatistic statistic = Main.getInstance().getPlayerStatistics().getStatisticsManager().getOrFetch(uuid.get());
        player.sendMessage(ChatColor.GOLD + "======== " + username + "'s Statistics ========");
        player.sendMessage(ChatColor.GOLD + "Kills: " + statistic.getStatistic(Statistic.MOB_KILLS));
        player.sendMessage(ChatColor.GOLD + "Deaths: " + statistic.getStatistic(Statistic.DEATHS));
        player.sendMessage(ChatColor.GOLD + "Blocks Broken: " + statistic.getStatistic(Statistic.BLOCKS_BROKEN));
        player.sendMessage(ChatColor.GOLD + "======== " + username + "'s Statistics ========");
        return true;
    }
}
