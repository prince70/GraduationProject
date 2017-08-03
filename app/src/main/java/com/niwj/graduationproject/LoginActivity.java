package com.niwj.graduationproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 用户登陆页面
 * 检测是否已经登录，登录记录，直接跳转到主页面
 */
public class LoginActivity extends ActionBarActivity {

 //   private static final String TAG = Reg
 private static final String TAG = "LoginActivity";
    private Button loginButton;
    private Button linkToRegisterButton;
    private EditText emailInput;
    private EditText passwordInput;
    private ProgressDialog progressDialog;


    public static final int REQUEST_CODE = 5663;
    public static final int RESULT_CODE_LOGIN_SUCCESS = 5361;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = (EditText) findViewById(R.id.emailInput);//email输入框
        passwordInput = (EditText) findViewById(R.id.passwordInput);//密码输入框
        loginButton = (Button) findViewById(R.id.loginButton);//登陆按钮
        linkToRegisterButton = (Button) findViewById(R.id.linkToRegisterScreenButton);//跳转到注册页面按钮
        progressDialog = new ProgressDialog(this);//进度条
        progressDialog.setCancelable(false);
        loginButton.setOnClickListener(new loginOnClickListener());
        linkToRegisterButton.setOnClickListener(new linkToRegisterOnClickListener());


    }

    //登陆按钮点击操作
    private class loginOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            //判断输入是否为空
            if (email.trim().length() > 0 && password.trim().length() > 0) {
                checkLogin(email, password);
            }else {
                Toast.makeText(getApplicationContext(), " 请输入邮箱或密码", Toast.LENGTH_LONG).show();
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
            finish();
        }
    }

    //用户登陆操作
    private void checkLogin(final String email, final String password) {
        String tag_string_req = "req_login";
        progressDialog.setMessage("登陆中...");
        showDiaglog();
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
            progressDialog.hide();
        }
    }

    private boolean isEmailValid(String email) {
        if(!email.contains("@")) {
            return false;
        }else {
            return true;
        }
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            return false;
        }else {
            return true;
        }
    }
}