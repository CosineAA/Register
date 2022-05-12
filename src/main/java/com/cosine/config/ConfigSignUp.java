package com.cosine.config;

import com.cosine.register.Register;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import static com.cosine.config.ConfigEvent.join;

public class ConfigSignUp implements CommandExecutor {

    Register plugin;

    Config registerConfig;
    ConfigurationSection register;

    public ConfigSignUp(Register plugin) {
        this.plugin = plugin;
        registerConfig = plugin.register();
        register = registerConfig.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 0) {
            player.sendMessage(register.getString("Yml.SignUpMessage"));
            return false;
        }
        if(args.length == 1) {
            player.sendMessage(register.getString("Yml.Success.SignUp"));
            join.put(player.getUniqueId(), true);
            register.set(player.getUniqueId() + ".password", args[0]);
            registerConfig.saveConfig();
        }
        return false;
    }
}
