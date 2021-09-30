package cn.nuc.edu.smartbeehive.item;

import java.sql.Date;

public class RecycleItem {
    private int uid;
    private int bid;
    private String baddress;


    private int sid;
    private int bee_count;
    private int bee_activity;
    private float bee_survivalRate;
    private int beehive_hum;
    private float beehive_tem;
    private float beehive_weight;
    private boolean beehive_led;
    private float beehive_c02;
    private Date UPDATE_TIME;
    private String status;

    public String getBaddress() {
        return baddress;
    }

    public void setBaddress(String baddress) {
        this.baddress = baddress;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getBee_count() {
        return bee_count;
    }

    public void setBee_count(int bee_count) {
        this.bee_count = bee_count;
    }

    public int getBee_activity() {
        return bee_activity;
    }

    public void setBee_activity(int bee_activity) {
        this.bee_activity = bee_activity;
    }

    public float getBee_survivalRate() {
        return bee_survivalRate;
    }

    public void setBee_survivalRate(float bee_survivalRate) {
        this.bee_survivalRate = bee_survivalRate;
    }

    public int getBeehive_hum() {
        return beehive_hum;
    }

    public void setBeehive_hum(int beehive_hum) {
        this.beehive_hum = beehive_hum;
    }

    public float getBeehive_tem() {
        return beehive_tem;
    }

    public void setBeehive_tem(float beehive_tem) {
        this.beehive_tem = beehive_tem;
    }

    public float getBeehive_weight() {
        return beehive_weight;
    }

    public void setBeehive_weight(float beehive_weight) {
        this.beehive_weight = beehive_weight;
    }

    public boolean isBeehive_led() {
        return beehive_led;
    }

    public void setBeehive_led(boolean beehive_led) {
        this.beehive_led = beehive_led;
    }

    public float getBeehive_c02() {
        return beehive_c02;
    }

    public void setBeehive_c02(float beehive_c02) {
        this.beehive_c02 = beehive_c02;
    }

    public Date getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(Date UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
