package com.kachat.game.libdata.apiServices;

import com.kachat.game.libdata.http.HttpManager;
import com.kachat.game.libdata.model.PostFileBean;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;



public class FileApi extends HttpManager {

    private static FileService mFileService= HttpManager.getInstance().create(FileService.class);

    //上传Log file
    public static Subscription postLogFile(MultipartBody.Part file, String mobile, int system, Observer<String> observer){
        return setSubscribe(mFileService.postLogFile(file, mobile, system),observer);
    }

}
