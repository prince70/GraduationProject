package com.niwj.graduationproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.niwj.graduationproject.api.pojo.DoctorRegister;
import com.niwj.graduationproject.api.utils.DoctorRegisterUtils;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button testBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testBtn = (Button) findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<DoctorRegister> call = DoctorRegisterUtils.doctorRegister(
                        "440903199407101234", "梁医生", "D098765", "18312552520", "qq19950712");
                call.enqueue(new Callback<DoctorRegister>() {
                    @Override
                    public void onResponse(Call<DoctorRegister> call, retrofit2.Response<DoctorRegister> response) {
                        Log.e(TAG, "onResponse: " + "成功");
                    }

                    @Override
                    public void onFailure(Call<DoctorRegister> call, Throwable t) {
                        Log.e(TAG, "onResponse: " + "失败");
                    }
                });
            }
        });
    }
}
