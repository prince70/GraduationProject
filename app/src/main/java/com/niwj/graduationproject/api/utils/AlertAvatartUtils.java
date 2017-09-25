package com.niwj.graduationproject.api.utils;

import com.niwj.graduationproject.api.BaseAPIUtils;
import com.niwj.graduationproject.api.interfaces.AlertAvatarInterface;
import com.niwj.graduationproject.api.pojo.AlertAvatar;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by prince70 on 2017/9/24.
 */

public class AlertAvatartUtils {

    public static Call<AlertAvatar> alertAvatarCall(String dUserId, MultipartBody.Part file) {
        Retrofit retrofit = BaseAPIUtils.getRetrofit();
        AlertAvatarInterface alertAvatarInterface = retrofit.create(AlertAvatarInterface.class);
        return alertAvatarInterface.alertAvatar(dUserId, file);
    }
}
