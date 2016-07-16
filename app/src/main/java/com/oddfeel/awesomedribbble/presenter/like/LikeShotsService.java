/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oddfeel.awesomedribbble.presenter.like;

import com.oddfeel.awesomedribbble.model.like.Like_Shots;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/14 0014.
 * email:970196066@qq.com
 */
public interface LikeShotsService {
    @GET("v1/users/{id}/likes")
    Observable<List<Like_Shots>> getLikeShots(@Path("id") long id,
                                              @Query("page") int page,
                                              @Query("per_page") int per_page,
                                              @Query("access_token") String access_token);
}
