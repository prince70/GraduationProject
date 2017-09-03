package com.niwj.graduationproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;

import com.niwj.graduationproject.R;

/**
 * Created by prince70 on 2017/8/25.
 * 自定义switchButton
 */

public class CustomSwitch extends CompoundButton implements View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "Switch";

    private int currentClickFlag = 0;// 避免一次按钮滑动没有完成又点击一次造成冲突
    private Paint paint;
    private int width; // 控件宽度
    private int height; // 高度
    private float startPosition; // 按钮起点位置
    private float endPosition; // 按钮终点位置
    private int btnWidth; // 按钮高度
    private float nowX; // 按钮当前位置
    private float preX;// 按钮点击之前的位置，用于再手指抬起时候做判断（因为手指点击的时候nowX就改变了）

    private boolean isChecked ; // 按钮是否被选中

    int lineCheckedColor;// 中间线条颜色
    int lineUnCheckedColor;// 没有选中时中间线条颜色
    int lineHeight;// 中间线条高度
    int innerCheckedBroderColor;// 里面按钮边框选中颜色
    int innerUnCheckedBroderColor;// 里面按钮边框未选中状态
    int innerBroderWidth;// 里面按钮边框宽度
    int btnCheckedColor;// 按钮选中颜色
    int btnUnCheckedColor;// 按钮没有选中颜色
    int innerPadding;

    private Rect lineRect; // 中间线

    private RectF buttonRect; // 按钮外面的圆范围
    private RectF buttonInnerRect; // 按钮里面的圆范围

    public CustomSwitch(Context context) {
        super(context);
        init();
    }

    public CustomSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.customSwith);
        lineCheckedColor = a.getColor(R.styleable.customSwith_lineCheckedColor, Color.parseColor("#00000000"));// 选中时中间线条颜色
        lineUnCheckedColor = a.getColor(R.styleable.customSwith_lineUnCheckedColor, Color.parseColor("#00000000"));// 没有选中时中间线条颜色
        lineHeight = a.getDimensionPixelSize(R.styleable.customSwith_lineHeight, 0);// 中间线条高度
        innerCheckedBroderColor = a.getColor(R.styleable.customSwith_innerCheckedBroderColor, Color.parseColor("#00000000"));// 里面按钮边框选中颜色
        innerUnCheckedBroderColor = a.getColor(R.styleable.customSwith_innerUnCheckedBroderColor, Color.parseColor("#00000000"));// 里面按钮边框未选中状态
        innerBroderWidth = a.getDimensionPixelSize(R.styleable.customSwith_innerBroderWidth, 0);// 里面按钮边框宽度
        btnCheckedColor = a.getColor(R.styleable.customSwith_btnCheckedColor, Color.parseColor("#2ECC71"));// 按钮选中颜色
        btnUnCheckedColor = a.getColor(R.styleable.customSwith_btnUnCheckedColor, Color.parseColor("#BDC3C7"));// 按钮没有选中颜色
        innerPadding = a.getDimensionPixelSize(R.styleable.customSwith_innerPadding, 0);// 里面按钮边框的距离
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿效果
        setOnTouchListener(this);
        ViewTreeObserver vo = this.getViewTreeObserver();
        vo.addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        width = getWidth();
        height = getHeight();
        btnWidth = height - getPaddingTop() - getPaddingBottom(); // 按钮宽度距控件上下5个单位
        startPosition = getPaddingLeft() + innerPadding;
        endPosition = width - btnWidth - getPaddingRight() - innerPadding; // 终点位置
        lineRect = new Rect(btnWidth / 2 + getPaddingLeft(), (height - lineHeight) / 2, width - btnWidth / 2 - getPaddingRight(), (height + lineHeight) / 2); // 画线的矩形
        if (isChecked) {
            nowX = endPosition;
            buttonRect = new RectF(endPosition, getPaddingTop() + innerPadding, btnWidth + endPosition, height - getPaddingBottom() - innerPadding); // 外面按钮的矩形
        } else {
            nowX = startPosition;
            buttonRect = new RectF(startPosition, getPaddingTop() + innerPadding, btnWidth + startPosition, height - getPaddingTop() - innerPadding); // 外面按钮的矩形
        }
        Log.d(TAG, "innerBroderWidth :" + innerBroderWidth);
        buttonInnerRect = new RectF(buttonRect.left + innerBroderWidth, buttonRect.top + innerBroderWidth, buttonRect.right - innerBroderWidth, buttonRect.bottom - innerBroderWidth);// 里面按钮的矩形
        invalidate();
    }

    /**
     * 设置按钮是否被选中
     */
    public void setChecked(boolean isChecked) {
        Log.d(TAG, "isChecked :" + isChecked);
        this.isChecked = isChecked;
        if (isChecked) {
            nowX = endPosition;
        } else {
            nowX = startPosition;
        }
        dX = 0;
        invalidate();
    }


    /**
     * 获取按钮选中状态
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * 状态改变监听
     */
    OnCheckedChangeListener changeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        buttonRect.left = nowX + dX;
        buttonRect.right = nowX + dX + btnWidth;
        buttonInnerRect.left = buttonRect.left + innerBroderWidth;
        buttonInnerRect.right = buttonRect.right - innerBroderWidth;
        Log.d(TAG, "isRight :" + isRight() + "nowX : " + nowX);
        if (isRight()) {
            paint.setColor(lineCheckedColor);
            canvas.drawRect(lineRect, paint);
            paint.setColor(innerCheckedBroderColor);
            canvas.drawOval(buttonRect, paint);
            paint.setColor(btnCheckedColor);
            canvas.drawOval(buttonInnerRect, paint);
        } else {
            paint.setColor(lineUnCheckedColor);
            canvas.drawRect(lineRect, paint);
            paint.setColor(innerUnCheckedBroderColor);
            canvas.drawOval(buttonRect, paint);
            paint.setColor(btnUnCheckedColor);
            canvas.drawOval(buttonInnerRect, paint);
        }
    }


    /**
     * 检查当前按钮的是否被选中
     */
    public boolean isRight() {
        return nowX + dX > (width - btnWidth) / 2;
    }


    /**
     * 检查触摸是否超出范围
     */
    public int isOut(float x) {
        boolean b1 = x >= 0;
        boolean b2 = x <= width - btnWidth;
        if (b1 && b2) {
            return 0;
        } else if (!b1) {
            return -1;
        } else {
            return 1;
        }
    }


    /**
     * 检查按钮状态
     */
    public int getBtnStatus(float x) {
        if (x < (width - btnWidth) / 2) {
            return -1; // 左边
        } else {
            return 1; // 右边
        }
    }

    public float downX;
    public float dX;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        int outCode = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = nowX;
                downX = event.getX();
                outCode = isOut(downX - btnWidth / 2);
                if (outCode == 0) {
                    nowX = downX - btnWidth / 2;
                } else if (outCode == -1) {
                    nowX = startPosition;
                } else {
                    nowX = endPosition;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                dX = x - downX;
                outCode = isOut(nowX + dX);
                if (outCode == 0) {

                } else if (outCode == -1) {
                    dX = 0;
                    nowX = startPosition;
                } else {
                    dX = 0;
                    nowX = endPosition;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                currentClickFlag++;
                nowX += dX;
                boolean tChecked = false;
                if (Math.abs(dX) < 3) {
                    dX = 0;
                    if (getBtnStatus(preX) < 0) {
                        scroll(nowX, endPosition, 1, currentClickFlag);
                        tChecked = true;
                    } else {
                        scroll(nowX, startPosition, -1, currentClickFlag);
                        tChecked = false;
                    }
                } else {
                    dX = 0;
                    if (getBtnStatus(nowX) < 0) {
                        scroll(nowX, startPosition, -1, currentClickFlag);
                        tChecked = false;
                    } else {
                        scroll(nowX, endPosition, 1, currentClickFlag);
                        tChecked = true;
                    }
                }
                if (tChecked != isChecked) {
                    isChecked = tChecked;
                    if (changeListener != null)
                        changeListener.onCheckedChanged(this, isChecked);
                    Log.e(TAG, "isChecked : " + isChecked);
                }
                break;
        }
        return true;
    }


    public void scroll(final float scrollStart, final float scrollEnd, final int status, final int flag) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentClickFlag != flag) {
                    return;
                }
                if (status == 1 && nowX < endPosition) {
                    nowX += 5;
                    if (nowX > endPosition) {
                        nowX = endPosition;
                    }
                    invalidate();
                    scroll(scrollStart + 5, scrollEnd, status, flag);
                } else if (status == -1 && nowX > startPosition) {
                    nowX -= 5;
                    if (nowX < startPosition) {
                        nowX = startPosition;
                    }
                    invalidate();
                    scroll(scrollStart - 5, scrollEnd, status, flag);
                }
            }
        }, 10);
    }

    private Handler handler = new Handler();


    private int convertDpToPixel(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    // 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    private int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

