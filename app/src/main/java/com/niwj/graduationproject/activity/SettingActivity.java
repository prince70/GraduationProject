package com.niwj.graduationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.control.ImageToast;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.PathUtil;
import com.tencent.bugly.beta.Beta;

/**
 * Created by prince70 on 2017/9/10.
 * 设置中心
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_setting);
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
        tv_clearCache= (TextView) findViewById(R.id.tv_clearCache);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_alertPwd://密码修改

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
                LoginUtils.loginOut(this);
                finish();
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

}
