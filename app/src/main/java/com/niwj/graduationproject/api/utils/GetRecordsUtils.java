package com.niwj.graduationproject.api.utils;

import com.niwj.graduationproject.api.BaseAPIUtils;
import com.niwj.graduationproject.api.interfaces.GetRecordsInterface;
import com.niwj.graduationproject.api.pojo.GetRecords;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by prince70 on 2017/9/25.
 */

public class GetRecordsUtils {
    public static Call<GetRecords> getRecordsCall(String dnumber) {
        Retrofit retrofit = BaseAPIUtils.getRetrofit();
        GetRecordsInterface getRecordsInterface = retrofit.create(GetRecordsInterface.class);
        return getRecordsInterface.getRecords(dnumber);
    }
}
