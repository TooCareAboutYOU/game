package com.kachat.game.libdata.ApiServices;

import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.UserBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 */
public interface UserService {

    //手机号注册获取验证码
    @FormUrlEncoded
    @POST("/captcha/register")
    Observable<BaseBean<GetCaptchaBean>> postPhoneCaptchaImpl(@Field("mobile") String mobile);

    //注册
    @FormUrlEncoded
    @POST("/users")
    Observable<BaseBean<UserBean>> postRegister(@Field("mobile") String mobile,
                                              @Field("password") String password,
                                              @Field("gender") String gender,
                                              @Field("age") String age,
                                              @Field("username") String username,
                                              @Field("system") int system);

    // 重置密码请求验证码
    @FormUrlEncoded
    @POST("/captcha/reset")
    Observable<BaseBean<GetCaptchaBean>> postPhoneCaptchaResetImpl(@Field("mobile") String mobile);

    //校验验证码
    @FormUrlEncoded
    @POST("/captcha/verify")
    Observable<BaseBean<MessageBean>> postVerifyCaptcha(@Field("mobile") String mobile, @Field("captcha") String captcha);

    //重置密码
    @FormUrlEncoded
    @POST("/password/reset")
    Observable<BaseBean<MessageBean>> postResetPwd(@Field("mobile") String mobile, @Field("captcha") String captcha, @Field("password") String password);


    //登录
    @FormUrlEncoded
    @POST("/login")
    Observable<BaseBean<UserBean>> postLoginImpl(@Field("mobile") String mobile, @Field("password") String password);

}
