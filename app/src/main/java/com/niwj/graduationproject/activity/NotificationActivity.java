package com.niwj.graduationproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mob.tools.SSDKHandlerThread;
import com.niwj.graduationproject.R;
import com.niwj.graduationproject.adapter.NotifyAdapter;
import com.niwj.graduationproject.entity.ResidentMsg;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by prince70 on 2017/8/23.
 * 通知用户activity
 */

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, NotifyAdapter.OnCheckedChangedListener {
    private static final String TAG = "NotificationActivity";
    private ListView mListView;
    private Button btn_confirm;
    private List<ResidentMsg> msgs = new ArrayList<>();
    private NotifyAdapter adapter;

    private String notifyName;
    private String notifyPhone;
    private String notifySystolicPressure;
    private String notifyDiastolicPressure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        initData();
    }

    private void initData() {
        mListView = (ListView) findViewById(R.id.lv_notify);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        ResidentMsg residentMsg1 = new ResidentMsg("1", "aaa", "!!!", "%%%", "444", "123");
        ResidentMsg residentMsg2 = new ResidentMsg("倪斯玲", "440923199409233181", "13437578156", "广东省广州市番禺区XXX", "90", "100");
        ResidentMsg residentMsg3 = new ResidentMsg("3", "ccc", "###", "&&&", "666", "789");
        msgs.add(residentMsg1);
        msgs.add(residentMsg2);
        msgs.add(residentMsg3);
        adapter = new NotifyAdapter(this, msgs);
        mListView.setAdapter(adapter);

        /**
         * 如果listitem里面包括button或者checkbox等控件，默认情况下listitem会失去焦点，
         * 导致无法响应item的事件，最常用的解决办法
         * 是在listitem的布局文件中设置descendantFocusability属性。
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResidentMsg residentMsg = msgs.get(position);
                Log.e(TAG, "onItemClick: " + residentMsg.toString());
                Toast.makeText(NotificationActivity.this, position + "", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm://发送通知
                Log.e(TAG, "onClick: 通知" + notifyName + "  " + notifyPhone + "  " + notifySystolicPressure + "  " + notifyDiastolicPressure);
                SMSSDK.getVerificationCode("+86", "13414901394", new OnSendMessageHandler() {
                    @Override
                    public boolean onSendMessage(String s, String s1) {
                        Log.e(TAG, "onSendMessage: " + s + s1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NotificationActivity.this, "发送通知成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return false;
                    }
                });
                break;
            default:
                break;
        }
    }


    @Override
    public void getItemMsg(String notify_name, String notify_phone, String notify_systolicPressure, String notify_diastolicPressure) {
        notifyName = notify_name;
        notifyPhone = notify_phone;
        notifySystolicPressure = notify_systolicPressure;
        notifyDiastolicPressure = notify_diastolicPressure;
    }
}
