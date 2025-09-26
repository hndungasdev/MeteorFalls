package com.hndung.meteorsfall.meteor;

import com.hndung.meteorsfall.MeteorsFall;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FlyingMeteor {
    private final MeteorsFall plugin;
    private final ArrayList<BlockDisplay> parts = new ArrayList<>();

    private Vector direction = new Vector(0, -1, 0);
    private double speed = 1.0;

    private int controlTask = -1;

    private Consumer<Location> onImpact;

    public FlyingMeteor(MeteorsFall plugin) {
        this.plugin = plugin;
    }

    public void spawnMeteor(Location location, Vector direction, double speed) {
        if(controlTask != - 1) plugin.getServer().getScheduler().cancelTask(controlTask);

        this.direction = direction == null ? new Vector(0, -1, 0) : direction.clone().normalize();
        this.speed = speed <= 0 ? 1.0 : speed;

        World world = location.getWorld();
        if (world == null) return;

        Material meteorShell = Material.MAGMA_BLOCK;

        int radius = 2; //KBT lag k=))
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double dist = Math.sqrt(x * x + y * y + z * z);
                    if (dist <= radius + 0.3) {
                        if (Math.random() < (dist / (radius + 0.3)) * 0.3) continue;

                        Location spawn = location.clone().add(x, y, z);
                        BlockDisplay part = world.spawn(spawn, BlockDisplay.class);
                        part.setBlock(meteorShell.createBlockData());
                        part.setFireTicks(Integer.MAX_VALUE);
                        parts.add(part);
                    }
                }
            }
        }

        controlTask = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for(BlockDisplay part : parts) {
                part.teleport(part.getLocation().add(this.direction.clone().multiply(this.speed).add(this.direction.clone().multiply(this.speed).multiply(0.08))));
                world.spawnParticle(Particle.FLAME, part.getLocation(), 2, 0.1, 0.1, 0.1, 0.01);
            }
            checkGround(world);
        }, 1L, 1L).getTaskId();
    }

    public void spawnMeteor(Location location, Location destination, float speed) {
        Vector dest = destination.toVector();
        Vector spawn = location.toVector();

        spawnMeteor(location, dest.clone().subtract(spawn).normalize(), speed);
    }

    public void setOnImpact(Consumer<Location> onImpact) {
        this.onImpact = onImpact;
    }

    private void impact(Location location) {
        if (onImpact != null) {
            onImpact.accept(location);
        }
        stop();
    }

    public void checkGround(World world) {
        for (BlockDisplay part : parts) {
            if (part.getLocation().getBlock().isSolid()) {
                impact(part.getLocation());
                break;
            }
        }
    }

    private void stop() {
        if (controlTask != -1) {
            plugin.getServer().getScheduler().cancelTask(controlTask);
            controlTask = -1;
        }
        for (BlockDisplay part : parts) {
            part.remove();
        }
        parts.clear();
    }
}
