#include <Wire.h>
#include "SSD1306.h"
#include <WiFi.h>
#include <PubSubClient.h>
#include <WiFiMulti.h>
#include <Ticker.h>


#include <DHT.h>//包含DHT头文件
#include <ESP32Servo.h>


#define servoPin 4
#define fmqPin 15
#define soilPin A0
#define DHTPIN 13      //定义DHT11模块连接管脚io2
#define LEDPIN 23
 
#define DHTTYPE DHT11   // 使用温度湿度模块的类型为DHT11
#define LED 23

// WiFi
const char *ssid = "Redmi"; // Enter your WiFi name
const char *password = "MENG9959";  // Enter WiFi password

// MQTT Broker
const char *mqtt_broker = "www.mziot.top";
const char *topic1 = "SmartBeehive/led";
const char *topic2 = "SmartBeehive/duoji";
const char *topic3 = "SmartBeehive/jidianqi";
const char *topic4 = "SmartBeehive/soil";
const char *topic5 = "SmartBeehive/tem";
const char *topic6 = "SmartBeehive/hum";
const char *topic7 = "SmartBeehive/light";
const char *topic8 = "SmartBeehive/statu";
const char *topic9 = "SmartBeehive/id";

const char *topic0 = "SmartBeehive/led0";
const char *mqtt_username = "emqx";
const char *mqtt_password = "public";
const int mqtt_port = 1883;


//TODO
Ticker ticker;
Ticker ticker1;
Ticker soilTicker;
Ticker LEDTicker;
Ticker OLEDTicker;
Ticker publishTicker;
Ticker printTicker;
Ticker dhtTicker;
Ticker lightTicker;
Ticker otherTicker;


WiFiMulti wifiMulti;
WiFiClient espClient;
PubSubClient client(espClient); 
Servo servo;


String wendu;
String shidu;
String turang;
String guang;
String ledzt;
String djzt;
String jdqzt;


int LEDflag = 0;
//存储中间值
int soilValue;
//定义土壤湿度
int soilMoisture;
//光照
int light0;
int light1;

SSD1306 display(0x3c, 21, 22);

int statu = 1 ;
int duojicount = 0;
int counter = 0;
int sudu = 90;

int flag=0;
   
DHT dht(DHTPIN, DHTTYPE);    //生成DHT对象，参数是引脚和DHT的类型
float humi_read = 0, temp_read = 0,soil_read=0,light_read=0;//定义浮点型全局变量 储存传感器读取的温湿度数据


void callback(char *topic, byte *payload, unsigned int length) {
   
   String message;
   for (int i = 0; i < length; i++) {
       message = message + (char) payload[i];  // convert *byte to string
   }
   
     Serial.print("Message arrived in topic: ");
     Serial.println("SmartBeehive/led");
     Serial.print("Message:");
     Serial.print(message);
     Serial.print("   ");
     if (message == "1") { 
            digitalWrite(LEDPIN, HIGH);
            LEDflag =1;
     }   // LED on
   if (message == "0") {
        digitalWrite(LEDPIN, LOW);
        LEDflag =0;
    //  jdqzt = "0";
    //   Serial.print(digitalRead(14));
    //   Serial.print("   关闭");
     } // LED off
   Serial.println();
   Serial.println("-----------------------");
   //client.publish(topic1,ledd);

}


void fengmingqi(){
    //tone(15,frequency);
    digitalWrite(fmqPin,LOW);
    delay(500);
    digitalWrite(fmqPin,HIGH);
    //noTone(15);
    delay(500);
}

void duojizheng(){
        if (duojicount<2)
        {
            servo.write(100); 
        }else{
            servo.write(90);
        }    
        duojicount++;

}

void duojifu(){
        if (duojicount<1)
        {
            servo.write(70); 
        }else{
            servo.write(90);
        }    
        duojicount++;

}

void LEDController(){
    if(light1>3800&&LEDflag==1){
      digitalWrite(23,LOW); 
     LEDflag = 0;
      }else if(light1<3000&&LEDflag==0){
      digitalWrite(23,HIGH);
    LEDflag = 1;
        }
    ledzt = (String)digitalRead(23);
}

