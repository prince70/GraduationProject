package com.niwj.graduationproject.api.pojo;

import java.util.List;

/**
 * Created by prince70 on 2017/9/24.
 * 修改头像
 */


public class AlertAvatar {

    /**
     * data : [{"dpassword":"123456","dphone":"13414901391","dnumber":"D02133178","didcard":"440923199502133178","dname":"倪伟金","duserid":"3vttbfid2iyphnlt2f","heading":"http://niweijin.51vip.biz/upload/3ccecc3755a57b6b.jpg"}]
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
         * dpassword : 123456
         * dphone : 13414901391
         * dnumber : D02133178
         * didcard : 440923199502133178
         * dname : 倪伟金
         * duserid : 3vttbfid2iyphnlt2f
         * heading : http://niweijin.51vip.biz/upload/3ccecc3755a57b6b.jpg
         */

        private String dpassword;
        private String dphone;
        private String dnumber;
        private String didcard;
        private String dname;
        private String duserid;
        private String heading;

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

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }
    }
}
