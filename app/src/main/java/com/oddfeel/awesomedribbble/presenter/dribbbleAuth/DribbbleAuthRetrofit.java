package com.oddfeel.awesomedribbble.presenter.dribbbleAuth;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class DribbbleAuthRetrofit {
    private static final String DRIBBBLEAUTH_API = "https://dribbble.com/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit == null){
            synchronized (DribbbleAuthRetrofit.class){
                if (retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(DRIBBBLEAUTH_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
