package com.github.nthportal.uhc;

import com.google.common.base.Function;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class CustomListener implements Listener {
    private final UHCPlugin plugin;

    public CustomListener(UHCPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (plugin.timer.getState() != Timer.State.RUNNING) {
            return;
        }

        List<Function<String, String>> replacements = new ArrayList<>();
        replacements.add(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s.replace(CommandUtil.Replacements.PLAYER, event.getEntity().getName());
            }
        });
        CommandUtil.executeCommands(plugin, Config.Events.ON_DEATH, replacements);
    }
}