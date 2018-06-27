package com.kachat.game.libdata.apiServices;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 *
 */
public interface FileService {

    @Multipart
    @POST("/files")
    Observable<String> postLogFile(@Part() MultipartBody.Part file, @Part("mobile") String mobile, @Part("system") int system);

}
