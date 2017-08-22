package com.niwj.graduationproject.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.activity.PhyExaDetailActivity;

/**
 * Created by prince70 on 2017/8/14.
 */

public class CustomELVAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {


    private LayoutInflater vi;
    private String[][] data;
    private String[][] details;
    private String[][] listinfo;
    private String[] groupname;
    private int[] ImgBckgrnd;
    private Context context;
    BounceInterpolator bounceInterpolator;
    View v;


    private static final int GROUP_ITEM_RESOURCE = R.layout.list_group;
    private static final int CHILD_ITEM_RESOURCE = R.layout.list_item;


    public CustomELVAdapter(Context context, Activity activity, String[] groupname, int[] ImgBckgrnd, String[][] listinfo, String[][] data, String[][] details) {
        this.context = context;
        this.groupname = groupname;
        this.ImgBckgrnd = ImgBckgrnd;
        this.listinfo = listinfo;
        this.data = data;
        this.details = details;

        vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        bounceInterpolator = new BounceInterpolator();
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String child = getChild(groupPosition, childPosition);
        String list = getList(groupPosition, childPosition);
        v = convertView;
        v = vi.inflate(CHILD_ITEM_RESOURCE, null);
        final ViewHolder holder = new ViewHolder(v);
        if (child != null) {
            holder.ExpCol.setFocusable(false);

            /**
             * 设置childlist头和细节
             */

            holder.ListHead.setText(Html.fromHtml(child));
            holder.ListDetail.setText(Html.fromHtml(list));

            /**
             * 展开收缩动画
             * 使用布局可见性属性+
             *
             */

            final Animation slidedown = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_down);
            final Animation slideup = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_up);


            /**SET BOUNCE INTERPOLATOR TO SLIDEDOWN**/
            slidedown.setInterpolator(bounceInterpolator);


            slideup.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.toggleLayout.setVisibility(View.GONE);
                }
            });


            /**
             * 展开收缩二级菜单
             * **/
            holder.ExpCol.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub


                    if (holder.ExpCol.isChecked()) {
                        holder.toggleLayout.startAnimation(slidedown);
                        holder.toggleLayout.setVisibility(View.VISIBLE);

                    } else {

                        holder.toggleLayout.startAnimation(slideup);

                    }
                }

            });
            /**
             * 子菜单的监听事件
             * **/
            holder.ChildLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (holder.ExpCol.isChecked()) {
                        holder.ExpCol.setChecked(false);
                    } else {
                        holder.ExpCol.setChecked(true);
                    }
                }
            });
            /**
             * 二级子按钮onclick方法
             *
             * **/
//            holder.directions.setText(details[groupPosition][childPosition]);
            holder.details.setText(details[groupPosition][childPosition]);
//            holder.details.setText("五六七八");

//            holder.directions.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    //
//                    //DO SOMETHING
//                    Toast.makeText(context, holder.directions.getText().toString(), Toast.LENGTH_SHORT).show();
//                }
//            });

            holder.details.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //DO SOMETHING
                    Toast.makeText(context, holder.details.getText().toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, PhyExaDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("details", holder.details.getText().toString());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        return v;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            v = vi.inflate(GROUP_ITEM_RESOURCE, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        if (getGroupName(groupPosition) != null) {
            /**SET GROUP HEAD TEXT**/
            holder.GroupHead.setText(getGroupName(groupPosition));
            /**SET IMAGE BACKGROUND
             * DO NOT LOAD IMAGES ON UI THREAD
             * USE ASYNCTASK TO LOAD IMAGES FROM WEB **/
            holder.LayoutBackground.setBackgroundResource(getImage(groupPosition));
        }
        return v;
    }

    public int getImage(int groupPosition) {
        return ImgBckgrnd[groupPosition];
    }

    public String getGroupName(int groupPosition) {
        return groupname[groupPosition];
    }

    /**
     * 获取一级标签下二级标签的内容
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public String getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return details[groupPosition][childPosition];
    }

    public String getList(int groupPosition, int childPosition) {
        return listinfo[groupPosition][childPosition];

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return details[groupPosition].length;
    }

    public int getGroupCount() {
        return groupname.length;
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }

    public String getGroup(int groupPosition) {
        return "group-" + groupPosition;
    }
}

