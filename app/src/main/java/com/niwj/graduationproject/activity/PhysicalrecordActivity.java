package com.niwj.graduationproject.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.adapter.PhysicalRecordAdapter;
import com.niwj.graduationproject.api.pojo.DeleteRecord;
import com.niwj.graduationproject.api.utils.DelRecordUtils;
import com.niwj.graduationproject.entity.Physicalrecord;
import com.niwj.graduationproject.entity.UserInfo;
import com.niwj.graduationproject.view.LoadingDialog;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prince70 on 2017/9/11.
 * 检测记录
 */

public class PhysicalrecordActivity extends AppCompatActivity {
    private static final String TAG = "PhysicalrecordActivity";
    private ListView lv_physical_record;
    private List<Physicalrecord> physicalrecords = new ArrayList<>();
    private PhysicalRecordAdapter adapter;
    private LoadingDialog loadingDialog;

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
        lv_physical_record.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String name = physicalrecords.get(position).getName();
                final String time = physicalrecords.get(position).getTime();
                final String docNumber = physicalrecords.get(position).getDocNumber();

                loadingDialog = new LoadingDialog(PhysicalrecordActivity.this);
                loadingDialog.show();

                new AlertDialog.Builder(PhysicalrecordActivity.this).setTitle("删除提示")
                        .setMessage("是否删除该条记录")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataSupport.deleteAll(Physicalrecord.class, "name = ?", name);

                                Call<DeleteRecord> call = DelRecordUtils.deleteRecordCall(docNumber, time);
                                call.enqueue(new Callback<DeleteRecord>() {
                                    @Override
                                    public void onResponse(Call<DeleteRecord> call, Response<DeleteRecord> response) {
                                        if (response.code() == 200) {
                                            physicalrecords.remove(position);
                                            adapter.notifyDataSetChanged();
                                            loadingDialog.dismiss();
                                            Toast.makeText(PhysicalrecordActivity.this, "数据删除成功",Toast.LENGTH_SHORT).show();
                                        }else {
                                            loadingDialog.dismiss();
                                            Toast.makeText(PhysicalrecordActivity.this, "数据删除失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DeleteRecord> call, Throwable t) {
                                        loadingDialog.dismiss();
                                        Log.e(TAG, "onFailure: " + call.request().toString());
                                    }
                                });


                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return true;
            }
        });


    }
}
