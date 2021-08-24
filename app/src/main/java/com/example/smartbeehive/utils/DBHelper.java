package com.example.smartbeehive.utils;

import com.example.smartbeehive.bean.SensorBeehive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {

    private static String diver = "com.mysql.jdbc.Driver";
    //加入utf-8是为了后面往表中输入中文，表中不会出现乱码的情况
    private static String url = "jdbc:mysql://123.56.86.163:9959/smart_beehive?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useJDBCCompliantTimezoneShift=true&nullCatalogMeansCurrent=true";
    private static String user = "root";  //用户名
    private static String password = "killhackersmom";  //密码


    /*
     * 连接数据库
     * */
    public static Connection getConn(){
        Connection conn = null;
        try {
            Class.forName(diver);
            conn = (Connection) DriverManager.getConnection(url,user,password);  //获取连接
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void insertSensorBeehiveBySid(final SensorBeehive sensorBeehive) {
        //执行插入操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                int u = 0;
                conn =(Connection) getConn();
                String sql = "insert into sensorbeehive (Bid, Beehive_hum, Beehive_Tem, Soil_hum, Beehive_led, Light, Bee_fengshan, Bee_jidianqi) values(?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    pst.setString(1, sensorBeehive.getBid());
                    pst.setString(2, sensorBeehive.getBeehive_Hum());
                    pst.setString(3, sensorBeehive.getBeehive_Tem());
                    pst.setString(4, sensorBeehive.getSoil_hum());
                    pst.setString(5, sensorBeehive.getBeehive_led());
                    pst.setString(6, sensorBeehive.getLight());
                    pst.setString(7, sensorBeehive.getBee_fengshan());
                    pst.setString(8, sensorBeehive.getBee_jidianqi());
                    u = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
