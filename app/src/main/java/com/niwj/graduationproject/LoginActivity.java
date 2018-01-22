package com.niwj.graduationproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.niwj.graduationproject.api.pojo.DoctorLogin;
import com.niwj.graduationproject.api.utils.DoctorLoginUtils;
import com.niwj.graduationproject.control.AppManager;
import com.niwj.graduationproject.control.ImageToast;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.SharePreferenceUtil;
import com.niwj.graduationproject.control.Utils;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.niwj.graduationproject.RegisterActivity.KEY_IDCARD;
import static com.niwj.graduationproject.RegisterActivity.KEY_NAME;
import static com.niwj.graduationproject.RegisterActivity.KEY_NUMBER;
import static com.niwj.graduationproject.RegisterActivity.KEY_PASSWORD;
import static com.niwj.graduationproject.RegisterActivity.KEY_PHONE;
import static com.niwj.graduationproject.RegisterActivity.KEY_USERID;
import static com.niwj.graduationproject.RegisterActivity.USER_FILENAME;
import static com.niwj.graduationproject.api.BaseAPIUtils.CHECK_URL;
import static com.niwj.graduationproject.control.LoginUtils.isHasLogin;

/**
 * 用户登陆页面
 * 检测是否已经登录，登录记录，直接跳转到主页面
 */
public class LoginActivity extends BaseActivity {
    //  判断是否退出
    private boolean mIsExit;


    //   private static final String TAG = Reg
    private static final String TAG = "LoginActivity";
    private Button loginButton;
    private Button linkToRegisterButton;
    private EditText idCardInput;
    private EditText passwordInput;
    private CheckBox cb_showPwd;
    private ProgressDialog progressDialog;

