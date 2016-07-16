/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oddfeel.awesomedribbble.adapter.bucket;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.model.bucket.Bucket;
import com.oddfeel.awesomedribbble.util.TimeUtil;

/**
 * Created by Administrator on 2016/7/15 0015.
 * email:970196066@qq.com
 */
public class BucketViewHolder extends BaseViewHolder<Bucket> {

    private TextView bucket_name;
    private TextView bucket_description;
    private TextView bucket_shots_count;
    private TextView bucket_updated_at;
    private TextView bucket_created_at;

    public BucketViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.bucket_item);
        bucket_name = $(R.id.bucket_name);
        bucket_description = $(R.id.bucket_description);
        bucket_shots_count = $(R.id.bucket_shots_count);
        bucket_updated_at = $(R.id.bucket_updated_at);
        bucket_created_at = $(R.id.bucket_created_at);
    }

    @Override
    public void setData(Bucket data) {
        super.setData(data);

        bucket_name.setText(data.getName());
        if (data.getDescription() == null){
            bucket_description.setText("TA没有添加描述");
        }else {
            bucket_description.setText(data.getDescription());
        }
        bucket_shots_count.setText("作品数: " + String.valueOf(data.getShots_count()));
        bucket_created_at.setText(TimeUtil.getdribbbleFormatTime(data.getCreated_at()));
        bucket_updated_at.setText(TimeUtil.getdribbbleFormatTime(data.getUpdated_at()));
    }
}
