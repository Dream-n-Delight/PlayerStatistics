package org.dreamndelight.playerstatistics.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.dreamndelight.playerstatistics.lib.main.PlayerStatistics;
import org.dreamndelight.playerstatistics.plugin.commands.StatsCommand;

import java.util.Objects;
import java.util.logging.Level;

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
        if (!setupStatistics()) {
            getLogger().log(Level.SEVERE, "PlayerStatisticsLib could not be accessed via the provider.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        setupCommands();

    }


    public PlayerStatistics getPlayerStatistics() {
        return this.playerStatistics;
    }

    private boolean setupStatistics() {
        if (playerStatistics == null) {
            RegisteredServiceProvider<PlayerStatistics> provider = Bukkit.getServer().getServicesManager().getRegistration(PlayerStatistics.class);
            if (provider != null) {
                this.playerStatistics = provider.getProvider();
                return true;
            }
            return false;
        }
        return false;
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
