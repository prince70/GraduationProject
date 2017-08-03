package com.niwj.graduationproject.api.interfaces;

import com.niwj.graduationproject.api.pojo.DoctorLogin;
import com.niwj.graduationproject.api.pojo.DoctorProfile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by prince70 on 2017/8/3.
 * 获取医生资料
 */

public interface GetProfileInterface {
    @GET("/doctor/profile")
    Call<DoctorProfile> getProfile(@Query("dUserId") String dUserId);
}
