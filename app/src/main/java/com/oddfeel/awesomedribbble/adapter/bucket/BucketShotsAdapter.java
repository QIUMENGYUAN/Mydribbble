
package com.oddfeel.awesomedribbble.adapter.bucket;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.oddfeel.awesomedribbble.model.like.Shot;

/**
 * Created by Administrator on 2016/7/15 0015.
 * email:970196066@qq.com
 */
public class BucketShotsAdapter extends RecyclerArrayAdapter<Shot>{
    public BucketShotsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BucketShotsViewHolder(parent);
    }
}
