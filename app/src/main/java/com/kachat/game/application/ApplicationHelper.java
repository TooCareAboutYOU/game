package com.kachat.game.application;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.blankj.utilcode.util.Utils;
import com.dnion.DollRoomSignaling;
import com.dnion.SharedRTCEnv;
import com.dnion.VAChatAPI;
import com.dnion.VAChatDelegate;
import com.dnion.VAChatSignaling;
import com.dnion.VADollAPI;
import com.dnion.VADollDelegate;
import com.dnion.VADollSignaling;
import com.dnion.VAGameAPI;
import com.dnion.VAGameDelegate;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kachat.game.BuildConfig;
import com.kachat.game.events.VAGameEventMessage;
import com.kachat.game.libdata.HttpLocalDataHelper;
import com.kachat.game.utils.SharedPreferencesHelper;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.EventBus;


public class ApplicationHelper {

    private static final String TAG = "ApplicationHelper";

    private static final String HTTP_URL="https://www.fresco-cn.org/";
    private static final String NAME= "KaChat_Preference";
    private static final int MODE=Context.MODE_PRIVATE;
    private static final String DB_NAME="KaChat.db";
    private static final String DB_PASSWORD="KaChat";

    public static void init(@NonNull Application application){
        Context mContext=application.getApplicationContext();
        initImageLoader(mContext);
        initNetWork(mContext);
        initDataBase(mContext);
        initLogger();
        initUtils(application);
        initEvent();
        initKaChatSDK(mContext);
    }

    private static void initImageLoader(@NonNull Context context){   Fresco.initialize(context);   }

    private static void initNetWork(@NonNull Context context){  HttpLocalDataHelper.init(context, HTTP_URL,DB_NAME,DB_PASSWORD);  }

    private static void initDataBase(@NonNull Context context){ SharedPreferencesHelper.init(context,NAME,MODE); }

    private static void initLogger(){   Logger.init().logLevel(LogLevel.FULL);   }

    private static void initUtils(@NonNull Application application){   Utils.init(application);   }

    private static void initEvent(){
        EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .throwSubscriberException(BuildConfig.DEBUG)
                .build();
    }

    private static void initKaChatSDK(@NonNull Context context){
        SharedRTCEnv.CreateSharedRTCEnv(context);

        VAChatAPI.CreateVAChatAPI(context,true);
        VAChatAPI.getInstance().setListener(new VAChatDelegate() {
            @Override
            public void onConnectionSuccess() {

            }

            @Override
            public void onConnectionFailed(String s) {

            }

            @Override
            public void onDisconnected(String s) {

            }

            @Override
            public void onChatStart(VAChatSignaling.ChatInfo chatInfo) {

            }

            @Override
            public void onChatFinish() {

            }

            @Override
            public void onChatTerminate() {

            }

            @Override
            public void onChatCancel() {

            }

            @Override
            public void onGotDoll(long l) {

            }

            @Override
            public void onGotUserVideo() {

            }

            @Override
            public void onLostUserVideo() {

            }
        });

        VADollAPI.CreateVADollAPI(context);
        VADollAPI.getInstance().setListener(new VADollDelegate() {
            @Override
            public void onConnectionSuccess() {

            }

            @Override
            public void onConnectionFailed(String s) {

            }

            @Override
            public void onDisconnected(String s) {

            }

            @Override
            public void onJoinRoom(DollRoomSignaling.DOLLRoomInfo dollRoomInfo) {

            }

            @Override
            public void onJoinRoomFailed(String s, String s1) {

            }

            @Override
            public void onLeaveRoom() {

            }

            @Override
            public void onReadyGame() {

            }

            @Override
            public void onCatchResult(int i) {

            }

            @Override
            public void onQueueInfoUpdate(DollRoomSignaling.DOLLRoomInfo dollRoomInfo, DollRoomSignaling.DOLLVAUserInfo dollvaUserInfo) {

            }

            @Override
            public void onStartGame(long l) {

            }

            @Override
            public void onStartGameFailed(long l, String s) {

            }

            @Override
            public void onRefreshRoomInfo(VADollSignaling.RoomInfo roomInfo) {

            }

            @Override
            public void onUserJoin(VADollSignaling.UserInfo userInfo) {

            }

            @Override
            public void onUserUpdate(VADollSignaling.UserInfo userInfo) {

            }

            @Override
            public void onUserLeave(VADollSignaling.UserInfo userInfo) {

            }

            @Override
            public void onGotUserVideo(String s) {

            }

            @Override
            public void onLostUserVideo(String s) {

            }

            @Override
            public void onTextMessage(String s, String s1) {

            }
        });

        VAGameAPI.CreateVAGameAPI(context);
        VAGameAPI.getInstance().setListener(new VAGameDelegate() {
            @Override
            public void onConnectionSuccess() {
                Log.i(TAG, "onConnectionSuccess: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_SUCCESS));
            }

            @Override
            public void onConnectionFailed(String s) {
                Log.i(TAG, "onConnectionFailed: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_FAILED,s));
            }

            @Override
            public void onDisconnected(String s) {
                Log.i(TAG, "onDisconnected: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_DISCONNECTED,s));
            }

            @Override
            public void onJoinGameRoomSuccess() {
                Log.i(TAG, "onJoinGameRoomSuccess: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_JOIN_SUCCESS));
            }

            @Override
            public void onJoinGameRoomFailed(String s) {
                Log.i(TAG, "onJoinGameRoomFailed: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_JOIN_FAILED,s));
            }

            @Override
            public void onLeaveRoom() {
                Log.i(TAG, "onLeaveRoom: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_LEAVE));
            }

            @Override
            public void onMatchGameSuccess() {
                Log.i(TAG, "onMatchGameSuccess: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_MATCH_SUCCESS));
            }

            @Override
            public void onGameMessage(String s) {
                Log.i(TAG, "onGameMessage: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_MESSAGE,s));
            }
        });
    }

}
