package com.niwj.graduationproject.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by prince70 on 2017/8/13.
 * 自定义Dialog
 */

public class MyDialog extends Dialog {
    private View convertView;

    public MyDialog(Context context, int layoutId) {
        super(context);
        convertView = LayoutInflater.from(context).inflate(layoutId, null);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(convertView);
        //设置窗口背景透明
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public <T extends View> T getView(int viewId) {
        return (T) convertView.findViewById(viewId);
    }

    public String getText(int viewId) {
        return ((TextView) getView(viewId)).getText().toString();
    }

    public MyDialog setText(int viewId, String text) {
        ((TextView) getView(viewId)).setText(text);
        return this;
    }

    public MyDialog setViewOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null)
            view.setOnClickListener(listener);
        return this;
    }

    @Override
    public void show() {
        if (!isShowing()) super.show();
    }

    @Override
    public void dismiss() {
        if (isShowing()) super.dismiss();
    }

    public String getResString(int resId) {
        return getContext().getResources().getString(resId);
    }
}
