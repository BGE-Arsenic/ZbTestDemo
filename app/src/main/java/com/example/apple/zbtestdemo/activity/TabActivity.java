package com.example.apple.zbtestdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apple.zbtestdemo.R;
import com.example.apple.zbtestdemo.activity.About.AboutActivity;
import com.example.apple.zbtestdemo.activity.develop.AboutDevelop;
import com.example.apple.zbtestdemo.activity.home.HomePage;
import com.example.apple.zbtestdemo.activity.index.IndexActivity;
import com.example.apple.zbtestdemo.activity.login.LoginActivity;
import com.example.apple.zbtestdemo.activity.needback.NeedBackActivity;
import com.example.apple.zbtestdemo.adapter.CommonViewPagerAdapter;
import com.example.apple.zbtestdemo.bean.PictureModel;
import com.example.apple.zbtestdemo.fragment.CategoryFragment;
import com.example.apple.zbtestdemo.listener.HomeListener.HomeView;
import com.example.apple.zbtestdemo.listener.PerfectClickListener;
import com.example.apple.zbtestdemo.utils.BaseUtils;
import com.example.apple.zbtestdemo.utils.GlideImageLoader;
import com.example.apple.zbtestdemo.utils.StatusBarUtils;
import com.example.apple.zbtestdemo.utils.ToastUtils;
import com.example.apple.zbtestdemo.view.dialog.CenterDialog;
import com.example.apple.zbtestdemo.view.pop.SelectPopWindow;
import com.example.apple.zbtestdemo.zbconstants.GlobalConfig;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import butterknife.BindView;


/**
 * Created by apple on 2017/6/12.
 */

public class TabActivity extends BaseActivity implements HomeView,OnBannerListener{

