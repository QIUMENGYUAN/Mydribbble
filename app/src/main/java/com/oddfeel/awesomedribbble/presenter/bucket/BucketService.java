
package com.oddfeel.awesomedribbble.presenter.bucket;

import com.oddfeel.awesomedribbble.model.bucket.Bucket;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/15 0015.
 * email:970196066@qq.com
 */
public interface BucketService {
    @GET("v1/users/{id}/buckets")
    Observable<List<Bucket>> getBuckets(@Path("id") long id,
                                        @Query("page") int page,
                                        @Query("per_page") int per_page,
                                        @Query("access_token") String access_token);
}
