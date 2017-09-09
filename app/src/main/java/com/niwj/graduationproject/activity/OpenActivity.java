package com.niwj.graduationproject.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niwj.graduationproject.LoginActivity;
import com.niwj.graduationproject.MainActivity;
import com.niwj.graduationproject.R;
import com.niwj.graduationproject.RegisterActivity;
import com.niwj.graduationproject.UserActivity;
import com.niwj.graduationproject.control.SharePreferenceUtil;

/**
 * Created by prince70 on 2017/9/8.
 * 开锁界面
 */

public class OpenActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OpenActivity";
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    private EditText[] editTexts = new EditText[4];//显示密码框
    private int pos = 0;
    private int times = 0;

    private Vibrator vibrator;//震动
    private TextView btn_delete;//删除键
    private LinearLayout openlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        initView();

    }

    private void initView() {

        btn_delete = (TextView) findViewById(R.id.btn_delete);
        openlayout = (LinearLayout) findViewById(R.id.openlayout);

        editTexts[0] = (EditText) findViewById(R.id.et_pwd1);
        editTexts[1] = (EditText) findViewById(R.id.et_pwd2);
        editTexts[2] = (EditText) findViewById(R.id.et_pwd3);
        editTexts[3] = (EditText) findViewById(R.id.et_pwd4);
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].setBackgroundResource(R.mipmap.circle);
        }
        btn0 = (Button) findViewById(R.id.btn_zero);
        btn1 = (Button) findViewById(R.id.btn_one);
        btn2 = (Button) findViewById(R.id.btn_two);
        btn3 = (Button) findViewById(R.id.btn_three);
        btn4 = (Button) findViewById(R.id.btn_four);
        btn5 = (Button) findViewById(R.id.btn_five);
        btn6 = (Button) findViewById(R.id.btn_six);
        btn7 = (Button) findViewById(R.id.btn_seven);
        btn8 = (Button) findViewById(R.id.btn_eight);
        btn9 = (Button) findViewById(R.id.btn_nine);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        // 添加 内容change listener ：输入焦点后移 + 密码验证
        editTexts[0].addTextChangedListener(new MyTextChangeWatcher());
        editTexts[1].addTextChangedListener(new MyTextChangeWatcher());
        editTexts[2].addTextChangedListener(new MyTextChangeWatcher());
        editTexts[3].addTextChangedListener(new MyTextChangeWatcher());

        //del 监听，输入焦点前移
        editTexts[1].setOnKeyListener(keyListener);
        editTexts[2].setOnKeyListener(keyListener);
        editTexts[3].setOnKeyListener(keyListener);
    }

    private void clearText() {
        editTexts[0].setText("");
        editTexts[0].setBackgroundResource(R.mipmap.circle);
        editTexts[1].setText("");
        editTexts[1].setBackgroundResource(R.mipmap.circle);
        editTexts[2].setText("");
        editTexts[2].setBackgroundResource(R.mipmap.circle);
        editTexts[3].setText("");
        editTexts[3].setBackgroundResource(R.mipmap.circle);
        editTexts[0].requestFocus();
        pos = 0;
    }

    private void deleteText() {
        // 从右往左依次删除  重新设置背景
        if (!TextUtils.isEmpty(editTexts[3].getText().toString())) {
            editTexts[3].setText("");
        } else if (!TextUtils.isEmpty(editTexts[2].getText().toString())) {
            editTexts[2].setText("");
            editTexts[2].setBackgroundResource(R.mipmap.circle);
        } else if (!TextUtils.isEmpty(editTexts[1].getText().toString())) {
            editTexts[1].setText("");
            editTexts[1].setBackgroundResource(R.mipmap.circle);
        } else if (!TextUtils.isEmpty(editTexts[0].getText().toString())) {
            editTexts[0].setText("");
            editTexts[0].setBackgroundResource(R.mipmap.circle);
            btn_delete.setText("");
        }
    }

    /**
     * 重输密码
     *
     * @param view
     */
    public void forget(View view) {
        showDialog();
    }

    public void showDialog() {
        new AlertDialog.Builder(OpenActivity.this)
                .setMessage("        是否退出并清除锁屏密码？")//设置显示的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(OpenActivity.this);
                        sp.remove("FirstPassword");
                        sp.remove("Checked");
                        //还需清除登陆信息
                        Intent intent = new Intent(OpenActivity.this, LoginActivity.class);
                        OpenActivity.this.startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件

            }
        }).show();//在按键响应事件中显示此对话框
    }

    /**
     * 删除文本
     *
     * @param view
     */
    public void deleteText(View view) {
        deleteText();
        if (pos > 0) {
            pos--;
        }
    }

    /**
     * 点击数字
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_zero:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("0");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("0");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("0");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("0");

                }
                break;
            case R.id.btn_one:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("1");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("1");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("1");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("1");

                }
                break;
            case R.id.btn_two:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("2");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("2");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("2");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("2");

                }
                break;
            case R.id.btn_three:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("3");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("3");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("3");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("3");
                }
                break;
            case R.id.btn_four:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("4");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("4");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("4");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("4");
                }
                break;
            case R.id.btn_five:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("5");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("5");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("5");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("5");
                }
                break;
            case R.id.btn_six:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("6");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("6");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("6");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("6");
                }
                break;
            case R.id.btn_seven:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("7");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("7");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("7");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("7");
                }
                break;
            case R.id.btn_eight:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("8");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("8");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("8");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("8");
                }
                break;
            case R.id.btn_nine:
                if (TextUtils.isEmpty(editTexts[0].getText().toString())) {
                    editTexts[0].setText("9");
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && TextUtils.isEmpty(editTexts[1].getText().toString())) {
                    editTexts[1].setText("9");
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && TextUtils.isEmpty(editTexts[2].getText().toString())) {
                    editTexts[2].setText("9");
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (!TextUtils.isEmpty(editTexts[0].getText().toString()) && !TextUtils.isEmpty(editTexts[1].getText().toString()) && !TextUtils.isEmpty(editTexts[2].getText().toString()) && TextUtils.isEmpty(editTexts[3].getText().toString())) {
                    editTexts[3].setText("9");
                }
                break;
            default:
                break;

        }
    }

    class MyTextChangeWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            SharePreferenceUtil sp = SharePreferenceUtil.getInstance(OpenActivity.this);
            String pwd = sp.getString("FirstPassword", "aaaa");
            if (s != null && s.length() == 1) {
                pos++;
                if (pos < 4) {
                    editTexts[pos].requestFocus();
                }
                if (pos == 1) {
                    editTexts[0].setBackgroundResource(R.mipmap.touch);
                    btn_delete.setText("删除");
                } else if (pos == 2) {
                    editTexts[1].setBackgroundResource(R.mipmap.touch);
                } else if (pos == 3) {
                    editTexts[2].setBackgroundResource(R.mipmap.touch);
                } else if (pos == 4) {
                    editTexts[3].setBackgroundResource(R.mipmap.touch);//背景  第四位相同的时候才设置背景
                    //改变布局
                    String pass2 = getcMsg();
                    if (pass2.equals(pwd)) {
                        /**
                         * 跳转回最开始的界面，即进入APP的页面
                         */
                        Log.e(TAG, "afterTextChanged: " + "执行");
