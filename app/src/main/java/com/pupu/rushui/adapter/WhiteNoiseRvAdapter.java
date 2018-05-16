package com.pupu.rushui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pupu.rushui.R;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.WhiteNoise;

import java.util.List;

/**
 * Created by pupu on 2018/5/13.
 */

public class WhiteNoiseRvAdapter extends RecyclerView.Adapter<WhiteNoiseRvAdapter.MyViewHolder> {

    Context context;
    List<WhiteNoise> mDatas;
    LayoutInflater inflater;

    public WhiteNoiseRvAdapter(Context context, List<WhiteNoise> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_rv_whitenoise, null);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
