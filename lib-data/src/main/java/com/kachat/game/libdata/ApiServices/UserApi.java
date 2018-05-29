package com.kachat.game.libdata.ApiServices;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.http.HttpManager;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.UserBean;
import rx.Observer;
import rx.Subscription;


public class UserApi extends HttpManager{

    private static UserService mUserService  = HttpManager.getInstance().create(UserService.class);

    //手机号注册获取验证码
    public static Subscription requestCaptcha(@NonNull String mobile,Observer<BaseBean<GetCaptchaBean>> observer){
        return setSubscribe(mUserService.postPhoneCaptchaImpl(mobile),observer);
    }

    //重置密码请求验证码
    public static Subscription requestResetCaptcha(@NonNull String mobile,Observer<BaseBean<GetCaptchaBean>> observer){
        return setSubscribe(mUserService.postPhoneCaptchaResetImpl(mobile),observer);
    }

    //校验验证码
    public static Subscription requestVerifyCaptcha(@NonNull String mobile,@NonNull String captcha,Observer<BaseBean<MessageBean>> observer){
        return setSubscribe(mUserService.postVerifyCaptcha(mobile,captcha),observer);
    }

    //重置密码
    public static Subscription requestResetPwd(@NonNull String mobile,
                                             @NonNull String captcha,
                                             @NonNull String password,
                                             Observer<BaseBean<MessageBean>> observer){
        return setSubscribe(mUserService.postResetPwd(mobile,captcha,password),observer);
    }


    //登录
    public static Subscription requestLogin(@NonNull String mobile,@NonNull String pwd, Observer<BaseBean<UserBean>> observer){
        return setSubscribe(mUserService.postLoginImpl(mobile,pwd),observer);
    }







}
