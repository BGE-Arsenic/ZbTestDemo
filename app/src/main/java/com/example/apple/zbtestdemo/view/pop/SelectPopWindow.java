package com.example.apple.zbtestdemo.view.pop;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.zbtestdemo.R;
import com.example.apple.zbtestdemo.utils.BaseUtils;

/**
 * Created by apple on 2017/6/29.
 * Auther BGE_Arsenic
 * 仿写微博弹窗
 */

public class SelectPopWindow extends PopupWindow implements View.OnClickListener{

    protected final int DISMISS = 1212;
    Activity mContext;
    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    private Bitmap mBitmap = null;
    private Bitmap overlay = null;

    private RelativeLayout relativeLayout;
    private TextView CameraContainer, ShotContainer,containerSimpleCamera;
    private popItemClickListener listener;
    private ImageView closeImageview;

    private boolean isPopshow = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DISMISS:
                    dismiss();
                    break;
            }
        }
    };

    public SelectPopWindow(Activity context,View contentView,popItemClickListener listener, int bottomMargin) {
        super(context);
        this.mContext = context;
        this.listener = listener;

        relativeLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.selectpop,null);
        setContentView(relativeLayout);

        CameraContainer = (TextView) relativeLayout.findViewById(R.id.container_Camera);
        ShotContainer = (TextView) relativeLayout.findViewById(R.id.container_Shot);
        containerSimpleCamera = (TextView) relativeLayout.findViewById(R.id.containerSimpleCamera);
        CameraContainer.setOnClickListener(this);
        ShotContainer.setOnClickListener(this);
        containerSimpleCamera.setOnClickListener(this);

        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;
        setWidth(mWidth);
        setHeight(mHeight);
        closeImageview = (ImageView) relativeLayout.findViewById(R.id.desImg);
        closeImageview.setOnClickListener(this);

        //显示动画效果
        showAnimation(relativeLayout);
        try{
            setBackgroundDrawable(new BitmapDrawable(mContext.getResources(),blur()));
        } catch (Exception e){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                setBackgroundDrawable(context.getDrawable(R.mipmap.iv_camear_bg));
            }
        }
        setOutsideTouchable(true);
        setFocusable(true);
        showAtLocation(contentView, Gravity.BOTTOM,0,statusBarHeight);
    }

    private void showAnimation(ViewGroup relativeLayout) {
        isPopshow = true;
        for (int i=0;i<relativeLayout.getChildCount();i++){
            final View views = relativeLayout.getChildAt(i);
            if (views.getId() == R.id.desImg){
                continue;
            }
            views.setOnClickListener(this);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator animation = ObjectAnimator.ofFloat(views,"translationY",600,0);
                    animation.setDuration(300);
                    ClickBackAnimation clickAnimation = new ClickBackAnimation();
                    animation.setEvaluator(clickAnimation);
                    animation.start();
                }
            },i*50);
        }

    }

    public void closeAnimation(ViewGroup layout){
        isPopshow = false;
        for (int i=0;i<layout.getChildCount();i++){
            final View child = layout.getChildAt(i);
            if (child.getId() == R.id.desImg){
                continue;
            }
            child.setOnClickListener(this);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                    fadeAnim.setDuration(200);
                    ClickBackAnimation kickAnimator = new ClickBackAnimation();
                    kickAnimator.setDuration(100);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                    fadeAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
            }, (layout.getChildCount() - i - 1) * 30);
        }
        overlay.recycle();
        overlay = null;
        mBitmap.recycle();
        mBitmap = null;
    }

    private Bitmap blur() {
        if (null != overlay) {
            overlay.recycle();
            overlay = null;
        }
        long startMs = System.currentTimeMillis();

        View view = mContext.getWindow().getDecorView();
//        view.buildDrawingCache();
        view.setDrawingCacheEnabled(true);
        mBitmap = view.getDrawingCache();
        if (mBitmap == null) {
            mBitmap = convertViewToBitmap(view, BaseUtils.getScreenWidth(), BaseUtils.getScreenHeight());
        }
        float scaleFactor = 8;//
        float radius = 10;//
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        overlay = Bitmap.createBitmap((int) (width / scaleFactor), (int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        overlay = doBlur(overlay, (int) radius, true);
        view.setDrawingCacheEnabled(false);
        return overlay;
    }
    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));

        return bitmap;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.popItemClicked(v);
        }
    }

    public interface popItemClickListener{
        void popItemClicked(View v);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }




    //高斯模糊
    public static Bitmap doBlur(Bitmap sentBitmap, int radius,
                                boolean canReuseInBitmap) {
        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }
        try {

            if (radius < 1) {
                return (null);
            }

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            int[] pix = new int[w * h];
            bitmap.getPixels(pix, 0, w, 0, 0, w, h);

            int wm = w - 1;
            int hm = h - 1;
            int wh = w * h;
            int div = radius + radius + 1;

            int r[] = new int[wh];
            int g[] = new int[wh];
            int b[] = new int[wh];
            int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
            int vmin[] = new int[Math.max(w, h)];

            int divsum = (div + 1) >> 1;
            divsum *= divsum;
            int dv[] = new int[256 * divsum];
            for (i = 0; i < 256 * divsum; i++) {
                dv[i] = (i / divsum);
            }

            yw = yi = 0;

            int[][] stack = new int[div][3];
            int stackpointer;
            int stackstart;
            int[] sir;
            int rbs;
            int r1 = radius + 1;
            int routsum, goutsum, boutsum;
            int rinsum, ginsum, binsum;

            for (y = 0; y < h; y++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                for (i = -radius; i <= radius; i++) {
                    p = pix[yi + Math.min(wm, Math.max(i, 0))];
                    sir = stack[i + radius];
                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);
                    rbs = r1 - Math.abs(i);
                    rsum += sir[0] * rbs;
                    gsum += sir[1] * rbs;
                    bsum += sir[2] * rbs;
                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }
                }
                stackpointer = radius;

                for (x = 0; x < w; x++) {

                    r[yi] = dv[rsum];
                    g[yi] = dv[gsum];
                    b[yi] = dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (y == 0) {
                        vmin[x] = Math.min(x + radius + 1, wm);
                    }
                    p = pix[yw + vmin[x]];

                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[(stackpointer) % div];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi++;
                }
                yw += w;
            }
            for (x = 0; x < w; x++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                yp = -radius * w;
                for (i = -radius; i <= radius; i++) {
                    yi = Math.max(0, yp) + x;

                    sir = stack[i + radius];

                    sir[0] = r[yi];
                    sir[1] = g[yi];
                    sir[2] = b[yi];

                    rbs = r1 - Math.abs(i);

                    rsum += r[yi] * rbs;
                    gsum += g[yi] * rbs;
                    bsum += b[yi] * rbs;

                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }

                    if (i < hm) {
                        yp += w;
                    }
                }
                yi = x;
                stackpointer = radius;
                for (y = 0; y < h; y++) {
                    // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                    pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                            | (dv[gsum] << 8) | dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (x == 0) {
                        vmin[y] = Math.min(y + r1, hm) * w;
                    }
                    p = x + vmin[y];

                    sir[0] = r[p];
                    sir[1] = g[p];
                    sir[2] = b[p];

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[stackpointer];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi += w;
                }
            }

            bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        } catch (Exception e) {

        }


        return (bitmap);
    }
}
