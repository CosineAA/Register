package com.cosine.register;

import com.cosine.config.Config;
import com.cosine.config.ConfigLogin;
import com.cosine.config.ConfigSignUp;
import com.cosine.config.ConfigEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Register extends JavaPlugin {

    private Config config;
    private Config register;

    @Override
    public void onEnable() {
        getLogger().info("회원가입 플러그인 활성화");
        config = new Config(this, "Config.yml");
        config.saveDefaultConfig();
        register = new Config(this, "Data.yml");
        register.saveDefaultConfig();

        if(config().getConfig().getBoolean("Yml.main")) {
            getCommand("회원가입").setExecutor(new ConfigSignUp(this));
            getCommand("로그인").setExecutor(new ConfigLogin(this));
            getServer().getPluginManager().registerEvents(new ConfigEvent(this), this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("회원가입 플러그인 비활성화");
    }

    private static Register instance;
    public Register() {instance = this;}
    public static Register getInstance() {return instance;}

    public Config config() {
        return this.config;
    }
    public Config register() {
        return this.register;
    }
}
