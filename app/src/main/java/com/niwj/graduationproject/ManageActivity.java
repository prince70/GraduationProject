package com.niwj.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.niwj.graduationproject.adapter.CustomELVAdapter;


/**
 * Created by prince70 on 2017/8/10.
 * 管理
 */

public class ManageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ManageActivity";
    private RadioButton btnHome;
    private RadioButton btnManage;
    private RadioButton btnUser;

    private ExpandableListView elv;

    /**
     * 这些数据可以从数据库或Web中获取，使用Web API和加载到适配器。
     *
     * @param savedInstanceState
     */
    private static final String[] groupname = {"套餐一", "套餐二", "套餐三"};


    private static final String[][] details =
                    {{"一1", "二2", "三3"},
                    {"四4", "五5", "六6"},
                    {"七7", "八8"}};


    private static final String[][] data =
            {{"一", "二", "三"},
                    {"四", "五", "六"},
                    {"七", "八"}};

    private static final String[][] listinfo =
            {{"床前明月光", "疑是地上霜", "举头望明月"},
                    {"一岁一枯荣", "野火烧不尽", "春风吹又生"},
                    {"天王霸地虎", "小鸡炖蘑菇"}};


    private static final int[] ImgBckgrnd = {R.mipmap.bangalore, R.mipmap.mysore, R.mipmap.coorg};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        btnHome = (RadioButton) findViewById(R.id.home_manage);
        btnManage = (RadioButton) findViewById(R.id.manage_manage);
        btnUser = (RadioButton) findViewById(R.id.user_manage);


        btnHome.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnUser.setOnClickListener(this);

        elv = (ExpandableListView) findViewById(R.id.lvExp1);
        elv.setFocusable(false);
        /**
         * 这可以用于activity或fragment中。
         */
        elv.setAdapter(new CustomELVAdapter(this, ManageActivity.this, groupname, ImgBckgrnd, listinfo, data,details));
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                /**
                 * TODO:return true to enable group click
                 */

                // DO SOMETHING
                Toast.makeText(ManageActivity.this, "abcd",Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
