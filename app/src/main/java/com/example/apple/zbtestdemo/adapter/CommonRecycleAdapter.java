package com.example.apple.zbtestdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/6/20.
 */

public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter{

    protected Context mContext;
    protected List<T> mData;
    private int layoutId;
    private View mView;

    public CommonRecycleAdapter(Context mContext,List<T> mData,int layoutId){
        this.mContext = mContext;
        this.mData = mData == null ? new ArrayList<T>() : mData;
        this.layoutId = layoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(layoutId,parent,false);
        return new CommonRecyclerHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommonRecyclerHolder){
            CommonRecyclerHolder commonRecyclerHolder = (CommonRecyclerHolder) holder;
            commonRecyclerHolder.position = position;
            convert(commonRecyclerHolder,mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (mData != null)?mData.size():0;
    }

    public void addData(List<T> data){
        if (mData != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setData(List<T> data){
        mData.clear();
        addData(data);
    }

    public abstract void convert(CommonRecyclerHolder holder,T t);
}
