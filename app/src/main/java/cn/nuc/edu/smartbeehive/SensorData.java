package cn.nuc.edu.smartbeehive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.List;

import cn.nuc.edu.smartbeehive.bean.Beehive;
import cn.nuc.edu.smartbeehive.bean.Sensor;
import cn.nuc.edu.smartbeehive.utils.CurrentUser;
import cn.nuc.edu.smartbeehive.utils.SimpleSensor;

public class SensorData extends AppCompatActivity {
    MyApplication myApplication = new MyApplication();
    private String url=myApplication.http_ip;
    private TextView beecount;
    private TextView beeactivity;
    private TextView beesurvival;
    private TextView beehiveTem;
    private TextView beehiveHem;
    private TextView beeHiveWeight;
    private TextView beehiveLED;
    private TextView beehiveCO2;
    private TextView zhaungtai;
    private ImageView fengxaingtupian;
    private ImageView baojing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);
        beecount = findViewById(R.id.beecount);
        beeactivity = findViewById(R.id.beeactivity);
        beesurvival = findViewById(R.id.beesurvival);
        beehiveTem = findViewById(R.id.beehiveTem);
        beehiveHem = findViewById(R.id.beehiveHem);
        beeHiveWeight = findViewById(R.id.beeHiveWeight);
        beehiveLED = findViewById(R.id.beehiveLED);
        beehiveCO2 = findViewById(R.id.beehiveCO2);
        zhaungtai = findViewById(R.id.fxzhaungtai);
        fengxaingtupian = findViewById(R.id.fengxaingtupian);
        baojing = findViewById(R.id.baojing1);
        System.out.println("bbbid2"+SimpleSensor.bbbid);
        int i = SimpleSensor.bbbid;//当前点击id
        OkGo.<String>post(url+"sensorinfo?"+"bid="+i)
                //.params("bid", SimpleSensor.bbbid)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        System.out.println("bbbid"+SimpleSensor.bbbid);
                        //JsonArray data = JSON.parseArray(response.body().toString(), Beehive.class);
                        //List<Beehive> data = JSON.parseArray(response.body().toString(), Beehive.class);
                        MySensorBeehive data = JSONObject.parseObject(response.body(), MySensorBeehive.class);

                        System.out.println("信息:"+response.body());
                        System.out.println("信息:"+data.getBeeCount());
                        //JSONObject data  = JSON.parseObject(response.body(),Beehive.class);
                        beecount.setText(String.valueOf(data.getBeeCount()));
                        beeactivity.setText(String.valueOf(data.getBeeActivity()));
                        beesurvival.setText(String.valueOf( data.getBeeSurvivalrate()));
                        beehiveTem.setText(String.valueOf( data.getBeehiveTem()));
                        beehiveHem.setText(String.valueOf(data.getBeehiveHum()));
                        beeHiveWeight.setText(String.valueOf( data.getBeehiveWeight()));
                        if (data.getBeehiveLed()==1){
                            beehiveLED.setText("开");
                        }else{
                            beehiveLED.setText("关");
                        }
                        beehiveCO2.setText(String.valueOf( data.getBeehiveCo2()));
//                        if(Integer.parseInt(data.getBeehiveTem())>30.0000||data.getBeehiveTem()<20.0000){
//                            zhaungtai.setText("状态异常");
//                            zhaungtai.setTextColor(Color.RED);
//                            fengxaingtupian.setVisibility(View.GONE);
//                            baojing.setVisibility(View.VISIBLE);
//
//                         }



                    }


                });


    }
}