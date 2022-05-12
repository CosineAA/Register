package com.cosine.config;

import com.cosine.register.Register;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class ConfigEvent implements Listener {

    Register plugin;

    public ConfigEvent(Register plugin) {
        this.plugin = plugin;
    }

    ConfigurationSection register = this.plugin.register().getConfig();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!register.contains(uuid.toString())) {
            
        }
    }
}