    @BindView(R.id.main_head_img)
    ImageView main_head_img;
    @BindView(R.id.main_toolbar)
    Toolbar main_toolbar;
    @BindView(R.id.main_appbar)
    AppBarLayout main_appbar;
    @BindView(R.id.main_tab)
    DachshundTabLayout main_tab;
    @BindView(R.id.main_vp)
    ViewPager main_vp;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.tab_activity)
    DrawerLayout tab_activity;
    @BindView(R.id.main_banner)
    Banner main_banner;
    @BindView(R.id.main_fab)
    FloatingActionButton main_fab;
    // 保存用户按返回键的时间
    private long mExitTime = 0;
    HomePage mHomePage;
    private CenterDialog centerDialog = null;
    private SelectPopWindow popWindow = null;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tab;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mHomePage = new HomePage(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4 以上版本
            // 设置 Toolbar 高度为 80dp，适配状态栏
            ViewGroup.LayoutParams layoutParams = main_toolbar.getLayoutParams();
            layoutParams.height = BaseUtils.dip2px(this,80);
            main_toolbar.setLayoutParams(layoutParams);
        }

        initDrawerLayout();

        main_banner.setIndicatorGravity(BannerConfig.RIGHT);
        main_banner.setOnBannerListener(this);

        String[] titles = {
                GlobalConfig.CATEGORY_NAME_APP,
                GlobalConfig.CATEGORY_NAME_ANDROID,
                GlobalConfig.CATEGORY_NAME_IOS,
                GlobalConfig.CATEGORY_NAME_FRONT_END,
                GlobalConfig.CATEGORY_NAME_RECOMMEND,
                GlobalConfig.CATEGORY_NAME_RESOURCE};

        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(),titles);
        // App
        CategoryFragment appFragment = CategoryFragment.newInstance(titles[0]);
        // Android
        CategoryFragment androidFragment = CategoryFragment.newInstance(titles[1]);
        // iOS
        CategoryFragment iOSFragment = CategoryFragment.newInstance(titles[2]);
        // 前端
        CategoryFragment frontFragment = CategoryFragment.newInstance(titles[3]);
        // 瞎推荐
        CategoryFragment referenceFragment = CategoryFragment.newInstance(titles[4]);
        // 拓展资源s
        CategoryFragment resFragment = CategoryFragment.newInstance(titles[5]);

        infoPagerAdapter.addFragment(appFragment);
        infoPagerAdapter.addFragment(androidFragment);
        infoPagerAdapter.addFragment(iOSFragment);
        infoPagerAdapter.addFragment(frontFragment);
        infoPagerAdapter.addFragment(referenceFragment);
        infoPagerAdapter.addFragment(resFragment);

        main_vp.setAdapter(infoPagerAdapter);
        main_tab.setupWithViewPager(main_vp);
        main_vp.setCurrentItem(0);
        main_vp.setOffscreenPageLimit(6);

        mHomePage.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHomePage != null){
            mHomePage.unsubscribe();
        }
    }

    private void initDrawerLayout() {
        nav_view.inflateHeaderView(R.layout.layout_main_nav);
        View view = nav_view.getHeaderView(0);
        view.findViewById(R.id.ll_nav_homepage).setOnClickListener(mListener);
        view.findViewById(R.id.ll_nav_scan_address).setOnClickListener(mListener);
        view.findViewById(R.id.ll_nav_deedback).setOnClickListener(mListener);
        view.findViewById(R.id.ll_nav_donation).setOnClickListener(mListener);
        view.findViewById(R.id.ll_nav_login).setOnClickListener(mListener);
        view.findViewById(R.id.ll_nav_exit).setOnClickListener(mListener);
        view.findViewById(R.id.user_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showNormalToast(TabActivity.this,"点击了头像");
                if (centerDialog != null){
                    centerDialog.dismiss();
                    centerDialog = null;
                }
                centerDialog = new CenterDialog(TabActivity.this);
                centerDialog.show();

            }
        });
        view.findViewById(R.id.user_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showNormalToast(TabActivity.this,"点击了用户名");
                popWindow = new SelectPopWindow(TabActivity.this, v, new SelectPopWindow.popItemClickListener() {
                    @Override
                    public void popItemClicked(View v) {
                        switch (v.getId()){
                            case R.id.container_Shot:

                                break;
                            case R.id.containerSimpleCamera:

                                break;
                            case R.id.container_Camera:

                                break;
                            case R.id.desImg:
                                popWindow.dismiss();
                                break;
                        }
                    }
                },100);

            }
        });
    }

    private PerfectClickListener mListener= new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(final View v) {
            tab_activity.closeDrawer(GravityCompat.START);
            tab_activity.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (v.getId()){
                        case R.id.ll_nav_homepage:
                            startActivity(new Intent(TabActivity.this, IndexActivity.class));
                            break;
                        case R.id.ll_nav_scan_address:
                            startActivity(new Intent(TabActivity.this,AboutActivity.class));
                            break;
                        case R.id.ll_nav_deedback:
                            startActivity(new Intent(TabActivity.this, NeedBackActivity.class));
                            break;
                        case R.id.ll_nav_donation:
                            startActivity(new Intent(TabActivity.this, AboutDevelop.class));
                            break;
                        case R.id.ll_nav_login:
                            startActivity(new Intent(TabActivity.this,LoginActivity.class));
                            break;
                        case R.id.ll_nav_exit:
                            finish();
                            break;
                        default:
                            break;
                    }
                }
            },400);

        }
    };

    @Override
    protected void beforeInit() {
        super.beforeInit();
        StatusBarUtils.setTranslucent(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Snackbar.make(tab_activity, R.string.exit_toast, Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void showBannerFail(String failMessage) {
        ToastUtils.showShortToast(failMessage);
    }

    @Override
    public void setBanner(List<String> imgUrls) {
        main_banner.setImages(imgUrls).setImageLoader(new GlideImageLoader()).start();
    }

    @Override
    public void OnBannerClick(int position) {
        PictureModel model =  mHomePage.getBannerMode().get(position);
        ToastUtils.showNormalToast(TabActivity.this,"点击了第"+(position+1)+"张图片");
    }
}
