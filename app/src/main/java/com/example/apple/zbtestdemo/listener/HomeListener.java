package com.example.apple.zbtestdemo.listener;

import java.util.List;

/**
 * Created by apple on 2017/6/14.
 */

public interface HomeListener {
    interface HomeView extends BaseView{
        void showBannerFail(String failMessage);
        void setBanner(List<String> imgUrls);
    }

    interface HomeGetData extends BaseDevMode{
        void getBannerData();
    }
}
