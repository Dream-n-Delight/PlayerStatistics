package org.dreamndelight.playerstatistics.plugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.dreamndelight.playerstatistics.main.PlayerStatistics;
import org.dreamndelight.playerstatistics.plugin.commands.StatsCommand;

import java.util.Objects;

public class Main extends JavaPlugin {

    private static Main instance;
    private PlayerStatistics playerStatistics;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        saveDefaultConfig();
        setupStatistics();

        setupCommands();

    }


    public PlayerStatistics getPlayerStatistics() {
        return this.playerStatistics;
    }

    private void setupStatistics() {
        if (playerStatistics == null) playerStatistics = new PlayerStatistics();
    }


    private void registerCommand(String command, CommandExecutor commandExecutor, TabExecutor tabExecutor, String... aliases) {
        Objects.requireNonNull(getCommand(command)).setExecutor(commandExecutor);
        if (tabExecutor != null) Objects.requireNonNull(getCommand(command)).setTabCompleter(tabExecutor);
        for (String alias : aliases) {
            Objects.requireNonNull(getCommand(alias)).setExecutor(commandExecutor);
            if (tabExecutor != null) Objects.requireNonNull(getCommand(alias)).setTabCompleter(tabExecutor);
        }
    }

    private void setupCommands() {
        registerCommand("stats", new StatsCommand(), null);
    }

}
