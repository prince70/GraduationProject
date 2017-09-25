package com.niwj.graduationproject.api.utils;

import com.niwj.graduationproject.api.BaseAPIUtils;
import com.niwj.graduationproject.api.interfaces.DelRecordInterface;
import com.niwj.graduationproject.api.pojo.DeleteRecord;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by prince70 on 2017/9/24.
 */

public class DelRecordUtils {
    public static Call<DeleteRecord> deleteRecordCall(String dnumber, String ctime) {
        Retrofit retrofit = BaseAPIUtils.getRetrofit();
        DelRecordInterface delRecordInterface = retrofit.create(DelRecordInterface.class);
        return delRecordInterface.delRecord(dnumber, ctime);
    }
}
