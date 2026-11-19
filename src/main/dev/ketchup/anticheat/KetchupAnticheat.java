package dev.ketchup.anticheat;

import dev.ketchup.anticheat.command.KacCommand;
import dev.ketchup.anticheat.data.PlayerDataManager;
import dev.ketchup.anticheat.listener.PlayerListener;
import dev.ketchup.anticheat.player.FloodgateHook;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class KetchupAntiCheat extends JavaPlugin {

    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        FloodgateHook floodgateHook = new FloodgateHook();

        playerDataManager = new PlayerDataManager(floodgateHook);

        getServer().getPluginManager().registerEvents(
                new PlayerListener(playerDataManager),
                this
        );

        getCommand("kac").setExecutor(
                new KacCommand(this, playerDataManager)
        );

        Bukkit.getOnlinePlayers().forEach(playerDataManager::add);

        getLogger().info("Ketchup's Anticheat enabled.");
        getLogger().info("Tracked players: " + playerDataManager.size());
        getLogger().info("Floodgate: " +
                (floodgateHook.isAvailable() ? "available" : "unavailable"));
    }

    @Override
    public void onDisable() {
        if (playerDataManager != null) {
            playerDataManager.clear();
        }

        getLogger().info("Ketchup's Anticheat disabled.");
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}