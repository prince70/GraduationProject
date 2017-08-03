package com.niwj.graduationproject.api.interfaces;

import com.niwj.graduationproject.api.pojo.DoctorRegister;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 伟金 on 2017/7/25.
 * 医生注册
 */

public interface DoctorRegisterInterface {
    @POST("/doctor/register")
    Call<DoctorRegister> doctorRegister(@Query("dIdCard") String dIdCard,
                                     @Query("dName") String dName,
                                     @Query("dNumber") String dNumber,
                                     @Query("dPhone") String dPhone,
                                     @Query("dPassword") String dPassword);

}
