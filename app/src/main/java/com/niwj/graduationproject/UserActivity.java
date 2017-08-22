package com.niwj.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.niwj.graduationproject.view.CircleImageView;

/**
 * Created by prince70 on 2017/8/10.
 * 我的
 */

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UserActivity";
    private RadioButton btnHome;
    private RadioButton btnManage;
    private RadioButton btnUser;
    private CircleImageView userIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initData();
    }

    private void initData() {
        btnHome = (RadioButton) findViewById(R.id.home_user);
        btnManage = (RadioButton) findViewById(R.id.manage_user);
        btnUser = (RadioButton) findViewById(R.id.user_user);
        userIcon = (CircleImageView) findViewById(R.id.userIcon);

        btnHome.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        userIcon.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userIcon://设置头像


                break;

            case R.id.home_user:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.scale_translate,R.anim.my_alpha_action);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            case R.id.manage_user:
                Intent intent1 = new Intent(this, ManageActivity.class);
                startActivity(intent1);
//                overridePendingTransition(R.anim.scale_translate_rotate,R.anim.my_alpha_action);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            case R.id.user_user:
                break;
            default:
                break;
        }
    }
}
