package com.cosine.mysql;

import com.cosine.config.Config;
import com.cosine.register.Register;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

import static com.cosine.register.Register.join;

public class Login implements CommandExecutor {

    final Register plugin;
    final MySQL mysql;

    final Config cfg;
    final Config registerConfig;
    final ConfigurationSection config;
    final ConfigurationSection register;

    public Login(Register plugin) {
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
        if(!mysql.Contains_Player(player.getUniqueId())) {
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
            try {
                String password = mysql.Get_Password(player.getUniqueId());
                if(password != null && password.equals(args[0])) {
                    String success = config.getString("Yml.Success.Login");
                    player.sendMessage(success);
                    join.put(player.getUniqueId(), true);
                    return false;
                }
                String error = config.getString("Yml.Error.Login");
                player.sendMessage(error);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
