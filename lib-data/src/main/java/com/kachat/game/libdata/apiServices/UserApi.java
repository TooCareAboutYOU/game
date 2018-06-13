package com.kachat.game.libdata.apiServices;

import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;

import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.http.HttpManager;
import com.kachat.game.libdata.model.FeedBacksBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.UserBean;

import java.util.Objects;

import rx.Observer;
import rx.Subscription;


public class UserApi extends HttpManager {

    private static UserService mUserService = HttpManager.getInstance().create(UserService.class);
    private static String token= Objects.requireNonNull(DaoQuery.queryUserData()).getToken();

//    //手机号注册获取验证码
    public static Subscription requestCaptcha(@NonNull String mobile, Observer<GetCaptchaBean> observer) {
        return setSubscribe(mUserService.postPhoneCaptchaImpl(mobile), observer);
    }

    //注册
    public static Subscription requestRegister(@NonNull String mobile, @NonNull String pwd, @NonNull String gender,
                                               @NonNull String age, @NonNull String username, @IntegerRes int system,
                                               Observer<UserBean> observer) {
        return setSubscribe(mUserService.postRegister(mobile, pwd, gender, age, username, system), observer);
    }
//
//    //重置密码请求验证码
//    public static Subscription requestResetCaptcha(@NonNull String mobile, Observer<BaseBean<GetCaptchaBean>> observer) {
//        return setSubscribe(mUserService.postPhoneCaptchaResetImpl(mobile), observer);
//    }

    //校验验证码
    public static Subscription requestVerifyCaptcha(@NonNull String mobile, @NonNull String captcha, Observer<MessageBean> observer) {
        return setSubscribe(mUserService.postVerifyCaptcha(mobile, captcha), observer);
    }
//
//    //重置密码
//    public static Subscription requestResetPwd(@NonNull String mobile,
//                                               @NonNull String captcha,
//                                               @NonNull String password,
//                                               Observer<BaseBean<MessageBean>> observer) {
//        return setSubscribe(mUserService.postResetPwd(mobile, captcha, password), observer);
//    }


    //登录
    public static Subscription requestLogin(@NonNull String mobile, @NonNull String pwd, Observer<UserBean> observer) {
        return setSubscribe(mUserService.postLoginImpl(mobile, pwd), observer);
    }


    //用户反馈
    public static Subscription requestFeedBacks(@NonNull String content, Observer<FeedBacksBean> observer) {
        return setSubscribe(mUserService.postFeedBacks(token,content), observer);
    }


    //用户券数
    public static Subscription getUserTicket(@NonNull String uid, Observer<MessageBean> observer) {
        return setSubscribe(mUserService.getUserTickets(uid), observer);
    }
}
