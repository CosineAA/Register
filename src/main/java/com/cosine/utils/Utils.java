package com.cosine.utils;

import com.cosine.config.Config;
import com.cosine.register.Register;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Utils {

    Register plugin;

    Config cfg;
    Config registerConfig;
    ConfigurationSection config;
    ConfigurationSection register;

    public Utils(Register plugin) {
        this.plugin = plugin;
        registerConfig = plugin.register();
        cfg = plugin.config();
        register = plugin.register().getConfig();
        config = plugin.config().getConfig();
    }

    public static HashMap<UUID, Boolean> join = new HashMap<>();

    public void Register(Player player, String choice) {
        String loop = config.getString("Yml.Message." + choice);
        String kick = "아무 행동도 하지 않아 자동으로 추방되었습니다.";
        new BukkitRunnable() {
            int time = 20;
            @Override
            public void run() {
                if(choice.equals("SignUp")) {
                    if (register.contains(player.getUniqueId().toString())) {
                        cancel();
                        return;
                    }
                }
                if(choice.equals("Login")) {
                    if(join.containsKey(player.getUniqueId())) {
                        cancel();
                        return;
                    }
                }
                if(time == 0) {
                    cancel();
                    Bukkit.getScheduler().runTask(plugin, () ->  player.kickPlayer(kick));
                    return;
                }
                player.sendMessage(loop);
                time--;
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }
}
