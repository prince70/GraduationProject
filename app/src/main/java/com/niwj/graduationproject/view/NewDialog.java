package com.niwj.graduationproject.view;

import android.content.Context;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.niwj.graduationproject.R;

import static com.niwj.graduationproject.control.Regualr.isIdcard;
import static com.niwj.graduationproject.control.Regualr.isMobile;

/**
 * Created by prince70 on 2017/8/13.
 * 新建用户弹框
 */

public class NewDialog extends MyDialog {

    public NewDialog(Context context, String msg) {
        super(context, R.layout.dialog_newbuild);

//        设置提示信息
        setText(R.id.tv_msg, msg);

//        取消按钮
        setViewOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 确定按钮的点击监听
     *
     * @param l
     * @return
     */
    public NewDialog setOnConfirmClickListener(final View.OnClickListener l) {
        setViewOnClickListener(R.id.btn_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    /**
     * 设置提示信息
     *
     * @param msg
     * @return
     */
    public NewDialog setMsg(String msg) {
        setText(R.id.tv_msg, msg);
        return this;
    }

    /**
     * 获取名字 身份证 手机 家庭住址
     *
     * @return
     */
    public String getEtName() {
        EditText et_name = getView(R.id.et_name);
        return et_name.getText().toString();
    }

    public EditText getEtNamebox() {
        EditText et_name = getView(R.id.et_name);
        return et_name;
    }

    public String getEtIdcard() {
        EditText et_idcard = getView(R.id.et_idcard);
        if (isIdcard(et_idcard.getText().toString())) {
            return et_idcard.getText().toString();
        } else {
            return null;
        }
    }

    public EditText getEtIdcardbox() {
        EditText et_idcard = getView(R.id.et_idcard);
        return et_idcard;
    }

    public String getEtPhone() {
        EditText et_phone = getView(R.id.et_phone);
        if (isMobile(et_phone.getText().toString())) {
            return et_phone.getText().toString();
        } else {
            return null;
        }
    }

    public EditText getEtPhonebox() {
        EditText et_phone = getView(R.id.et_phone);
        return et_phone;
    }

    public String getEtAddress() {
        EditText et_address = getView(R.id.et_address);
        return et_address.getText().toString();
    }

    public EditText getEtAddressbox() {
        EditText et_address = getView(R.id.et_address);
        return et_address;
    }

    /**
     * 设置输入类型数字
     */
    public void setNumberType() {
        EditText et_msg = getView(R.id.et_phone);
        et_msg.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    /**
     * 设置身份证的输入类型
     */
    public void setIdCardType() {
        EditText et_msg = getView(R.id.et_idcard);
        // LoginUtils.setIdCardInputLimit(et_msg);
        String digits = "1234567890X";
        et_msg.setKeyListener(DigitsKeyListener.getInstance(digits));
    }

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        EditText et_name = getView(R.id.et_name);
        EditText et_idcard = getView(R.id.et_idcard);
        EditText et_phone = getView(R.id.et_phone);
        EditText et_address = getView(R.id.et_address);
        if (et_name != null) {
            et_name.setFocusable(true);
            et_name.setFocusableInTouchMode(true);
            et_name.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) et_name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(et_name, 0);
        } else if (et_idcard != null) {
            et_idcard.setFocusable(true);
            et_idcard.setFocusableInTouchMode(true);
            et_idcard.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) et_idcard.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(et_idcard, 0);
        } else if (et_phone != null) {
            et_phone.setFocusable(true);
            et_phone.setFocusableInTouchMode(true);
            et_phone.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) et_phone.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(et_phone, 0);
        } else if (et_address != null) {
            et_address.setFocusable(true);
            et_address.setFocusableInTouchMode(true);
            et_address.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) et_address.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(et_address, 0);
        }
    }
}
