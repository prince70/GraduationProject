package com.niwj.graduationproject.api.utils;


import com.niwj.graduationproject.api.BaseAPIUtils;
import com.niwj.graduationproject.api.interfaces.BookExamInterface;
import com.niwj.graduationproject.api.pojo.BookExam;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by prince70 on 2017/10/31.
 */

public class BookExamUtils {
    public static Call<BookExam> bookExam(RequestBody body) {
        Retrofit retrofit = BaseAPIUtils.getRetrofit();
        BookExamInterface bookExamInterface = retrofit.create(BookExamInterface.class);
        return bookExamInterface.bookexam(body);
    }
}
