package com.niwj.graduationproject.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.niwj.graduationproject.LoginActivity;
import com.niwj.graduationproject.R;
import com.niwj.graduationproject.api.pojo.DoctorProfile;
import com.niwj.graduationproject.api.pojo.LoginBean;
import com.niwj.graduationproject.api.utils.GetProfileUtils;
import com.niwj.graduationproject.entity.UserInfo;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.niwj.graduationproject.RegisterActivity.KEY_HEADIMG;
import static com.niwj.graduationproject.RegisterActivity.KEY_IDCARD;
import static com.niwj.graduationproject.RegisterActivity.KEY_NAME;
import static com.niwj.graduationproject.RegisterActivity.KEY_NUMBER;
import static com.niwj.graduationproject.RegisterActivity.KEY_PASSWORD;
import static com.niwj.graduationproject.RegisterActivity.KEY_PHONE;
import static com.niwj.graduationproject.RegisterActivity.KEY_USERID;
import static com.niwj.graduationproject.RegisterActivity.USER_FILENAME;

/**
 * 登录Utils
 */
public class LoginUtils {

    private static final String TAG = "LoginUtils";

    //登录状态改变广播Action
    public static final String ACTION_LOGIN_STATUS_CHANGE = "ACTION_LOGIN_STATUS_CHANGE";
    private static final int STROKE_WIDTH = 4;
    public static final String REGISTER_RECEIVE = "register_receive";


    private static LoginFinishListener mLoginFinishListener;    //登录完成的回调


    /**
     * 获取当前用户id，没找到则返回空串
     */

    public static String getUserId(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        String userid = sp.getString(KEY_USERID, "");
        return userid;
    }


    /**
     * 读取身份证号
     */
    public static String getIdCard(Context context) {
        if (getUserId(context) == null) return null;
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_IDCARD, "");
    }


    /**
     * 读取用户名
     */
    public static String getUsername(Context context) {
        if (getUserId(context) == null) return null;
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_NAME, "");
    }

    /**
     * 获取工号
     *
     * @param context
     * @return
     */
    public static String getNumber(Context context) {
        if (getUserId(context) == null) return null;
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_NUMBER, "");
    }

    /**
     * 获取手机号
     */
    public static String getLastMobile(Context context) {
        if (getUserId(context) == null) return null;
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_PHONE, "");
    }

    /**
     * 设置头像路径
     */
    public static void setHeadimg(Context context, String headimg) {
        if (isHasLogin(context)) {
            SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
            sp.setString(KEY_HEADIMG, headimg);
        }
    }

    /**
     * 读取头像路径
     */
    public static String getHeadimg(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_HEADIMG, "");
    }


    /**
     * 登录用户
     */
    public static boolean loginIn(Context context, LoginBean bean) {
        if (context == null) return false;
        //获取用户资料信息
        if (!getProfile(context, bean.getDuserid())) return false;
        List<UserInfo> list = DataSupport.where("username = ?", bean.getDname()).limit(1).find(UserInfo.class);
        if (list.isEmpty()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setDname(bean.getDname());
            if (!userInfo.save()) {
                Log.e(TAG, "保存新建用户失败");
                return false;
            }
        }
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        sp.setString(KEY_USERID, bean.getDuserid());
        sp.setString(KEY_NAME, bean.getDname());
        sp.setString(KEY_IDCARD, bean.getDidcard());
        bean.getDnumber();
        bean.getDphone();

        return true;
    }


    /**
     * 注销用户
     */
    public static void loginOut(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        sp.remove(KEY_USERID);
        sp.remove(KEY_NAME);
        sp.remove(KEY_IDCARD);
        sp.remove(KEY_PHONE);
        sp.remove(KEY_HEADIMG);
        sp.remove(KEY_NUMBER);
        sendBroadCase(context, false);
    }

    /**
     * 判断是否已登录
     */
    public static boolean isHasLogin(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.hasKey(KEY_USERID) && !sp.getString(KEY_USERID, "").isEmpty();
    }


    /**
     * 登录检测
     */
    public static boolean loginCheck(Activity activity, LoginFinishListener listener) {
        if (!isHasLogin(activity)) {
            mLoginFinishListener = listener;
            Toast.makeText(activity, activity.getString(R.string.enter_after_login), Toast.LENGTH_SHORT).show();
            //打开登录的Activity
            activity.startActivityForResult(new Intent(activity, LoginActivity.class), LoginActivity.REQUEST_CODE);
            return false;
        }
        if (listener != null) {
            listener.loginFinish();
        }
        return true;
    }

    public static void loginCheckOnActivityResult(Activity activity, int requestCode, int resultCode) {
        if (requestCode == LoginActivity.REQUEST_CODE) {
            if (resultCode == LoginActivity.RESULT_CODE_LOGIN_SUCCESS) {
                if (mLoginFinishListener != null) {
                    mLoginFinishListener.loginFinish();
                }
            } else {
                activity.finish();
            }
        }
    }

    /**
     * 获取用户资料信息
     *
     * @return 是否成功
     */
    public static boolean getProfile(final Context context, String user_id) {
        Call<DoctorProfile> call = GetProfileUtils.getProfile(user_id);
        try {
            Response<DoctorProfile> response = call.execute();
            DoctorProfile body = response.body();
            if (body != null) {
                if (body.getCode() == 0) {
                    List<DoctorProfile.DataBean> data = body.getData();
                    if (data != null) {
                        //保存到配置文件
                        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
                        sp.setString(KEY_IDCARD, data.get(0).getDidcard());
                        sp.setString(KEY_NAME, data.get(0).getDname());
                        sp.setString(KEY_NUMBER, data.get(0).getDnumber());
                        sp.setString(KEY_PHONE, data.get(0).getDphone());
                        sp.setString(KEY_PASSWORD, data.get(0).getDpassword());
                        sp.setString(KEY_HEADIMG, data.get(0).getHeading());
                    }
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 登录的回调
     */
    public interface LoginListener {
        /**
         * 返回的数据
         *
         * @param code 状态码
         */
        void onResponse(int code);
    }

    /**
     * 发送登陆状态改变的广播
     *
     * @param status true为登陆，false为注销
     */
    private static void sendBroadCase(Context context, boolean status) {
        Intent intent = new Intent();
        intent.putExtra("status", status);
        intent.setAction(ACTION_LOGIN_STATUS_CHANGE);
        context.sendBroadcast(intent);
    }

    /**
     * 设置身份证输入框字符限制
     */
    public static void setIdCardInputLimit(EditText et) {
        String usernameDigits = "0123456789Xx";
        et.setKeyListener(DigitsKeyListener.getInstance(usernameDigits));
    }

    /**
     * 设置密码输入框字符限制
     */
    public static void setPasswordInputLimit(EditText et) {
        //限制输入的字符
        final String passwordDigits = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM~!@#$%^&*()_+-=`{}|[]\\;':\"<>?,./";
        et.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return passwordDigits.toCharArray();
            }

            @Override
            public int getInputType() {
                return 1;   //英文键盘
            }
        });
    }

    /**
     * 登录完成的回调
     */
    public interface LoginFinishListener {
        /**
         * 登录状态
         */
        void loginFinish();
    }
}
