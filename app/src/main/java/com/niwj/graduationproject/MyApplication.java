package com.niwj.graduationproject;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by prince70 on 2017/8/3.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);//初始化数据库
    }
}
