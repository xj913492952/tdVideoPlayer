package com.example.gsyvideoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.holder.RecyclerItemDefaultHolder;
import com.example.gsyvideoplayer.model.VideoModel;

import java.util.List;

/**
 * Created by guoshuyu on 2017/1/9.
 */

public class RecyclerDefaultAdapter extends RecyclerView.Adapter {
    private final static String TAG = "RecyclerBaseAdapter";

    private List<VideoModel> itemDataList = null;
    private Context context = null;

    public RecyclerDefaultAdapter(Context context, List<VideoModel> itemDataList) {
        this.itemDataList = itemDataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_video_item_default, parent, false);
        final RecyclerItemDefaultHolder holder = new RecyclerItemDefaultHolder(context, v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        RecyclerItemDefaultHolder recyclerItemViewHolder = (RecyclerItemDefaultHolder) holder;
        recyclerItemViewHolder.onBind(position, itemDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    public void setListData(List<VideoModel> data) {
        itemDataList = data;
        notifyDataSetChanged();
    }
}
