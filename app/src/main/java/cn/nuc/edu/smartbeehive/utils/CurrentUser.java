package cn.nuc.edu.smartbeehive.utils;

import java.util.List;

import cn.nuc.edu.smartbeehive.bean.Beehive;

public class CurrentUser {
    public static String uid;
    public static String uname;
    public static String upassword;
    public static String uphone;
    public static int bcount = 0;
    public static int[] bid = new int[100];
    public static int Bid = 0;
    public static int Sid = 0;
    public static int Bee_count = 0;
    public static int Bee_activity = 0;
    public static int Beehive_Hum = 0;
    public static float Beehive_Tem = 0;
    public static int Beehive_led = 0;
    public static  float Bee_survivalRate = 0;
    public static float Beehive_co2 = 0;
    public static float Beehive_weight = 0;
    public static List<Beehive> beehives;

}
