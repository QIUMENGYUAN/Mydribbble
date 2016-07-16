
/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oddfeel.awesomedribbble.adapter.shots;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.model.shots.Shots;

/**
 * Created by Administrator on 2016/6/20 0020.
 * email:970196066@qq.com
 */
public class ShotsViewHolder extends BaseViewHolder<Shots>{
    private ImageView imageView;
    public ShotsViewHolder(ViewGroup itemView) {
        super(itemView,R.layout.shots_item);
        imageView = $(R.id.shots_image);
    }

    @Override
    public void setData(Shots data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getImages().getNormal())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
