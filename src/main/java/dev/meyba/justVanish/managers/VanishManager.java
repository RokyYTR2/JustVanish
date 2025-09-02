package dev.meyba.justVanish.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishManager {
    private final Set<UUID> vanishedPlayers;

    public VanishManager() {
        this.vanishedPlayers = new HashSet<>();
    }

    public void vanishPlayer(Player player) {
        this.vanishedPlayers.add(player.getUniqueId());
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(player);
        }
    }

    public void unVanishPlayer(Player player) {
        this.vanishedPlayers.remove(player.getUniqueId());
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(player);
        }
    }

    public boolean isVanished(Player player) {
        return this.vanishedPlayers.contains(player.getUniqueId());
    }

    public Set<Player> getVanishedPlayers() {
        Set<Player> players = new HashSet<>();
        for (UUID uuid : this.vanishedPlayers) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                players.add(player);
            }
        }
        return players;
    }
}