package com.niwj.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private RadioButton btnHome;
    private RadioButton btnManage;
    private RadioButton btnUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHome = (RadioButton) findViewById(R.id.home_main);
        btnManage = (RadioButton) findViewById(R.id.manage_main);
        btnUser = (RadioButton) findViewById(R.id.user_main);

        btnHome.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_main:
                break;
            case R.id.manage_main:
                Intent intent = new Intent(this, ManageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);//淡入淡出
                break;
            case R.id.user_main:
                Intent intent1 = new Intent(this, UserActivity.class);
                startActivity(intent1);
//                overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);//上下交错
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            default:
                break;
        }
    }
}
