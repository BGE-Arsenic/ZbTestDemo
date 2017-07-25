package com.example.apple.zbtestdemo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.test.suitebuilder.annotation.Suppress;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apple on 2017/6/13.
 */

public class BaseUtils {

    /**
     * context
     */
    private static Context mContext;

    private BaseUtils(){};

    public static void initContext(Context context){
        BaseUtils.mContext = context.getApplicationContext();
    }

    public static Context getContext(){
        if (mContext != null) return mContext;
        throw new NullPointerException("");
    }

    public static void openLink(Context context,String inputStr){
        Uri issuesUri = Uri.parse(inputStr);
        Intent intent = new Intent(Intent.ACTION_VIEW,issuesUri);
        context.startActivity(intent);
    }


    /**
     * Screen info
     */
    public static int getScreenWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) BaseUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    public static int getScreenWidth(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    public static int getScreenHeight(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) BaseUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    //info about Bar
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object object = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {

            c = Class.forName("com.android.internal.R$dimen");
            object = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    //dip px change
    public static int dip2px(Context context,float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale*dipValue + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * time
     */
    public static String dateFormat(String timeStr){
        if (timeStr == null){
            return "unknow";
        }
        @SuppressLint("SimpleDateFormat")SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        @SuppressLint("SimpleDateFormat")SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(timeStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "unkmow";
        }
    }

}