void OLEDController(){
    display.init();
  display.setFont(ArialMT_Plain_10);
  display.drawString(0, 0,"Temp:");
  display.drawString(45, 0,wendu);
  display.drawString(0, 10,"Humi:");
  display.drawString(45, 10,shidu);
  display.drawString(0, 20,"Soil:");
  display.drawString(45, 20,turang);
  display.drawString(0, 30,"Light:");
  display.drawString(50, 30,guang);
  display.display();
}

void publishController(){

  //pubilsh温度
      const char * wd = new char[5];
      wd = wendu.c_str();
      client.publish(topic5,wd);
      //pubilsh湿度
      const char * sd = new char[5];
      sd = shidu.c_str();
      client.publish(topic6,sd);
      //pubilsh led
      const char * ledd = new char[1];
      ledd = ledzt.c_str();
      client.publish(topic1,ledd);
      //pubilsh 舵机
      const char * dj = new char[5];
      dj = djzt.c_str();
      client.publish(topic2,dj);
      //pubilsh 继电器
      const char * jdq = new char[1];
      jdq = jdqzt.c_str();
      client.publish(topic3,jdq);
       //pubilsh 土壤
      const char * tr = new char[5];
      tr = turang.c_str();
      client.publish(topic4,tr);
      //pubilsh 光照
      const char * gz = new char[5];
      gz = guang.c_str();
      client.publish(topic7,gz);
        //publish状态
       if(statu == 1 ){
          client.publish(topic8,"1");
      }else{
           client.publish(topic8,"2");
      }
      //publish id
      const char * macid = new char[20];
      String macid0 = String(WiFi.macAddress());
      macid = macid0.c_str();
      client.publish(topic9,macid);

}


void printController(){
    Serial.print("湿度: ");
    Serial.println(humi_read);
    Serial.print("温度: ");
    Serial.println(temp_read);
    Serial.print("土壤湿度: ");
    Serial.print(soilMoisture);
    Serial.print("%");
    Serial.print(" 引脚值: ");
    Serial.println(soilValue);
    Serial.print("光照: ");
    Serial.print(light1);
    Serial.print(" 引脚值: ");
    Serial.println(light0);
    Serial.print("led状态：");
      Serial.println(digitalRead(23));
      Serial.print("蜂鸣器状态：");
      Serial.println(digitalRead(fmqPin));
      Serial.print("继电器状态：");
        Serial.println(digitalRead(14));
        Serial.println(flag);
        Serial.println("--------------------");

}


void soilController(){

 if(soilValue>4000){
            digitalWrite(14,HIGH); 
        }else if(soilValue<3000){
            digitalWrite(14,LOW);
            }

     soilValue = analogRead(35);

    //把电压值按照[0,1023]映射到[100,0]
    soilMoisture = map(soilValue,1023,4095,100,0);
    turang = (String)soilMoisture;
}

void dhtController(){
    float h = dht.readHumidity();//湿度 赋值给h
    float t = dht.readTemperature();//温度   赋值给t
   
    if (isnan(h) || isnan(t))//判断是否成功读取到温湿度数据
    {
        //Serial.println("Failed to read from DHT sensor!");//读取温湿度失败
    }
    else//成功读取
    {   
        //打印
        // Serial.print("湿度: ");
        // Serial.println(h);
        // Serial.print("温度: ");
        // Serial.println(t);
        humi_read = h;//将读取到的湿度赋值给全局变量humi_read
        temp_read = t;//将读取到的温度赋值给全局变量temp_read
    }
    wendu = (String)temp_read;
    shidu = (String)humi_read;
}

