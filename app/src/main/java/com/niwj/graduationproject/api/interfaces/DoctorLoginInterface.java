package com.niwj.graduationproject.api.interfaces;

import com.niwj.graduationproject.api.pojo.DoctorLogin;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by prince70 on 2017/8/3.
 * 医生登录
 */

public interface DoctorLoginInterface {
    @POST("/doctor/login")
    Call<DoctorLogin> doctorLogin(@Query("dIdCard") String dIdCard,
                                  @Query("dPassword") String dPassword);
}
