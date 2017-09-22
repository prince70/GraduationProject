package com.niwj.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.niwj.graduationproject.activity.OpenActivity;
import com.niwj.graduationproject.control.Constants;
import com.niwj.graduationproject.control.ImageToast;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.SharePreferenceUtil;
import com.niwj.graduationproject.entity.Physicalrecord;
import com.niwj.graduationproject.exam.DisplayData;
import com.niwj.graduationproject.view.NewDialog;
import com.niwj.graduationproject.view.NumberAnimTextView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Physicalrecord mPhysicalrecord;//检测记录

    //  判断是否退出
    private boolean mIsExit;

    // 定时检测是否有蓝牙设备数据过来
    private Timer refreshUITimer = null;
    int glob_INVALUE = -10000;
    private int[] i_value = new int[]{glob_INVALUE, glob_INVALUE, glob_INVALUE, glob_INVALUE, glob_INVALUE,
            glob_INVALUE, glob_INVALUE, glob_INVALUE, glob_INVALUE};
    private DisplayData dispData;
    int glob_systolic = 5;
    int glob_diastolic = 6;
    int glob_avg = 7;
    int glob_xiudai = 8;


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
    private NumberAnimTextView tv_diastolicPressure;
    private NumberAnimTextView meanPressure;
    private NumberAnimTextView tv_cuffPressure;

    private AVLoadingIndicatorView avi;
    private AVLoadingIndicatorView avi2;

    private Handler show_handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 4000:
                    dispData();//检测
                    break;

            }
        }

    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            JSONObject data = (JSONObject) msg.obj;
            switch (msg.what) {
                case Constants.MESSAGE_UPDATE_XUEYADATA: //Constants常数类  更新血压数据
//                    dispData();
//                    RefreshXueYa();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String username = LoginUtils.getUsername(this);
        String headimg = LoginUtils.getHeadimg(this);
        String number = LoginUtils.getNumber(this);
        Log.e(TAG, "onCreate:   用户名" + username + "     头像" + headimg + "    工号" + number);
        Judge();
//        LoginUtils.loginCheck(this, new LoginUtils.LoginFinishListener() {
//            @Override
//            public void loginFinish() {
//
//            }
//        });
        initView();
        initXueYa();
        TextView tv_time = (TextView) findViewById(R.id.tv_time);
        String s = currentTime();
        tv_time.setText(s);
//TODO 检测是否已经登录，才能进入主页面
    }

    /**
     * 判断是否设置了隐私密码
     */
    private void Judge() {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this);
        boolean privateTag = sp.getBoolean("Checked", false);
        Log.e(TAG, "Judge: 是否设置了隐私密码" + privateTag);
        if (privateTag) {
            startActivity(new Intent(this, OpenActivity.class));
        }
    }

    private void initXueYa() {
        // 刷新界面定时器
        refreshUITimer = new Timer();
        refreshUITimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Map<String, Object> map = dispData.getTvData();
                int value_diastolic = (int) map.get("diastolic");
                int value_xiudaiya = (int) map.get("xiudaiya");

                //袖带压
                if (i_value[glob_xiudai] != value_xiudaiya) {
                    i_value[glob_xiudai] = value_xiudaiya;
                    Message msg4 = new Message();
                    msg4.what = 4000;
                    show_handle.sendMessage(msg4);
                }
                //血压
                if (i_value[glob_diastolic] != value_diastolic) {
                    i_value[glob_diastolic] = value_diastolic;
                    Message msg1 = new Message();
                    msg1.what = Constants.MESSAGE_UPDATE_XUEYADATA;
                    mHandler.sendMessage(msg1);
                }
            }
        }, 500, 500);
        dispData.setupDataDeal();
        dispData.setupThread();
    }

    /**
     * 数字改变效果 https://github.com/Bakumon/NumberAnimTextView
     * Pin 解锁https://github.com/OrangeGangsters/LolliPin
     */

    private void initView() {

        dispData = new DisplayData(this);
        btnHome = (RadioButton) findViewById(R.id.home_main);
        btnManage = (RadioButton) findViewById(R.id.manage_main);
        btnUser = (RadioButton) findViewById(R.id.user_main);

        ll_new_build = (LinearLayout) findViewById(R.id.ll_new_build);
        ll_alert_message = (LinearLayout) findViewById(R.id.ll_alert_message);
        ll_one_key = (LinearLayout) findViewById(R.id.ll_one_key);

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi_dialog);
        avi2 = (AVLoadingIndicatorView) findViewById(R.id.avi_dialog2);
        tv_residentName = (TextView) findViewById(R.id.tv_residentName);
        tv_residentIdcard = (TextView) findViewById(R.id.tv_residentIdcard);
        tv_residentPhone = (TextView) findViewById(R.id.tv_residentPhone);
        tv_residentAddress = (TextView) findViewById(R.id.tv_residentAddress);

        tv_systolicPressure = (NumberAnimTextView) findViewById(R.id.tv_systolicPressure);
        tv_diastolicPressure = (NumberAnimTextView) findViewById(R.id.tv_diastolicPressure);
        meanPressure = (NumberAnimTextView) findViewById(R.id.meanPressure);
        tv_cuffPressure = (NumberAnimTextView) findViewById(R.id.tv_cuffPressure);

        ll_new_build.setOnClickListener(this);
        ll_alert_message.setOnClickListener(this);
        ll_one_key.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnUser.setOnClickListener(this);
    }

    /**
     * 显示数据
     */
    private void dispData() {
        Map<String, Object> map = dispData.getTvData();
        i_value[glob_diastolic] = (int) map.get("diastolic");//舒张压
        i_value[glob_systolic] = (int) map.get("systolic");//收缩压
        i_value[glob_avg] = (int) map.get("avg");//平均压
        i_value[glob_xiudai] = (int) map.get("xiudaiya");//袖带压

        tv_systolicPressure.setText(String.valueOf(i_value[glob_systolic]));
        tv_diastolicPressure.setText(String.valueOf(i_value[glob_diastolic]));
        meanPressure.setText(String.valueOf(i_value[glob_avg]));
        tv_cuffPressure.setText(String.valueOf(i_value[glob_xiudai]));

        Log.e(TAG, "dispData:收缩压 " + String.valueOf(i_value[glob_systolic]));
        Log.e(TAG, "dispData:舒张压 " + String.valueOf(i_value[glob_diastolic]));
        Log.e(TAG, "dispData: 平均压" + String.valueOf(i_value[glob_avg]));
        Log.e(TAG, "dispData: 袖带压" + String.valueOf(i_value[glob_xiudai]));
//        TODO 保存数据
        if (tv_systolicPressure.getText().equals("-10000")) {
            tv_systolicPressure.setText("-");
        }
        if (tv_diastolicPressure.getText().equals("-10000")) {
            tv_diastolicPressure.setText("-");
        }
        if (meanPressure.getText().equals("-10000")) {
            meanPressure.setText("-");
        }
//        代表结束了
        if (tv_cuffPressure.getText().equals("-10000")) {
            tv_cuffPressure.setText("-");
            avi.setVisibility(View.GONE);
            avi2.setVisibility(View.GONE);
            String string = tv_residentName.getText().toString();
            String string1 = tv_residentIdcard.getText().toString();
            String string2 = tv_residentPhone.getText().toString();
            String string3 = tv_residentAddress.getText().toString();
            String string4 = tv_systolicPressure.getText().toString();
            String string5 = tv_diastolicPressure.getText().toString();
            String string6 = meanPressure.getText().toString();
            String username = LoginUtils.getUsername(this);
            String number = LoginUtils.getNumber(this);
            String s = currentTime();
            Log.e(TAG, "dispData: " + string + "\n" + string1 + "\n" + string2 + "\n" + string3 + "\n" + string4 + "\n" + string5 + "\n" + string6 + "\n" + username + "\n" + number + "\n" + s);
            mPhysicalrecord = new Physicalrecord();
            mPhysicalrecord.setName(tv_residentName.getText().toString());
            mPhysicalrecord.setIdcard(tv_residentIdcard.getText().toString());
            mPhysicalrecord.setPhonenumber(tv_residentPhone.getText().toString());
            mPhysicalrecord.setAddress(tv_residentAddress.getText().toString());
            mPhysicalrecord.setSystolicPressure(tv_systolicPressure.getText().toString());
            mPhysicalrecord.setDiastolicPressure(tv_diastolicPressure.getText().toString());
            mPhysicalrecord.setMeanPressure(meanPressure.getText().toString());
            mPhysicalrecord.setDocName(LoginUtils.getUsername(this));
            mPhysicalrecord.setDocNumber(LoginUtils.getNumber(this));
            mPhysicalrecord.setTime(currentTime());
            mPhysicalrecord.save();
            List<Physicalrecord> all = DataSupport.findAll(Physicalrecord.class);
            for (Physicalrecord physicalrecord : all) {
                Log.e(TAG, "dispData: " + physicalrecord.toString());
            }

//            TODO 上传服务器
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_one_key://一键检测
                boolean residentMsg = getResidentMsg();
                Log.e(TAG, "onClick: " + residentMsg);
                if (residentMsg) {
                    int visibility = avi.getVisibility();
                    Log.e(TAG, "onClick:是否可见值 " + visibility);
                    if (visibility == 8) {
//                    开始检测
                        dispData.fun_start();
                        avi.setVisibility(View.VISIBLE);
                        avi2.setVisibility(View.VISIBLE);
                        Log.e(TAG, "onClick:可见 " + avi.getVisibility());//0
                    } else {
//                    结束检测
                        dispData.fun_stop();
                        avi.setVisibility(View.GONE);
                        avi2.setVisibility(View.GONE);
                        Log.e(TAG, "onClick:不可见 " + avi.getVisibility());//8
                    }
                } else {
                    ImageToast.ImageToast(this, R.mipmap.ic_help, "居民信息还没录入", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.ll_alert_message://修改
                final NewDialog dialog = new NewDialog(this, "修改居民信息");
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
                        if (etIdcard == null) {
                            ImageToast.ImageToast(MainActivity.this, R.mipmap.ic_help, "身份证输入错误", Toast.LENGTH_SHORT);
                        } else {
                            tv_residentIdcard.setText(etIdcard);
                        }
                        if (etPhone == null) {
                            ImageToast.ImageToast(MainActivity.this, R.mipmap.ic_help, "手机号码输入错误", Toast.LENGTH_SHORT);
                        } else {
                            tv_residentPhone.setText(etPhone);
                        }
                        tv_residentName.setText(etName);
                        tv_residentAddress.setText(etAddress);
                    }
                }).show();

                break;
            case R.id.ll_new_build://新建
                final NewDialog dialog2 = new NewDialog(this, "请录入居民信息");
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
                        if (etIdcard == null) {
                            ImageToast.ImageToast(MainActivity.this, R.mipmap.ic_help, "身份证输入错误", Toast.LENGTH_SHORT);
                        } else {
                            tv_residentIdcard.setText(etIdcard);
                        }
                        if (etPhone == null) {
                            ImageToast.ImageToast(MainActivity.this, R.mipmap.ic_help, "手机号码输入错误", Toast.LENGTH_SHORT);
                        } else {
                            tv_residentPhone.setText(etPhone);
                        }
                        tv_residentName.setText(etName);
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
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            default:
                break;
        }
    }

    /**
     * 判断居民信息是否已经录入
     *
     * @return
     */
    private boolean getResidentMsg() {
        boolean isEmpty;
        String residentName = tv_residentName.getText().toString();
        String residentIdcard = tv_residentIdcard.getText().toString();
        String residentPhone = tv_residentPhone.getText().toString();
        String residentAddress = tv_residentAddress.getText().toString();
        if (residentName.equals("") || residentIdcard.equals("") || residentPhone.equals("") || residentAddress.equals("")) {
            isEmpty = false;
        } else {
            isEmpty = true;
        }
        return isEmpty;
    }

    /**
     * 刷新血压数据
     */
