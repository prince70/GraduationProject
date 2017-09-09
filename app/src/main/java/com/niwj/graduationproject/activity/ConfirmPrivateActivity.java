package com.niwj.graduationproject.activity;

import android.content.Context;
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
import android.widget.Toast;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.UserActivity;
import com.niwj.graduationproject.control.ImageToast;
import com.niwj.graduationproject.control.SharePreferenceUtil;

/**
 * Created by prince70 on 2017/9/8.
 * 确认隐私密码
 */

public class ConfirmPrivateActivity extends AppCompatActivity {

    private static EditText[] editTexts = new EditText[4];
    private int pos = 0;
    private boolean tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_private);
        initView();
    }

    /**
     * 跳转焦点
     */
    private void initView() {

        editTexts[0] = (EditText) findViewById(R.id.num1);
        editTexts[1] = (EditText) findViewById(R.id.num2);
        editTexts[2] = (EditText) findViewById(R.id.num3);
        editTexts[3] = (EditText) findViewById(R.id.num4);

        // 添加 内容change listener ：输入焦点后移 + 密码验证
        editTexts[0].addTextChangedListener(new MyTextChangeWatcher());
        editTexts[1].addTextChangedListener(new MyTextChangeWatcher());
        editTexts[2].addTextChangedListener(new MyTextChangeWatcher());
        editTexts[3].addTextChangedListener(new MyTextChangeWatcher());

        //del 监听，输入焦点前移
        editTexts[1].setOnKeyListener(keyListener);
        editTexts[2].setOnKeyListener(keyListener);
        editTexts[3].setOnKeyListener(keyListener);

        setResult(0);
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
                if (pos < 4) {
                    editTexts[pos].requestFocus();
                }
                if (pos == 4) {
                    String pass1 = PrivateActivity.getMsg();
                    String pass2 = getSMsg();
                    if (!pass1.equals(pass2)) {
                        ImageToast.ImageToast(ConfirmPrivateActivity.this, R.mipmap.ic_help,getResources().getString(R.string.password_no_same), Toast.LENGTH_SHORT);
//                        ToastUtil.showToast(getContext(), "两次密码不相同");
//                        Toast.makeText(ConfirmPrivateActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                        clear();
                    } else {
                        UserActivity.mCustomSwitch.setChecked(true);
                        tag = UserActivity.mCustomSwitch.isChecked();
                        Log.e("TAG", tag + "第一次");
                        setResult(1);
                        finish();

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

    public static String getSMsg() {
        String psPwd;
        String str1, str2, str3, str4;
        str1 = editTexts[0].getText().toString();
        str2 = editTexts[1].getText().toString();
        str3 = editTexts[2].getText().toString();
        str4 = editTexts[3].getText().toString();
        psPwd = str1 + str2 + str3 + str4;
        return psPwd;

    }

    /**
     * 保存草稿
     */
    private void saveDraft() {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this);
        sp.setString("FirstPassword", getSMsg());
        sp.setBoolean("Checked", tag);

    }

    /**
     * 读取草稿
     */
    public void loadCheckedDraft() {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this);
        boolean tag = sp.getBoolean("Checked", false);
        UserActivity.mCustomSwitch.setChecked(tag);
        Log.e("TAG", UserActivity.mCustomSwitch.isChecked() + "第二次");

    }

    @Override
    public void finish() {
        super.finish();
        saveDraft();
    }

    /**
     * 清空文本信息
     */
    private void clear() {
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

}
