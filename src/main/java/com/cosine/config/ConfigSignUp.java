package com.cosine.config;

import com.cosine.register.Register;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import static com.cosine.utils.Utils.join;

public class ConfigSignUp implements CommandExecutor {

    Register plugin;

    Config cfg;
    Config registerConfig;
    ConfigurationSection config;
    ConfigurationSection register;

    public ConfigSignUp(Register plugin) {
        this.plugin = plugin;
        registerConfig = plugin.register();
        cfg = plugin.config();
        register = plugin.register().getConfig();
        config = plugin.config().getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(join.containsKey(player.getUniqueId())) {
            return false;
        }
        if(register.contains(player.getUniqueId().toString())) {
            String message = config.getString("Yml.Message.Login");
            player.sendMessage(message);
            return false;
        }
        if(args.length == 0) {
            String message = config.getString("Yml.Message.SignUp");
            player.sendMessage(message);
            return false;
        }
        if(args.length == 1) {
            String message = config.getString("Yml.Message.SignUp");
            player.sendMessage(message);
            return false;
        }
        if(args.length == 2) {
            if(args[0].equals(args[1])) {
                String success = config.getString("Yml.Success.SignUp");
                player.sendMessage(success);
                join.put(player.getUniqueId(), true);
                register.set(player.getUniqueId() + ".password", args[0]);
                registerConfig.saveConfig();
                return false;
            }
            String error = config.getString("Yml.Error.SignUp");
            player.sendMessage(error);
            return false;
        }
        String error = config.getString("Yml.Error.SignUp");
        player.sendMessage(error);
        return false;
    }
}
