package dev.ketchup.anticheat.data;

import dev.ketchup.anticheat.player.FloodgateHook;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class PlayerDataManager {

    private final Map<UUID, PlayerData> players = new HashMap<>();
    private final FloodgateHook floodgateHook;

    public PlayerDataManager(FloodgateHook floodgateHook) {
        this.floodgateHook = floodgateHook;
    }

    public void add(Player player) {
        PlayerData data = new PlayerData(
                player.getUniqueId(),
                player.getName(),
                floodgateHook.getEdition(player.getUniqueId())
        );

        players.put(player.getUniqueId(), data);
    }

    public void remove(UUID uuid) {
        players.remove(uuid);
    }

    public PlayerData get(UUID uuid) {
        return players.get(uuid);
    }

    public boolean contains(UUID uuid) {
        return players.containsKey(uuid);
    }

    public int size() {
        return players.size();
    }

    public Collection<PlayerData> getAll() {
        return List.copyOf(players.values());
    }

    public void clear() {
        players.clear();
    }
}