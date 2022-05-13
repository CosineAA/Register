package com.cosine.mysql;

import com.cosine.register.Register;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import static com.cosine.register.Register.join;

public class Event implements Listener {

    final Register plugin;
    final MySQL mysql;

    private final ConfigurationSection config;

    public Event(Register plugin) {
        this.plugin = plugin;
        mysql = plugin.mysql();
        config = plugin.config().getConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!mysql.Contains_Player(player.getUniqueId())) {
            Register(player, "SignUp");
        } else {
            Register(player, "Login");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(join.containsKey(player.getUniqueId())) {
            join.remove(player.getUniqueId());
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(!join.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(!join.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(!mysql.Contains_Player(player.getUniqueId())) {
            event.setCancelled(true);
        }
        if(!join.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
    public void Register(Player player, String choice) {
        String loop = config.getString("Yml.Message." + choice);
        String kick = "아무 행동도 하지 않아 자동으로 추방되었습니다.";
        new BukkitRunnable() {
            int time = 20;
            @Override
            public void run() {
                if(choice.equals("SignUp")) {
                    if (mysql.Contains_Player(player.getUniqueId())) {
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
