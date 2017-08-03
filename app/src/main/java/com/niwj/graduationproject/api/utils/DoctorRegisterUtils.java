package com.niwj.graduationproject.api.utils;

import com.niwj.graduationproject.api.BaseAPIUtils;
import com.niwj.graduationproject.api.interfaces.DoctorRegisterInterface;
import com.niwj.graduationproject.api.pojo.DoctorRegister;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by 伟金 on 2017/7/25.
 * 医生注册Utils
 */

public class DoctorRegisterUtils {

    public static Call<DoctorRegister> doctorRegister(String dIdCard, String dName, String dNumber, String dPhone, String dPassword) {
        Retrofit retrofit = BaseAPIUtils.getRetrofit();
        DoctorRegisterInterface doctorRegisterInterface = retrofit.create(DoctorRegisterInterface.class);
        return doctorRegisterInterface.doctorRegister(dIdCard, dName, dNumber, dPhone, dPassword);
    }

}
