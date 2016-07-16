
package com.oddfeel.awesomedribbble.presenter.shots;

import com.oddfeel.awesomedribbble.model.shots.Shots;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/16 0016.
 * email:970196066@qq.com
 */
public interface UserShotsService {
    @GET("v1/users/{user}/shots")
    Observable<List<Shots>> getShots(@Path("user") long userId,
                                     @Query("page") int page,
                                     @Query("per_page") int per_page,
                                     @Query("access_token") String access_token);
}
