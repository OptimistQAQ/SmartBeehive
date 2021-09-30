package cn.nuc.edu.smartbeehive;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    MyUser user;
    MyAdmin admin;
    MyBeehive beehive;
    MySensorBeehive sensorbeehive;


    public String ip="http://10.0.116.2:3535/";//用tcp通信服务器地址
    public String http_ip="http://123.56.86.163:3535/";//用http通信时的IP地址
    public int communiMode=1;//1表示tcp通信，2表示http通信
    public int objPort=35885;
    Context context;

}
















































//笨卓
