package dev.meyba.justVanish.commands;

import dev.meyba.justVanish.JustVanish;
import dev.meyba.justVanish.managers.VanishManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Commands implements CommandExecutor, TabCompleter {
    private final VanishManager vanishManager;
    private final JustVanish plugin;

    public Commands(VanishManager vanishManager, JustVanish plugin) {
        this.vanishManager = vanishManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("prefix"));

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "reload":
                    if (!sender.hasPermission("justvanish.reload")) {
                        String noPermissionMsg = this.plugin.getConfig().getString("messages.no-permission");
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', noPermissionMsg));
                        return true;
                    }
                    this.plugin.reloadConfig();
                    String successMsg = this.plugin.getConfig().getString("messages.reload-success");
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', successMsg));
                    return true;

                case "help":
                    if (!sender.hasPermission("justvanish.help")) {
                        String noPermissionMsg = this.plugin.getConfig().getString("messages.no-permission");
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', noPermissionMsg));
                        return true;
                    }
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "ʜᴇʟᴘ ᴍᴇɴᴜ:"));
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "/vanish - ᴛᴏɢɢʟᴇꜱ ᴠᴀɴɪꜱʜ."));
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "/vanish reload - ʀᴇʟᴏᴀᴅꜱ ᴛʜᴇ ᴄᴏɴꜰɪɢ."));
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "/vanish help - ꜱʜᴏᴡꜱ ᴛʜɪꜱ ᴍᴇɴᴜ."));
                    return true;
                default:
                    break;
            }
        }

        if (!(sender instanceof Player player)) {
            String msg = this.plugin.getConfig().getString("messages.not-a-player");
            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', msg));
            return true;
        }

        if (!player.hasPermission("justvanish.use")) {
            String noPermissionMsg = this.plugin.getConfig().getString("messages.no-permission");
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', noPermissionMsg));
            return true;
        }

        if (this.vanishManager.isVanished(player)) {
            String leaveMessage = this.plugin.getConfig().getString("messages.join-message");
            if (leaveMessage != null) {
                leaveMessage = leaveMessage.replace("%player%", player.getName());
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', leaveMessage));
            }
            this.vanishManager.unVanishPlayer(player);
            String msg = this.plugin.getConfig().getString("messages.vanish-off");
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', msg));

            String msgAction = this.plugin.getConfig().getString("actionbar.vanish-off");
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacy(prefix + ChatColor.translateAlternateColorCodes('&', msgAction)));

            String soundName = this.plugin.getConfig().getString("sounds.vanish-off");
            try {
                Sound sound = Sound.valueOf(soundName.toUpperCase());
                player.playSound(player.getLocation(), sound, 1f, 1f);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid sound name in config: " + soundName);
            }
        } else {
            String joinMessage = this.plugin.getConfig().getString("messages.leave-message");
            if (joinMessage != null) {
                joinMessage = joinMessage.replace("%player%", player.getName());
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
            }
            this.vanishManager.vanishPlayer(player);
            String msg = this.plugin.getConfig().getString("messages.vanish-on");
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', msg));

            String msgAction = this.plugin.getConfig().getString("actionbar.vanish-on");
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacy(prefix + ChatColor.translateAlternateColorCodes('&', msgAction)));

            String soundName = this.plugin.getConfig().getString("sounds.vanish-on");
            try {
                Sound sound = Sound.valueOf(soundName.toUpperCase());
                player.playSound(player.getLocation(), sound, 1f, 1f);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid sound name in config: " + soundName);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("justvanish.use") || sender.hasPermission("justvanish.help")) {
                completions.add("vanish");
            }
            if (sender.hasPermission("justvanish.reload")) {
                completions.add("reload");
            }
            if (sender.hasPermission("justvanish.help")) {
                completions.add("help");
            }
        }

        return completions.stream()
                .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
    }
}