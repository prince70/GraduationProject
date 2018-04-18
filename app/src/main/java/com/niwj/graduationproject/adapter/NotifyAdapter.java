package com.niwj.graduationproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.entity.ResidentMsg;

import java.util.List;

/**
 * Created by prince70 on 2017/8/23.
 * 通知消息适配器
 */

public class NotifyAdapter extends BaseAdapter {

    private Activity mContext;
    private List<ResidentMsg> msgDatas;
    private int tempPosition = -1;  //记录已经点击的CheckBox的位置

    public NotifyAdapter(Activity mContext, List<ResidentMsg> msgDatas) {
        this.mContext = mContext;
        this.msgDatas = msgDatas;
    }


    @Override
    public int getCount() {
        return msgDatas.size()>0?msgDatas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return msgDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.notification_item, null);
            holder.notify_name = (TextView) convertView.findViewById(R.id.tv_notify_name);
            holder.notify_idcard = (TextView) convertView.findViewById(R.id.tv_notify_idcard);
            holder.notify_phone = (TextView) convertView.findViewById(R.id.tv_notify_phone);
            holder.notify_address = (TextView) convertView.findViewById(R.id.tv_notify_address);
            holder.notify_systolicPressure = (TextView) convertView.findViewById(R.id.tv_notify_systolicPressure);
            holder.notify_diastolicPressure = (TextView) convertView.findViewById(R.id.tv_notify_diastolicPressure);
            holder.notify_meanPressure = (TextView) convertView.findViewById(R.id.tv_notify_meanPressure);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_notify);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        在这里显示
//TODO 使用多个textView 到时候在接口提取数据较方便
        holder.notify_name.setText(msgDatas.get(position).getName());
        holder.notify_idcard.setText(msgDatas.get(position).getIdcard());
        holder.notify_phone.setText(msgDatas.get(position).getPhonenumber());
        holder.notify_address.setText(msgDatas.get(position).getAddress());
        holder.notify_systolicPressure.setText(msgDatas.get(position).getSystolicPressure());
        holder.notify_diastolicPressure.setText(msgDatas.get(position).getDiastolicPressure());
        holder.notify_meanPressure.setText(msgDatas.get(position).getMeanPressure());

//        holder.checkBox.setChecked(false);
//
//        final ViewHolder finalHolder = holder;
//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                Toast.makeText(mContext, isChecked + ""+finalHolder.textView.getText(), Toast.LENGTH_SHORT).show();
//                if (isChecked) {
//                    if (mContext instanceof OnCheckedChangedListener) {
//                        ((OnCheckedChangedListener) mContext).getItemMsg(
//                                finalHolder.notify_name.getText().toString(),
//                                finalHolder.notify_phone.getText().toString(),
//                                finalHolder.notify_systolicPressure.getText().toString(),
//                                finalHolder.notify_diastolicPressure.getText().toString(),
//                                finalHolder.notify_meanPressure.getText().toString());
////                        Toast.makeText(mContext, finalHolder.textView.getText()+ "", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
        holder.checkBox.setId(position);    //设置当前position为CheckBox的id
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (tempPosition != -1) {
                        //根据id找到上次点击的CheckBox,将它设置为false.
                        CheckBox tempCheckBox = (CheckBox) mContext.findViewById(tempPosition);
                        if (tempCheckBox != null) {
                            tempCheckBox.setChecked(false);
                        }
                    }
                    //保存当前选中CheckBox的id值
                    tempPosition = buttonView.getId();

                } else {    //当CheckBox被选中,又被取消时,将tempPosition重新初始化.
                    tempPosition = -1;
                }
            }
        });
        if (position == tempPosition)   //比较位置是否一样,一样就设置为选中,否则就设置为未选中.
            holder.checkBox.setChecked(true);
        else holder.checkBox.setChecked(false);

        return convertView;
    }

    public static class ViewHolder {
        public  TextView notify_name;
        public  TextView notify_idcard;
        public TextView notify_phone;
        public  TextView notify_address;
        public  TextView notify_systolicPressure;//收缩压
        public  TextView notify_diastolicPressure;//舒张压
        public  TextView notify_meanPressure;//平均压
        public  CheckBox checkBox;
    }

    public interface OnCheckedChangedListener {
        void getItemMsg(String notify_name, String notify_phone, String notify_systolicPressure, String notify_diastolicPressure, String notify_meanPressure);
    }

    //返回当前CheckBox选中的位置,便于获取值.
    public int getSelectPosition() {
        return tempPosition;
    }



}
