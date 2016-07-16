/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oddfeel.awesomedribbble.presenter.shots;

import com.oddfeel.awesomedribbble.model.shots.Shots;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/20 0020.
 * email:970196066@qq.com
 */
public interface ShotsService {
    @GET("v1/shots")
    Observable<List<Shots>> getShots(@Query("list") String list,
                                     @Query("sort") String sort,
                                     @Query("timeframe") String timeframe,
                                     @Query("page") int page,
                                     @Query("per_page") int per_page,
                                     @Query("access_token") String access_token);
}
