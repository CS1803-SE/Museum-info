package com.example.museuminfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
    static  Connection connection=null;

    /**
     * 登录信息验证
     * @param name
     * @param password
     * @return
     */
    public boolean login(String name, String password) {
        boolean irow=false;
        try{
            getConn();
            String sql = "select * from user where NickName=? and Password=?";
            PreparedStatement pres = connection.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, password);
            ResultSet res = pres.executeQuery();
            irow=pres.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeAll();
        }
        if(irow!=false){
            System.out.println("结果集也不为空！");
        }
        return irow;
    }

    public int setDianp(String museumname,String username,float rb_exb,float rb_ser,float rb_envir,String pingl_str) {
        //boolean irow=false;
        int n=0;
        try{
            getConn();
            String sql = "insert into userdianping(museumName,NickName,exhi_point,server_point,enviro_point,pinglun) values (?,?,?,?,?,?)";
            PreparedStatement pres = connection.prepareStatement(sql);
            pres.setString(1, museumname);
            pres.setString(2, username);
            pres.setFloat(3,rb_exb);
            pres.setFloat(4, rb_ser);
            pres.setFloat(5, rb_envir);
            pres.setString(6, pingl_str);
            //ResultSet res = pres.executeQuery();
            n=pres.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return n;
    }

    /**
     * 获取某个博物馆的展览信息
     * @param museumname
     * @return
     */
    public static List<Map<String, Object>> getexhiinfo(String museumname) {
        //     List<Museum> list=new ArrayList<>();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        //获取数据库连接对象
        try {
            getConn();
            String sql = "select * from Exhibition where museumName='"+museumname+"'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("展览的结果集也不为空");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", rs.getString("exhibitionName"));
                    map.put("time", rs.getString("exhibitionTime"));
                    map.put("intro", rs.getString("exhibitionIntroduction"));
                    List.add(map);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return List;
    }



    /**
     * 获取某个博物馆的藏品信息
     * @param museumname
     * @return
     */
    public static List<Map<String, Object>> getcangpininfo(String museumname) {
        //     List<Museum> list=new ArrayList<>();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        //获取数据库连接对象
        try {
            getConn();
            String sql = "select * from Collection where museumName=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,museumname);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("藏品的结果集也不为空");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", rs.getString("collectionName"));
                    map.put("intro", rs.getString("collectionIntroduction"));
                    List.add(map);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return List;
    }

    /**
     * 获取某个博物馆的新闻信息
     * @param museumname
     * @return
     */
    public static List<Map<String, Object>> getnewsinfo(String museumname) {
        //     List<Museum> list=new ArrayList<>();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        //获取数据库连接对象
        try {
            getConn();
            String sql = "select * from newsall where museum=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,museumname);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("新闻的结果集也不为空");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", rs.getString("title2"));
                    map.put("intro", rs.getString("content"));
                    map.put("author", rs.getString("author"));
                    map.put("time", rs.getString("time"));
                    List.add(map);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return List;
    }

    /**
     * 获取某个博物馆按照时间搜索的新闻信息
     * @param museumname
     * @return
     */
    public static List<Map<String, Object>> getnewsinfobytime(String museumname,String time1,String time2) {
        //     List<Museum> list=new ArrayList<>();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        //获取数据库连接对象
        try {
            getConn();
            String sql = "select * from newsall where museum=? and time between ? and ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,museumname);
            ps.setString(2,time1);
            ps.setString(3,time2);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("新闻的结果集也不为空");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", rs.getString("title2"));
                    map.put("intro", rs.getString("content"));
                    map.put("author", rs.getString("author"));
                    map.put("time", rs.getString("time"));
                    List.add(map);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return List;
    }



    /**
     * 搜索展览信息
     * @param
     * @return
     */
    public static List<Map<String, Object>> searchexhiinfo(String search) {
        //     List<Museum> list=new ArrayList<>();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        //获取数据库连接对象
        try {
            getConn();
            String sql = "select * from Exhibition where exhibitionName like '%"+search+"%'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("展览的结果集也不为空");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", rs.getString("exhibitionName"));
                    map.put("time", rs.getString("exhibitionTime"));
                    map.put("intro", rs.getString("exhibitionIntroduction"));
                    List.add(map);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return List;
    }


    /**
     * 搜索藏品信息
     */
    public static List<Map<String, Object>> searchcangpininfo(String search) {
        //     List<Museum> list=new ArrayList<>();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        //获取数据库连接对象
        try {
            getConn();
            String sql = "select * from Collection where collectionName like '%"+search+"%'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", rs.getString("collectionName"));
                    map.put("intro", rs.getString("collectionIntroduction"));
                    List.add(map);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return List;
    }

    /**
     * 获取整个博物馆的信息
     * @return
     */
    public static List<Map<String, Object>> getMuseumData() {
        //     List<Museum> list=new ArrayList<>();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        //获取数据库连接对象
        try {
            getConn();
            String sql = "select * from MuseumBasicInformation";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("博物馆的结果集也不为空");
                    Map<String, Object> map = new HashMap<String, Object>();
                    if(rs.getString("museumName")==null){
                        map.put("museumName","暂无信息");
                    }else{
                        map.put("museumName", rs.getString("museumName"));
                    }
                    if (rs.getString("openingTime")==null){
                        map.put("openingTime","暂无信息");
                    }else {
                        map.put("openingTime", rs.getString("openingTime"));
                    }
                    if (rs.getString("address")==null){
                        map.put("address","暂无信息");
                    }else {
                        map.put("address", rs.getString("address"));
                    }
                    if (rs.getString("introduction")==null){
                        map.put("introduction","暂无信息");
                    }else {
                        map.put("introduction", rs.getString("introduction"));
                    }
                    if (rs.getString("consultationTelephone")==null){
                        map.put("telephone","暂无信息");
                    }else {
                        map.put("telephone", rs.getString("consultationTelephone"));
                    }

        /*            map.put("openingTime", rs.getString("openingTime"));
                    map.put("address", rs.getString("address"));
                    map.put("introduction", rs.getString("introduction"));
                    map.put("telephone",rs.getString("consultationTelephone"));*/

                    List.add(map);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return List;
    }

/**
 * 获取用户点评的信息
 *
 */
public static List<Map<String, Object>> getmydianping(String username) {
    //     List<Museum> list=new ArrayList<>();
    List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
    //获取数据库连接对象
    try {
        getConn();
        String sql = "select * from userdianping where NickName=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs != null) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("museumName", rs.getString("museumName"));
                map.put("exhi_point", rs.getDouble("exhi_point"));
                map.put("server_point", rs.getDouble("server_point"));
                map.put("enviro_point", rs.getDouble("enviro_point"));
                map.put("pinglun", rs.getString("pinglun"));
                List.add(map);
            }
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    } finally {
        closeAll();
    }
    return List;
}
    /**
     * 获取博物馆排行榜的信息
     * @return
     */
        public static List<Map<String, Object>> getMuseumRank(){
            //     List<Museum> list=new ArrayList<>();
            List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
            //获取数据库连接对象
            try{
                getConn();
                String sql="select * from MuseumRank order by All_Point desc";
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        System.out.println("博物馆的结果集也不为空");

                        Map<String, Object> map=new HashMap<String, Object>();
                        map.put("museumName",rs.getString("museumName"));
                        map.put("All_Point",rs.getDouble("All_Point"));
                        map.put("P_exhiPoint",rs.getDouble("P_exhiPoint"));
                        map.put("P_serPoint",rs.getDouble("P_serPoint"));
                        map.put("P_envirPoint",rs.getDouble("P_envirPoint"));

                        List.add(map);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                closeAll();
            }
        return List;
    }


    /**
     * 进行搜索操作，按博物馆名称
     * @param search
     * @return
     */
    public static List<Map<String, Object>> getSearchData(String search) {
        //     List<Museum> list=new ArrayList<>();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        //获取数据库连接对象
        try {
            getConn();
            String sql = "select * from MuseumBasicInformation where museumName like '%"+search+"%'";
            PreparedStatement ps = connection.prepareStatement(sql);
         //   ps.setString(1, search);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("博物馆的结果集也不为空");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("museumID", rs.getInt("museumID"));
                    map.put("museumName", rs.getString("museumName"));
                    map.put("openingTime", rs.getString("openingTime"));
                    map.put("address", rs.getString("address"));
                    map.put("introduction", rs.getString("introduction"));

                    List.add(map);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return List;
    }

//返回用户的收藏信息
    public static List<Map<String, Object>> getusercolletion(String username) {
        //     List<Museum> list=new ArrayList<>();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        //获取数据库连接对象
        try {
            getConn();
            String sql = "select * from museumbasicinformation left join usercolletion on museumbasicinformation.museumID=usercolletion.museumID where NickName=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("博物馆的结果集也不为空");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("museumID", rs.getInt("museumID"));
                    map.put("museumName", rs.getString("museumName"));
                    map.put("openingTime", rs.getString("openingTime"));
                    map.put("address", rs.getString("address"));
                    map.put("introduction", rs.getString("introduction"));

                    List.add(map);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll();
        }
        return List;
    }


    //获取用户个人信息，显示在个人详情页里
    public static Map<String, Object> getmyinfo(String niname){
        System.out.println("用户的昵称为"+niname);
        Map<String, Object> map=new HashMap<String, Object>();
   //     UserData userData=new UserData();
        try {
            getConn();

            String sql = "select * from user where NickName=?";
            PreparedStatement pres = connection.prepareStatement(sql);
            pres.setString(1, niname);
            ResultSet rs=pres.executeQuery();

            if(rs!=null){
              //  rs.next();
                System.out.println("有把值赋给usedao");

                if(rs.next()){

                    map.put("nickname", rs.getString("NickName"));
                    System.out.println(rs.getString("NickName"));
                    map.put("idnumber", rs.getString("IDNumber"));
                    map.put("name", rs.getString("Name"));
                    map.put("phone", rs.getString("PhoneNumber"));
                    map.put("email", rs.getString("E_mail"));
              //      rs.next();
                    System.out.println(1);
                }
            }else{
                System.out.println("博物馆信息为空");
            }

            }catch (SQLException e) {
            System.out.println("sql有问题");
                return null;
            }
        finally {
            closeAll();
        }
        return map;
    }

    /**
     * 博物馆详情页内获取信息
     * @param museumname
     * @return
     */
    public static Map<String, Object> getmuseuinfo(String museumname){
        System.out.println("博物馆名称为"+museumname);
        Map<String, Object> map=new HashMap<String, Object>();
        //     UserData userData=new UserData();
        try {
            getConn();
            String sql = "select * from MuseumBasicInformation where museumName='"+museumname+"'";
            PreparedStatement pres = connection.prepareStatement(sql);
            //        pres.setString(1, niname);
            ResultSet rs=pres.executeQuery();
            if(rs!=null){
           /*     userData.setusernickname(rs.getString("NickName"));
                userData.setuserid(rs.getString("IDNumber"));
                userData.setusername(rs.getString("Name"));
                userData.setuseremail(rs.getString("E_mail"));
                userData.setuserphone(rs.getString("PhoneNumber"));*/

                //   System.out.println(userData.getusername());


                    map.put("museumID", rs.getString("museumID"));
                    map.put("museumName", rs.getString("museumName"));
                    map.put("openingTime", rs.getString("openingTime"));
                    map.put("address", rs.getString("address"));
                    map.put("introduction", rs.getString("introduction"));


          /*          map.put("nickname", rs.getString("NickName"));
                    map.put("idnumber", rs.getString("IDNumber"));
                    map.put("name", rs.getString("Name"));
                    map.put("phone", rs.getString("PhoneNumber"));
                    map.put("email", rs.getString("E_mail"));*/

            }else{
                System.out.println("用户信息为空");
            }
        }catch (SQLException e) {
            return null;
        }
        finally {
            closeAll();
        }
        //   return userData;
        return map;
    }



    //获取数据库的连接
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
