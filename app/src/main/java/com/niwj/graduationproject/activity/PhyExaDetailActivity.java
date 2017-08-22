package com.niwj.graduationproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.niwj.graduationproject.R;

/**
 * Created by prince70 on 2017/8/22.
 * 体检套餐详情
 */

public class PhyExaDetailActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_test);
        mTextView = (TextView) findViewById(R.id.test);
        initData();
    }

    private void initData() {
        String details = getIntent().getStringExtra("details");
        mTextView.setText(details);
    }
}
