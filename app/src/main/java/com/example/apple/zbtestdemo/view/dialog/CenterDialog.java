package com.example.apple.zbtestdemo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.example.apple.zbtestdemo.R;
import com.example.apple.zbtestdemo.activity.index.ChooseWaysPhotoesActivity;

/**
 * Created by apple on 2017/6/29.
 */

public class CenterDialog extends Dialog {

    private Context context;
    private TextView tv_change_img,tv_change_no;

    public CenterDialog(@NonNull Context context) {
        super(context,R.style.centerDialog);
        this.context = context;
    }

    public CenterDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected CenterDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_center);
        initView();
    }

    private void initView() {
        tv_change_img = (TextView) findViewById(R.id.tv_change_img);
        tv_change_no = (TextView) findViewById(R.id.tv_change_no);
        tv_change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,ChooseWaysPhotoesActivity.class);
                context.startActivity(intent);
                dismiss();
            }
        });
        tv_change_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
