package com.niwj.graduationproject;

import android.app.Application;

import com.mob.MobSDK;

import org.litepal.LitePal;

/**
 * Created by prince70 on 2017/8/3.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);//初始化数据库
        MobSDK.init(this,"2064178124118","fc8191fe576bbacd13f888d38a3489c9");//初始化SMS
    }
}
