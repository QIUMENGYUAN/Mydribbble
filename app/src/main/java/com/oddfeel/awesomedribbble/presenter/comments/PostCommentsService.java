package com.oddfeel.awesomedribbble.presenter.comments;

import com.oddfeel.awesomedribbble.model.shots.Comments;

import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/14 0014.
 * email:970196066@qq.com
 */
public interface PostCommentsService {
    @POST("v1/shots/{shot}/comments")
    Observable<Comments> postComment(@Path("shot") long shotId,
                           @Query("body") String body,
                           @Query("access_token") String access_token);
}