void statuController(){

     if((temp_read>33.00||humi_read>90.00)&&flag == 0){
            flag =1;
            statu = 0;
            Serial.print("蜂箱异常1");
            Serial.print("   ");
            Serial.print("温度：");
            Serial.print(temp_read);
            Serial.print("  ");
            Serial.print("湿度：");
            Serial.println(humi_read);
            //servo.write(110);
            //Serial.print(servo.read);
            ticker.attach(1,fengmingqi);
            ticker1.attach_ms(1000,duojizheng);
        }else if((temp_read<15.00||humi_read<30)&&flag == 0){
            statu =0;
            Serial.print("蜂箱异常2");
            Serial.print("   ");
            Serial.print("温度：");
            Serial.print(temp_read);
            Serial.print("  ");
            Serial.print("湿度：");
            Serial.println(humi_read);
            //servo.write(110);
            //Serial.print(servo.read);
            ticker.attach(1,fengmingqi);
        }else if((temp_read>33.00||humi_read>90)&&flag == 1){
            statu =0;
            Serial.print("蜂箱异常3");
            Serial.print("   ");
            Serial.print("温度：");
            Serial.print(temp_read);
            Serial.print("  ");
            Serial.print("湿度：");
            Serial.println(humi_read);
            //servo.write(110);
            //Serial.print(servo.read);
            ticker.attach(1,fengmingqi);

        }
        else if((temp_read<25.60||humi_read<30)&&flag == 1){
            statu =0;
            flag = 0;
            Serial.print("蜂箱异常4");
            Serial.print("   ");
            Serial.print("温度：");
            Serial.print(temp_read);
            Serial.print("  ");
            Serial.print("湿度：");
            Serial.println(humi_read);
            //servo.write(110);
            //Serial.print(servo.read);
            ticker.attach(1,fengmingqi);
            ticker1.attach_ms(1000,duojifu);
        }
        else{
            statu = 1;
            servo.write(90);
            ticker.detach();
            ticker1.detach();
            digitalWrite(fmqPin,LOW);
        }

}


void lightController(){
        light0 = analogRead(36);
        light1 = map(light0,0,4093,4093,0);
        guang = (String)light1;
}

void otherController(){
     djzt = (String)sudu;
     jdqzt = (String)digitalRead(14);
}



void setup() {
    display.setFont(ArialMT_Plain_10);
    Serial.begin(9600);
    pinMode(fmqPin,OUTPUT);
    pinMode(23, OUTPUT);
    pinMode(14, OUTPUT);
    pinMode(35,INPUT);
    pinMode(servoPin, OUTPUT);
    servo.attach(servoPin);
    servo.write(sudu);
    dht.begin();//初始化DHT传感器
    // connecting to a WiFi network
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        
        //display.drawString(0, 0,"WiFiNotConnect");
        Serial.println("Connecting to WiFi..");
    }
   // pinMode(23,OUTPUT);
    Serial.println("Connected to the WiFi network");
    //connecting to a mqtt broker
    client.setServer(mqtt_broker, mqtt_port);
    //client.setCallback(callback);
    client.setCallback(callback);
    while (!client.connected()) {
        String client_id = "SmartBeehive-client-";
        client_id += String(WiFi.macAddress());
        Serial.printf("The client %s connects to the public mqtt broker\n", client_id.c_str());
        if (client.connect(client_id.c_str(), mqtt_username, mqtt_password)) {
            Serial.println("Public emqx mqtt broker connected");
        } else {
            Serial.print("failed with state ");
            Serial.print(client.state());
            delay(2000);
        }
    }
    // publish and subscribe
    //client.publish(topic, "hello emqx");
    client.subscribe(topic0);
    // client.subscribe(topic2);
    // client.subscribe(topic3);
    // client.subscribe(topic4);
    // client.subscribe(topic5);
    // client.subscribe(topic6);
    // client.subscribe(topic7);




    dhtTicker.attach_ms(100,dhtController);
    otherTicker.attach_ms(100,otherController);
    lightTicker.attach_ms(100,lightController);
    LEDTicker.attach_ms(100,LEDController);
    soilTicker.attach_ms(100,soilController);
    OLEDTicker.attach_ms(4000,OLEDController);
    //printTicker.attach_ms(2000,printController);
    publishTicker.attach_ms(5000,publishController);
    
  
}



void loop(){
    
    client.loop();
    
}
