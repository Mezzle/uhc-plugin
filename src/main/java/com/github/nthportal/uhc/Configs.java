package com.github.nthportal.uhc;

import org.bukkit.configuration.file.FileConfiguration;

public class Configs {
    public static final String EPISODE_TIME = "episode-time";

    public static void setup(UHCPlugin plugin) {
        FileConfiguration config = plugin.config;

        config.addDefault(EPISODE_TIME, 20);
        config.options().copyDefaults(true);

        plugin.saveConfig();
    }
}
