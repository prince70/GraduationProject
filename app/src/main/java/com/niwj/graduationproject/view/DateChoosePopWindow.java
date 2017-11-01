package com.niwj.graduationproject.view;

import android.content.Context;
import android.view.View;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.control.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by prince70 on 2017/10/31.
 * 日期选择框
 */

public class DateChoosePopWindow extends BottomPopupWindow implements ScrollSelector.OnItemSelectorListener{

    /**
     * 设置回调接口
     */
    public void setDateChooseListener(DateChooseListener l){
        mListener = l;
    }

    /**
     * 设置日期
     */
    public void setDate(int year, int month , int day){
        int posYear = years.indexOf(year + "");
        if (posYear < 0) return;

        int posMonth = months.indexOf(month + "");
        if (posMonth < 0) return;

        if (posMonth != month){
            resetDays();
        }

        int posDay = days.indexOf(day + "");
        if(posDay < 0) return;

        this.year = year;
        this.month = month;
        this.day = day;

        selector_year.setSelectedIndex(posYear + 1);
        selector_month.setSelectedIndex(posMonth + 1);
        selector_day.setSelectedIndex(posDay + 1);
    }

    /**
     * 设模式
     * @param year 显示年份
     * @param month 显示月份
     * @param day 显示日
     */
    public void setMode(boolean year, boolean month, boolean day){
        this.mode = 1;
        if (year) this.mode *= 2;
        if (month) this.mode *= 3;
        if (day) this.mode *= 7;

        selector_year.setVisibility(this.mode % 2 == 0 ? View.VISIBLE : View.GONE);
        selector_month.setVisibility(this.mode % 3 == 0 ? View.VISIBLE : View.GONE);
        selector_day.setVisibility(this.mode % 7 == 0 ? View.VISIBLE : View.GONE);
    }

    private int year;
    private int month;
    private int day;
    private int mode = 42;                      //显示模式
    private ArrayList<String> years;
    private ArrayList<String> months;
    private ArrayList<String> days;
    private ScrollSelector selector_year;
    private ScrollSelector selector_month;
    private ScrollSelector selector_day;
    private DateChooseListener mListener;       //日期选择的回调接口

    public DateChoosePopWindow(Context context){
        this(context, 150, 50);
    }

    /**
     * 基于当前日期
     */
    public DateChoosePopWindow(Context context, int frontYearNum, int rearYearNum){
        this(context, frontYearNum, rearYearNum, Calendar.getInstance());
    }

    /**
     * @param context
     * @param frontYearNum 前面的年份数
     * @param rearYearNum 后面的年份数
     * @param calendar 基日期
     */
    public DateChoosePopWindow(Context context, int frontYearNum, int rearYearNum, Calendar calendar) {
        super(context, R.layout.pop_datechoose);

        //获取控件
        selector_year = getView(R.id.selector_year);
        selector_month = getView(R.id.selector_month);
        selector_day = getView(R.id.selector_day);

        //设置支持滚动手势
        selector_year.setRollAble(true);
        selector_month.setRollAble(true);
        selector_day.setRollAble(true);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);

        //初始化内容
        //当前年份往前150年，往后50年
        years = new ArrayList<>();
        months = new ArrayList<>();
        days = new ArrayList<>();

        for (int i = 0; i < frontYearNum + rearYearNum + 1; i++){
            years.add((year - frontYearNum + i) + "");
        }
        for (int i = 0; i < 12; i++){
            months.add((i + 1) + "");
        }
        for (int i = 0; i< DateUtils.getMonthMaxDay(year, month); i++){
            days.add((i + 1) + "");
        }

        selector_year.setItemContents(years);
        selector_month.setItemContents(months);
        selector_day.setItemContents(days);

        //设置滚动监听
        selector_year.setOnItemSelectorListener(this);
        selector_month.setOnItemSelectorListener(this);
        selector_day.setOnItemSelectorListener(this);

        //设置当前位置
        selector_year.setSelectedIndex(frontYearNum + 1);
        selector_month.setSelectedIndex(month);
        selector_day.setSelectedIndex(day);

        setDateText();
    }

    private void setDateText(){
        setDateText(year, month, day);
    }

    /**
     * 设置日期展示
     */
    protected void setDateText(int year, int month, int day){
        String str = "";
        if (mode % 2 == 0) str += year + "年";
        if (mode % 3 == 0) str += month + "月";
        if (mode % 7 == 0) str += day + "日" + DateUtils.getWeek(year, month, day);
        setMsg(str);
    }

    @Override
    public void onItemSelector(View v, int position) {
        int lastMonthMaxDay = DateUtils.getMonthMaxDay(year, month);
        switch (v.getId()){
            case R.id.selector_year:
                if (position < years.size()) {
                    year = Integer.valueOf(years.get(position));
                }
                break;
            case R.id.selector_month:
                month = position + 1;
                break;
            case R.id.selector_day:
                day = position + 1;
                break;
        }
        if (lastMonthMaxDay != DateUtils.getMonthMaxDay(year, month)) {
            resetDays();
        }
        setDateText();
    }

    private void resetDays(){
        days = new ArrayList<>();
        for (int i = 0, size = DateUtils.getMonthMaxDay(year, month); i < size; i++) {
            days.add((i + 1) + "");
        }
        selector_day.setItemContents(days);
    }

    @Override
    protected void confirm() {
        if (mListener != null){
            mListener.dateChoose(year, month, day);
        }
    }

    @Override
    protected void cancel() {

    }

    /**
     * 日期选择的回调接口
     */
    public interface DateChooseListener{
        void dateChoose(int year, int month, int day);
    }
}
