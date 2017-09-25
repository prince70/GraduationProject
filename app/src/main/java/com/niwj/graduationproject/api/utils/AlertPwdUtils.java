package com.niwj.graduationproject.api.utils;

import com.niwj.graduationproject.api.BaseAPIUtils;
import com.niwj.graduationproject.api.interfaces.AlertPwdInterface;
import com.niwj.graduationproject.api.pojo.AlertPwd;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by prince70 on 2017/9/25.
 */

public class AlertPwdUtils {
    public static Call<AlertPwd> alertPwdCall(String duserid,String oldpassword ,String dpassword) {
        Retrofit retrofit = BaseAPIUtils.getRetrofit();
        AlertPwdInterface alertPwdInterface = retrofit.create(AlertPwdInterface.class);
        return alertPwdInterface.alertPwd(duserid, oldpassword,dpassword);
    }
}
