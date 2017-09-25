package com.niwj.graduationproject.api.interfaces;

import com.niwj.graduationproject.api.pojo.DeleteRecord;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by prince70 on 2017/9/24.
 */

public interface DelRecordInterface {
    @POST("/resident/del")
    Call<DeleteRecord> delRecord(@Query("dnumber") String dnumber,
                                 @Query("ctime") String ctime);
}
