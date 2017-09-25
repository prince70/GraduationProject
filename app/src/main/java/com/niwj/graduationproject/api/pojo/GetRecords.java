package com.niwj.graduationproject.api.pojo;

import java.util.List;

/**
 * Created by prince70 on 2017/9/25.
 */

public class GetRecords {

    /**
     * data : [{"dnumber":"D02133178","diastolicpressure":119,"dname":"倪伟金","ridcard":"440923199502132580","raddress":"广东省湛江市赤坎区","rname":"张飞","rphone":"13414901234","ctime":"2017年09月24日 19:16:51","systolicpressure":180,"meanpressure":131}]
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
         * dnumber : D02133178
         * diastolicpressure : 119
         * dname : 倪伟金
         * ridcard : 440923199502132580
         * raddress : 广东省湛江市赤坎区
         * rname : 张飞
         * rphone : 13414901234
         * ctime : 2017年09月24日 19:16:51
         * systolicpressure : 180
         * meanpressure : 131
         */

        private String dnumber;
        private int diastolicpressure;
        private String dname;
        private String ridcard;
        private String raddress;
        private String rname;
        private String rphone;
        private String ctime;
        private int systolicpressure;
        private int meanpressure;

        public String getDnumber() {
            return dnumber;
        }

        public void setDnumber(String dnumber) {
            this.dnumber = dnumber;
        }

        public int getDiastolicpressure() {
            return diastolicpressure;
        }

        public void setDiastolicpressure(int diastolicpressure) {
            this.diastolicpressure = diastolicpressure;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getRidcard() {
            return ridcard;
        }

        public void setRidcard(String ridcard) {
            this.ridcard = ridcard;
        }

        public String getRaddress() {
            return raddress;
        }

        public void setRaddress(String raddress) {
            this.raddress = raddress;
        }

        public String getRname() {
            return rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public String getRphone() {
            return rphone;
        }

        public void setRphone(String rphone) {
            this.rphone = rphone;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public int getSystolicpressure() {
            return systolicpressure;
        }

        public void setSystolicpressure(int systolicpressure) {
            this.systolicpressure = systolicpressure;
        }

        public int getMeanpressure() {
            return meanpressure;
        }

        public void setMeanpressure(int meanpressure) {
            this.meanpressure = meanpressure;
        }
    }
}
