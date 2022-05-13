package com.cosine.register;

import com.cosine.config.*;
import com.cosine.mysql.Event;
import com.cosine.mysql.Login;
import com.cosine.mysql.MySQL;
import com.cosine.mysql.SignUp;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Register extends JavaPlugin {

    private Config config;
    private Config register;
    private MySQL mysql;

    public String host;
    public String port;
    public String user;
    public String password;

    public String url;

    public static HashMap<UUID, Boolean> join = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("회원가입 플러그인 활성화");

        config = new Config(this, "Config.yml");
        register = new Config(this, "Data.yml");

        config.saveDefaultConfig();
        register.saveDefaultConfig();

        if(config().getConfig().getBoolean("Yml.main")) {
            getServer().getLogger().info("Yml이 활성화 되었습니다.");
            getCommand("회원가입").setExecutor(new ConfigSignUp(this));
            getCommand("로그인").setExecutor(new ConfigLogin(this));
            getServer().getPluginManager().registerEvents(new ConfigEvent(this), this);
        } else {
            getServer().getLogger().info("MySQL이 활성화 되었습니다.");

            host = config.getConfig().getString("MySQL.host");
            port = config.getConfig().getString("MySQL.port");
            user = config.getConfig().getString("MySQL.user");
            password = config.getConfig().getString("MySQL.password");

            url = "jdbc:mysql://" + host + ":" + port;

            mysql = new MySQL(this, url, user, password);

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
