
package com.oddfeel.awesomedribbble.presenter.user;

import com.oddfeel.awesomedribbble.model.shots.Shots;
import com.oddfeel.awesomedribbble.model.user.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/21 0021.
 * email:970196066@qq.com
 */
public interface UserService {
   @GET("v1/user")
    Observable<User> getUserInfo(@Query("access_token") String access_token);
}
