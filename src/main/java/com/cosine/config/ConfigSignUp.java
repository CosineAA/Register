package com.cosine.config;

import com.cosine.register.Register;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigSignUp implements CommandExecutor {

    Register plugin;

    public ConfigSignUp(Register plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return false;
    }
}
