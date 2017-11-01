package com.niwj.graduationproject.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.niwj.graduationproject.BaseActivity;
import com.niwj.graduationproject.R;
import com.niwj.graduationproject.api.pojo.BookExam;
import com.niwj.graduationproject.api.utils.BookExamUtils;
import com.niwj.graduationproject.entity.BookExamination;
import com.niwj.graduationproject.view.DateChoosePopWindow;
import com.niwj.graduationproject.view.TimeChoosePopWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.niwj.graduationproject.control.Regualr.isIdcard;

/**
 * Created by prince70 on 2017/10/30.
 * 确认签约体检信息
 */

public class ConfirmBookPhysicalExaminationActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ConfirmBookPhysicalExam";
    private EditText et_bname;
    private EditText et_bidcard;
    private EditText et_bdate;
    private EditText et_btime;
    private EditText et_bmoney;
    private EditText et_bmedical;

    private Button btn_confirm_book;

    private Calendar startCalendar;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_physical_examination);
        initData();
    }

    private void initData() {
        title = getIntent().getStringExtra("title");
        Log.e(TAG, "initData: title" + title);
        startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.HOUR_OF_DAY, 9);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        et_bname = (EditText) findViewById(R.id.et_bname);
        et_bidcard = (EditText) findViewById(R.id.et_bidcard);
        et_bdate = (EditText) findViewById(R.id.et_bdate);
        et_btime = (EditText) findViewById(R.id.et_btime);
        et_bmoney = (EditText) findViewById(R.id.et_bmoney);
        et_bmedical = (EditText) findViewById(R.id.et_bmedical);
        et_bmedical.setText(title);
        btn_confirm_book = (Button) findViewById(R.id.btn_confirm_book);
        et_bdate.setOnClickListener(this);
        et_btime.setOnClickListener(this);
        btn_confirm_book.setOnClickListener(this);

    }

    /**
     * 确认签约信息
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_bdate:
                DateChoosePopWindow dateChoosePopWindow = new DateChoosePopWindow(this);
                dateChoosePopWindow.show();
                dateChoosePopWindow.setDateChooseListener(new DateChoosePopWindow.DateChooseListener() {
                    @Override
                    public void dateChoose(int year, int month, int day) {
                        et_bdate.setText(year + "年" + month + "月" + day + "日");
                    }
                });
                break;

            case R.id.et_btime:
                TimeChoosePopWindow choosePopWindow = new TimeChoosePopWindow(this, startCalendar);
                choosePopWindow.setTimeChooseListener(new TimeChoosePopWindow.TimeChooseListener() {
                    @Override
                    public void timeChoose(int hour, int minute) {
                        et_btime.setText(hour + ":" + minute);
                    }
                });

                choosePopWindow.show();
                break;

            case R.id.btn_confirm_book:
                String bname = et_bname.getText().toString();
                String bidcard = et_bidcard.getText().toString();
                String bdate = et_bdate.getText().toString();
                String btime = et_btime.getText().toString();
                String bmoney = et_bmoney.getText().toString();

                if (!bname.equals("") || !bidcard.equals("") || !bdate.equals("") || !btime.equals("") || !bmoney.equals("")) {
                    if (isIdcard(bidcard)) {

//                        PostRecord.DataBean dataBean = new PostRecord.DataBean();
//                        dataBean.setCtime(s);
//                        dataBean.setDiastolicpressure(Integer.parseInt(diastolicPressure));
//                        dataBean.setDname(username);
//                        dataBean.setMeanpressure(Integer.parseInt(meanPressure));
//                        dataBean.setRaddress(residentAddress);
//                        dataBean.setRidcard(residentIdcard);
//                        dataBean.setRname(residentName);
//                        dataBean.setRphone(residentPhone);
//                        dataBean.setSystolicpressure(Integer.parseInt(systolicPressure));
                        BookExamination dataBean = new BookExamination();
                        dataBean.setBtype(title);
                        dataBean.setBname(bname);
                        dataBean.setBidcard(bidcard);
                        dataBean.setBdate(bdate);
                        dataBean.setBtime(btime);
                        dataBean.setBmoney(bmoney);

                        Gson gson = new Gson();
                        String contentJSON = gson.toJson(dataBean);
                        try {
                            JSONObject jsonObject = new JSONObject(contentJSON);
                            contentJSON = null;
                            contentJSON = jsonObject.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG, "dispData: 上传的json  " + contentJSON);


                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), contentJSON);
                        Call<BookExam> call = BookExamUtils.bookExam(body);
                        call.enqueue(new Callback<BookExam>() {
                            @Override
                            public void onResponse(Call<BookExam> call, Response<BookExam> response) {
                                String s = call.request().toString();
                                Log.e(TAG, "onResponse: "+s );
                                int code = response.code();
                                if (code==200){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ConfirmBookPhysicalExaminationActivity.this, "预约成功",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<BookExam> call, Throwable t) {
                                String s = call.request().toString();
                                Log.e(TAG, "onFailure: "+s );
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ConfirmBookPhysicalExaminationActivity.this, "预约失败",Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });


                        Toast.makeText(this, "预约成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "身份证输入有误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "您是否还有什么信息没填呢", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "onClick: " + bname + bidcard + bdate + btime + bmoney);
                break;

            default:
                break;
        }
    }
}
