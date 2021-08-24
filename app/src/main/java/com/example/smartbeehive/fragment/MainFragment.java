package com.example.smartbeehive.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartbeehive.R;
import com.example.smartbeehive.bean.SensorBeehive;
import com.example.smartbeehive.utils.DBHelper;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


/**
 * @author 65667
 */
public class MainFragment extends Fragment {

    private View root;

    private TextView textView;
    private TextView textView0;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView8;

    private LinearLayout menuLed;

    private static MqttAndroidClient mqttAndroidClient;
    public  String message = "weilianjie";
    private static String TAG = "Android_MQTT_Demo";
    private MqttConnectOptions mMqttConnectOptions;

    public String HOST = "tcp://www.mziot.top:1883";//服务器地址（协议+地址+端口号）
    public String USERNAME = "emqx";//用户名
    public String PASSWORD = "public";//密码

    public static String PUBLISH_TOPIC = "SmartBeehive/tem";//发布主题
    public static String PUBLISH_TOPIC0 = "SmartBeehive/hum";//发布主题
    public static String PUBLISH_TOPIC1 = "SmartBeehive/soil";//发布主题
    public static String PUBLISH_TOPIC2 = "SmartBeehive/light";//发布主题
    public static String PUBLISH_TOPIC3 = "SmartBeehive/jidianqi";//发布主题
    public static String PUBLISH_TOPIC4 = "SmartBeehive/duoji";//发布主题
    public static String PUBLISH_TOPIC5 = "SmartBeehive/led";//发布主题
    public static String PUBLISH_TOPIC6 = "SmartBeehive/statu"; //蜂箱状态
    public static String PUBLISH_TOPIC7 = "SmartBeehive/id"; //蜂箱MAC
    public static String PUBLISH_TOPIC8 = "SmartBeehive/led0";

    public static String RESPONSE_TOPIC = "SmartBeehive/tem";//响应主题

    public String CLIENT_ID = "SmartBeehive-client-1";

