package com.example.apple.zbtestdemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apple.zbtestdemo.activity.BaseActivity;
import com.example.apple.zbtestdemo.activity.TabActivity;
import com.example.apple.zbtestdemo.zbconstants.Constants;

import java.util.Random;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {


    private Boolean isIn = false;

    @BindView(R.id.splash_iv_pic)
    ImageView splash_iv_pic;
    @BindView(R.id.splash_tv_jump)
    TextView splash_tv_jump;
    @BindView(R.id.splash_iv_defult_pic)
    ImageView splash_iv_defult_pic;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        int i = new Random().nextInt(Constants.TRANSITION_URLS.length);
        splash_iv_defult_pic.setImageDrawable(getResources().getDrawable(R.drawable.img_transition_default));
        Glide.with(this)
                .load(Constants.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(splash_iv_pic);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splash_iv_defult_pic.setVisibility(View.GONE);
            }
        },1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转
                toTabAvtivity();
            }
        },3500);
    }

    private void toTabAvtivity() {
        if (isIn){
            return;
        }
        Intent inttent = new Intent(this, TabActivity.class);
        startActivity(inttent);
        overridePendingTransition(R.anim.screen_zoom_in,R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }


}
