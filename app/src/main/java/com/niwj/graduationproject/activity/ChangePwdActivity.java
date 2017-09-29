package com.niwj.graduationproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.niwj.graduationproject.LoginActivity;
import com.niwj.graduationproject.R;
import com.niwj.graduationproject.api.pojo.AlertPwd;
import com.niwj.graduationproject.api.utils.AlertPwdUtils;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.Utils;
import com.niwj.graduationproject.view.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prince70 on 2017/9/25.
 */

public class ChangePwdActivity extends AppCompatActivity {
    private static final String TAG = "ChangePwdActivity";
    private EditText old_pwd, new_pwd, check_new_pwd;
    private TextView error_old, error_new, error_first_new;
    private CheckBox show_pwd;
    private ImageButton clear_oldPwd, clear_newPwd, clear_checkNewPwd;

    private String oldPwd_input;//旧密码
    private String newPwd_input;//新密码
    private String checknewPwd_input;//确认新密码

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);
        initView();
    }

    private void initView() {
        old_pwd = (EditText) findViewById(R.id.old_pwd);
        new_pwd = (EditText) findViewById(R.id.new_pwd);
        check_new_pwd = (EditText) findViewById(R.id.check_new_pwd);

        new_pwd.setFocusableInTouchMode(false);
        new_pwd.setFocusable(false);
        new_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pwd.setFocusableInTouchMode(true);
                new_pwd.setFocusable(true);
            }
        });
        check_new_pwd.setFocusableInTouchMode(false);
        check_new_pwd.setFocusable(false);
        check_new_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_new_pwd.setFocusableInTouchMode(true);
                check_new_pwd.setFocusable(true);
            }
        });

        error_old = (TextView) findViewById(R.id.error_old);
        error_new = (TextView) findViewById(R.id.error_new);
        error_first_new = (TextView) findViewById(R.id.error_first_new);

        show_pwd = (CheckBox) findViewById(R.id.show_pwd);

        clear_oldPwd = (ImageButton) findViewById(R.id.clear_oldPwd);
        clear_newPwd = (ImageButton) findViewById(R.id.clear_newPwd);
        clear_checkNewPwd = (ImageButton) findViewById(R.id.clear_checkNewPwd);

        /**
         * 旧密码
         */
        old_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s != null) {
                    clear_oldPwd.setBackgroundResource(R.mipmap.close_bg);
                }
                old_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if (s.length() == 0) {
                                error_old.setText(R.string.oldpwd_not_input);
                            } else if (s.length() < 6) {
                                error_old.setText(R.string.oldpwd_error);
                            }
                        }
                    }
                });
                oldPwd_input = old_pwd.getText().toString();
                Log.e(TAG, "afterTextChanged:旧密码：" + oldPwd_input);
            }
        });
        if (old_pwd.requestFocus()) {
            old_pwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null) {
                        clear_oldPwd.setBackgroundResource(R.mipmap.close_bg);
                        error_old.setText("");
                    }
                    oldPwd_input = old_pwd.getText().toString();
                    Log.e(TAG, "afterTextChanged: 旧密码：" + oldPwd_input);
                }
            });
        }

        /**
         * 新密码
         */
        new_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s != null) {
                    clear_newPwd.setBackgroundResource(R.mipmap.close_bg);
                }
                new_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if (s.length() == 0) {
                                error_first_new.setText(R.string.newpwd_not_input);
                            } else if (s.length() < 6) {
                                error_first_new.setText(R.string.pwd_less_six);
                            }
                        }
                        newPwd_input = new_pwd.getText().toString();
                        Log.e(TAG, "onFocusChange: 新密码：" + newPwd_input);
                    }
                });
            }
        });
        if (new_pwd.requestFocus()) {
            new_pwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null) {
                        clear_newPwd.setBackgroundResource(R.mipmap.close_bg);
                        error_first_new.setText("");
                    }
                    newPwd_input = new_pwd.getText().toString();
                    Log.e(TAG, "afterTextChanged: 新密码：" + newPwd_input);
                }
            });
        }
        /**
         * 确认新密码
         */
        check_new_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s != null) {
                    clear_checkNewPwd.setBackgroundResource(R.mipmap.close_bg);
                }
                check_new_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        checknewPwd_input = check_new_pwd.getText().toString();
                        Log.e(TAG, "onFocusChange: 确认新密码：" + checknewPwd_input);
                        if (!hasFocus) {
                            if (s.length() == 0) {
                                error_new.setText(R.string.pwd_inconformity);
                            } else if (s.length() < 6) {
                                error_new.setText(R.string.pwd_less_six);
                            } else if (!checknewPwd_input.equals(newPwd_input)) {
                                error_new.setText(R.string.pwd_inconformity);
                            }
                        }
                    }
                });
            }
        });
        if (check_new_pwd.requestFocus()) {
            check_new_pwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null) {
                        clear_checkNewPwd.setBackgroundResource(R.mipmap.close_bg);
                        error_new.setText("");
                    }
                    checknewPwd_input = check_new_pwd.getText().toString();
                    Log.e(TAG, "afterTextChanged: 新密码：" + checknewPwd_input);
                }
            });
        }

    }

    /**
     * 清空旧密码
     *
     * @param view
     */
    public void clear_oldPwd(View view) {
        old_pwd.setText("");
        error_old.setText("");
//        clear_oldPwd.getBackground().setAlpha(0);//透明
        clear_oldPwd.setBackgroundResource(0);//去掉背景
        boolean b = old_pwd.requestFocus();
        Log.e(TAG, "clear_oldPwd: " + b);
    }

    /**
     * 清空新密码
     *
     * @param view
     */
    public void clear_newPwd(View view) {
        new_pwd.setText("");
        error_first_new.setText("");
        clear_newPwd.setBackgroundResource(0);
    }

    /**
     * 清空确认新密码
     *
     * @param view
     */
    public void clear_checkNewPwd(View view) {
        check_new_pwd.setText("");
        error_new.setText("");
        clear_checkNewPwd.setBackgroundResource(0);
    }

    /**
     * 确认修改密码
     *
     * @param view
     */
    public void confirm_pwd(View view) {
        check_new_pwd.setFocusable(false);//让控件失去焦点，得到值

        Log.e(TAG, "confirm_pwd: "+oldPwd_input+"    !!"+newPwd_input+"      !!"+checknewPwd_input );
        if (oldPwd_input == null || newPwd_input == null || checknewPwd_input == null) {
            Toast.makeText(this, R.string.pwd_empty, Toast.LENGTH_SHORT).show();
        } else if (!newPwd_input.equals(checknewPwd_input)) {
            Toast.makeText(this, R.string.pwd_inconformity, Toast.LENGTH_SHORT).show();
            error_new.setText(R.string.pwd_inconformity);
        } else {
            changePwd(LoginUtils.getUserId(this), oldPwd_input, newPwd_input);
        }
    }

    /**
     * 密文明文切换显示
     *
     * @param view
     */
    public void IsShowPwd(View view) {
        if (show_pwd.isChecked()) {
            ChangePwdActivity.this.old_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ChangePwdActivity.this.new_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ChangePwdActivity.this.check_new_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        } else {
            ChangePwdActivity.this.old_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ChangePwdActivity.this.new_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ChangePwdActivity.this.check_new_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void changePwd(String duserid, String oldpassword, String dpassword) {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        Call<AlertPwd> call = AlertPwdUtils.alertPwdCall(duserid, oldpassword, dpassword);
        call.enqueue(new Callback<AlertPwd>() {
            @Override
            public void onResponse(Call<AlertPwd> call, Response<AlertPwd> response) {
                if (response.code() == 200) {
                    loadingDialog.dismiss();
                    Toast.makeText(ChangePwdActivity.this, "密码修改成功,请重新登录", Toast.LENGTH_LONG).show();
                    LoginUtils.loginOut(ChangePwdActivity.this);
                    startActivity(new Intent(ChangePwdActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<AlertPwd> call, Throwable t) {
                Log.e(TAG, "onFailure: " + call.request().toString());
                loadingDialog.dismiss();
                Toast.makeText(ChangePwdActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
