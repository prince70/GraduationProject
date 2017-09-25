package com.niwj.graduationproject.api.interfaces;

import com.niwj.graduationproject.api.pojo.GetRecords;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by prince70 on 2017/9/25.
 * 获取医生对应的所有体检信息，同步
 */

public interface GetRecordsInterface {
    @GET("/resident/get")
    Call<GetRecords> getRecords(@Query("dnumber") String dnumber);
}
