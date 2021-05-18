package com.example.museuminfo.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtil {
    private static JdbcUtil instance;
    public static JdbcUtil getInstance(){
        if (instance ==null){
            instance = new JdbcUtil();
        }
        return instance;
    }
    public Connection getConnection(String dbName, String name, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("驱动加载成功");
            String url = "jdbc:mysql://120.26.86.149/u606804608_MuseumSpider?useSSL=false";
            return DriverManager.getConnection(url,name,password);
        } catch (Exception e) {
            System.out.println("驱动加载失败");
            return null;
        }
    }

}
