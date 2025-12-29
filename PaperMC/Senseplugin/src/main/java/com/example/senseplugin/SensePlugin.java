package com.example.senseplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class SensePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Sense-Mod aktiviert!");
        getServer().getPluginManager().registerEvents(new SenseListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Sense-Mod deaktiviert!");
    }
}
