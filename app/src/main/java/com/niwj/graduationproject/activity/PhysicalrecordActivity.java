package com.niwj.graduationproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.adapter.PhysicalRecordAdapter;
import com.niwj.graduationproject.entity.Physicalrecord;
import com.niwj.graduationproject.entity.UserInfo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prince70 on 2017/9/11.
 * 检测记录
 */

public class PhysicalrecordActivity extends AppCompatActivity {
    private static final String TAG = "PhysicalrecordActivity";
    private ListView lv_physical_record;
    private List<Physicalrecord> physicalrecords = new ArrayList<>();
    private PhysicalRecordAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicalrecord);
        initData();

    }

    private void initData() {
//        DataSupport.deleteAll(Physicalrecord.class);
        lv_physical_record = (ListView) findViewById(R.id.lv_physical_record);
        List<Physicalrecord> all = DataSupport.findAll(Physicalrecord.class);
        Log.e(TAG, "initData: " + all.toString());
        for (Physicalrecord physicalrecord : all) {
            Log.e(TAG, "initData: " + physicalrecord.toString());
            physicalrecords.add(physicalrecord);
        }

        Log.e(TAG, "initData: physicalrecords " + physicalrecords.toString());
        adapter = new PhysicalRecordAdapter(this, physicalrecords);
        lv_physical_record.setAdapter(adapter);


    }
}
