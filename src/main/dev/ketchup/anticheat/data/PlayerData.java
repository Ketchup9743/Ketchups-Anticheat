package dev.ketchup.anticheat.data;

import dev.ketchup.anticheat.player.PlayerEdition;

import java.util.UUID;

public final class PlayerData {

    private final UUID uuid;
    private final String username;
    private final PlayerEdition edition;
    private final long joinTime;

    public PlayerData(
            UUID uuid,
            String username,
            PlayerEdition edition
    ) {
        this.uuid = uuid;
        this.username = username;
        this.edition = edition;
        this.joinTime = System.currentTimeMillis();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public PlayerEdition getEdition() {
        return edition;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public long getSessionDuration() {
        return System.currentTimeMillis() - joinTime;
    }
}