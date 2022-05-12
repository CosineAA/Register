package com.cosine.config;

import com.cosine.register.Register;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import static com.cosine.config.ConfigEvent.join;

public class ConfigLogin implements CommandExecutor {

    Register plugin;

    Config registerConfig;
    ConfigurationSection register;

    public ConfigLogin(Register plugin) {
        this.plugin = plugin;
        registerConfig = plugin.register();
        register = registerConfig.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 0) {
            String message = register.getString("Yml.LoginMessage");
            player.sendMessage(message);
            return false;
        }
        if(args.length == 1) {
            String message = register.getString("Yml.Success.Login");
            join.put(player.getUniqueId(), true);
            player.sendMessage(message);
        }
        return false;
    }
}
