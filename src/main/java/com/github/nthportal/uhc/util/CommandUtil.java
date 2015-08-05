package com.github.nthportal.uhc.util;

import com.github.nthportal.uhc.UHCPlugin;
import com.google.common.base.Function;
import org.bukkit.Server;
import org.bukkit.command.CommandException;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class CommandUtil {
    public static void executeCommands(UHCPlugin plugin, String event) {
        executeCommands(plugin, event, Collections.<Function<String, String>>emptyList());
    }

    public static void executeCommands(UHCPlugin plugin, String event, List<Function<String, String>> replaceFunctions) {
        List<String> commands = plugin.getConfig().getStringList(event);
        for (String command : commands) {
            if (command.startsWith("/")) {
                command = command.substring(1);
            }
            for (Function<String, String> function : replaceFunctions) {
                command = function.apply(command);
            }

            // Execute command
            Server server = plugin.getServer();
            try {
                server.dispatchCommand(server.getConsoleSender(), command);
            }
            // Why is this a RuntimeException?
            // It's not in my control if someone else doesn't know how to code their CommandExecutor
            catch (CommandException e) {
                plugin.logger.log(Level.WARNING, "Exception running command: " + command, e);
            }
        }
    }

    public static Function<String, String> replacementFunction(final String target, final String replacement) {
        return new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input.replace(target, replacement);
            }
        };
    }

    public static class ReplaceTargets {
        public static final String MINUTES = "{{minutes}}";
        public static final String EPISODE = "{{episode}}";
        public static final String COUNTDOWN_MARK = "{{mark}}";
        public static final String PLAYER = "{{player}}";
    }
}
