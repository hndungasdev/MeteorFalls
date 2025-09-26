package com.hndung.meteorsfall.commands;

import com.hndung.meteorsfall.MeteorsFall;
import com.hndung.meteorsfall.meteor.Meteor;
import com.hndung.meteorsfall.meteor.MeteorType;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SummonMeteor implements CommandExecutor {
    private final MeteorsFall plugin;
    private final Random random = new Random();

    public SummonMeteor(MeteorsFall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can run this command!");
            return true;
        }

        // Destination = player location
        Location destination = player.getLocation();

        // Spawn location = a bit above and offset randomly
        Location spawn = destination.clone().add(
                random.nextInt(10) - 60, // random offset X
                200,                     // spawn height above player
                random.nextInt(10) - 60  // random offset Z
        );

        // Pick random meteor type
        MeteorType type = random.nextBoolean() ? MeteorType.ORE : MeteorType.QUARTZ;

        // Spawn meteor
        Meteor meteor = new Meteor(plugin);
        meteor.init(spawn, destination, type);

        player.sendMessage("§6☄ A meteor is falling! Type: §e" + type);

        return true;
    }
}
