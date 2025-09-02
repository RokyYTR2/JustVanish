package dev.meyba.justVanish.commands;

import dev.meyba.justVanish.JustVanish;
import dev.meyba.justVanish.managers.VanishManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class VanishCommand implements CommandExecutor {
    private final VanishManager vanishManager;
    private final JustVanish plugin;
    private final String prefix;

    public VanishCommand(VanishManager vanishManager, JustVanish plugin) {
        this.vanishManager = vanishManager;
        this.plugin = plugin;
        this.prefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("prefix"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            String msg = this.plugin.getConfig().getString("messages.not-a-player");
            sender.sendMessage(this.prefix + ChatColor.translateAlternateColorCodes('&', msg));
            return true;
        }

        if (!player.hasPermission("justvanish.use")) {
            String msg = this.plugin.getConfig().getString("messages.no-permission");
            player.sendMessage(this.prefix + ChatColor.translateAlternateColorCodes('&', msg));
            return true;
        }

        if (this.vanishManager.isVanished(player)) {
            this.vanishManager.unVanishPlayer(player);
            String msg = this.plugin.getConfig().getString("messages.vanish-off");
            player.sendMessage(this.prefix + ChatColor.translateAlternateColorCodes('&', msg));
        } else {
            this.vanishManager.vanishPlayer(player);
            String msg = this.plugin.getConfig().getString("messages.vanish-on");
            player.sendMessage(this.prefix + ChatColor.translateAlternateColorCodes('&', msg));
        }
        return true;
    }
}