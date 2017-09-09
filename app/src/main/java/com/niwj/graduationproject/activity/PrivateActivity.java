package com.niwj.graduationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.control.SharePreferenceUtil;

import static com.niwj.graduationproject.UserActivity.mCustomSwitch;

/**
 * Created by prince70 on 2017/9/8.
 * 设置锁屏密码
 */

public class PrivateActivity extends AppCompatActivity {
    private static final String TAG = "PrivateActivity";
    private static EditText[] editTexts = new EditText[4];
    private int pos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        initView();
    }

    /**
     * 跳转焦点
     */
    private void initView() {

        editTexts[0] = (EditText) findViewById(R.id.ed_num1);
        editTexts[1] = (EditText) findViewById(R.id.ed_num2);
        editTexts[2] = (EditText) findViewById(R.id.ed_num3);
        editTexts[3] = (EditText) findViewById(R.id.ed_num4);

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
    class MyTextChangeWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && s.length() == 1) {
                pos++;
                if (pos < 4){
                    editTexts[pos].requestFocus();
                }
                if (pos == 4){
                    //改变布局
                    Intent intent = new Intent(PrivateActivity.this, ConfirmPrivateActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1){
            finish();
        }
    }

    /**
     * 监听删除键 前移焦点
     */
    private View.OnKeyListener keyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN){
                if (pos > 0){
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

    public static String getMsg()
    {
        String pPwd;
        String str1,str2,str3,str4;
        str1=editTexts[0].getText().toString();
        str2=editTexts[1].getText().toString();
        str3=editTexts[2].getText().toString();
        str4=editTexts[3].getText().toString();
        pPwd=str1+str2+str3+str4;
        return pPwd;
    }


    /**
     * 保存草稿
     */
    private void saveDraft() {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this);
        sp.setString("FirstPassword", getMsg());
    }

    /**
     * 清空文本信息
     */
    private void clear()
    {
        editTexts[0].setText("");
        editTexts[1].setText("");
        editTexts[2].setText("");
        editTexts[3].setText("");
        editTexts[0].requestFocus();
        pos = 0;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        clear();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 后退
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        JudgeSwitch();
    }

    private void JudgeSwitch() {
        SharePreferenceUtil sp= SharePreferenceUtil.getInstance(this);
        boolean checked = sp.getBoolean("Checked", false);
        Log.e(TAG, "开启返回后的状态: "+checked );
        if (checked) {
            mCustomSwitch.setChecked(true);
        } else {
            mCustomSwitch.setChecked(false);
        }
    }
}
