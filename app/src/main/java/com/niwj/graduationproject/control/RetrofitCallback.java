package com.niwj.graduationproject.control;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prince70 on 2017/9/24.
 * 回调
 */

public abstract class RetrofitCallback <T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            onSuccess(call, response);
        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }

    public abstract void onSuccess(Call<T> call, Response<T> response);
    public void onLoading(long total, long progress) {
    }
}