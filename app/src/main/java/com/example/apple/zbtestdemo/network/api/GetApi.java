package com.example.apple.zbtestdemo.network.api;

import com.example.apple.zbtestdemo.bean.CategoryResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by apple on 2017/6/12.
 */

public interface GetApi {

    /**
     * 获取数据请求
     */
    @GET("data/{category}/{count}/{page}")
    Observable<CategoryResult> getCategoryData(@Path("category")String category,@Path("count")int count,@Path("page")int page);
}
