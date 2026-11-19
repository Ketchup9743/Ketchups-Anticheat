package dev.ketchup.anticheat.player;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.UUID;

public final class FloodgateHook {

    private final Object api;
    private final Method isFloodgatePlayer;

    public FloodgateHook() {
        Plugin floodgate = Bukkit.getPluginManager().getPlugin("floodgate");

        if (floodgate == null || !floodgate.isEnabled()) {
            api = null;
            isFloodgatePlayer = null;
            return;
        }

        try {
            ClassLoader loader = floodgate.getClass().getClassLoader();

            Class<?> apiClass = Class.forName(
                    "org.geysermc.floodgate.api.FloodgateApi",
                    true,
                    loader
            );

            api = apiClass.getMethod("getInstance").invoke(null);

            isFloodgatePlayer = apiClass.getMethod(
                    "isFloodgatePlayer",
                    UUID.class
            );

        } catch (ReflectiveOperationException | LinkageError exception) {
            throw new IllegalStateException(
                    "Could not initialize Floodgate integration.",
                    exception
            );
        }
    }

    public boolean isAvailable() {
        return api != null && isFloodgatePlayer != null;
    }

    public PlayerEdition getEdition(UUID uuid) {
        if (!isAvailable()) {
            return PlayerEdition.JAVA;
        }

        try {
            boolean bedrock = (boolean) isFloodgatePlayer.invoke(api, uuid);

            return bedrock ? PlayerEdition.BEDROCK : PlayerEdition.JAVA;

        } catch (ReflectiveOperationException exception) {
            return PlayerEdition.UNKNOWN;
        }
    }
}