
package com.oddfeel.awesomedribbble.presenter.user;

import com.oddfeel.awesomedribbble.model.user.User;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/15 0015.
 * email:970196066@qq.com
 */
public interface UserInfoService {
    @GET("v1/user/{id}")
    Observable<User> getUserInfo(
            @Path("id") long id,
            @Query("access_token") String access_token);
}
