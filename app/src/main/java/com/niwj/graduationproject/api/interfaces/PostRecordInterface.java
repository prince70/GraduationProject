package com.niwj.graduationproject.api.interfaces;

import com.niwj.graduationproject.api.pojo.PostRecord;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by prince70 on 2017/9/24.
 */

public interface PostRecordInterface {
    @POST("/resident/check")
    Call<PostRecord> postRecord(@Query("dnumber") String dnumber,
                                @Body RequestBody body);
}
