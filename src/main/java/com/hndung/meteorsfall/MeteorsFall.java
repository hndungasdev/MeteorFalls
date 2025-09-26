package com.hndung.meteorsfall;

import com.hndung.meteorsfall.commands.SummonMeteor;
import com.hndung.meteorsfall.quartz.QuarztManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MeteorsFall extends JavaPlugin {

    public QuarztManager quarztManager;
    public static MeteorsFall INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        quarztManager = new QuarztManager(this);
        getCommand("summonmeteor").setExecutor(new SummonMeteor(this));
    }

    @Override
    public void onDisable() {

    }
}
