package com.niwj.graduationproject;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.mob.MobSDK;

import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;

/**
 * Created by prince70 on 2017/8/3.
 */

public class MyApplication extends Application {

    private SerialPort mSerialPort = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);//初始化数据库
        MobSDK.init(this,"2064178124118","fc8191fe576bbacd13f888d38a3489c9");//初始化SMS
    }
    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
			/* Read serial port parameters */
            SharedPreferences sp = getSharedPreferences("android_serialport_api.sample_preferences", MODE_PRIVATE);
            String path = sp.getString("DEVICE", "");

            int baudrate = Integer.decode(sp.getString("BAUDRATE", "-1"));

            //Log.v("path", path);
            Log.v("baudrate", Integer.toString(baudrate));

			/* Check parameters */
            if ( (path.length() == 0) || (baudrate == -1)) {
                //		throw new InvalidParameterException();
            }
            Log.v("state", "pass");
            String str_path = "/dev/ttyMT2";
            int n_baudrate = 115200;
			/* Open the serial port */
            //mSerialPort = new SerialPort(new File(path), baudrate, 0);
            mSerialPort = new SerialPort(new File(str_path), n_baudrate, 0);
            Log.v("mytest", "mytest");
        }
        return mSerialPort;
    }
}
