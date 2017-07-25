package com.example.apple.zbtestdemo.listener;

import android.view.View;

/**
 * Created by apple on 2017/6/20.
 */

public class ListenerWithPosition implements View.OnClickListener{

    private int position;
    private Object holder;
    private OnClickWithPositionListener mOnclickerLitener;

    public ListenerWithPosition(int position, Object holder){
        this.position = position;
        this.holder = holder;
    }

    public interface OnClickWithPositionListener<T>{
        void onclick(View v,int position, T holder);
    }

    @Override
    public void onClick(View v) {
        if (mOnclickerLitener!=null){
            mOnclickerLitener.onclick(v,position,holder);
        }
    }

    public void setOnclickListener(OnClickWithPositionListener mOnclickerLitener){
        this.mOnclickerLitener = mOnclickerLitener;
    }
}
