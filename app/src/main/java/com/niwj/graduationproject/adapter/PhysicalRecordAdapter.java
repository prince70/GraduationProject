package com.niwj.graduationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.entity.Physicalrecord;

import java.util.List;

/**
 * Created by prince70 on 2017/9/11.
 */

public class PhysicalRecordAdapter extends BaseAdapter {
    private Context context;
    private List<Physicalrecord> physicalrecords;

    public PhysicalRecordAdapter(Context context, List<Physicalrecord> physicalrecords) {
        this.context = context;
        this.physicalrecords = physicalrecords;
    }

    @Override
    public int getCount() {
        return physicalrecords.size();
    }

    @Override
    public Object getItem(int position) {
        return physicalrecords.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.physical_record_item, null);
            holder.record_name = (TextView) convertView.findViewById(R.id.tv_record_name);
            holder.record_idcard = (TextView) convertView.findViewById(R.id.tv_record_idcard);
            holder.record_phone = (TextView) convertView.findViewById(R.id.tv_record_phone);
            holder.record_address = (TextView) convertView.findViewById(R.id.tv_record_address);
            holder.record_systolicPressure = (TextView) convertView.findViewById(R.id.tv_record_systolicPressure);
            holder.record_diastolicPressure = (TextView) convertView.findViewById(R.id.tv_record_diastolicPressure);
            holder.record_meanPressure = (TextView) convertView.findViewById(R.id.tv_record_meanPressure);
            holder.record_docName = (TextView) convertView.findViewById(R.id.tv_record_docName);
            holder.record_docNumber = (TextView) convertView.findViewById(R.id.tv_record_docNumber);
            holder.record_time = (TextView) convertView.findViewById(R.id.tv_record_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.record_name.setText(physicalrecords.get(position).getName());
        holder.record_idcard.setText(physicalrecords.get(position).getIdcard());
        holder.record_phone.setText(physicalrecords.get(position).getPhonenumber());
        holder.record_address.setText(physicalrecords.get(position).getAddress());
        holder.record_systolicPressure.setText(physicalrecords.get(position).getSystolicPressure());
        holder.record_diastolicPressure.setText(physicalrecords.get(position).getDiastolicPressure());
        holder.record_meanPressure.setText(physicalrecords.get(position).getMeanPressure());
        holder.record_docName.setText(physicalrecords.get(position).getDocName());
        holder.record_docNumber.setText(physicalrecords.get(position).getDocNumber());
        holder.record_time.setText(physicalrecords.get(position).getTime());

        return convertView;
    }

    class ViewHolder {
        TextView record_name;
        TextView record_idcard;
        TextView record_phone;
        TextView record_address;
        TextView record_systolicPressure;//收缩压
        TextView record_diastolicPressure;//舒张压
        TextView record_meanPressure;//平均压
        TextView record_docName;
        TextView record_docNumber;
        TextView record_time;
    }
}
