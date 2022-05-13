package com.cosine.mysql;

import com.cosine.register.Register;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySQL {

    Register plugin;

    String result;

    String url;
    String user;
    String password;

    public MySQL(Register plugin, String url, String user, String password) {
        this.plugin = plugin;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void Create_DataBase(String db) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, password);

            pstmt = connection.prepareStatement(url);

            String create = "create database if not exists " + db;

            int success = pstmt.executeUpdate(create);
            if(success == 0) {
                plugin.getLogger().info("DB가 이미 존재하거나 DB 생성에 실패하였습니다.");
            } else {
                plugin.getLogger().info("DB 생성에 성공하였습니다.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void Create_Table(String table) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url + "/registers", user, password);

            pstmt = connection.prepareStatement(url);

            String create = "create table if not exists " + table + "(uuid varchar(100) not null, password varchar(20) not null);";
            pstmt.execute(create);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public Boolean Contains_Player(UUID uuid) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url + "/registers", user, password);

            pstmt = connection.prepareStatement(url);
            String name = uuid.toString();
            rs = pstmt.executeQuery("select * from players where uuid = '" + name + "'");
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public void Set_Password(UUID uuid, String pass) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url + "/registers", user, password);

            String sql = "insert into players values(?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, pass);
            pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public String Get_Password(UUID uuid) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection connection = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    connection = DriverManager.getConnection(url + "/registers", user, password);

                    String sql = "select * from players";

                    pstmt = connection.prepareStatement(sql);
                    rs = pstmt.executeQuery();
                    while(rs.next()) {
                        if(rs.getString("uuid").equals(uuid.toString())) {
                            result = rs.getString("password");
                        }
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (connection != null) connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        service.submit(runnable);
        service.shutdown();
        return result;
    }
}
