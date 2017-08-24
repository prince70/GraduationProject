package com.niwj.graduationproject.adapter;

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

    private Context mContext;
    private List<ResidentMsg> msgDatas;

    public NotifyAdapter(Context mContext, List<ResidentMsg> msgDatas) {
        this.mContext = mContext;
        this.msgDatas = msgDatas;
    }


    @Override
    public int getCount() {
        return msgDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return msgDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.notification_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_notify_msg);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_notify);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        在这里显示

        holder.textView.setText(msgDatas.get(position).toString());
        holder.checkBox.setChecked(false);

        final ViewHolder finalHolder = holder;
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(mContext, isChecked + ""+finalHolder.textView.getText(), Toast.LENGTH_SHORT).show();
                if (isChecked){
                    if (mContext instanceof OnCheckedChangedListener){
                        ((OnCheckedChangedListener) mContext).getItemMsg(finalHolder.textView.getText().toString());
//                        Toast.makeText(mContext, finalHolder.textView.getText()+ "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView textView;
        CheckBox checkBox;
    }

    public interface OnCheckedChangedListener {
        void getItemMsg(String notify_msg);
    }


}
