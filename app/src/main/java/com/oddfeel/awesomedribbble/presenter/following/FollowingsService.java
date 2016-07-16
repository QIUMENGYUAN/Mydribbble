

package com.oddfeel.awesomedribbble.presenter.following;

import com.oddfeel.awesomedribbble.model.following.Followings;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/16 0016.
 * email:970196066@qq.com
 */
public interface FollowingsService {
    @GET("v1/users/{id}/following")
    Observable<List<Followings>> getFollowings(@Path("id") long id,
                                               @Query("page") int page,
                                               @Query("per_page") int pageSize,
                                               @Query("access_token") String access_token);
}
