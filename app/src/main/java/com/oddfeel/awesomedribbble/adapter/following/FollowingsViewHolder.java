
package com.oddfeel.awesomedribbble.adapter.following;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.model.following.Followings;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/7/16 0016.
 * email:970196066@qq.com
 */
public class FollowingsViewHolder extends BaseViewHolder<Followings> {

    private ImageView imageView;
    private TextView name;
    private TextView bio;

    public FollowingsViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.followings_item);

        imageView = $(R.id.image_following_item);
        name = $(R.id.textView_name);
        bio = $(R.id.textView_bio);
    }

    @Override
    public void setData(Followings data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getFollowee().getAvatar_url())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);

        name.setText(data.getFollowee().getName());
        if (data.getFollowee().getBio().isEmpty()){
            bio.setText("简介去哪里了？");
        }else {
            bio.setText(data.getFollowee().getBio());
        }
    }
}