    private static Integer index = 1;
    private static String tem = "28.30";
    private static String hum = "";
    private static String soil = "";
    private static String light = "";
    private static String duoji = "";
    private static String jidianqi = "";
    private static String led = "";
    private static String status = "";
    private static String id = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
//        initData();
        InitMQTTConnect();
        return root;
    }

    private void initView() {
        textView = (TextView) root.findViewById(R.id.text111);
        textView0 = (TextView) root.findViewById(R.id.text222);
        textView1 = (TextView) root.findViewById(R.id.text333);
        textView2 = (TextView) root.findViewById(R.id.text444);
        textView3 = (TextView) root.findViewById(R.id.text555);
        textView4 = (TextView) root.findViewById(R.id.text666);
        textView5 = (TextView) root.findViewById(R.id.text777);
        textView6 = (TextView) root.findViewById(R.id.status);
        textView8 = (TextView) root.findViewById(R.id.text888);
        menuLed = (LinearLayout) root.findViewById(R.id.menu_led);
        menuLed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (led.equals("")) {
                    Log.e("123", "456");
                }
                else{
                    if (led.equals("1")) {
                        publish("0");
                    } else {
                        Log.e("456", "789");
                        publish("1");
                    }
                }

            }
        });
    }

    private void InitMQTTConnect() {
        mqttAndroidClient = new MqttAndroidClient(getContext(), HOST, CLIENT_ID);

        mMqttConnectOptions = new MqttConnectOptions();
        // 在重新启动和重新连接时记住状态
        mMqttConnectOptions.setCleanSession(true);
        // 设置连接的用户名
        mMqttConnectOptions.setUserName(USERNAME);
        // 设置密码
        mMqttConnectOptions.setPassword(PASSWORD.toCharArray());
        // 设置超时时间，单位：秒
        mMqttConnectOptions.setConnectionTimeout(0);
        // 心跳包发送间隔，单位：秒
        mMqttConnectOptions.setKeepAliveInterval(15);
        //设置服务质量
        MqttMessage message = new MqttMessage("PayLoad".getBytes());
        message.setQos(0);

        mqttAndroidClient.setCallback(mqttCallback);// 回调

        connectionMQTTServer();
    }

    /**
     * 进行连接操作
     */

    private void connectionMQTTServer() { // 连接操作

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //进行服务器连接
                    /***
                     * mMqttConnectOptions MQTT设置
                     * iMqttActionListener MQTT连接监听
                     */
                    mqttAndroidClient.connect(mMqttConnectOptions, null, new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) { // 连接成功
                            message = "success";
                            try {
                                Toast.makeText(getActivity(), "连接成功！", Toast.LENGTH_LONG).show();
                                Log.e("123","123123");
                                mqttAndroidClient.subscribe(PUBLISH_TOPIC, 0);//订阅主题，参数：主题、服务质量
                                mqttAndroidClient.subscribe(PUBLISH_TOPIC0, 0);//订阅主题，参数：主题、服务质量
                                mqttAndroidClient.subscribe(PUBLISH_TOPIC1, 0);//订阅主题，参数：主题、服务质量
                                mqttAndroidClient.subscribe(PUBLISH_TOPIC2, 0);//订阅主题，参数：主题、服务质量
                                mqttAndroidClient.subscribe(PUBLISH_TOPIC3, 0);//订阅主题，参数：主题、服务质量
                                mqttAndroidClient.subscribe(PUBLISH_TOPIC4, 0);//订阅主题，参数：主题、服务质量
                                mqttAndroidClient.subscribe(PUBLISH_TOPIC5, 0);//订阅主题，参数：主题、服务质量
                                mqttAndroidClient.subscribe(PUBLISH_TOPIC6, 0);
                                mqttAndroidClient.subscribe(PUBLISH_TOPIC7, 0);
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) { //连接失败
                            Toast.makeText(getActivity(), "连接失败！正在重新连接！", Toast.LENGTH_LONG).show();
                            Log.e("123","456456");
                            message = "fail";
                            exception.printStackTrace();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    connectionMQTTServer(); // ReConnection
                                }
                            }, 5000);   //延时5秒重新连接MQTT服务器
                        }
                    });

                } catch (MqttException e) {
                    e.fillInStackTrace();
                }
            }
        }).run();

    }


    private MqttCallback mqttCallback = new MqttCallbackExtended() {  //回传
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {
            /**
             *与服务器的连接成功完成时调用。
             * @param reconnect如果为true，则连接是自动重新连接的结果。
             * @param serverURI建立连接的服务器URI。
             **/

        }

        @Override
        public void connectionLost(Throwable cause) {

            Log.i(TAG, "连接断开 ");
            connectionMQTTServer(); // ReConnection
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {  // 接收的消息
            //String length = String.valueOf(message.getPayload().);
            // Log.e("长度", length);
            message.getId();
            String s = new String(message.getPayload(), "GB2312");
            if (index == 7) {
                index = 1;
                SensorBeehive sensorBeehive = new SensorBeehive();
                sensorBeehive.setBid("1");
                sensorBeehive.setBeehive_Tem(tem);
                sensorBeehive.setBeehive_Hum(hum);
                sensorBeehive.setSoil_hum(soil);
                sensorBeehive.setLight(light);
                sensorBeehive.setBee_fengshan(duoji);
                sensorBeehive.setBee_jidianqi(jidianqi);
                sensorBeehive.setBeehive_led(led);
                DBHelper.insertSensorBeehiveBySid(sensorBeehive);
            }


            Log.e(TAG, topic + s);  //接收的消息
            if (topic.equals("SmartBeehive/tem")){
                textView.setText(s + "℃");
                tem = s;
            }
            if (topic.equals("SmartBeehive/hum")){
                textView0.setText("湿度："+s);
                hum = s;
            }
            if (topic.equals("SmartBeehive/soil")){
                textView1.setText("土壤湿度："+s);
                soil = s;

            }
            if (topic.equals("SmartBeehive/light")){
                textView2.setText("光照强度："+s);
                light = s;
            }
            if (topic.equals("SmartBeehive/duoji")){
                textView3.setText("舵机状态："+s);
                duoji = s;
            }
            if (topic.equals("SmartBeehive/jidianqi")){
                textView4.setText("继电器状态："+s);
                jidianqi = s;
            }
            if (topic.equals("SmartBeehive/led")){
                textView5.setText("LED状态："+s);
                led = s;

            }
            if (topic.equals("SmartBeehive/statu")) {
                if (s.equals("1")){
                    textView6.setText("正常");
                }else {
                    textView6.setText("异常");
                }
            }
            if (topic.equals("SmartBeehive/id")) {
                textView8.setText(s);
                id = s;
            }
            index++;
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    };

    /**
     * 发布消息 publish(主题,消息的字节数组,服务质量,是否在服务器保留断开连接后的最后一条消息);
     *
     * @param message
     */

    /**
     * 将消息发布到服务器上的主题。
     * <p>
     * 一种方便的方法，它将创建一个新的{@link MqttMessage}对象
     * 具有字节数组有效负载和指定的QoS，然后将其发布。
     * </ p>
     *
     * @throws IllegalArgumentException 如果QoS的值不为0、1或2。
     * @param主题 将消息传递到例如“ finance / stock / ibm”。
     * @参数有效负载 用作有效载荷的字节数组
     * @参数qos 提供消息的服务质量。有效值是0、1或2。
     * @param保留 服务器是否应保留此消息。
     * @return令牌用于跟踪并等待发布完成。的 令牌将传递给已设置的任何回调。
     * @抛出MqttPersistenceException 发生问题时存储消息
     * @抛出MqttException 用于发布消息时遇到的其他错误。
     * 例如，正在处理太多消息。
     * @see #publish（String topic, byte[] payload, int qos,boolean retained）
     **/
    public static void publish(String message) {
        String topic = PUBLISH_TOPIC8;
        Boolean retained = false;
        try {
            //参数分别为：主题、消息的字节数组、服务质量、是否在服务器保留断开连接后的最后一条消息
            mqttAndroidClient.publish(topic, message.getBytes(), 0, retained.booleanValue());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void response(String message) {
        String topic = RESPONSE_TOPIC;
        Integer qos = 0;
        Boolean retained = false;
        try {
            //参数分别为：主题、消息的字节数组、服务质量、是否在服务器保留断开连接后的最后一条消息
            mqttAndroidClient.publish(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mqttAndroidClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
