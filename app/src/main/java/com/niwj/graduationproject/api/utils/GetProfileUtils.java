package com.niwj.graduationproject.api.utils;

import com.niwj.graduationproject.api.BaseAPIUtils;
import com.niwj.graduationproject.api.interfaces.DoctorLoginInterface;
import com.niwj.graduationproject.api.interfaces.GetProfileInterface;
import com.niwj.graduationproject.api.pojo.DoctorLogin;
import com.niwj.graduationproject.api.pojo.DoctorProfile;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by 伟金 on 2017/7/25.
 * 获取医生详情Utils
 */

public class GetProfileUtils {

    public static Call<DoctorProfile> getProfile(String dUserId) {
        Retrofit retrofit = BaseAPIUtils.getRetrofit();
        GetProfileInterface profileInterface = retrofit.create(GetProfileInterface.class);
        return profileInterface.getProfile(dUserId);
    }

}
