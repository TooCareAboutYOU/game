package com.kachat.game.libdata.apiServices;

import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.http.HttpManager;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.FeedBacksBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.LivesBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.PropsBean;
import com.kachat.game.libdata.model.ScenesBean;
import com.kachat.game.libdata.model.SingsBean;
import com.kachat.game.libdata.model.UpdateUserData;
import com.kachat.game.libdata.model.UserBean;

import java.util.Objects;

import rx.Observer;
import rx.Subscription;


public class UserApi extends HttpManager {

    private static final String TAG = "UserApi";

    private static UserService mUserService = HttpManager.getInstance().create(UserService.class);

    private static String token(){return Objects.requireNonNull(DaoQuery.queryUserData()).getToken();}
    private static int uid() {return Objects.requireNonNull(DaoQuery.queryUserData()).getUid();}

//    //手机号注册获取验证码
    public static Subscription requestCaptcha(@NonNull String mobile, Observer<BaseBean<GetCaptchaBean>> observer) {
        return setSubscribe(mUserService.postPhoneCaptchaImpl(mobile), observer);
    }

    //注册
    public static Subscription requestRegister(@NonNull String mobile, @NonNull String pwd, @NonNull String gender,
                                               @NonNull String age, @NonNull String username, @IntegerRes int system,
                                               Observer<BaseBean<UserBean>> observer) {
        return setSubscribe(mUserService.postRegister(mobile, pwd, gender, age, username, system), observer);
    }

//    //重置密码请求验证码
    public static Subscription requestResetCaptcha(@NonNull String mobile, Observer<BaseBean<GetCaptchaBean>> observer) {
        return setSubscribe(mUserService.postPhoneCaptchaResetImpl(mobile), observer);
    }

    //校验验证码
    public static Subscription requestVerifyCaptcha(@NonNull String mobile, @NonNull String captcha, Observer<BaseBean<MessageBean>> observer) {
        return setSubscribe(mUserService.postVerifyCaptcha(mobile, captcha), observer);
    }

    //重置密码
    public static Subscription requestResetPwd(@NonNull String mobile, @NonNull String captcha, @NonNull String password, Observer<BaseBean<MessageBean>> observer) {
        return setSubscribe(mUserService.postResetPwd(mobile, captcha, password), observer);
    }


    //登录
    public static Subscription requestLogin(@NonNull String mobile, @NonNull String pwd, Observer<BaseBean<UserBean>> observer) {
        return setSubscribe(mUserService.postLoginImpl(mobile, pwd), observer);
    }

    //更新用户信息
    public static Subscription updateUserData(Observer<BaseBean<UpdateUserData>> observer) {
        return setSubscribe(mUserService.putUserData(token(),uid()), observer);
    }

    //用户反馈
    public static Subscription requestFeedBacks(int type,@NonNull String content, Observer<BaseBean<FeedBacksBean>> observer) {
        return setSubscribe(mUserService.postFeedBacks(token(),type,content), observer);
    }


    //用户券数
    public static Subscription getUserTicket(Observer<BaseBean<MessageBean>> observer) {
        return setSubscribe(mUserService.getUserTickets(uid()), observer);
    }

    //用户拥有场景
    public static Subscription getUserScenes(Observer<BaseBean<ScenesBean>> observer) {
        return setSubscribe(mUserService.getUserScenes(uid()), observer);
    }

    //用户道具表
    public static Subscription getUserProps(Observer<BaseBean<PropsBean>> observer) {
        Log.i(TAG, "getUserProps: "+uid()+"\n"+token());
        return setSubscribe(mUserService.getUserProps(token(),uid()), observer);
    }

    //判断用户
    public static Subscription getUserSignsStatus(Observer<BaseBean<MessageBean>> observer) {
        return setSubscribe(mUserService.getUserSignsStatus(uid()), observer);
    }

    //用户签到
    public static Subscription requestSigns(@NonNull String deviceId, Observer<BaseBean<SingsBean>> observer) {
        return setSubscribe(mUserService.postSigns(uid(),deviceId), observer);
    }

    //聊天赠送礼物
    public static Subscription postChatGifts(@IntegerRes int userFromId, @IntegerRes int userToId, @IntegerRes int prop,Observer<BaseBean<MessageBean>> observer) {
        return setSubscribe(mUserService.postChatGifts(userFromId,userToId,prop), observer);
    }

    //聊天结束统计
    public static Subscription postChatResult(@IntegerRes int userFromId, @IntegerRes int userToId, @IntegerRes int time,Observer<BaseBean<MessageBean>> observer) {
        return setSubscribe(mUserService.postChatResult(userFromId,userToId,time), observer);
    }

}
