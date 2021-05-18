package com.example.museuminfo;


import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//在museumDao里要实现获取静态获取list的方法
public class MuseumDao {
    static  Connection connection=null; //打开数据库对象

    static PreparedStatement ps=null;//操作整合sql语句的对象
    static ResultSet rs=null;//查询结果的集合
    public static MuseumDao museumDao=null;

    /**
     * 获取MySQL数据库单例类对象
     * */
    public MuseumDao(){}
    public static MuseumDao getMuseumDao(){
        if(museumDao==null){
            museumDao=new MuseumDao();
        }
        return museumDao;
    }

    public static List<Museum> getMuseumData(){
        List<Museum> list=new ArrayList<>();
        //mysql语句
        String sql="select * from MuseumBasicInformation";
        //获取数据库连接对象
        try{
            getConn();
            if(connection!=null&&(!connection.isClosed())){
                System.out.println("连上数据库了-博物馆");
                ps= (PreparedStatement) connection.prepareStatement(sql);
                if(ps!=null) {
                    rs = ps.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            System.out.println("博物馆的结果集也不为空");
                            Museum museum=new Museum();
                            museum.setMuseumID(rs.getInt("museumID"));
                            museum.setMuseumName(rs.getString("museumName"));
                            museum.setOpeningTime(rs.getString("openingTime"));
                            museum.setAddress(rs.getString("address"));
                            museum.setIntro((Text) rs.getCharacterStream("introduction"));

                            list.add(museum);
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeAll();
        return list;
    }

    public static void getConn(){
        String url = "jdbc:mysql://120.26.86.149/u606804608_MuseumSpider?useSSL=false";
        //连接数据库使用的用户名
        String userName = "root";
        //连接的数据库时使用的密码
        String password = "jk1803_SE";
        try {
            //1、加载驱动
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("驱动加载成功！！！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            //2、获取与数据库的连接
            connection = DriverManager.getConnection(url, userName, password);
            if(connection!=null){
                System.out.println("数据库连接成功！");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeAll(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
