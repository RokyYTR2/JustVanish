package dev.meyba.justVanish.commands;

import dev.meyba.justVanish.JustVanish;
import dev.meyba.justVanish.managers.VanishManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    private final VanishManager vanishManager;
    private final JustVanish plugin;

    public VanishCommand(VanishManager vanishManager, JustVanish plugin) {
        this.vanishManager = vanishManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("messages.not-a-player")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("justvanish.use")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("messages.no-permission")));
            return true;
        }

        if (this.vanishManager.isVanished(player)) {
            this.vanishManager.unVanishPlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("messages.vanish-off")));
        } else {
            this.vanishManager.vanishPlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("messages.vanish-on")));
        }
        return true;
    }
}