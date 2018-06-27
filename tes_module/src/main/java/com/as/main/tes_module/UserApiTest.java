package com.as.main.tes_module;

import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.http.HttpManager;
import com.kachat.game.libdata.model.FeedBacksBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.PropsBean;
import com.kachat.game.libdata.model.ScenesBean;
import com.kachat.game.libdata.model.SingsBean;
import com.kachat.game.libdata.model.UpdateUserData;
import com.kachat.game.libdata.model.UserBean;

import java.util.Objects;

import rx.Observer;
import rx.Subscription;


public class UserApiTest extends HttpManager {

    private static final String TAG = "UserApi";

    private static UserServiceTest mUserService = HttpManager.getInstance().create(UserServiceTest.class);

//    //手机号注册获取验证码
    public static Subscription requestCaptcha(@NonNull String mobile, Observer<BaseBean<GetCaptchaBeanTest>> observer) {
        return setSubscribe(mUserService.postPhoneCaptchaImpl(mobile), observer);
    }

}
