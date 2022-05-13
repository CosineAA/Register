package com.cosine.mysql;

import com.cosine.config.Config;
import com.cosine.register.Register;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import static com.cosine.register.Register.join;

public class SignUp implements CommandExecutor {

    final Register plugin;
    final MySQL mysql;

    final Config cfg;
    final Config registerConfig;
    final ConfigurationSection config;
    final ConfigurationSection register;

    public SignUp(Register plugin) {
        this.plugin = plugin;
        mysql = plugin.mysql();
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
        if(mysql.Contains_Player(player.getUniqueId())) {
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
                mysql.Set_Password(player.getUniqueId(), args[1]);
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
