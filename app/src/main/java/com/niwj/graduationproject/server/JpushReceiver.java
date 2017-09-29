package com.niwj.graduationproject.server;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.niwj.graduationproject.LoginActivity;
import com.niwj.graduationproject.MainActivity;
import com.niwj.graduationproject.control.AppManager;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.SharePreferenceUtil;
import com.niwj.graduationproject.control.Utils;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

import static com.niwj.graduationproject.RegisterActivity.KEY_IDCARD;
import static com.niwj.graduationproject.RegisterActivity.KEY_PASSWORD;
import static com.niwj.graduationproject.RegisterActivity.USER_FILENAME;
import static com.niwj.graduationproject.api.BaseAPIUtils.CHECK_URL;

public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = "JpushReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        // Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + AndroidUtil.printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.e(TAG, "onReceive: " + intent.getAction());
            //Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "onReceive: " + intent.getAction());

            Log.e("jpush", bundle.getString(JPushInterface.EXTRA_MESSAGE) + "自定义消息");
            System.out.println(bundle.getString(JPushInterface.EXTRA_MESSAGE) + "自定义消息");
//            通知在这个activity展示
//            Intent mIntent = new Intent(context, LoginActivity.class);
            Intent mIntent = new Intent();

            Log.e(TAG, "onReceive:Intent !!!! " + mIntent);
            //mIntent.putExtra("intentType", 0);

            mIntent.putExtra("MessageContent", bundle.getString(JPushInterface.EXTRA_MESSAGE));
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Log.e(TAG, "onReceive: 消息！！！" + message);


            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mIntent.setClass(context, LoginActivity.class);
//            context.startActivity(mIntent);




            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppManager.AppExitAll(context);

                        }
                    }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AppManager.AppExitAll(context);
                }
            });
            AlertDialog ad = builder.create();
//ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG); //系统中关机对话框就是这个属性
            ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            ad.setCanceledOnTouchOutside(false);                                   //点击外面区域不会让dialog消失
            ad.show();


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "onReceive: " + intent.getAction());
            //Log.d(TAG, "接受到推送下来的通知");
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.e(TAG, "onReceive: " + intent.getAction());
            //Log.d(TAG, "用户点击打开了通知");
        } else {
            //Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }


}

