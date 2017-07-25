package com.example.apple.zbtestdemo.fragment;

import android.os.Bundle;;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;

import com.example.apple.zbtestdemo.R;
import com.example.apple.zbtestdemo.activity.home.CategoryPresenter;
import com.example.apple.zbtestdemo.adapter.CategoryRecycleAdapter;
import com.example.apple.zbtestdemo.bean.CategoryResult;
import com.example.apple.zbtestdemo.utils.ToastUtils;
import com.example.apple.zbtestdemo.view.RecyclerViewDivider;
import com.example.apple.zbtestdemo.view.recyclerview.OnLoadMoreListener;
import com.example.apple.zbtestdemo.view.recyclerview.RecyclerViewWithFooter;
import com.example.apple.zbtestdemo.activity.home.CategoryContract.ICategoryView;
import com.example.apple.zbtestdemo.activity.home.CategoryContract.ICategoryPresenter;

import butterknife.BindView;

/**
 * Created by apple on 2017/6/20.
 */

public class CategoryFragment extends BaseFragment implements ICategoryView,OnRefreshListener,OnLoadMoreListener{

    public static final String CATEGORY_NAME = "com.example.apple.zbtestdemo.fragment.CategoryFragment.CATEGORY_NAME";

    @BindView(R.id.recycleView)
    RecyclerViewWithFooter mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String categoryName;
    private CategoryRecycleAdapter mAdapter;
    private ICategoryPresenter mICategoryPresenter;

    public static CategoryFragment newInstance(String mCategoryName) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_NAME, mCategoryName);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_category;
    }

    @Override
    protected void init() {
        mICategoryPresenter = new CategoryPresenter(this);
        categoryName = getArguments().getString(CATEGORY_NAME);
        mAdapter = new CategoryRecycleAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setEmpty();

        mICategoryPresenter.subscribe();
    }

    @Override
    public void getCategoryItemsFail(String failMessage) {
        if (getUserVisibleHint()){
            ToastUtils.showShortToast(failMessage);
        }
    }

    @Override
    public void setCategoryItems(CategoryResult categoryResult) {
        mAdapter.setData(categoryResult.results);
    }

    @Override
    public void addCategoryItems(CategoryResult categoryResult) {
        mAdapter.addData(categoryResult.results);
    }

    @Override
    public void showSwipeLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipeLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setLoading() {
        mRecyclerView.setLoading();
    }

    @Override
    public String getCategoryName() {
        return this.categoryName;
    }

    @Override
    public void noMore() {
        mRecyclerView.setEnd("没有更多数据!");
    }

    @Override
    public void onRefresh() {
        mICategoryPresenter.getCategoryItems(true);
    }

    @Override
    public void onLoadMore() {
        mICategoryPresenter.getCategoryItems(false);
    }
}
