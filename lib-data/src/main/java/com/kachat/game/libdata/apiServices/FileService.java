package com.kachat.game.libdata.apiServices;

import com.kachat.game.libdata.model.PostFileBean;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.Header;
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
