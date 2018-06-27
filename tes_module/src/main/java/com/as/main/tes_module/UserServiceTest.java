package com.as.main.tes_module;

import com.kachat.game.libdata.model.FeedBacksBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.PropsBean;
import com.kachat.game.libdata.model.ScenesBean;
import com.kachat.game.libdata.model.SingsBean;
import com.kachat.game.libdata.model.UpdateUserData;
import com.kachat.game.libdata.model.UserBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 */
public interface UserServiceTest {

    //手机号注册获取验证码
    @FormUrlEncoded
    @POST("/captcha/register")
    Observable<BaseBean<GetCaptchaBeanTest>> postPhoneCaptchaImpl(@Field("mobile") String mobile);



}