package com.pupu.rushui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.entity.AlarmTime;

import java.util.List;

/**
 * Created by pupu on 2018/4/15.
 */

public class AlarmRvAdapter extends RecyclerView.Adapter<AlarmRvAdapter.MyViewHolder> {

    Context context;
    List<AlarmTime> mDatas;
    LayoutInflater inflater;

    public AlarmRvAdapter(Context context, List<AlarmTime> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_rv_alarm, null);
        if (parent.getChildCount() == 0) {
            parent.addView(view);
        }
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        AlarmTime alarmTime = mDatas.get(position);
        //设置时间，24小时制
        String hour24 = alarmTime.getHour24() + "";
        String minute = alarmTime.getMinute() + "";
        if (Integer.valueOf(hour24) < 10) {
            hour24 = "0" + hour24;
        }
        if (Integer.valueOf(minute) < 10) {
            minute = "0" + minute;
        }
        holder.tv_time.setText(hour24 + ":" + minute);

        holder.cv_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        CardView cv_alarm;
        CheckBox cb_alarm;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            cv_alarm = itemView.findViewById(R.id.cv_alarm);
            cb_alarm = itemView.findViewById(R.id.cb_alarm);
        }
    }

    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
