package com.cosine.config;

import com.cosine.register.Register;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import static com.cosine.register.Register.join;

public class ConfigLogin implements CommandExecutor {

    Register plugin;

    Config cfg;
    Config registerConfig;
    ConfigurationSection config;
    ConfigurationSection register;

    public ConfigLogin(Register plugin) {
        this.plugin = plugin;
        registerConfig = plugin.register();
        cfg = plugin.config();
        register = plugin.register().getConfig();
        config = plugin.config().getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(join.containsKey(player.getUniqueId())) {
            return false;
        }
        if(!register.contains(player.getUniqueId().toString())) {
            String message = config.getString("Yml.Message.SignUp");
            player.sendMessage(message);
            return false;
        }
        if(args.length == 0) {
            String message = config.getString("Yml.Message.Login");
            player.sendMessage(message);
            return false;
        }
        if(args.length == 1) {
            String password = register.getString(player.getUniqueId() + ".password");
            if(password.equals(args[0])) {
                String success = config.getString("Yml.Success.Login");
                player.sendMessage(success);
                join.put(player.getUniqueId(), true);
                return false;
            }
            String error = config.getString("Yml.Error.Login");
            player.sendMessage(error);
        }
        return false;
    }
}
