package com.niwj.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.niwj.graduationproject.activity.NotificationActivity;
import com.niwj.graduationproject.activity.PhysicalrecordActivity;
import com.niwj.graduationproject.adapter.CustomELVAdapter;
import com.niwj.graduationproject.api.pojo.GetRecords;
import com.niwj.graduationproject.api.utils.GetRecordsUtils;
import com.niwj.graduationproject.control.LoginUtils;
import com.niwj.graduationproject.control.Utils;
import com.niwj.graduationproject.entity.Physicalrecord;
import com.niwj.graduationproject.view.LoadingDialog;

import org.litepal.crud.DataSupport;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by prince70 on 2017/8/10.
 * 管理
 */

public class ManageActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ManageActivity";
    private RadioButton btnHome;
    private RadioButton btnManage;
    private RadioButton btnUser;

    private ExpandableListView elv;

    private LoadingDialog loadingDialog;
    private Physicalrecord physicalrecord;

    private LinearLayout ll_notification;
    private LinearLayout ll_record;
    private LinearLayout ll_sync;

    /**
     * 这些数据可以从数据库或Web中获取，使用Web API和加载到适配器。
     *
     * @param savedInstanceState
     */

    private String[] groupname;
    private String[][] details;
    private String[][] data;
    private String[][] listinfo;
    private static final int[] ImgBckgrnd = {R.mipmap.chang1, R.mipmap.chang2, R.mipmap.chang3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(R.layout.activity_manage);
        Log.e(TAG, "onCreate: " + "ManageActivity");
        initTitles();//这个必须在initData()之前
        initData();

    }

    /**
     * 初始化标题栏
     */
    private void initTitles() {
        /**
         * 这些数据可以从数据库或Web中获取，使用Web API和加载到适配器。
         *
         * @param savedInstanceState
         */
//        组名
        groupname = new String[]{getResources().getString(R.string.Check_One),
                getResources().getString(R.string.Check_Two),
                getResources().getString(R.string.Check_Three)};
//        二级标题
        data = new String[][]{
                {getResources().getString(R.string.Student_package),
                        getResources().getString(R.string.Youth_package),
                        getResources().getString(R.string.Middleage_package),
                        getResources().getString(R.string.Elderly_package)},
                {getResources().getString(R.string.Cardio_cerebrovascular_package),
                        getResources().getString(R.string.Hypertension_package),
                        getResources().getString(R.string.Health_care_package),
                        getResources().getString(R.string.Health_care_brain_package),
                        getResources().getString(R.string.Immune_function_test_package)},
                {getResources().getString(R.string.Gynecological_routine_package),
                        getResources().getString(R.string.Premarital_medical_package),
                        getResources().getString(R.string.Pre_pregnancy_medical_package),
                        getResources().getString(R.string.Entrance_examination_package)}};
//        二级详情
        listinfo = new String[][]{
                {getResources().getString(R.string.Student_package_money),
                        getResources().getString(R.string.Youth_package_money),
                        getResources().getString(R.string.Middleage_package_money),
                        getResources().getString(R.string.Elderly_package_money)},
                {getResources().getString(R.string.Cardio_cerebrovascular_package_money),
                        getResources().getString(R.string.Hypertension_package_money),
                        getResources().getString(R.string.Health_care_package_money),
                        getResources().getString(R.string.Health_care_brain_package_money),
                        getResources().getString(R.string.Immune_function_test_package_money)},
                {getResources().getString(R.string.Gynecological_routine_package_money),
                        getResources().getString(R.string.Premarital_medical_package_money),
                        getResources().getString(R.string.Pre_pregnancy_medical_package_money),
                        getResources().getString(R.string.Entrance_examination_package_money)}};
//        三级详情
        details = new String[][]{
                {getResources().getString(R.string.Student_package_msg),
                        getResources().getString(R.string.Youth_package_msg),
                        getResources().getString(R.string.Middleage_package_msg),
                        getResources().getString(R.string.Elderly_package_msg)},
                {getResources().getString(R.string.Cardio_cerebrovascular_package_msg),
                        getResources().getString(R.string.Hypertension_package_msg),
                        getResources().getString(R.string.Health_care_package_msg),
                        getResources().getString(R.string.Health_care_brain_package_msg),
                        getResources().getString(R.string.Immune_function_test_package_msg)},
                {getResources().getString(R.string.Gynecological_routine_package_msg),
                        getResources().getString(R.string.Premarital_medical_package_msg),
                        getResources().getString(R.string.Pre_pregnancy_medical_package_msg),
                        getResources().getString(R.string.Entrance_examination_package_msg)}};
    }

    private void initData() {
        btnHome = (RadioButton) findViewById(R.id.home_manage);
        btnManage = (RadioButton) findViewById(R.id.manage_manage);
        btnUser = (RadioButton) findViewById(R.id.user_manage);
        ll_notification = (LinearLayout) findViewById(R.id.ll_notification);
        ll_record = (LinearLayout) findViewById(R.id.ll_record);
        ll_sync = (LinearLayout) findViewById(R.id.ll_sync);

        btnHome.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        ll_notification.setOnClickListener(this);
        ll_record.setOnClickListener(this);
        ll_sync.setOnClickListener(this);

        elv = (ExpandableListView) findViewById(R.id.lvExp1);
        elv.setFocusable(false);
        /**
         * 这可以用于activity或fragment中。
         */
        elv.setAdapter(new CustomELVAdapter(this, ManageActivity.this, groupname, ImgBckgrnd, listinfo, data, details));
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                /**
                 * TODO:return true to enable group click
                 */

                // DO SOMETHING
//                Toast.makeText(ManageActivity.this, "abcd",Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_sync://一键同步
                loadingDialog = new LoadingDialog(this);
                loadingDialog.show();

                Call<GetRecords> call = GetRecordsUtils.getRecordsCall(LoginUtils.getNumber(this));
                call.enqueue(new Callback<GetRecords>() {
                    @Override
                    public void onResponse(Call<GetRecords> call, Response<GetRecords> response) {
                        if (response.code() == 200) {
                            List<Physicalrecord> physicalrecords = DataSupport.findAll(Physicalrecord.class);
                            List<GetRecords.DataBean> data = response.body().getData();
                            int localsize = physicalrecords.size();
                            int netsize = data.size();
                            if (localsize != netsize) {
                                for (int i = 0; i < netsize; i++) {
                                    physicalrecord = new Physicalrecord(data.get(i).getRname(), data.get(i).getRidcard(),
                                            data.get(i).getRphone(), data.get(i).getRaddress(),
                                            String.valueOf(data.get(i).getSystolicpressure()),
                                            String.valueOf(data.get(i).getDiastolicpressure()),
                                            String.valueOf(data.get(i).getMeanpressure()), data.get(i).getDname(),
                                            data.get(i).getDnumber(), data.get(i).getCtime());

                                    physicalrecord.save();
                                }
                            }
                            loadingDialog.dismiss();
                            Toast.makeText(ManageActivity.this, "数据同步成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetRecords> call, Throwable t) {
                        loadingDialog.dismiss();
                        Toast.makeText(ManageActivity.this, "数据同步失败", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: " + call.request().toString());
                    }
                });

                break;

            case R.id.ll_record://体检记录
                startActivity(new Intent(this, PhysicalrecordActivity.class));
                break;

            case R.id.ll_notification://通知用户
                startActivity(new Intent(this, NotificationActivity.class));
                break;

            case R.id.home_manage:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);//右往左推出效果
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            case R.id.manage_manage:
                break;
            case R.id.user_manage:
                Intent intent1 = new Intent(this, UserActivity.class);
                startActivity(intent1);
//                overridePendingTransition(R.anim.slide_left,R.anim.slide_right);//左右交错效果
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            default:
                break;
        }
    }
}
