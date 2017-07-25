package com.example.apple.zbtestdemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by apple on 2017/6/13.
 */

public class ToastUtils {

    public static void showShortToast(String message){
        Toast.makeText(BaseUtils.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public  static void showLongToast(String message){
        Toast.makeText(BaseUtils.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void showNormalToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