    private EventHandler eventHandler;
    public static final int REQUEST_CODE = 5663;
    public static final int RESULT_CODE_LOGIN_SUCCESS = 5361;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(R.layout.activity_login);
        Log.e(TAG, "onCreate: " + "LoginActivity");
//        x.view().inject(this);
//        init();
        boolean hasLogin = isHasLogin(this);
        if (hasLogin) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        /**
         * mob
         */
        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
//        SMSSDK.setAskPermisionOnReadContact(boolShowInDialog);

        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    String msg = throwable.getMessage();
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                    }
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);


        //            有记录的话就读取记录
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(LoginActivity.this);
        String idcard = sp.getString(KEY_IDCARD, "");
        String idcard1 = sp.getString("idcard", "");
        String pwd = sp.getString(KEY_PASSWORD, "");
        Log.e(TAG, "onCreate: hhhhhhh" + idcard + "   " + pwd);


        idCardInput = (EditText) findViewById(R.id.idCardInput);//身份证输入框

        passwordInput = (EditText) findViewById(R.id.passwordInput);//密码输入框

        idCardInput.setText(idcard1);
        passwordInput.setText(pwd);

        cb_showPwd = (CheckBox) findViewById(R.id.cb_showPwd);
        loginButton = (Button) findViewById(R.id.loginButton);//登陆按钮

        linkToRegisterButton = (Button) findViewById(R.id.linkToRegisterScreenButton);//跳转到注册页面按钮

        progressDialog = new ProgressDialog(this);//进度条
        progressDialog.setCancelable(false);
        loginButton.setOnClickListener(new loginOnClickListener());
        linkToRegisterButton.setOnClickListener(new linkToRegisterOnClickListener());


    }

    private void init() {
        //利用Intent判断是否有自定义消息
        String message = getIntent().getStringExtra("MessageContent");
        if (message != null && !message.equals("")) {
            new AlertDialog.Builder(this).setTitle("系统提示").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO 在这里可清除本地的用户信息  发送到服务器，登录状态位置为0


                }
            }).setNegativeButton("重新登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO 再次执行登录操作  清除本地的用户信息  发送到服务器，登录状态位置为0
                }
            }).show();
            Log.e(TAG, "init: " + "你要被强制下线，我告诉你");
        }

    }

    //登陆按钮点击操作
    private class loginOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final String idCard = idCardInput.getText().toString().trim();
            final String password = passwordInput.getText().toString().trim();
            boolean cancel = false;
            View focusView = null;  //焦点
            String errorMsg = null;
            //判断输入是否为空
            if (TextUtils.isEmpty(idCard)) {
                if (TextUtils.isEmpty(idCard)) {
                    focusView = idCardInput;
                    cancel = true;
                    errorMsg = getResources().getString(R.string.idCard_tip);
                    idCardInput.setError(errorMsg);
                } else if (!isIdcard(idCard)) {
                    focusView = idCardInput;
                    cancel = true;
                    errorMsg = getResources().getString(R.string.idCard_error);
                    idCardInput.setError(errorMsg);
                }
            }
            //判断密码是否为空
            if (TextUtils.isEmpty(password)) {
                focusView = passwordInput;
                cancel = true;
                errorMsg = getResources().getString(R.string.password_tip);
                passwordInput.setError(errorMsg);
            } else if (!isPasswordValid(password)) {
                focusView = passwordInput;
                cancel = true;
                errorMsg = getResources().getString(R.string.password_error);
                passwordInput.setError(errorMsg);
            }
            if (!cancel) {
                SharePreferenceUtil sp = SharePreferenceUtil.getInstance(LoginActivity.this);
                sp.setString("idcard", idCard);

                RequestParams params = new RequestParams(CHECK_URL);
                params.addParameter("username", idCard);
                params.addParameter("password", password);
                Log.e(TAG, "onClick: " + idCard + "   " + password);
                /**
                 * 登录的时候分配一个RegistrationID，别的设备再次登录时也会产生一个RegistrationID，则会发生冲突，则会发送广播通知下线
                 */
                params.addParameter("registrationId", JPushInterface.getRegistrationID(getApplicationContext()));
                x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e(TAG, "onSuccess: 服务器返回的值  " + result);
                        if (result.equals("login")) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            checkLogin(idCard, password);
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败--账号密码问题", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e(TAG, "onError: " + ex.toString() + "       " + isOnCallback);
                        Toast.makeText(LoginActivity.this, "登录失败---网络问题", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

            } else {
                focusView.requestFocus();
                cancel = false;
                Toast.makeText(LoginActivity.this, R.string.idcard_pwd_input, Toast.LENGTH_SHORT).show();
            }

        }
    }

    //跳转到注册页面按钮点击操作
    private class linkToRegisterOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //跳转到注册页面
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
//            finish();
        }
    }

    //用户登陆操作
    private void checkLogin(final String idcard, final String password) {
        String tag_string_req = "req_login";
        progressDialog.setMessage("登录中...");
        showDiaglog();

        Call<DoctorLogin> call = DoctorLoginUtils.doctorLogin(idcard, password);
        call.enqueue(new Callback<DoctorLogin>() {
            @Override
            public void onResponse(@NonNull Call<DoctorLogin> call, @NonNull Response<DoctorLogin> response) {
                Request request = call.request();
                Log.e(TAG, "onResponse: " + request.toString());
                DoctorLogin body = response.body();
                if (body != null) {
                    int code = body.getCode();
                    if (code == 0) {
                        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(LoginActivity.this, USER_FILENAME);
                        sp.setString(KEY_USERID, body.getData().get(0).getDuserid());
                        sp.setString(KEY_NAME, body.getData().get(0).getDname());
                        sp.setString(KEY_IDCARD, body.getData().get(0).getDidcard());
                        sp.setString(KEY_NUMBER, body.getData().get(0).getDnumber());
                        sp.setString(KEY_PHONE, body.getData().get(0).getDphone());
                        sp.setString(KEY_PASSWORD, body.getData().get(0).getDpassword());
                        String userId = LoginUtils.getUserId(LoginActivity.this);
                        String number = LoginUtils.getNumber(LoginActivity.this);
                        String username = LoginUtils.getUsername(LoginActivity.this);
                        Log.e(TAG, "onResponse: userId   " + userId + "  number" + number + "  username" + username);
                        Log.e(TAG, "onResponse: " + "登录成功");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);//activity不加入后退栈
                        AppManager.finishCurrentActivity();
                        hideDialog();
                    } else {
                        Toast.makeText(LoginActivity.this, "账号不存在！", Toast.LENGTH_SHORT).show();
                        hideDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<DoctorLogin> call, Throwable t) {
                Request request = call.request();
                Log.e(TAG, "onFailure: " + getResources().getString(R.string.login_fail) + request.toString() + t.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, R.string.server_down, Toast.LENGTH_SHORT).show();
                        hideDialog();
                    }
                });
            }
        });
    }

    //显示进度条
    private void showDiaglog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    //隐藏进度条
    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isIdcard(String idcard) {
        Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher m = p.matcher(idcard);
        return m.matches();
    }

    public void IsShowPwd(View view) {
        if (cb_showPwd.isChecked()) {
            passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
        finish();
    }

    /**
     * 点击2次结束应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                AppManager.AppExit(this);
//                System.exit(0);
            } else {
                ImageToast.ImageToast(this, R.mipmap.ic_help, "再按一次退出", Toast.LENGTH_SHORT);
                mIsExit = true;
//                2秒后mIsExit重新置为false
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}