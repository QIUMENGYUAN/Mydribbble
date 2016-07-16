
package com.oddfeel.awesomedribbble.presenter.comments;

import com.oddfeel.awesomedribbble.model.shots.Comments;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/22 0022.
 * email:970196066@qq.com
 */
public interface CommentsService {
    @GET("v1/shots/{id}/comments")
    Observable<List<Comments>> getComments(@Path("id") String id,
                                           @Query("page") int page,
                                           @Query("per_page") int per_page,
                                           @Query("access_token") String access_token);
}
