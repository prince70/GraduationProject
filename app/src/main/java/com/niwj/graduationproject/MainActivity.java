package com.niwj.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.niwj.graduationproject.view.NewDialog;
import com.niwj.graduationproject.view.NumberAnimTextView;
import com.wang.avi.AVLoadingIndicatorView;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private RadioButton btnHome;
    private RadioButton btnManage;
    private RadioButton btnUser;

    private LinearLayout ll_new_build;
    private LinearLayout ll_alert_message;
    private LinearLayout ll_one_key;
    private TextView tv_residentName;
    private TextView tv_residentIdcard;
    private TextView tv_residentPhone;
    private TextView tv_residentAddress;

    private NumberAnimTextView tv_systolicPressure;

    private AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
//TODO  数字改变效果 https://github.com/Bakumon/NumberAnimTextView  DONE
//
//    TODO Pin 解锁https://github.com/OrangeGangsters/LolliPin
    private void initView() {
        btnHome = (RadioButton) findViewById(R.id.home_main);
        btnManage = (RadioButton) findViewById(R.id.manage_main);
        btnUser = (RadioButton) findViewById(R.id.user_main);

        ll_new_build= (LinearLayout) findViewById(R.id.ll_new_build);
        ll_alert_message= (LinearLayout) findViewById(R.id.ll_alert_message);
        ll_one_key= (LinearLayout) findViewById(R.id.ll_one_key);

        avi= (AVLoadingIndicatorView) findViewById(R.id.avi_dialog);
        tv_residentName= (TextView) findViewById(R.id.tv_residentName);
        tv_residentIdcard= (TextView) findViewById(R.id.tv_residentIdcard);
        tv_residentPhone= (TextView) findViewById(R.id.tv_residentPhone);
        tv_residentAddress= (TextView) findViewById(R.id.tv_residentAddress);

        tv_systolicPressure= (NumberAnimTextView) findViewById(R.id.tv_systolicPressure);

        ll_new_build.setOnClickListener(this);
        ll_alert_message.setOnClickListener(this);
        ll_one_key.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_one_key://一键检测

                tv_systolicPressure.setNumberString("90");
                int visibility = avi.getVisibility();
                if (visibility==8){
                    avi.setVisibility(View.VISIBLE);

                }else {
                    avi.setVisibility(View.GONE);

                }
                break;
            case R.id.ll_alert_message://修改
                final NewDialog dialog =  new NewDialog(this, "修改居民信息");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.showKeyboard();
                    }
                }, 200);
                EditText etNamebox = dialog.getEtNamebox();
                EditText etIdcardbox = dialog.getEtIdcardbox();
                EditText etPhonebox = dialog.getEtPhonebox();
                EditText etAddressbox = dialog.getEtAddressbox();
                etNamebox.setText(tv_residentName.getText());
                etIdcardbox.setText(tv_residentIdcard.getText());
                etPhonebox.setText(tv_residentPhone.getText());
                etAddressbox.setText(tv_residentAddress.getText());
                dialog.setOnConfirmClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String etName = dialog.getEtName();
                        String etIdcard = dialog.getEtIdcard();
                        String etPhone = dialog.getEtPhone();
                        String etAddress = dialog.getEtAddress();

                        tv_residentName.setText(etName);
                        tv_residentIdcard.setText(etIdcard);
                        tv_residentPhone.setText(etPhone);
                        tv_residentAddress.setText(etAddress);
                    }
                }).show();

                break;
            case R.id.ll_new_build://新建
                final NewDialog dialog2 =  new NewDialog(this, "请录入居民信息");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog2.showKeyboard();
                    }
                }, 200);
                dialog2.setOnConfirmClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String etName = dialog2.getEtName();
                        String etIdcard = dialog2.getEtIdcard();
                        String etPhone = dialog2.getEtPhone();
                        String etAddress = dialog2.getEtAddress();

                        tv_residentName.setText(etName);
                        tv_residentIdcard.setText(etIdcard);
                        tv_residentPhone.setText(etPhone);
                        tv_residentAddress.setText(etAddress);
                    }
                }).show();

            break;

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
