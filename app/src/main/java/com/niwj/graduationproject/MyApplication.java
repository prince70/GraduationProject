package com.niwj.graduationproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.multidex.*;
import android.support.multidex.BuildConfig;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mob.MobSDK;
import com.niwj.graduationproject.activity.SettingActivity;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.ui.UILifecycleListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

import org.litepal.LitePal;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by prince70 on 2017/8/3.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static final String APP_ID = "5fdcd2b180";
    private SerialPort mSerialPort = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);//初始化数据库
        MobSDK.init(this, "2064178124118", "fc8191fe576bbacd13f888d38a3489c9");//初始化SMS
//        初始化bugly  注册时申请的APPID
        initBeta();
        Bugly.init(getApplicationContext(), APP_ID, false);

//        初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

    private void initBeta() {

        Beta.autoInit = true;
        Beta.autoCheckUpgrade = false;
        Beta.initDelay = 3 * 1000;
        Beta.largeIconId = R.mipmap.app_logo;
        Beta.smallIconId = R.mipmap.app_logo;
        Beta.defaultBannerId = R.mipmap.ic_login_background;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Beta.showInterruptedStrategy = true;
        Beta.canShowUpgradeActs.add(SettingActivity.class);
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        Beta.tipsDialogLayoutId = R.layout.tips_dialog;
        Beta.upgradeDialogLifecycleListener = new UILifecycleListener<UpgradeInfo>() {
            @Override
            public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onCreate");
            }

            @Override
            public void onStart(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onStart");
            }

            @Override
            public void onResume(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onResume");
            }

            @Override
            public void onPause(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onPause");
            }

            @Override
            public void onStop(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onStop");
            }

            @Override
            public void onDestroy(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onDestory");
            }
        };

        /* 设置更新状态回调接口 */
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeSuccess(boolean isManual) {
                Toast.makeText(getApplicationContext(), "检测到新版本111", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgradeFailed(boolean isManual) {
                Toast.makeText(getApplicationContext(), "检测更新失败111", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onUpgradeFailed: " + isManual);
            }

            @Override
            public void onUpgrading(boolean isManual) {
                Toast.makeText(getApplicationContext(), "正在检查更新111", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                Toast.makeText(getApplicationContext(), "下载完成111", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onUpgradeNoVersion(boolean isManual) {
                Toast.makeText(getApplicationContext(), "目前已经是最新版本111", Toast.LENGTH_SHORT).show();
            }
        };

        Beta.autoDownloadOnWifi = false;
        Beta.canShowApkInfo = true;
        Beta.enableHotfix = false;//关闭热更新能力
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            // Read serial port parameters
            SharedPreferences sp = getSharedPreferences("android_serialport_api.sample_preferences", MODE_PRIVATE);
            String path = sp.getString("DEVICE", "");

            int baudrate = Integer.decode(sp.getString("BAUDRATE", "-1"));

            //Log.v("path", path);
            Log.v("baudrate", Integer.toString(baudrate));

			//* Check parameters *//*
            if ((path.length() == 0) || (baudrate == -1)) {
                //		throw new InvalidParameterException();
            }
            Log.v("state", "pass");
            String str_path = "/dev/ttyMT2";
            int n_baudrate = 115200;
            //* Open the serial port *//*
            //mSerialPort = new SerialPort(new File(path), baudrate, 0);
            mSerialPort = new SerialPort(new File(str_path), n_baudrate, 0);
            Log.v("mytest", "mytest");
        }
        return mSerialPort;
    }
}
