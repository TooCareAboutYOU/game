package com.kachat.game.libdata.apiServices;

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
public interface UserService {


    //注册获取验证码
    @FormUrlEncoded
    @POST("/captcha/register")
    Observable<BaseBean<GetCaptchaBean>> postPhoneCaptchaImpl(@Field("mobile") String mobile);

    //注册
    @FormUrlEncoded
    @POST("/users")
    Observable<BaseBean<UserBean>> postRegister(@Field("mobile") String mobile, @Field("password") String password,
                                              @Field("gender") String gender, @Field("age") String age,
                                              @Field("username") String username, @Field("system") int system);

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
    Observable<BaseBean<MessageBean>> postResetPwd(@Field("mobile") String mobile,
                                                   @Field("captcha") String captcha,
                                                   @Field("password") String password);


    //登录
    @FormUrlEncoded
    @POST("/login")
    Observable<BaseBean<UserBean>> postLoginImpl(@Field("mobile") String mobile, @Field("password") String password);

    //更新用户信息
    @PUT("/users/{uid}")
    Observable<BaseBean<UpdateUserData>> putUserData(@Header("Authorization") String token, @Path("uid") int uid);

    //用户反馈
    @FormUrlEncoded
    @POST("/feedbacks")
    Observable<BaseBean<FeedBacksBean>> postFeedBacks(@Header("Authorization") String token,@Field("type") int type, @Field("content") String content);


    //用户券数
    @GET("/users/{uid}/tickets")
    Observable<BaseBean<MessageBean>> getUserTickets(@Path("uid") int uid);

    //用户拥有场景
    @GET("/users/{uid}/scenes")
    Observable<BaseBean<ScenesBean>> getUserScenes(@Path("uid") int uid);

    // http://api.e3webrtc.com:8004/users/{uid}/props
    //用户拥有道具
    @GET("/users/{uid}/props")
    Observable<BaseBean<PropsBean>> getUserProps(@Header("Authorization") String token, @Path("uid") int uid);

    //  http://api.e3webrtc.com:8004/signs?uid=19
    //检查用户是否签到
    @GET("/signs")
    Observable<BaseBean<MessageBean>> getUserSignsStatus(@Query("uid") int uid);

    // http://api.e3webrtc.com:8004/signs?uid=19
    //用户签到
    @FormUrlEncoded
    @POST("/signs")
    Observable<BaseBean<SingsBean>> postSigns(@Field("user") int uid, @Field("device") String deviceId);

    // http://api.e3webrtc.com:8004/chats/gifts?user_from=1&user_to=1&prop=1
    //聊天赠送礼物
    @FormUrlEncoded
    @POST("/chats/gifts")
    Observable<BaseBean<MessageBean>> postChatGifts(@Field("user_from") int userFromId, @Field("user_to") int userToId, @Field("prop") int prop);


    //聊天结束统计
    @FormUrlEncoded
    @POST("/chats/results")
    Observable<BaseBean<MessageBean>> postChatResult(@Field("user_from") int userFromId, @Field("user_to") int userToId, @Field("time") int time);


}
