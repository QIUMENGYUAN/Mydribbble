
package com.oddfeel.awesomedribbble.presenter.following;

import com.oddfeel.awesomedribbble.model.like.Shot;
import com.oddfeel.awesomedribbble.model.shots.Shots;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/16 0016.
 * email:970196066@qq.com
 */
public interface FollowingShotsService {
    @GET("v1/user/following/shots")
    Observable<List<Shots>> getFollowingShots(@Query("page") int page,
                                              @Query("per_page") int pageSize,
                                              @Query("access_token") String access_token);
}
