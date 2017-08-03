package com.niwj.graduationproject.api.pojo;

import java.util.List;

/**
 * Created by 伟金 on 2017/7/25.
 * 注册实体类
 */

public class DoctorRegister {

    /**
     * data : [{"dpassword":"888888","dphone":"13245685215","dnumber":"009","didcard":"440923199409231234","dname":"梁伟恒","duserid":"2mykgerw6yqt85w8uv"},{"dpassword":"123456","dphone":"13437578156","dnumber":"008","didcard":"440923199409233181","dname":"倪斯玲","duserid":"egsvp63s4cv9els0cs"},{"dpassword":"wojiushiwo1234","dphone":"13414908520","dnumber":"D123456","didcard":"440923199407101234","dname":"梁伟恒","duserid":"xdgtak3ggzpyfo5efm"},{"dpassword":"123456","dphone":"13414901394","dnumber":"007","didcard":"440923199502133177","dname":"倪伟金","duserid":"ysmu1cr8zwx2a00ibp"}]
     * state : true
     * code : 0
     */

    private String state;
    private int code;
    private List<DataBean> data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * dpassword : 888888
         * dphone : 13245685215
         * dnumber : 009
         * didcard : 440923199409231234
         * dname : 梁伟恒
         * duserid : 2mykgerw6yqt85w8uv
         */

        private String dpassword;
        private String dphone;
        private String dnumber;
        private String didcard;
        private String dname;
        private String duserid;

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
}
