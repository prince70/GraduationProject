package com.niwj.graduationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niwj.graduationproject.BaseActivity;
import com.niwj.graduationproject.LoginActivity;
import com.niwj.graduationproject.R;
import com.niwj.graduationproject.control.ImageToast;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.PathUtil;
import com.niwj.graduationproject.control.SharePreferenceUtil;
import com.niwj.graduationproject.control.Utils;
import com.tencent.bugly.beta.Beta;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

import static com.niwj.graduationproject.RegisterActivity.KEY_IDCARD;
import static com.niwj.graduationproject.RegisterActivity.KEY_PASSWORD;
import static com.niwj.graduationproject.RegisterActivity.USER_FILENAME;
import static com.niwj.graduationproject.api.BaseAPIUtils.CHECK_URL;

/**
 * Created by prince70 on 2017/9/10.
 * 设置中心
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SettingActivity";
    private RelativeLayout rl_alertPwd;
    private RelativeLayout rl_check_for_update;
    private RelativeLayout rl_term_of_service;
    private RelativeLayout rl_about_us;
    private RelativeLayout rl_clearCache;
    private RelativeLayout rl_logout;
    private TextView tv_version_current;
    private ImageView iv_newApp;
    private TextView tv_clearCache;

    private String System_version;
    private Handler mHandler = new Handler();
    private boolean isClearCacheEnable = true;  //只能清理一次

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(R.layout.activity_setting);
        Log.e(TAG, "onCreate: " + "SettingActivity");
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        rl_alertPwd = (RelativeLayout) findViewById(R.id.rl_alertPwd);
        rl_check_for_update = (RelativeLayout) findViewById(R.id.rl_check_for_update);
        rl_term_of_service = (RelativeLayout) findViewById(R.id.rl_term_of_service);
        rl_about_us = (RelativeLayout) findViewById(R.id.rl_about_us);
        rl_clearCache = (RelativeLayout) findViewById(R.id.rl_clearCache);
        rl_logout = (RelativeLayout) findViewById(R.id.rl_logout);

        rl_alertPwd.setOnClickListener(this);
        rl_check_for_update.setOnClickListener(this);
        rl_term_of_service.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        rl_clearCache.setOnClickListener(this);
        rl_logout.setOnClickListener(this);

        tv_version_current = (TextView) findViewById(R.id.tv_version_current);
        tv_version_current.setText("检查更新" + "(当前版本" + getCurrentVersion() + ")");
        iv_newApp = (ImageView) findViewById(R.id.iv_newApp);
        tv_clearCache = (TextView) findViewById(R.id.tv_clearCache);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_alertPwd://密码修改
                startActivity(new Intent(this, ChangePwdActivity.class));
                break;
            case R.id.rl_check_for_update://检查更新
                Beta.checkUpgrade();
                break;
            case R.id.rl_term_of_service://服务条款
                startActivity(new Intent(this, TermServiceActivity.class));
                break;
            case R.id.rl_about_us://关于我们
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.rl_clearCache://清理缓存

                if (!isClearCacheEnable) return;
                isClearCacheEnable = false;
                ImageToast.ImageToast(this, R.mipmap.ic_help, "正在清理", Toast.LENGTH_SHORT);
//        ToastUtil.showToast(getContext(), getResString(R.string.clearing));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PathUtil.clearCache();
                        if (mHandler == null) return;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Context context = SettingActivity.this;
                                if (context != null) {
                                    ImageToast.ImageToast(context, R.mipmap.ic_ok, "清理完成", Toast.LENGTH_SHORT);
//                            ToastUtil.showToast(context, getResString(R.string.clear_finish));
                                }
                                if (tv_clearCache != null) {
                                    tv_clearCache.setText("清理缓存");
                                }
                            }
                        });
                    }
                }).start();
                break;
            case R.id.rl_logout://退出登录

                SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this, USER_FILENAME);
                String idcard = sp.getString(KEY_IDCARD, "");
                String password = sp.getString(KEY_PASSWORD, "");
                Log.e(TAG, "onClick: " + idcard + "\n" + password);

                RequestParams params = new RequestParams(CHECK_URL);
                params.addParameter("username", idcard);
                params.addParameter("password", password);
                params.addParameter("registrationId", JPushInterface.getRegistrationID(this));

                Log.e(TAG, "onClick: 登出"+idcard+"\n"+password+"\n"+JPushInterface.getRegistrationID(this) );
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e(TAG, "onSuccess:退出 " + result);
                        if (result.equals("logout")) {
                            LoginUtils.loginOut(SettingActivity.this);
                            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();
                            Log.e(TAG, "onSuccess: " + "退出成功");
                        } else if (result.equals("false")) {
                            Log.e(TAG, "onSuccess: " + "退出失败");
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e(TAG, "onError: " + "退出失败");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });



                /*LoginUtils.loginOut(SettingActivity.this);
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();*/


                break;
            default:
                break;
        }

    }

    public String getCurrentVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(SettingActivity.this.getPackageName(), 0);
            System_version = info.versionName;
            return System_version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return System_version;
    }

    /**
     * 统计缓存大小
     */
    private void countCacheSize() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String cacheSize = PathUtil.getCacheSize();
                if (mHandler == null) return;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (tv_clearCache != null) {
                            tv_clearCache.setText("清理缓存" + "(" + cacheSize + ")");
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
