package com.niwj.graduationproject.control;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Stack;

import cn.jpush.android.api.JPushInterface;

import static com.niwj.graduationproject.RegisterActivity.KEY_IDCARD;
import static com.niwj.graduationproject.RegisterActivity.KEY_PASSWORD;
import static com.niwj.graduationproject.RegisterActivity.USER_FILENAME;
import static com.niwj.graduationproject.api.BaseAPIUtils.CHECK_URL;

/**
 * Created by prince70 on 2017/9/28.
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */

public class AppManager {
    private static final String TAG = "AppManager";

    public static Stack<Activity> activityStack = new Stack<Activity>();

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        return activityStack.lastElement();
    }


    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        Activity activity = activityStack.pop();
        activity.finish();
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出应用程序并删除用户信息，登录状态位置为0
     */
    public static void AppExitAll(final Context context) {
//      DONE 因为已经在别处登录了，不用置状态位为0了  删除所有资料即可
        LoginUtils.loginOut(context);
        try {
            finishAllActivity();
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
