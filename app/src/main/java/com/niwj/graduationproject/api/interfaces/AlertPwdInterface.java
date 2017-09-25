package com.niwj.graduationproject.api.interfaces;

import com.niwj.graduationproject.api.pojo.AlertPwd;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by prince70 on 2017/9/25.
 */

public interface AlertPwdInterface {
    @POST("/doctor/alert/password")
    Call<AlertPwd> alertPwd(@Query("duserid") String duserid,
                            @Query("oldpassword") String oldpassword,
                            @Query("dpassword") String dpassword);
}
