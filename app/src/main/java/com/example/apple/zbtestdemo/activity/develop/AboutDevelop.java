package com.example.apple.zbtestdemo.activity.develop;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.apple.zbtestdemo.R;
import com.example.apple.zbtestdemo.activity.BaseActivity;

/**
 * Created by apple on 2017/6/24.
 */

public class AboutDevelop extends BaseActivity implements View.OnClickListener{

    private RelativeLayout more_rl;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.aboutdevelop_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        more_rl = (RelativeLayout) findViewById(R.id.more_rl);
        more_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_rl:

                break;
        }
    }
}
