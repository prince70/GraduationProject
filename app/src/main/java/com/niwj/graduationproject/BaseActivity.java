package com.niwj.graduationproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.niwj.graduationproject.control.AppManager;

/**
 * Created by prince70 on 2017/9/28.
 */

public class BaseActivity extends Activity {

    protected Context context = BaseActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		上报日志

        /**
         * 在这里把所有Activity推入栈
         */
        AppManager.addActivity(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
//		MainService.removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    protected void initLayout(int layoutResID) {
        setContentView(layoutResID);
    }

    public void startActivity(Context content,Class<?> cls){
        Intent intent=new Intent(content,cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void startActivityForResult(Intent intent, int requestCode){
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        super.startActivityForResult(intent, requestCode);
    }

    public Context getContext(){
        return context;
    }

    public Activity getActivity(){
        return this;
    }

    public String getResString(int resId){
        return getResources().getString(resId);
    }

    public <T extends View> T getView(int viewId){
        return (T) findViewById(viewId);
    }


}