package com.niwj.graduationproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import com.niwj.graduationproject.R;

/**
 * Created by prince70 on 2017/8/22.
 * 体检套餐详情
 */

public class PhyExaDetailActivity extends AppCompatActivity {
    private TextView mTextView;
    private WebView wv_details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_test);
        mTextView = (TextView) findViewById(R.id.test);
        wv_details = (WebView) findViewById(R.id.wv_details);
        initData();
    }

    private void initData() {
        String details = getIntent().getStringExtra("details");
        mTextView.setText(details);
        if (details.equals(getResources().getString(R.string.Student_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_One/Student_package.html");
        } else if (details.equals(getResources().getString(R.string.Youth_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_One/Youth_package.html");
        } else if (details.equals(getResources().getString(R.string.Middleage_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_One/Middleage_package.html");
        } else if (details.equals(getResources().getString(R.string.Elderly_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_One/Elderly_package.html");
        } else if (details.equals(getResources().getString(R.string.Cardio_cerebrovascular_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_Two/Cardio_cerebrovascular_package.html");
        } else if (details.equals(getResources().getString(R.string.Hypertension_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_Two/Hypertension_package.html");
        } else if (details.equals(getResources().getString(R.string.Health_care_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_Two/Health_care_package.html");
        } else if (details.equals(getResources().getString(R.string.Health_care_brain_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_Two/Health_care_brain_package.html");
        } else if (details.equals(getResources().getString(R.string.Immune_function_test_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_Two/Immune_function_test_package.html");
        } else if (details.equals(getResources().getString(R.string.Gynecological_routine_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_Three/Gynecological_routine_package.html");
        } else if (details.equals(getResources().getString(R.string.Premarital_medical_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_Three/Premarital_medical_package.html");
        } else if (details.equals(getResources().getString(R.string.Pre_pregnancy_medical_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_Three/Pre_pregnancy_medical_package.html");
        } else if (details.equals(getResources().getString(R.string.Entrance_examination_package_msg))) {
            wv_details.loadUrl("file:///android_asset/Check_Three/Entrance_examination_package.html");
        }
    }
}
