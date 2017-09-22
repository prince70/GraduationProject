package com.niwj.graduationproject.entity;



import org.litepal.crud.DataSupport;

/**
 * Created by prince70 on 2017/9/10.
 * 体检记录
 */

public class Physicalrecord extends DataSupport {
    private int id;//自增长
    private String name;
    private String idcard;
    private String phonenumber;
    private String address;
    private String systolicPressure;//收缩压
    private String diastolicPressure;//舒张压
    private String meanPressure;//平均压
    private String docName;//医生名字
    private String docNumber;//医生工号
    private String time;//检测时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMeanPressure() {
        return meanPressure;
    }

    public void setMeanPressure(String meanPressure) {
        this.meanPressure = meanPressure;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Physicalrecord() {
    }

    public Physicalrecord(String name, String idcard, String phonenumber, String address, String systolicPressure, String diastolicPressure, String meanPressure) {
        this.name = name;
        this.idcard = idcard;
        this.phonenumber = phonenumber;
        this.address = address;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.meanPressure = meanPressure;
    }

    public Physicalrecord(String name, String idcard, String phonenumber, String address, String systolicPressure, String diastolicPressure, String meanPressure, String docName, String docNumber, String time) {
        this.name = name;
        this.idcard = idcard;
        this.phonenumber = phonenumber;
        this.address = address;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.meanPressure = meanPressure;
        this.docName = docName;
        this.docNumber = docNumber;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Physicalrecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idcard='" + idcard + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", address='" + address + '\'' +
                ", systolicPressure='" + systolicPressure + '\'' +
                ", diastolicPressure='" + diastolicPressure + '\'' +
                ", meanPressure='" + meanPressure + '\'' +
                ", docName='" + docName + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
