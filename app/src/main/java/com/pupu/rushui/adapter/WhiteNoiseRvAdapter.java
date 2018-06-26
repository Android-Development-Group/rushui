package com.pupu.rushui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.common.RxBusConstant;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.WhiteNoise;
import com.pupu.rushui.util.RxBusUtils;
import com.pupu.rushui.widget.DownloadView;

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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_whiteNoiseName.setText(mDatas.get(position).getName());

        if (mDatas.get(position).getState() == WhiteNoise.STATE_CHECKED) {
            holder.cb_dv.setState(DownloadView.STATE_CHECKED);
        } else if (mDatas.get(position).getState() == WhiteNoise.STATE_NO_DOWNLOADED) {
            holder.cb_dv.setState(DownloadView.STATE_NO_DOWNLOADED);
        } else if (mDatas.get(position).getState() == WhiteNoise.STATE_DOWNLOADING) {
            holder.cb_dv.setState(DownloadView.STATE_DOWNLOADING);
            holder.cb_dv.updateProgress(mDatas.get(position).getProgress());
        } else if (mDatas.get(position).getState() == WhiteNoise.STATE_NO_CHECKED) {
            holder.cb_dv.setState(DownloadView.STATE_NO_CHECKED);
        }
        holder.cv_whiteNoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
                if (holder.cb_dv.getState() == DownloadView.STATE_CHECKED) {
                    holder.cb_dv.setState(DownloadView.STATE_NO_CHECKED);
                } else if (holder.cb_dv.getState() == DownloadView.STATE_NO_CHECKED) {
                    holder.cb_dv.setState(DownloadView.STATE_CHECKED);
                } else if (holder.cb_dv.getState() == DownloadView.STATE_NO_DOWNLOADED) {
                    holder.cb_dv.setState(DownloadView.STATE_DOWNLOADING);
                } else if (holder.cb_dv.getState() == DownloadView.STATE_DOWNLOADING) {
                    //无动作

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv_whiteNoise;
        TextView tv_whiteNoiseName;
        DownloadView cb_dv;

        public MyViewHolder(View itemView) {
            super(itemView);
            cv_whiteNoise = itemView.findViewById(R.id.cv_whiteNoise);
            tv_whiteNoiseName = itemView.findViewById(R.id.tv_whiteNoiseName);
            cb_dv = itemView.findViewById(R.id.cb_dv);
        }
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
