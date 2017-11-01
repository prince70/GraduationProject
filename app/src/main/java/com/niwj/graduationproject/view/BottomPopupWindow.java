package com.niwj.graduationproject.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.niwj.graduationproject.R;

/**
 * Created by prince70 on 2017/10/30.
 * 底部弹窗
 */

public abstract class BottomPopupWindow extends MyPopupWindow implements View.OnClickListener{

    private final int bgColor = 0x30000000; //半透明背景色

    private TextView tv_msg;
    private View viewInside;

    private Context context;
    private PopupWindow pop_bg;

    public BottomPopupWindow(Context context, int insideLayoutId) {
        super(context, R.layout.pop_bottompopupwindow);
        this.context = context;

        tv_msg = super.getView(R.id.tv_msg);
        super.getView(R.id.btn_cancel).setOnClickListener(this);
        super.getView(R.id.btn_confirm).setOnClickListener(this);
        super.getView(R.id.bg).setOnClickListener(this);

        LinearLayout ll_inside = super.getView(R.id.ll_inside);
        viewInside = LayoutInflater.from(context).inflate(insideLayoutId, ll_inside);

        View bg = new View(context);
        bg.setBackgroundColor(bgColor);
        pop_bg = new PopupWindow(bg);
        pop_bg.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop_bg.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //渐变效果
        pop_bg.setAnimationStyle(R.style.BottomPopBgStyle);

        //设置宽高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        //从底部弹出的动画效果
        this.setAnimationStyle(R.style.BottomPopStyle);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            //确定
//            case R.id.btn_confirm:
//
//                break;
//            case R.id.btn_cancel:
//                cancel();
//                break;
//        }
        if (v.getId() == R.id.btn_confirm){
            confirm();
        }else if (v.getId() == R.id.btn_cancel){
            cancel();
        }else {
            cancel();
        }
        dismiss();
    }

    public void hide_tv_msg(){
        tv_msg.setVisibility(View.GONE);
    }

    public void show(){
        //添加半透明背景
        View view = new View(getContext());
        if (!pop_bg.isShowing()) pop_bg.showAtLocation(view , Gravity.TOP, 0, 0);
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //移除半透明背景
        if (pop_bg.isShowing()) pop_bg.dismiss();
    }

    /**
     * 确定
     */
    protected abstract void confirm();

    protected abstract void cancel();

    protected Context getContext() {
        return context;
    }

    /**
     * 设置弹窗顶部的文字
     */
    public void setMsg(String msg){
        tv_msg.setText(msg);
    }

    public String getMsg(){
        return tv_msg.getText().toString();
    }

    @Override
    public <T extends View> T getView(int viewId) {
        return (T) viewInside.findViewById(viewId);
    }
}
