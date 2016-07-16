/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oddfeel.awesomedribbble.adapter.shots.comments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.model.shots.Comments;
import com.oddfeel.awesomedribbble.util.PatternUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/6/22 0022.
 * email:970196066@qq.com
 */
public class CommentsViewHolder extends BaseViewHolder<Comments>{
    private CircleImageView image_comments_item;
    private TextView textView_shots_comment_contain;
    private TextView textView_shots_comment_author;
    public CommentsViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.comments_item);
        image_comments_item = $(R.id.image_comments_item);
        textView_shots_comment_contain = $(R.id.textView_shots_comment_contain);
        textView_shots_comment_author = $(R.id.textView_shots_comment_author);
    }

    @Override
    public void setData(Comments data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getUser().getAvatar_url())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image_comments_item);

        textView_shots_comment_contain.setText(PatternUtil.Nohtml(data.getBody()));
        textView_shots_comment_author.setText(data.getUser().getUsername());
    }
}
