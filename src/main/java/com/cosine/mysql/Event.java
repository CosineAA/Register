package com.cosine.mysql;

import com.cosine.config.Config;
import com.cosine.register.Register;
import com.cosine.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.cosine.utils.Utils.join;

public class Event implements Listener {

    Register plugin;
    MySQL mysql;
    Utils utils;

    public Event(Register plugin) {
        this.plugin = plugin;
        utils = plugin.utils();
        mysql = plugin.mysql();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!mysql.Contains_Player(player.getUniqueId())) {
            utils.Register(player, "SignUp");
        } else {
            utils.Register(player, "Login");
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
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(!mysql.Contains_Player(player.getUniqueId())) {
            event.setCancelled(true);
        }
        if(!join.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
