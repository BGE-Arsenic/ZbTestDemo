package com.example.apple.zbtestdemo.activity.login;

import android.app.Activity;
import android.content.Intent;

import com.example.apple.zbtestdemo.activity.login.WebContract.IWebPresenter;
import com.example.apple.zbtestdemo.activity.login.WebContract.IWebView;

/**
 * Created by apple on 2017/6/24.
 */

public class WebPresenter implements IWebPresenter{
    private IWebView mWebView;
    private String mGankUrl;
    private Activity mActivity;

    public WebPresenter(IWebView webView){
        this.mWebView = webView;
    }


    @Override
    public void subscribe() {
        mActivity = mWebView.getWebViewContext();
        Intent intent = mActivity.getIntent();
        mWebView.setGankTitle(intent.getStringExtra(LoginActivity.GANK_TITLE));
        mWebView.initWebView();
        mGankUrl = intent.getStringExtra(LoginActivity.GANK_URL);
        mWebView.loadGankUrl(mGankUrl);
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public String getGankUrl() {
        return this.mGankUrl;
    }
}
