package com.niwj.graduationproject.api.interfaces;

import com.niwj.graduationproject.api.pojo.BookExam;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by prince70 on 2017/10/31.
 * 预约体检信息接口
 */

public interface BookExamInterface {
    @POST("/resident/book")
    Call<BookExam> bookexam(@Body RequestBody body);
}
