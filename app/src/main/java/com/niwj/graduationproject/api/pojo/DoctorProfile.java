package com.niwj.graduationproject.api.pojo;

import java.util.List;

/**
 * Created by prince70 on 2017/8/3.
 * 医生详情实体类
 */

public class DoctorProfile {

    /**
     * data : [{"dpassword":"qq19950712","dphone":"18312552520","dnumber":"D098765","didcard":"440903199407101234","dname":"梁医生","duserid":"527phedeqpqc52je57"}]
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
         * dpassword : qq19950712
         * dphone : 18312552520
         * dnumber : D098765
         * didcard : 440903199407101234
         * dname : 梁医生
         * duserid : 527phedeqpqc52je57
         */

        private String dpassword;
        private String dphone;
        private String dnumber;
        private String didcard;
        private String dname;
        private String duserid;
        private String heading;

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
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
}
