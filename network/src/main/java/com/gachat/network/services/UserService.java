package com.gachat.network.services;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 */
public interface UserService {

    @FormUrlEncoded
    @POST("/login")
    Observable<String> getLoginImpl(@Field("mobile") String mobile, @Field("password") String password);

}
