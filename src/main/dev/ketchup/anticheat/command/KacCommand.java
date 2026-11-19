package dev.ketchup.anticheat.command;

import dev.ketchup.anticheat.KetchupAntiCheat;
import dev.ketchup.anticheat.data.PlayerData;
import dev.ketchup.anticheat.data.PlayerDataManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class KacCommand implements CommandExecutor {

    private final KetchupAntiCheat plugin;
    private final PlayerDataManager playerDataManager;

    public KacCommand(
            KetchupAntiCheat plugin,
            PlayerDataManager playerDataManager
    ) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
    }

    private void send(CommandSender sender, String message) {
        sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&', message)
        );
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (!sender.hasPermission("kac.admin")) {
            send(sender, "&cYou don't have permission.");
            return true;
        }

        if (args.length == 0) {
            send(sender, "&8&m-------------------------");
            send(sender, "&6&lKetchup's Anticheat");
            send(sender, "&7/kac status");
            send(sender, "&7/kac info <player>");
            send(sender, "&7/kac reload");
            send(sender, "&8&m-------------------------");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "status" -> {
                send(sender, "&6&lKetchup's Anticheat");
                send(sender, "&7Version: &f" +
                        plugin.getPluginMeta().getVersion());
                send(sender, "&7Tracked players: &f" +
                        playerDataManager.size());
            }

            case "info" -> {
                if (args.length < 2) {
                    send(sender, "&cUsage: /kac info <player>");
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);

                if (target == null) {
                    send(sender, "&cPlayer not found.");
                    return true;
                }

                PlayerData data = playerDataManager.get(
                        target.getUniqueId()
                );

                if (data == null) {
                    send(sender, "&cPlayer data is unavailable.");
                    return true;
                }

                send(sender, "&6&lPlayer Information");
                send(sender, "&7Name: &f" + data.getUsername());
                send(sender, "&7UUID: &f" + data.getUuid());
                send(sender, "&7Edition: &f" + data.getEdition());
                send(sender, "&7Session: &f" +
                        data.getSessionDuration() / 1000 + "s");
            }

            case "reload" -> {
                plugin.reloadConfig();
                send(sender, "&aConfiguration reloaded.");
            }

            default -> send(sender, "&cUnknown subcommand.");
        }

        return true;
    }
}