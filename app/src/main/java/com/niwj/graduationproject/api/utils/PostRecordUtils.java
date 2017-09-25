package com.niwj.graduationproject.api.utils;

import com.niwj.graduationproject.api.BaseAPIUtils;
import com.niwj.graduationproject.api.interfaces.PostRecordInterface;
import com.niwj.graduationproject.api.pojo.PostRecord;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by prince70 on 2017/9/24.
 */

public class PostRecordUtils {

    public static Call<PostRecord> postRecordCall(String dNumber, RequestBody body){
        Retrofit retrofit = BaseAPIUtils.getRetrofit();
        PostRecordInterface postRecordInterface = retrofit.create(PostRecordInterface.class);
        return postRecordInterface.postRecord(dNumber, body);
    }
}
