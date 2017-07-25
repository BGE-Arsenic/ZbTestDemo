package com.example.apple.zbtestdemo.activity.home;

import com.example.apple.zbtestdemo.bean.CategoryResult;
import com.example.apple.zbtestdemo.view.base.BasePresenter;
import com.example.apple.zbtestdemo.view.base.BaseView;

/**
 * Created by apple on 2017/6/20.
 */

public interface CategoryContract {
    interface ICategoryView extends BaseView {

        void getCategoryItemsFail(String failMessage);

        void setCategoryItems(CategoryResult categoryResult);

        void addCategoryItems(CategoryResult categoryResult);

        void showSwipeLoading();

        void hideSwipeLoading();

        void setLoading();

        String getCategoryName();

        void noMore();
    }

    interface ICategoryPresenter extends BasePresenter {

        void getCategoryItems(boolean isRefresh);
    }
}
