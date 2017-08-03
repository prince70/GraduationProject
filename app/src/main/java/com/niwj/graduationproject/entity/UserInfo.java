package com.niwj.graduationproject.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by prince70 on 2017/8/3.
 * 用户信息表
 */

public class UserInfo extends DataSupport{
    private int id;
    private String dpassword;
    private String dphone;
    private String dnumber;
    private String didcard;
    private String dname;
    private String duserid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDpassword() {
        return dpassword;
    }

    public void setDpassword(String dpassword) {
        this.dpassword = dpassword;
    }

    public String getDphone() {
        return dphone;
    }

    public void setDphone(String dphone) {
        this.dphone = dphone;
    }

    public String getDnumber() {
        return dnumber;
    }

    public void setDnumber(String dnumber) {
        this.dnumber = dnumber;
    }

    public String getDidcard() {
        return didcard;
    }

    public void setDidcard(String didcard) {
        this.didcard = didcard;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDuserid() {
        return duserid;
    }

    public void setDuserid(String duserid) {
        this.duserid = duserid;
    }
}
