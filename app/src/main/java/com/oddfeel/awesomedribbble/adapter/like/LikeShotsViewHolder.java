/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oddfeel.awesomedribbble.adapter.like;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.model.like.Like_Shots;

/**
 * Created by Administrator on 2016/7/14 0014.
 * email:970196066@qq.com
 */
public class LikeShotsViewHolder extends BaseViewHolder<Like_Shots> {

    private ImageView likeshotImg;

    private ImageButton button_shot_like_count;
    private ImageButton button_shot_view_count;
    private ImageButton button_shot_comment_count;
    private ImageButton button_shot_bucket_count;
    private TextView text_shot_like_count;
    private TextView text_shot_view_count;
    private TextView text_shot_comment_count;
    private TextView text_shot_bucket_count;

    public LikeShotsViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.likeshots_item);
        likeshotImg = $(R.id.likeshotImg);

        button_shot_like_count = $(R.id.button_shot_like_count);
        button_shot_view_count = $(R.id.button_shot_view_count);
        button_shot_comment_count = $(R.id.button_shot_comment_count);
        button_shot_bucket_count = $(R.id.button_shot_bucket_count);

        text_shot_like_count = $(R.id.text_shot_like_count);
        text_shot_view_count = $(R.id.text_shot_view_count);
        text_shot_comment_count = $(R.id.text_shot_comment_count);
        text_shot_bucket_count = $(R.id.text_shot_bucket_count);
    }

    @Override
    public void setData(Like_Shots data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getShot().getImages().getHidpi())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(likeshotImg);
        text_shot_like_count.setText(String.valueOf(data.getShot().getLikes_count()));
        text_shot_view_count.setText(String.valueOf(data.getShot().getViews_count()));
        text_shot_comment_count.setText(String.valueOf(data.getShot().getComments_count()));
        text_shot_bucket_count.setText(String.valueOf(data.getShot().getBuckets_count()));
    }
}
