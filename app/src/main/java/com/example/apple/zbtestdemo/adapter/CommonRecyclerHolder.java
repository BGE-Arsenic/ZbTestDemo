package com.example.apple.zbtestdemo.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.example.apple.zbtestdemo.listener.ListenerWithPosition;

/**
 * Created by apple on 2017/6/20.
 */

public class CommonRecyclerHolder extends RecyclerView.ViewHolder{

    public View mConvertView;
    public int position;
    private SparseArray<View> mViews;

    public CommonRecyclerHolder(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public CommonRecyclerHolder setTextViewText(@IdRes int viewId,String text){
        TextView tv = getView(viewId);
        if (!TextUtils.isEmpty(text)){
            tv.setText(text);
        } else {
            tv.setText(" ");
        }
        return this;
    }

    public CommonRecyclerHolder setOnclickListener(ListenerWithPosition.OnClickWithPositionListener onclickListener,@IdRes int... viewid){
        ListenerWithPosition listenerWithPosition = new ListenerWithPosition(position,this);
        listenerWithPosition.setOnclickListener(onclickListener);
        for (int id : viewid){
            View v = getView(id);
            v.setOnClickListener(listenerWithPosition);
        }
        return this;
    }
}
