package com.example.apple.zbtestdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/6/14.
 */

public class CommonViewPagerAdapter extends FragmentPagerAdapter {

    private String[] title;
    private List<Fragment> mFragments = new ArrayList<>();

    public CommonViewPagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }

    public void addFragment(Fragment fragment){mFragments.add(fragment);}
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
