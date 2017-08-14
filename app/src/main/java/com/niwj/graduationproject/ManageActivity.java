package com.niwj.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.RadioButton;


/**
 * Created by prince70 on 2017/8/10.
 * 管理
 */

public class ManageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ManageActivity";
    private RadioButton btnHome;
    private RadioButton btnManage;
    private RadioButton btnUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        btnHome = (RadioButton) findViewById(R.id.home_manage);
        btnManage = (RadioButton) findViewById(R.id.manage_manage);
        btnUser = (RadioButton) findViewById(R.id.user_manage);



        btnHome.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnUser.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_manage:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);//右往左推出效果
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            case R.id.manage_manage:
                break;
            case R.id.user_manage:
                Intent intent1 = new Intent(this, UserActivity.class);
                startActivity(intent1);
//                overridePendingTransition(R.anim.slide_left,R.anim.slide_right);//左右交错效果
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            default:
                break;
        }
    }
}
