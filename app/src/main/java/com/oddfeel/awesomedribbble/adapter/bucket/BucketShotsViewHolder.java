
package com.oddfeel.awesomedribbble.adapter.bucket;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.model.like.Shot;

/**
 * Created by Administrator on 2016/7/15 0015.
 * email:970196066@qq.com
 */
public class BucketShotsViewHolder extends BaseViewHolder<Shot> {
    private ImageView imageView;

    public BucketShotsViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.bucketshots_item);
        imageView = $(R.id.bucketshots_image);
    }

    @Override
    public void setData(Shot data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getImages().getNormal())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
