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
import com.kachat.game.Constant;
import com.kachat.game.events.VAChatEventMessage;
import com.kachat.game.events.VADollEventMessage;
import com.kachat.game.events.VAGameEventMessage;
import com.kachat.game.events.services.UpLoadBugLogService;
import com.kachat.game.libdata.HttpLocalDataHelper;
import com.kachat.game.utils.manager.CrashHandlerManager;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.EventBus;


public class ApplicationHelper {

    private static final String TAG = "ApplicationHelper";

    private static final String DB_NAME="KaChat.db";
    private static final String DB_PASSWORD="KaChat";

    public static void init(@NonNull Application application){
        initImageLoader(application.getApplicationContext());
        initNetWork(application.getApplicationContext());
        initLogger();
        initUtils(application);
        initEvent();
        initKaChatSDK(application.getApplicationContext());
        initCrash(application);
    }

    private static void initImageLoader(@NonNull Context context){   Fresco.initialize(context);   }

    private static void initNetWork(@NonNull Context context){  HttpLocalDataHelper.init(context, Constant.HTTP_URL,DB_NAME,DB_PASSWORD);  }

    private static void initLogger(){   Logger.init().logLevel(LogLevel.FULL);   }

    private static void initUtils(@NonNull Application application){   Utils.init(application);   }

    private static void initEvent(){
        EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .throwSubscriberException(BuildConfig.DEBUG)
                .build();
    }

    private static void initCrash(@NonNull Application application){
        CrashHandlerManager.getInstance().init(application.getApplicationContext());
    }

    private static void initKaChatSDK(@NonNull Context context){
        SharedRTCEnv.CreateSharedRTCEnv(context);

        VAChatAPI.CreateVAChatAPI(context,true);
        VAChatAPI.getInstance().setListener(new VAChatDelegate() {
            @Override
            public void onConnectionSuccess() {
                EventBus.getDefault().post(new VAChatEventMessage(VAChatEventMessage.VAChatEvent.VACHAT_SUCCESS));
            }

            @Override
            public void onConnectionFailed(String s) {
                EventBus.getDefault().post(new VAChatEventMessage(VAChatEventMessage.VAChatEvent.VACHAT_FAILED,s));
            }

            @Override
            public void onDisconnected(String s) {
                EventBus.getDefault().post(new VAChatEventMessage(VAChatEventMessage.VAChatEvent.VACHAT_DISCONNECTED,s));
            }

            @Override
            public void onChatStart(VAChatSignaling.ChatInfo chatInfo) {
                EventBus.getDefault().post(new VAChatEventMessage(VAChatEventMessage.VAChatEvent.VACHAT_START,chatInfo));
            }

            @Override
            public void onChatFinish(int i) {
                EventBus.getDefault().post(new VAChatEventMessage(VAChatEventMessage.VAChatEvent.VACHAT_FINISH,i));

            }

            @Override
            public void onChatTerminate(int i) {
                EventBus.getDefault().post(new VAChatEventMessage(VAChatEventMessage.VAChatEvent.VACHAT_TERMINATE,i));

            }

            @Override
            public void onGotDoll(long l) {
                EventBus.getDefault().post(new VAChatEventMessage(VAChatEventMessage.VAChatEvent.VACHAT_GOT_DOLL,l));
            }

            @Override
            public void onGotUserVideo() {
                EventBus.getDefault().post(new VAChatEventMessage(VAChatEventMessage.VAChatEvent.VACHAT_GOT_VIDEO));
            }

            @Override
            public void onLostUserVideo() {
                EventBus.getDefault().post(new VAChatEventMessage(VAChatEventMessage.VAChatEvent.VACHAT_LOST_VIDEO));
            }
        });

        VADollAPI.CreateVADollAPI(context);
        VADollAPI.getInstance().setListener(new VADollDelegate() {
            @Override
            public void onConnectionSuccess() {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VADOLL_CONNECT_SUCCESS));
            }

            @Override
            public void onConnectionFailed(String s) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VADOLL_CONNECT_FAILED,s));
            }

            @Override
            public void onDisconnected(String s) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VADOLL_DISCONNECTED,s));
            }

            @Override
            public void onJoinRoom(DollRoomSignaling.DOLLRoomInfo dollRoomInfo) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VADOLL_JOIN_SUCCESS,dollRoomInfo));

            }

            @Override
            public void onJoinRoomFailed(String s, String s1) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VADOLL_JOIN_FAILED,s,s1));

            }

            @Override
            public void onLeaveRoom() {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VADOLL_LEAVE));

            }

            @Override
            public void onReadyGame() {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VADOLL_READY));

            }

            @Override
            public void onCatchResult(int i) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VADOLL_RESULT,i));

            }

            @Override
            public void onQueueInfoUpdate(DollRoomSignaling.DOLLRoomInfo dollRoomInfo, DollRoomSignaling.DOLLVAUserInfo dollvaUserInfo) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VADOLL_QUEUE_UPDATE,dollRoomInfo,dollvaUserInfo));

            }

            @Override
            public void onStartGame(long l) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VACHAT_START_SUCCESS,l));

            }

            @Override
            public void onStartGameFailed(long l, String s) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VACHAT_START_FAILED,s,l));

            }

            @Override
            public void onRefreshRoomInfo(VADollSignaling.RoomInfo roomInfo) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VACHAT_REFRESH_ROOM_INFO,roomInfo));

            }

            @Override
            public void onUserJoin(VADollSignaling.UserInfo userInfo) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VACHAT_REMOTE_JOIN,userInfo));

            }

            @Override
            public void onUserUpdate(VADollSignaling.UserInfo userInfo) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VACHAT_USER_UPDATE,userInfo));

            }

            @Override
            public void onUserLeave(VADollSignaling.UserInfo userInfo) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VACHAT_REMOTE_LEAVE,userInfo));

            }

            @Override
            public void onGotUserVideo(String s) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VACHAT_GOT_VIDEO,s));

            }

            @Override
            public void onLostUserVideo(String s) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VACHAT_LOST_VIDEO,s));

            }

            @Override
            public void onTextMessage(String s, String s1) {
                EventBus.getDefault().post(new VADollEventMessage(VADollEventMessage.VADollEvent.VACHAT_MESSAGE,s,s1));

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
            public void onVideoChatStart() {
                Log.i(TAG, "onVideoChatStart: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_VIDEO_START));
            }

            @Override
            public void onVideoChatFail() {
                Log.i(TAG, "onVideoChatFail: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_VIDEO_FAILED));
            }

            @Override
            public void onVideoChatStop() {
                Log.i(TAG, "onVideoChatStop: ");
                EventBus.getDefault().post(new VAGameEventMessage(VAGameEventMessage.VAGameEvent.VAGAME_VIDEO_STOP));
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
