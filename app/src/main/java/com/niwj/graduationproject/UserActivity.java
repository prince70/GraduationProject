package com.niwj.graduationproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.niwj.graduationproject.control.ImageSelectUtil;
import com.niwj.graduationproject.control.ImageUtil;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.PathUtil;
import com.niwj.graduationproject.control.SharePreferenceUtil;
import com.niwj.graduationproject.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by prince70 on 2017/8/10.
 * 我的
 */

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UserActivity";
    private RadioButton btnHome;
    private RadioButton btnManage;
    private RadioButton btnUser;
    private static CircleImageView userIcon;
    private static Handler mHandler;
    public static ImageSelectUtil imageSelectUtil;

    private LinearLayout ll_lock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initData();
        updateUserIcon();
        imageSelectUtil = new ImageSelectUtil(this, PathUtil.getTempPicFilename(), 100);
        imageSelectUtil.setSelectFinishListener(new ImageSelectUtil.SelectedFinishListener() {
            @Override
            public void onSelectedFinish(ArrayList<String> paths) {
                String imgPath = paths.get(0);
                Log.e(TAG, "onSelectedFinish: " + imgPath);
                String realPath = "file://" + imgPath;
                SharePreferenceUtil sp = SharePreferenceUtil.getInstance(UserActivity.this);
                sp.setString("imgPath", realPath);
                String path = getFilesDir().getParentFile().getPath();
                Log.e(TAG, "onSelectedFinish: " + path);
//                Picasso.with(UserActivity.this).load("http://pic.qiantucdn.com/58pic/17/19/70/458PIC958PICS6K_1024.jpg!/fw/780/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center").into(userIcon);
                Picasso.with(UserActivity.this).load(realPath).into(userIcon);

            }
        });
    }

    private void initData() {
//        TODO 进来后更新头像  不然APP退出后就不见了
        btnHome = (RadioButton) findViewById(R.id.home_user);
        btnManage = (RadioButton) findViewById(R.id.manage_user);
        btnUser = (RadioButton) findViewById(R.id.user_user);
        userIcon = (CircleImageView) findViewById(R.id.userIcon);

        ll_lock= (LinearLayout) findViewById(R.id.ll_lock);

        btnHome.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        userIcon.setOnClickListener(this);
        ll_lock.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_lock://锁屏密码

                break;

            case R.id.userIcon://设置头像
                ImageSelectUtil.singleSelectImage(this);
                updateUserIcon(this);
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


    /**
     * 更新头像
     */
    public static void updateUserIcon(final Context context) {
        if (userIcon == null || mHandler == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = ImageUtil.loadBitmap(LoginUtils.getHeadimg(context), 1000, 1000);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            userIcon.setImageBitmap(bitmap);
                        } else {
                            userIcon.setImageResource(R.mipmap.profile_test);
                        }
                    }
                });
            }
        }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageSelectUtil.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUserIcon() {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this);
        String imgPath = sp.getString("imgPath", "");
        if (!imgPath.equals("")) {
            Picasso.with(this).load(imgPath).into(userIcon);
        }

    }
}
