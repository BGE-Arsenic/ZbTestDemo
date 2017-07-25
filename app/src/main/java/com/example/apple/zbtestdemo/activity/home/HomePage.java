package com.example.apple.zbtestdemo.activity.home;

import com.example.apple.zbtestdemo.bean.CategoryResult;
import com.example.apple.zbtestdemo.bean.PictureModel;
import com.example.apple.zbtestdemo.listener.HomeListener.HomeGetData;
import com.example.apple.zbtestdemo.listener.HomeListener.HomeView;
import com.example.apple.zbtestdemo.network.NetWorkUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by apple on 2017/6/14.
 */

public class HomePage implements HomeGetData{

    private Subscription sbs;
    private HomeView hv;
    private List<PictureModel> pictureModels;

    public HomePage(HomeView hv){
        this.hv = hv;
        pictureModels = new ArrayList<PictureModel>();
    }

    public List<PictureModel> getBannerMode(){
        return this.pictureModels;
    }

    @Override
    public void subscribe() {
        getBannerData();
    }

    @Override
    public void unsubscribe() {
        if (sbs != null && !sbs.isUnsubscribed()){
            sbs.unsubscribe();
        }
    }

    @Override
    public void getBannerData() {
        sbs = NetWorkUtil.getNetDataApi()
                .getCategoryData("福利",5,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CategoryResult categoryResult) {
                        if (categoryResult != null && categoryResult.results != null && categoryResult.results.size() > 0){
                            List<String> imgurls = new ArrayList<String>();
                            for (CategoryResult.ResultsBean resultsBean:categoryResult.results){
                                if (!resultsBean.url.isEmpty()){
                                    imgurls.add(resultsBean.url);
                                }
                                PictureModel model = new PictureModel();
                                model.desc = resultsBean.desc;
                                model.url = resultsBean.url;
                                pictureModels.add(model);
                            }
                            hv.setBanner(imgurls);
                        } else {
                            hv.showBannerFail("Banner 加载失败...");
                        }
                    }
                });

    }
}
