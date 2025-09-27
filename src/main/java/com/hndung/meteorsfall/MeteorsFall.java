package com.hndung.meteorsfall;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.hndung.meteorsfall.commands.SummonMeteor;
import com.hndung.meteorsfall.meteor.Meteor;
import com.hndung.meteorsfall.meteor.MeteorType;
import com.hndung.meteorsfall.quartz.QuarztManager;
import com.hndung.meteorsfall.Utils;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class MeteorsFall extends JavaPlugin {
    public QuarztManager quarztManager;
    public static MeteorsFall INSTANCE;
    private int time = 60;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();
        this.saveResource("message.yml", false);
        quarztManager = new QuarztManager(this);
        getCommand("summonmeteor").setExecutor(new SummonMeteor(this));
        getServer().getScheduler().runTaskTimer(this, () -> {
            if(getServer().getWorld("world") == null) return;
            if(getServer().getWorld("world").getLoadedChunks().length == 0) return;
            Chunk[] chunks = getServer().getWorld("world").getLoadedChunks();
            Random random = new Random();
            Chunk chunk = chunks[random.nextInt(chunks.length)];
            int chunkX = chunk.getX() << 4;
            int chunkZ = chunk.getZ() << 4;

            int x = chunkX + random.nextInt(16);
            int z = chunkZ + random.nextInt(16);
            int y = chunk.getWorld().getHighestBlockYAt(x, z);

            Location destination = new Location(chunk.getWorld(), x, y, z);

            Location spawn = destination.clone().add(
                    random.nextInt(10) - 100,
                    300,
                    random.nextInt(10) - 100
            );

            MeteorType type = random.nextBoolean() ? MeteorType.ORE : MeteorType.QUARTZ;

            Meteor meteor = new Meteor(this);
            meteor.init(spawn, destination, type);
            getServer().broadcast(Component.text("Đéo gì đấy sẽ rơi tại tọa độ x: " + x + ", y: " + y + ", z:" + z));
        }, 20L * time, 20L * time);
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a╔════════════════ MeteorsFall - 3fmc.com ═════════════════╗" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a║                                                         ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a║  Plugin: MeteorsFall - 3FMC Fight For Fap               ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a║  Version:" + " " + this.getPluginMeta().getVersion() + "                                         ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a║  Support: 1.21.x                                        ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a║  Github: https://github.com/hndungasdev/MeteorFalls     ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a║  Discord: https://discord.gg/6gz9vaA6                   ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a║                                                         ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a║                  -- Make with love --                   ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&a╚═════════════════════════════════════════════════════════╝"));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c╔════════════════ MeteorsFall - 3fmc.com ═════════════════╗" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c║                                                         ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c║  Plugin: MeteorsFall - 3FMC Fight For Fap               ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c║  Version:" + " " + this.getPluginMeta().getVersion() + "                                         ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c║  Support: 1.21.x                                        ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c║  Github: https://github.com/hndungasdev/MeteorFalls     ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c║  Discord: https://discord.gg/6gz9vaA6                   ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c║                                                         ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c║                  -- Make with love --                   ║" ));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&c╚═════════════════════════════════════════════════════════╝"));
    }

}
