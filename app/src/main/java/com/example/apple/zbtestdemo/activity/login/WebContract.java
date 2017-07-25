package com.example.apple.zbtestdemo.activity.login;

import android.app.Activity;

import com.example.apple.zbtestdemo.view.base.BasePresenter;
import com.example.apple.zbtestdemo.view.base.BaseView;

/**
 * Created by apple on 2017/6/24.
 */

public interface WebContract {

    interface IWebView extends BaseView {
        Activity getWebViewContext();

        void setGankTitle(String title);

        void loadGankUrl(String url);

        void initWebView();
    }

    interface IWebPresenter extends BasePresenter {
        String getGankUrl();
    }
}
