package com.hndung.meteorsfall.quartz;

import com.hndung.meteorsfall.MeteorsFall;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

import java.util.concurrent.ConcurrentHashMap;

public class QuarztManager implements Listener {
    private final ConcurrentHashMap<Location, Integer> quarztDurability = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Location, TextDisplay> display = new ConcurrentHashMap<>();

    private final MeteorsFall plugin;

    public QuarztManager(MeteorsFall plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void addQuarzt(Location location) {
        quarztDurability.put(location, 20);
        Location displayLoc = location.clone().add(0, 1, 0);
        TextDisplay text = location.getWorld().spawn(displayLoc, TextDisplay.class);
        text.text(Component.text("Quarzt Durability: 20"));
        display.put(location, text);
    }

    public void removeQuarzt(Location location) {
        TextDisplay text = display.remove(location);
        if (text != null) text.remove();
        quarztDurability.remove(location);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        if (quarztDurability.containsKey(loc)) {
            int durability = quarztDurability.get(loc) - 1;
            if (durability > 0) {
                quarztDurability.put(loc, durability);
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> loc.getBlock().setType(Material.NETHER_QUARTZ_ORE), 1L);

                TextDisplay text = display.get(loc);
                if (text != null) {
                    text.text(Component.text("Quarzt Durability: " + durability));
                }
            } else {
                removeQuarzt(loc);
            }
        }
    }
}
