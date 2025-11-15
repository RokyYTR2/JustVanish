package dev.meyba.justVanish;

import dev.meyba.justVanish.commands.Commands;
import dev.meyba.justVanish.listeners.PlayerListener;
import dev.meyba.justVanish.managers.VanishManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class JustVanish extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        VanishManager vanishManager = new VanishManager();

        getCommand("vanish").setExecutor(new Commands(vanishManager, this));

        getServer().getPluginManager().registerEvents(new PlayerListener(vanishManager), this);

        getLogger().info("JustVanish has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("JustVanish has been disabled!");
    }
}