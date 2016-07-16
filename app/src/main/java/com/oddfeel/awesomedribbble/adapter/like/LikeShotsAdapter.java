
package com.oddfeel.awesomedribbble.adapter.like;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.oddfeel.awesomedribbble.model.like.Like_Shots;

/**
 * Created by Administrator on 2016/7/14 0014.
 * email:970196066@qq.com
 */
public class LikeShotsAdapter extends RecyclerArrayAdapter<Like_Shots> {
    public LikeShotsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new LikeShotsViewHolder(parent);
    }
}
