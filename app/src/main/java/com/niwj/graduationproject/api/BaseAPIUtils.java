package com.niwj.graduationproject.api;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 伟金 on 2017/7/25.
 */

public class BaseAPIUtils {
    public static final String BASE_URL_REALLY = "http://192.168.2.201:8888/";

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BaseAPIUtils.BASE_URL_REALLY)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .build();
    }

    public static Gson buildGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new StringNullDefaultAdapter())
                .registerTypeAdapter(char.class, new StringNullDefaultAdapter())
                .create();
        return gson;
    }

}
