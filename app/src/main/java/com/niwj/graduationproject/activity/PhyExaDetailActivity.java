package com.niwj.graduationproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.niwj.graduationproject.BaseActivity;
import com.niwj.graduationproject.R;

import java.util.Calendar;

/**
 * Created by prince70 on 2017/8/22.
 * 体检套餐详情
 */

public class PhyExaDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "PhyExaDetailActivity";
    private TextView mTextView;
    private WebView wv_details;
    private Button btn_book;
    private String ExamTitles;


    private Calendar startCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(R.layout.activity_physical_test);
        bindListener();
        initData();
        ExamTitles = getIntent().getStringExtra("title");
        Log.e(TAG, "onCreate:ExamTitles "+ ExamTitles);
    }

    private void bindListener() {
        mTextView = (TextView) findViewById(R.id.test);
        wv_details = (WebView) findViewById(R.id.wv_details);
        btn_book = (Button) findViewById(R.id.btn_book);
        btn_book.setOnClickListener(this);
        startCalendar=Calendar.getInstance();
        startCalendar.set(Calendar.HOUR_OF_DAY,9);
        startCalendar.set(Calendar.MINUTE,0);
        startCalendar.set(Calendar.SECOND,0);
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

    /**
     * 预约点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ConfirmBookPhysicalExaminationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title",ExamTitles);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
