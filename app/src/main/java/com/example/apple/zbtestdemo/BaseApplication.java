package com.example.apple.zbtestdemo;

import android.app.Application;

import com.example.apple.zbtestdemo.storeage.StoreageManager;
import com.example.apple.zbtestdemo.utils.BaseUtils;
import com.squareup.leakcanary.LeakCanary;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Created by apple on 2017/6/13.
 */

public class BaseApplication extends Application {

    private static BaseApplication baseApplication;

    public static BaseApplication getBaseApplication(){return baseApplication;}


    @Override
    public void onCreate() {
        super.onCreate();

        //初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(baseApplication);


        BGASwipeBackManager.getInstance().init(this);

        StoreageManager.INSTANCE.initConfig(this);
        baseApplication = this;

        //初始化基本工具类
        BaseUtils.initContext(this);
    }
}
