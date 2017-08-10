package com.niwj.graduationproject.api.utils;

import com.niwj.graduationproject.api.BaseAPIUtils;
import com.niwj.graduationproject.api.interfaces.DoctorLoginInterface;
import com.niwj.graduationproject.api.interfaces.DoctorRegisterInterface;
import com.niwj.graduationproject.api.pojo.DoctorLogin;
import com.niwj.graduationproject.api.pojo.DoctorRegister;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by 伟金 on 2017/7/25.
 * 医生登录Utils
 */

public class DoctorLoginUtils {

    public static Call<DoctorLogin> doctorLogin(String dIdCard, String dPassword) {
        Retrofit retrofit = BaseAPIUtils.getRetrofit();
        DoctorLoginInterface loginInterface = retrofit.create(DoctorLoginInterface.class);
        return loginInterface.doctorLogin(dIdCard, dPassword);
    }

}
