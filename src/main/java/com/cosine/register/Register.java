package com.cosine.register;

import com.cosine.config.*;
import com.cosine.mysql.Event;
import com.cosine.mysql.Login;
import com.cosine.mysql.MySQL;
import com.cosine.mysql.SignUp;
import com.cosine.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Register extends JavaPlugin {

    private Config config;
    private Config register;
    private MySQL mysql;
    private Utils utils;

    public String host;
    public String port;
    public String user;
    public String password;

    public String url;

    @Override
    public void onEnable() {
        getLogger().info("회원가입 플러그인 활성화");
        mysql = new MySQL(this);
        utils = new Utils(this);
        config = new Config(this, "Config.yml");
        config.saveDefaultConfig();
        register = new Config(this, "Data.yml");
        register.saveDefaultConfig();

        host = config.getConfig().getString("MySQL.host");
        port = config.getConfig().getString("MySQL.port");
        user = config.getConfig().getString("MySQL.user");
        password = config.getConfig().getString("MySQL.password");
        url = "jdbc:mysql://" + host + ":" + port;

        if(config().getConfig().getBoolean("Yml.main")) {
            getCommand("회원가입").setExecutor(new ConfigSignUp(this));
            getCommand("로그인").setExecutor(new ConfigLogin(this));
            getServer().getPluginManager().registerEvents(new ConfigEvent(this), this);
        } else {
            mysql.Create_DataBase("registers");
            mysql.Create_Table("players");
            getCommand("회원가입").setExecutor(new SignUp(this));
            getCommand("로그인").setExecutor(new Login(this));
            getServer().getPluginManager().registerEvents(new Event(this), this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("회원가입 플러그인 비활성화");
    }

    private static Register instance;
    public Register() {instance = this;}
    public static Register getInstance() {return instance;}

    public Utils utils() {return this.utils;}
    public MySQL mysql() {
        return this.mysql;
    }
    public Config config() {
        return this.config;
    }
    public Config register() {
        return this.register;
    }
}
