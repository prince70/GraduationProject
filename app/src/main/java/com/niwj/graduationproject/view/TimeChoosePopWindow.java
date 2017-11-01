package com.niwj.graduationproject.view;

import android.content.Context;
import android.view.View;

import com.niwj.graduationproject.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by prince70 on 2017/10/30.
 * 日期选择弹窗
 */

public class TimeChoosePopWindow  extends BottomPopupWindow implements ScrollSelector.OnItemSelectorListener{

    private int hour;
    private int minute;

    private ArrayList<String> hours;
    private ArrayList<String> minutes;

    private ScrollSelector selector_hour;
    private ScrollSelector selector_minute;
    private TimeChooseListener mListener;                                                           //时间选择的回调接口

    public TimeChoosePopWindow(Context context){
        this(context, Calendar.getInstance());                                                       //9点整
    }

    /**
     * @param context
     * @param calendar 现在时间
     */
    public TimeChoosePopWindow(Context context, Calendar calendar) {
        super(context, R.layout.pop_time_choose);

        //获取控件
        selector_hour = getView(R.id.selector_hour);
        selector_minute = getView(R.id.selector_minute);

        //设置支持滚动手势
        selector_hour.setRollAble(true);
        selector_minute.setRollAble(true);

        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);

        //初始化内容
        hours = new ArrayList<>();
        minutes = new ArrayList<>();

        for (int i = 0; i < 24; i++){
            hours.add(i + "");              //小时范围 0 - 23
        }

        for (int i = 0; i< 60; i++){
            minutes.add(i  + "");          //分钟范围 0 - 59
        }

        //设置范围
        selector_hour.setItemContents(hours);
        selector_minute.setItemContents(minutes);

        //设置滚动监听
        selector_hour.setOnItemSelectorListener(this);
        selector_minute.setOnItemSelectorListener(this);

        //设置当前位置
        selector_hour.setSelectedIndex(hour + 1);
        selector_minute.setSelectedIndex(minute);

        setDateText();
    }

    /**
     * 设置日期展示
     */
    private void setDateText(){
        setMsg(hour + ": " + minute );
    }

    /**
     * 设置回调接口
     */
    public void setTimeChooseListener(TimeChooseListener l){
        mListener = l;
    }

    /**
     * 设置日期
     */
    @Deprecated
    public void setTime(int hour , int minute){

        int posHour = hours.indexOf(hour + "");
        if (posHour < 0) return;

        int posMinute = minutes.indexOf(minute + "");
        if(posMinute < 0) return;

        selector_hour.setSelectedIndex(posHour + 1);
        selector_minute.setSelectedIndex(posMinute);

//        selector_hour.setSelectedIndex(posHour + 1);
//        selector_minute.setSelectedIndex(posMinute + 1);
//        selector_minute.setSelectedIndex(posMinute + 2);
    }

    @Override
    public void onItemSelector(View v, int position) {
        switch (v.getId()){
            case R.id.selector_hour:
                hour = position;
                break;
            case R.id.selector_minute:
                minute = position;
                break;
        }
        setDateText();
    }

    @Override
    protected void confirm() {
        if (mListener != null){
            mListener.timeChoose(hour, minute);
        }
    }

    @Override
    protected void cancel() {

    }

    /**
     * 日期选择的回调接口
     */
    public interface TimeChooseListener{
        void timeChoose(int hour, int minute);
    }
}