//    private void RefreshXueYa() {
//        MedicalDevice device = medicalDevices.get(5);
//        if (null != device.getExamPeople()) {
//            if (!mMainShouSuoYa.getText().equals("-")) {
//                String shousuoya = mMainShouSuoYa.getText().toString();
//                String shuzhangya = mMainShuZhangYa.getText().toString();
//                String Avg = mMainAvg.getText().toString();
//                ExamRecordXueYa record1 = new ExamRecordXueYa();
//                record1.setSystolic(Integer.parseInt(shousuoya));
//                record1.setDiastolic(Integer.parseInt(shuzhangya));
//                record1.setAvg(Integer.parseInt(Avg));
//                record1.setDateStr(StringUtil.formatDate(new Date(), "yyyy-MM-dd"));
//                record1.setIdCard(device.getExamPeople().getIdCard());
//                record1.save();
//            } else {
//                ExamRecordXueYa record1 = new ExamRecordXueYa();
//                record1.setSystolic(0);
//                record1.setDiastolic(0);
//                record1.setAvg(0);
//                record1.setDateStr(StringUtil.formatDate(new Date(), "yyyy-MM-dd"));
//                record1.setIdCard(device.getExamPeople().getIdCard());
//                record1.save();
//                Toast.makeText(this,"血压数据保存成功！", Toast.LENGTH_SHORT).show();
//            }
//            // 更新体检状态
//            boolean updated = updateExamPersonStatus(device.getExamPeople());
//            // 如果更新成功
//            if (updated) {
//                initCheckedPeopleData();
//                initUncheckedPeopleData();
//                initExamPeopleData();
//            }
//        }
//    }

    /**
     * 点击2次结束应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                ImageToast.ImageToast(this, R.mipmap.ic_help, "再按一次退出", Toast.LENGTH_SHORT);
                mIsExit = true;
//                2秒后mIsExit重新置为false
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    private String currentTime() {
        String currentTime;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        currentTime = format.format(date);
        return currentTime;
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        LoginUtils.loginCheck(this, new LoginUtils.LoginFinishListener() {
//            @Override
//            public void loginFinish() {
//
//            }
//        });
//    }
}
