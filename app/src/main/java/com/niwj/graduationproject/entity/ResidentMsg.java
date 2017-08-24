package com.niwj.graduationproject.entity;

/**
 * Created by prince70 on 2017/8/23.
 * 居民 通知信息
 */

public class ResidentMsg {
    private String name;
    private String idcard;
    private String phonenumber;
    private String address;
    private String systolicPressure;//收缩压
    private String diastolicPressure;//舒张压

    public ResidentMsg() {
    }

    public ResidentMsg(String name, String idcard, String phonenumber, String address, String systolicPressure, String diastolicPressure) {
        this.name = name;
        this.idcard = idcard;
        this.phonenumber = phonenumber;
        this.address = address;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(String systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public String getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(String diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    @Override
    public String toString() {
        return "ResidentMsg{" +
                "name='" + name + '\'' +
                ", idcard='" + idcard + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", address='" + address + '\'' +
                ", systolicPressure='" + systolicPressure + '\'' +
                ", diastolicPressure='" + diastolicPressure + '\'' +
                '}';
    }
}
