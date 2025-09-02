package dev.meyba.justVanish.listeners;

import dev.meyba.justVanish.managers.VanishManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private final VanishManager vanishManager;

    public PlayerListener(VanishManager vanishManager) {
        this.vanishManager = vanishManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player newPlayer = event.getPlayer();
        for (Player onlinePlayer : this.vanishManager.getVanishedPlayers()) {
            newPlayer.hidePlayer(onlinePlayer);
        }
    }
}