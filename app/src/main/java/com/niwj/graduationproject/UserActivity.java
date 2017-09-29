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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.niwj.graduationproject.activity.ClosePrivateActivity;
import com.niwj.graduationproject.activity.PrivateActivity;
import com.niwj.graduationproject.activity.SettingActivity;
import com.niwj.graduationproject.api.pojo.AlertAvatar;
import com.niwj.graduationproject.api.pojo.DoctorProfile;
import com.niwj.graduationproject.api.utils.AlertAvatartUtils;
import com.niwj.graduationproject.api.utils.GetProfileUtils;
import com.niwj.graduationproject.control.FileRequestBody;
import com.niwj.graduationproject.control.ImageSelectUtil;
import com.niwj.graduationproject.control.ImageUtil;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.PathUtil;
import com.niwj.graduationproject.control.SharePreferenceUtil;
import com.niwj.graduationproject.control.Utils;
import com.niwj.graduationproject.view.CircleImageView;
import com.niwj.graduationproject.view.CustomSwitch;
import com.niwj.graduationproject.view.LoadingDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prince70 on 2017/8/10.
 * 我的
 */

public class UserActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "UserActivity";
    private RadioButton btnHome;
    private RadioButton btnManage;
    private RadioButton btnUser;
    private static CircleImageView userIcon;
    private static Handler mHandler;
    public static ImageSelectUtil imageSelectUtil;

    private TextView tv_username;
    private TextView tv_usernumber;
    private LinearLayout ll_lock;
    private LinearLayout ll_settings;
    public static CustomSwitch mCustomSwitch;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(R.layout.activity_user);
        Log.e(TAG, "onCreate: "+"UserActivity" );
        initData();
        updateUserIcon();
        updateSwicthchBtn();
        imageSelectUtil = new ImageSelectUtil(this, PathUtil.getTempPicFilename(), 100);
        imageSelectUtil.setSelectFinishListener(new ImageSelectUtil.SelectedFinishListener() {
            @Override
            public void onSelectedFinish(ArrayList<String> paths) {
                String imgPath = paths.get(0);
                Log.e(TAG, "onSelectedFinish: " + imgPath);
                String realPath = "file://" + imgPath;

                postImage(imgPath);

//                SharePreferenceUtil sp = SharePreferenceUtil.getInstance(UserActivity.this);
//                sp.setString("imgPath", realPath);
//                String path = getFilesDir().getParentFile().getPath();
//                Log.e(TAG, "onSelectedFinish: " + path);
//                Picasso.with(UserActivity.this).load(realPath).into(userIcon);

            }
        });
    }

    //    每次进来更新switchBtn的状态
    private void updateSwicthchBtn() {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this);
        boolean openTag = sp.getBoolean("Checked", false);  //默认值
        boolean closeTag = sp.getBoolean("UnChecked", true);
        Log.e(TAG, openTag + "打开的TAG");
        Log.e(TAG, closeTag + "关闭的TAG");
        if (openTag) {
            mCustomSwitch.setChecked(true);
        } else {
            mCustomSwitch.setChecked(false);
        }
    }

    private void initData() {
//         进来后更新头像  不然APP退出后就不见了
        btnHome = (RadioButton) findViewById(R.id.home_user);
        btnManage = (RadioButton) findViewById(R.id.manage_user);
        btnUser = (RadioButton) findViewById(R.id.user_user);
        userIcon = (CircleImageView) findViewById(R.id.userIcon);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_usernumber = (TextView) findViewById(R.id.tv_usernumber);

        tv_username.setText(LoginUtils.getUsername(this));
        tv_usernumber.setText(LoginUtils.getNumber(this));

        ll_lock = (LinearLayout) findViewById(R.id.ll_lock);
        ll_settings = (LinearLayout) findViewById(R.id.ll_settings);
        mCustomSwitch = (CustomSwitch) findViewById(R.id.switchButton);

        btnHome.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        userIcon.setOnClickListener(this);
        ll_lock.setOnClickListener(this);
        ll_settings.setOnClickListener(this);
//        switch按钮的监听事件
        mCustomSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startActivity(new Intent(UserActivity.this, PrivateActivity.class));
                } else {
                    startActivity(new Intent(UserActivity.this, ClosePrivateActivity.class));
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_settings://设置中心
                startActivity(new Intent(this, SettingActivity.class));

                break;
            case R.id.ll_lock://锁屏密码
                if (!mCustomSwitch.isChecked()) {
                    startActivity(new Intent(this, PrivateActivity.class));
                } else {
                    startActivity(new Intent(this, ClosePrivateActivity.class));
                }
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

//
        Call<DoctorProfile> call = GetProfileUtils.getProfile(LoginUtils.getUserId(this));
        call.enqueue(new Callback<DoctorProfile>() {
            @Override
            public void onResponse(Call<DoctorProfile> call, Response<DoctorProfile> response) {
                if (response.code() == 200) {
                    String heading = response.body().getData().get(0).getHeading();
                    Log.e(TAG, "onResponse: 获取到网络图片地址" + heading);
                    Picasso.with(UserActivity.this).load(heading).into(userIcon);
                }
            }

            @Override
            public void onFailure(Call<DoctorProfile> call, Throwable t) {
                Request request = call.request();
                Log.e(TAG, "onFailure: " + request.toString());
                Picasso.with(UserActivity.this).load(R.mipmap.ic_launcher).into(userIcon);
            }
        });

//        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this);
//        String imgPath = sp.getString("imgPath", "");
//        if (!imgPath.equals("")) {
//            Picasso.with(this).load(imgPath).into(userIcon);
//        }

    }

    /**
     * 上传图片到服务器
     *
     * @param localPath
     */
    private void postImage(String localPath) {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        File file = new File(localPath);
        Log.e(TAG, "postImage: 图片路径" + file.getPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        FileRequestBody body = new FileRequestBody(requestBody, null);
        Call<AlertAvatar> call = AlertAvatartUtils.alertAvatarCall(LoginUtils.getUserId(this), MultipartBody.Part.createFormData("file", file.getName(), body));
        call.enqueue(new Callback<AlertAvatar>() {
            @Override
            public void onResponse(Call<AlertAvatar> call, Response<AlertAvatar> response) {
                if (response.code() == 200) {
                    String heading = response.body().getData().get(0).getHeading();
                    Log.e(TAG, "onResponse:得到的网络地址 " + heading);
//                    TODO 设置图片到本地
                    Picasso.with(UserActivity.this).load(heading).into(userIcon);
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AlertAvatar> call, Throwable t) {
                Request request = call.request();
                Log.e(TAG, "onFailure: " + request.toString());
                loadingDialog.dismiss();
            }
        });

    }


}
