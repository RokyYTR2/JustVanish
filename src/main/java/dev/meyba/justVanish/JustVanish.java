package dev.meyba.justVanish;

import dev.meyba.justVanish.commands.Commands;
import dev.meyba.justVanish.listeners.PlayerListener;
import dev.meyba.justVanish.managers.VanishManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class JustVanish extends JavaPlugin {
    private VanishManager vanishManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.vanishManager = new VanishManager();

        this.getCommand("vanish").setExecutor(new Commands(this.vanishManager, this));

        this.getServer().getPluginManager().registerEvents(new PlayerListener(this.vanishManager), this);

        getLogger().info("JustVanish has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("JustVanish has been disabled!");
    }
}