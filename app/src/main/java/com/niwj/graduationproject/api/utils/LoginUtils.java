package com.niwj.graduationproject.api.utils;

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
import com.niwj.graduationproject.api.pojo.DoctorLogin;
import com.niwj.graduationproject.api.pojo.DoctorProfile;
import com.niwj.graduationproject.api.pojo.LoginBean;
import com.niwj.graduationproject.entity.UserInfo;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录
 */
public class LoginUtils {

    private static final String TAG = "LoginUtils";

    //保存用户信息的sp文件名
    private static final String USER_FILENAME = "userFile";
    //登录状态改变广播Action
    public static final String ACTION_LOGIN_STATUS_CHANGE = "ACTION_LOGIN_STATUS_CHANGE";
    private static final int STROKE_WIDTH = 4;
    public static final String REGISTER_RECEIVE = "register_receive";

    //Key名
    private static final String KEY_USERID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IDCARD = "idcard";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_EXPIRE = "expire";
    private static final String KEY_NAME = "name";
    private static final String KEY_HEADIMG = "headimg";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_LASTACCOUNT = "lastAccount";
    private static final String KEY_LASTMOBILE = "lastMobile";
    private static final String KEY_DOCTOR_ID = "doctorId";

    private static LoginFinishListener mLoginFinishListener;    //登录完成的回调

    /**
     * 设置最后手机号
     */
    public static void setLastMobile(Context context, String mobile) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        sp.setString(KEY_LASTMOBILE, mobile);
    }

    /**
     * 获取最后手机号
     */
    public static String getLastMobile(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_LASTMOBILE, "");
    }

    /**
     * 设置最后账号
     */
    public static void setLastAccount(Context context, String account) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        sp.setString(KEY_LASTACCOUNT, account);
    }

    /**
     * 读取记住的最后一个账号
     */
    public static String getLastAccount(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_LASTACCOUNT, "");
    }

    /**
     * 读取用户名
     */
    public static String getUsername(Context context) {
        if (getToken(context) == null) return null;
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_USERNAME, "");
    }

    /**
     * 读取token
     */
    public static String getToken(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        String token = sp.getString(KEY_TOKEN, "");
        return token;
    }

    /**
     * 设置身份证号
     */
    public static void setIdCard(Context context, String idCard) {
        if (isHasLogin(context)) {
            SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
            sp.setString(KEY_IDCARD, idCard);
        }
    }

    /**
     * 读取身份证号
     */
    public static String getIdCard(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_IDCARD, "");
    }

    /**
     * 设置手机号
     */
    public static void setMobile(Context context, String mobile) {
        if (isHasLogin(context)) {
            SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
            sp.setString(KEY_MOBILE, mobile);
        }
    }

    /**
     * 读取手机号
     */
    public static String getMobile(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_MOBILE, "");
    }

    /**
     * 设置名字
     */
    public static void setName(Context context, String name) {
        if (isHasLogin(context)) {
            SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
            sp.setString(KEY_NAME, name);
        }
    }

    /**
     * 读取名字
     */
    public static String getName(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_NAME, "");
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
     * 设置邮箱地址
     */
    public static void setEmail(Context context, String email) {
        if (isHasLogin(context)) {
            SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
            sp.setString(KEY_EMAIL, email);
        }
    }

    /**
     * 读取邮箱地址
     */
    public static String getEmail(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_EMAIL, "");
    }

    /**
     * 读取doctorId
     */

    public static String getDoctorId(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_DOCTOR_ID, "");
    }

    /**
     * 获取当前用户id，没找到则返回空串
     */
    public static String getUserId(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.getString(KEY_USERID, "");
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
        sp.setString(KEY_USERNAME, bean.getDname());
        sp.setString(KEY_IDCARD, bean.getDidcard());
        bean.getDnumber();
        bean.getDphone();

        return true;
    }

    /**
     * 登录操作，发送用户名和密码到服务器
     */
    public static void login(final Context context, String username, String password, final LoginListener listener) {
        if (listener == null) return;

        setLastAccount(context, username);

        Call<DoctorLogin> call = DoctorLoginUtils.doctorLogin(username, password);
        call.enqueue(new Callback<DoctorLogin>() {
            @Override
            public void onResponse(Call<DoctorLogin> call, final Response<DoctorLogin> response) {
                if (response.isSuccessful()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (loginIn(context, new LoginBean())) {
                                sendBroadCase(context, true);
                                listener.onResponse(0);
                            } else {
                                listener.onResponse(-1);
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "账号不存在", Toast.LENGTH_SHORT).show();
                    listener.onResponse(-1);
                }
            }

            @Override
            public void onFailure(Call<DoctorLogin> call, Throwable t) {
                listener.onResponse(-1);
                int resId;
                if (t.getMessage() != null && t.getMessage().contains("Expected BEGIN_OBJECT")) {
                    //密码不正确
                    resId = R.string.password_error;
                } else {
                    resId = R.string.server_down;
                }
                Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "login fail:" + t.getMessage());
            }
        });
    }


    /**
     * 注销用户
     */
    public static void loginOut(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        sp.remove(KEY_USERID);
        sp.remove(KEY_USERNAME);
        sp.remove(KEY_IDCARD);
        sp.remove(KEY_MOBILE);
        sp.remove(KEY_TOKEN);
        sp.remove(KEY_EXPIRE);
        sp.remove(KEY_NAME);
        sp.remove(KEY_HEADIMG);
        sp.remove(KEY_EMAIL);
        sendBroadCase(context, false);

        //清空消息
//        if (MsgFragment.data_msg != null) {
//            MsgFragment.data_msg.clear();
//            MsgFragment.data_msg = null;
//        }
    }

    /**
     * 判断是否已登录
     */
    public static boolean isHasLogin(Context context) {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(context, USER_FILENAME);
        return sp.hasKey(KEY_TOKEN) && !sp.getString(KEY_TOKEN, "").isEmpty();
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
                        sp.setString(KEY_NAME, data.get(0).getDname());
                        data.get(0).getDnumber();
                        data.get(0).getDphone();
                        data.get(0).getDidcard();
                        data.get(0).getDuserid();
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
