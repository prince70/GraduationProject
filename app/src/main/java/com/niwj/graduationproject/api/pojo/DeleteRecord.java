package com.niwj.graduationproject.api.pojo;

/**
 * Created by prince70 on 2017/9/24.
 */

public class DeleteRecord {

    /**
     * data : 删除成功
     * state : true
     * code : 0
     */

    private String data;
    private String state;
    private int code;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
}
