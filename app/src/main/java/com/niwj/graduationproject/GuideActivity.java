package com.niwj.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.niwj.graduationproject.control.SharePreferenceUtil;
import com.niwj.graduationproject.control.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prince70 on 2017/8/10.
 * 引导页
 */

public class GuideActivity extends BaseActivity {

    private static final String TAG = "GuideActivity";
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<View> mListViews;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private boolean IsFirst;//第一次进入应用

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(R.layout.activity_guide);
        Log.e(TAG, "onCreate: " + "GuideActivity");
        JudgeFirst();

        mListViews = new ArrayList<>();

        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.activity_guide_item_view1, null);
        imageView1 = (ImageView) view1.findViewById(R.id.iv_guide_item1);
        Picasso.with(GuideActivity.this).load(R.mipmap.gfirst).into(imageView1);

        View view2 = inflater.inflate(R.layout.activity_guide_item_view2, null);
        imageView2 = (ImageView) view2.findViewById(R.id.iv_guide_item2);
        Picasso.with(GuideActivity.this).load(R.mipmap.gsecond).into(imageView2);

        View view3 = inflater.inflate(R.layout.activity_guide_item_view3, null);
        imageView3 = (ImageView) view3.findViewById(R.id.iv_guide_item3);
        Picasso.with(GuideActivity.this).load(R.mipmap.gthird).into(imageView3);

        View view4 = inflater.inflate(R.layout.activity_guide_item_view4, null);
        imageView4 = (ImageView) view4.findViewById(R.id.iv_guide_item4);
        Picasso.with(GuideActivity.this).load(R.mipmap.gfourth).into(imageView4);

        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideActivity.this.startActivity(new Intent(GuideActivity.this,
                        LoginActivity.class));
                finish();
            }
        });

        mListViews.add(view1);
        mListViews.add(view2);
        mListViews.add(view3);
        mListViews.add(view4);

        mViewPager = (ViewPager) findViewById(R.id.guide_view_group);
        mPagerAdapter = new PagerAdapter(mListViews);
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private boolean misScrolled;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        misScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        misScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !misScrolled) {
                            startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                            GuideActivity.this.finish();
                        }
                        misScrolled = true;
                        break;
                }

            }
        });
    }

    /**
     * 判断是否第一次进入应用
     */
    private void JudgeFirst() {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this);
        IsFirst = sp.getBoolean("FirstCome", true);
        if (!IsFirst) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private class PagerAdapter extends android.support.v4.view.PagerAdapter {
        private List<View> mViews;

        public PagerAdapter(List<View> mViews) {
            this.mViews = mViews;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(mViews.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(mViews.get(position), 0);
            return mViews.get(position);
        }
    }

    private void saveTag() {
        SharePreferenceUtil sp = SharePreferenceUtil.getInstance(this);
        sp.setBoolean("FirstCome", false);
    }

    @Override
    public void finish() {
        super.finish();
        saveTag();
    }
}
