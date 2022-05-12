package com.cosine.mysql;

import com.cosine.register.Register;

import java.sql.*;

public class MySQL {

    Register plugin;

    String url;
    String user;
    String password;

    public MySQL(Register plugin) {
        this.plugin = plugin;
        url = plugin.url;
        user = plugin.user;
        password = plugin.password;
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
            boolean success = pstmt.execute(create);
            if(!success) {
                plugin.getLogger().info("Table이 이미 존재하거나 Table 생성에 실패하였습니다.");
            } else {
                plugin.getLogger().info("Table 생성에 성공하였습니다.");
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
}
