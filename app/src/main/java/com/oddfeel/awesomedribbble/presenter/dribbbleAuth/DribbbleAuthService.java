
package com.oddfeel.awesomedribbble.presenter.dribbbleAuth;

import com.oddfeel.awesomedribbble.model.user.AccessToken;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface DribbbleAuthService {
    @POST("/oauth/token")
    Observable<AccessToken> getAccessToken(@Query("client_id") String client_id,
                                           @Query("client_secret") String client_secret,
                                           @Query("code") String code);

}
