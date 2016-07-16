
package com.oddfeel.awesomedribbble.presenter.shots;

import com.oddfeel.awesomedribbble.model.shots.Like;

import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/14 0014.
 * email:970196066@qq.com
 */
public interface LikeService {
    @POST("v1/shots/{id}/like")
    Observable<Like> like(@Path("id") long shotId
                          //@Query("access_token") String access_token
    );
}