//                        DONE 解决为什么跳转不了到mainactivity
                        startActivity(new Intent(OpenActivity.this, MainActivity.class));
                        finish();
                    } else {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        long[] pattern = {10, 200, 10, 200};
                        vibrator.vibrate(pattern, -1);//-1表示只震动一次
                        shakeView();
                        Toast.makeText(OpenActivity.this, R.string.password_wrong, Toast.LENGTH_SHORT).show();
                        clearText();
                        times++;
                    }
                    if (times == 3) {
                        showDialog();
                        times = 0;
                    }
                }
            }
        }
    }

    /**
     * 监听删除键 前移焦点
     */
    private View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (pos > 0) {
                    pos--;
                    if (pos < 3) {
                        editTexts[pos].setText("");
                    }
                    editTexts[pos].requestFocus();
                }
            }
            return false;
        }
    };

    /**
     * 重启activity恢复初始状态
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        clearText();
    }

    /**
     * 获取输入的密码
     *
     * @return
     */
    private String getcMsg() {
        String cpPwd;
        String str1, str2, str3, str4;
        str1 = editTexts[0].getText().toString();
        str2 = editTexts[1].getText().toString();
        str3 = editTexts[2].getText().toString();
        str4 = editTexts[3].getText().toString();
        cpPwd = str1 + str2 + str3 + str4;
        return cpPwd;
    }

    /**
     * 获取第一位数字
     *
     * @return
     */
    private String getFirstNum() {
        String str1;
        str1 = editTexts[0].getText().toString();
        return str1;
    }

    /**
     * 获取第二位数字
     *
     * @return
     */
    private String getSecondNum() {
        String str1;
        str1 = editTexts[1].getText().toString();
        return str1;
    }

    /**
     * 获取第三位数字
     *
     * @return
     */
    private String getThirdNum() {
        String str1;
        str1 = editTexts[2].getText().toString();
        return str1;
    }


    /**
     * shakeView
     */
    public void shakeView() {
        TranslateAnimation animation = new TranslateAnimation(0, -50, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(100);
        animation.setRepeatCount(3);
        animation.setRepeatMode(Animation.REVERSE);
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].startAnimation(animation);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //返回桌面
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
        //结束整个进程  不会记录

        //未解锁情况下每次进去activity都要解锁
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
