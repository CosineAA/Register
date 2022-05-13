package com.cosine.config;

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

public class ConfigEvent implements Listener {

    Register plugin;
    Utils utils;

    Config cfg;
    Config registerConfig;
    ConfigurationSection config;
    ConfigurationSection register;

    public ConfigEvent(Register plugin) {
        this.plugin = plugin;
        utils = plugin.utils();
        registerConfig = plugin.register();
        cfg = plugin.config();
        register = plugin.register().getConfig();
        config = plugin.config().getConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!register.contains(player.getUniqueId().toString())) {
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
        if(!register.contains(player.getUniqueId().toString())) {
            event.setCancelled(true);
        }
        if(!join.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
