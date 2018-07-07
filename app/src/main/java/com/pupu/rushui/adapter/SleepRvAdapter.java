package com.pupu.rushui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pupu.rushui.R;
import com.pupu.rushui.entity.SleepData;

import java.util.List;

/**
 * Created by pupu on 2018/7/2.
 */

public class SleepRvAdapter extends RecyclerView.Adapter<SleepRvAdapter.MyViewHolder> {

    List<SleepData> mDatas;
    Context context;

    public SleepRvAdapter(Context context, List<SleepData> mDatas) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_rv_sleepdata, null);
        if (parent.getChildCount() == 0) {
            parent.addView(view);
        }
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
