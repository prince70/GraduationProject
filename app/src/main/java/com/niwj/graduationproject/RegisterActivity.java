package com.niwj.graduationproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.niwj.graduationproject.api.pojo.DoctorRegister;
import com.niwj.graduationproject.api.utils.DoctorRegisterUtils;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.SharePreferenceUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 用户注册页面
 */
public class RegisterActivity extends ActionBarActivity {
    public static final String USER_FILENAME="userFile";
    public static final String KEY_USERID="duserid";
    public static final String KEY_NAME="dname";
    public static final String KEY_IDCARD="didcard";
    public static final String KEY_NUMBER="dnumber";
    public static final String KEY_PHONE="dphone";
    public static final String KEY_PASSWORD="dpassword";
    public static final String KEY_HEADIMG="dheading";



    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button registerButton;
    private Button linkToLoginButton;
    private EditText idCardInput;
    private EditText nameInput;
    private EditText numberInput;
    private EditText phoneInput;
    private EditText passwordInput;
    private CheckBox cb_showPwd;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idCardInput = (EditText) findViewById(R.id.idCardInput);//身份证输入框
        nameInput = (EditText) findViewById(R.id.nameInput);//姓名输入框
        numberInput = (EditText) findViewById(R.id.numberInput);//工号输入框
        phoneInput = (EditText) findViewById(R.id.phoneInput);//手机号输入框
        passwordInput = (EditText) findViewById(R.id.passwordInput);//密码输入框
        cb_showPwd = (CheckBox) findViewById(R.id.cb_showPwd);

        registerButton = (Button) findViewById(R.id.registerButton);//注册按钮
        linkToLoginButton = (Button) findViewById(R.id.linkToLoginScreenButton);//跳转到登陆页面按钮

        progressDialog = new ProgressDialog(this);//进度条
        progressDialog.setCancelable(false);

        //注册按钮点击操作
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCard = idCardInput.getText().toString().trim();
                String name = nameInput.getText().toString().trim();
                String number = numberInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                boolean cancel = false;
                View focusView = null;  //焦点
                String errorMsg = null;
//                判断身份证是否为空
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

                //判断名字是否为空
                if (TextUtils.isEmpty(name)) {
                    focusView = nameInput;
                    cancel = true;
                    errorMsg = getResources().getString(R.string.name_tip);
                    nameInput.setError(errorMsg);
                }

                //判断工号是否为空且是否符合格式,必须包含D,9位数，身份证后八位
                if (TextUtils.isEmpty(number)) {
                    focusView = numberInput;
                    cancel = true;
                    errorMsg = getResources().getString(R.string.number_tip);
                    numberInput.setError(errorMsg);
                } else if (!isDnumber(number)) {
                    focusView = numberInput;
                    cancel = true;
                    errorMsg = getResources().getString(R.string.number_error);
                    numberInput.setError(errorMsg);
                }

//                判断手机号是否为空且是否符合格式
                if (TextUtils.isEmpty(phone)) {
                    focusView = phoneInput;
                    cancel = true;
                    errorMsg = getResources().getString(R.string.phone_tip);
                    phoneInput.setError(errorMsg);

                } else if (!isPhone(phone)) {
                    focusView = phoneInput;
                    cancel = true;
                    errorMsg = getResources().getString(R.string.phone_error);
                    phoneInput.setError(errorMsg);
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


                //判断用户输入是否有效,如果有效,则进行注册操作
                if (!cancel) {
                    registerUser(idCard, name, number, phone, password);
                } else {
                    focusView.requestFocus();
                    cancel = false;
//                    Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }
        });

        //跳转到登陆页面操作
        linkToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //注册用户
    private void registerUser(String idcard, String name, String number, String phone, String password) {
        progressDialog.setMessage("注册中...");
        showDialog();

        Call<DoctorRegister> call = DoctorRegisterUtils.doctorRegister(idcard, name, number, phone, password);
        call.enqueue(new Callback<DoctorRegister>() {
            @Override
            public void onResponse(Call<DoctorRegister> call, Response<DoctorRegister> response) {
                Request request = call.request();
                Log.e(TAG, "onResponse: " + request.toString());
                DoctorRegister body = response.body();
                int code = body.getCode();
                if (code == 0) {
                    String duserid = body.getData().get(0).getDuserid();
                    String dname = body.getData().get(0).getDname();
                    String didcard = body.getData().get(0).getDidcard();
                    String dnumber = body.getData().get(0).getDnumber();
                    String dphone = body.getData().get(0).getDphone();
                    String dpassword = body.getData().get(0).getDpassword();
                    Log.e(TAG, "onResponse: 注册成功后"+duserid+dname+didcard+dnumber+dphone+dpassword );
//                    SharePreferenceUtil sp = SharePreferenceUtil.getInstance(RegisterActivity.this,USER_FILENAME);
//                    sp.setString(KEY_USERID,duserid);
//                    sp.setString(KEY_NAME,dname);
//                    sp.setString(KEY_IDCARD,didcard);
//                    sp.setString(KEY_NUMBER,dnumber);
//                    sp.setString(KEY_PHONE,dphone);
//                    sp.setString(KEY_PASSWORD,dpassword);
                    Log.e(TAG, "onResponse: " + R.string.regiester_success);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, R.string.regiester_success, Toast.LENGTH_SHORT).show();
                            final Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            Timer timer = new Timer();
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(intent);
                                }
                            };
                           timer.schedule(task, 1000);
                        }
                    });

                    hideDialog();
                }else {
                    Toast.makeText(RegisterActivity.this, R.string.regiester_fail, Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            }

            @Override
            public void onFailure(Call<DoctorRegister> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, R.string.server_down, Toast.LENGTH_SHORT).show();
                        hideDialog();
                    }
                });
                Request request = call.request();
                Log.e(TAG, "onResponse: " + R.string.regiester_fail + request.toString() + t.toString());
            }
        });

    }

    //显示进度条
    private void showDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    //隐藏进度条
    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    /**
     * 判断医生工号是否符合规范
     * 字符串比较用equals
     *
     * @param number
     * @return
     */
    private boolean isDnumber(String number) {
        String idcard = idCardInput.getText().toString().trim();
        if (!number.substring(0, 1).contains("D")) {
            return false;
        } else if (!(number.length() == 9)) {
            return false;
        } else if (!(number.substring(1, 9).equals(idcard.substring(10, idcard.length())))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断密码是否符合规范
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断身份证是否符合规范
     *
     * @param idcard
     * @return
     */
    private boolean isIdcard(String idcard) {
        Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher m = p.matcher(idcard);
        return m.matches();
    }

    /**
     * 判断手机号码是否符合规范
     *
     * @param phone
     * @return
     */
    private boolean isPhone(String phone) {
        Pattern p = Pattern.compile("^1\\d{10}");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public void IsShowPwd(View view) {
        if (cb_showPwd.isChecked()) {
            passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}