package com.example.apple.zbtestdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.apple.zbtestdemo.R;
import com.example.apple.zbtestdemo.bean.CategoryResult.ResultsBean;
import com.example.apple.zbtestdemo.listener.ListenerWithPosition;
import com.example.apple.zbtestdemo.storeage.StoreageManager;
import com.example.apple.zbtestdemo.utils.BaseUtils;
import com.example.apple.zbtestdemo.utils.ToastUtils;

/**
 * Created by apple on 2017/6/20.
 */

public class CategoryRecycleAdapter extends CommonRecycleAdapter<ResultsBean> implements ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder>{

    public CategoryRecycleAdapter(Context context){
        super(context,null, R.layout.item_category);
    }

    @Override
    public void convert(CommonRecyclerHolder holder, ResultsBean resultsBean) {
        if (resultsBean != null){
            ImageView imageView = holder.getView(R.id.category_item_img);
            if (StoreageManager.INSTANCE.isListShowImg()){
                imageView.setVisibility(View.VISIBLE);
                String quality = "";
                if (resultsBean.images != null && resultsBean.images.size() > 0){
                    switch (StoreageManager.INSTANCE.getThumbnailQuality()){
                        case 0://原图
                            quality = "";
                            break;
                        case 1:
                            quality = "?imageView2/0/w/400";
                            break;
                        case 2:
                            quality = "?imageView2/0/w/190";
                             break;
                    }
                    Glide.with(mContext)
                            .load(resultsBean.images.get(0)+quality)
                            .placeholder(R.mipmap.image_default)
                            .error(R.mipmap.image_default)
                            .into(imageView);
                } else {//列表不显示图片
                    Glide.with(mContext).load(R.mipmap.image_default).into(imageView);
                }
            } else {
                imageView.setVisibility(View.GONE);
            }

            holder.setTextViewText(R.id.category_item_desc, resultsBean.desc == null ? "unknown" : resultsBean.desc);
            holder.setTextViewText(R.id.category_item_author, resultsBean.who == null ? "unknown" : resultsBean.who);
            holder.setTextViewText(R.id.category_item_time, BaseUtils.dateFormat(resultsBean.publishedAt));
            holder.setTextViewText(R.id.category_item_src, resultsBean.source == null ? "unknown" : resultsBean.source);
            holder.setOnclickListener(this, R.id.category_item_layout);

        }
    }

    @Override
    public void onclick(View v, int position, CommonRecyclerHolder holder) {
//        Intent intent = new Intent(mContext, WebViewActivity.class);
//        intent.putExtra(WebViewActivity.GANK_TITLE, mData.get(position).desc);
//        intent.putExtra(WebViewActivity.GANK_URL, mData.get(position).url);
//        mContext.startActivity(intent);
        ToastUtils.showShortToast("点击了第"+(position+1)+"个位置");
    }
}
