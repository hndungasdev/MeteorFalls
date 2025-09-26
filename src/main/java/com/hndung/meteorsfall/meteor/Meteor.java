package com.hndung.meteorsfall.meteor;

import com.hndung.meteorsfall.MeteorsFall;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Meteor {
    private final MeteorsFall plugin;

    private MeteorType type;

    public Meteor(MeteorsFall plugin) {
        this.plugin = plugin;
    }

    public void init(Location spawn, Location destination, MeteorType type) {
        FlyingMeteor flying = new FlyingMeteor(plugin);

        this.type = type;

        flying.setOnImpact(this::handleHitGround);

        flying.spawnMeteor(spawn, destination, 2f);
    }

    public void handleHitGround(Location loc) {
        loc.getWorld().createExplosion(loc, 6F, true, true);

        switch (type) {
            case ORE -> {
                List<Material> ores = new ArrayList<>();
                ores.add(Material.COAL_ORE);
                ores.add(Material.IRON_ORE);
                ores.add(Material.COPPER_ORE);
                ores.add(Material.GOLD_ORE);
                ores.add(Material.REDSTONE_ORE);
                ores.add(Material.LAPIS_ORE);
                ores.add(Material.DIAMOND_ORE);
                ores.add(Material.EMERALD_ORE);
                ores.add(Material.DEEPSLATE_COAL_ORE);
                ores.add(Material.DEEPSLATE_IRON_ORE);
                ores.add(Material.DEEPSLATE_COPPER_ORE);
                ores.add(Material.DEEPSLATE_GOLD_ORE);
                ores.add(Material.DEEPSLATE_REDSTONE_ORE);
                ores.add(Material.DEEPSLATE_LAPIS_ORE);
                ores.add(Material.DEEPSLATE_DIAMOND_ORE);
                ores.add(Material.DEEPSLATE_EMERALD_ORE);
                ores.add(Material.MAGMA_BLOCK);

                replaceSurface(loc, new Random().nextInt(4) + 8, ores);
            }

            case QUARTZ -> replaceSurface(loc, 0, Material.NETHER_QUARTZ_ORE);
        }
    }

    private void replaceSurface(Location center, int radius, List<Material> blocks) {
        World world = center.getWorld();
        if (world == null) return;

        int cx = center.getBlockX();
        int cy = center.getBlockY();
        int cz = center.getBlockZ();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double dist = Math.sqrt(x * x + z * z);
                if (dist <= radius) {
                    Location target = new Location(world, cx + x, cy, cz + z);
                    int surfaceY = world.getHighestBlockYAt(target);
                    Location surface = new Location(world, cx + x, surfaceY, cz + z);
                    Material block = blocks.get(new Random().nextInt(blocks.size()));
                    surface.getBlock().setType(block);
                    if(block == Material.NETHER_QUARTZ_ORE) MeteorsFall.INSTANCE.quarztManager.addQuarzt(surface);
                }
            }
        }
    }

    private void replaceSurface(Location center, int radius, Material block) {
        replaceSurface(center, radius, java.util.Collections.singletonList(block));
    }
}
