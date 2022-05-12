package com.cosine.config;

import com.cosine.register.Register;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigEvent implements Listener {

    Register plugin;
    Config registerConfig;
    ConfigurationSection register;

    public ConfigEvent(Register plugin) {
        this.plugin = plugin;
        registerConfig = plugin.register();
        register = plugin.register().getConfig();
    }

    public static HashMap<UUID, Boolean> join = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!register.contains(uuid.toString())) {
            Register(player, "SignUp");
        } else {
            Register(player, "Login");
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!register.contains(uuid.toString())) {
            event.setCancelled(true);
            return;
        }
        if(!join.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
    public void Register(Player player, String choice) {
        new BukkitRunnable() {
            final String loop = register.getString("Yml.Message." + choice);
            int time = 20;
            @Override
            public void run() {
                time--;
                player.sendMessage(loop);
                if(register.contains(player.getUniqueId().toString())) {
                    cancel();
                    String message = register.getString("Yml.Success." + choice);
                    player.sendMessage(message);
                }
                if(time == 0) {
                    cancel();
                    String message = "로그인을 하지 않아 자동으로 추방되었습니다.";
                    player.kickPlayer(message);
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }
}
