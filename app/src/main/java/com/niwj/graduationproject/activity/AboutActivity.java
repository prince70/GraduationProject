package com.niwj.graduationproject.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.niwj.graduationproject.*;
import com.niwj.graduationproject.control.Utils;

/**
 * Created by prince70 on 2017/9/10.
 * 关于我们
 */

public class AboutActivity extends com.niwj.graduationproject.BaseActivity {
    private TextView current_version;
    private String System_version;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(R.layout.activity_about);
        current_version= (TextView) findViewById(R.id.current_version);
        current_version.setText("当前版本："+getCurrentVersion());
    }
    private String getCurrentVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(AboutActivity.this.getPackageName(), 0);
            System_version = info.versionName;
            return System_version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return System_version;
    }
}
