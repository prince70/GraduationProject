package com.niwj.graduationproject.api.interfaces;

import com.niwj.graduationproject.api.pojo.AlertAvatar;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by prince70 on 2017/9/24.
 */

public interface AlertAvatarInterface {
    @Multipart//必须要加这个
    @POST("/doctor/alert/profile")
    Call<AlertAvatar> alertAvatar(@Query("dUserId") String dUserId,
                                  @Part MultipartBody.Part file);
}
