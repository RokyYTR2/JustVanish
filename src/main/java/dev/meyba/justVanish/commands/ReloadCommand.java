package dev.meyba.justVanish.commands;

import dev.meyba.justVanish.JustVanish;
import dev.meyba.justVanish.managers.VanishManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class ReloadCommand implements CommandExecutor {
    private final JustVanish plugin;

    public ReloadCommand(JustVanish plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("prefix"));

        if (!sender.hasPermission("justvanish.reload")) {
            String msg = this.plugin.getConfig().getString("messages.no-permission");
            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', msg));
            return true;
        }

        this.plugin.reloadConfig();

        String msg = this.plugin.getConfig().getString("messages.reload-success");
        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', msg));
        return true;
    }
}