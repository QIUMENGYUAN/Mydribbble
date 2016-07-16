
package com.oddfeel.awesomedribbble.adapter.shots;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.oddfeel.awesomedribbble.model.shots.Shots;

/**
 * Created by Administrator on 2016/6/20 0020.
 * email:970196066@qq.com
 */
public class ShotsAdapter extends RecyclerArrayAdapter<Shots> {
    public ShotsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShotsViewHolder(parent);
    }
}
