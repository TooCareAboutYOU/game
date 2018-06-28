package com.kachat.game.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.dnion.SharedRTCEnv;
import com.dnion.VAGameAPI;
import com.dnion.VAGameDelegate;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kachat.game.BuildConfig;
import com.kachat.game.Constant;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.events.PublicEventMessage;
import com.kachat.game.events.services.NetConnectService;
import com.kachat.game.libdata.HttpLocalDataHelper;
import com.kachat.game.ui.user.login.LoginActivity;
import com.kachat.game.utils.SharedPreferencesHelper;
import com.kachat.game.utils.manager.CrashHandlerManager;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;
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
//        initCrash(application);

        GlideBuilder glideBuilder=new GlideBuilder();
        Glide.init(application.getApplicationContext(),glideBuilder);
        SharedPreferencesHelper.init(application.getApplicationContext(),"JX",Context.MODE_PRIVATE);
    }


    private static void initImageLoader(@NonNull Context context){   Fresco.initialize(context);   }

    private static void initNetWork(@NonNull Context context){  HttpLocalDataHelper.init(context, Constant.HTTP_URL,DB_NAME,DB_PASSWORD);  }

    private static void initLogger(){
        Logger.init().logLevel(LogLevel.FULL);
    }

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

        VAGameAPI.CreateVAGameAPI(context,Constant.SDK_URL);
        VAGameAPI.getInstance().setListener(new VAGameDelegate() {
            @Override
            public void onSessionReady() {
                Log.i(TAG, "onSessionReady: ");
                EventBus.getDefault().post(new DNGameEventMessage(DNGameEventMessage.DNGameEvent.SESSION_READY));
            }

            @Override
            public void onSessionBroken() {
                Log.i(TAG, "onSessionBroken: ");
                EventBus.getDefault().post(new DNGameEventMessage(DNGameEventMessage.DNGameEvent.SESSION_BROKEN));

            }

            @Override
            public void onSessionOccupy() {
                Log.i(TAG, "onSessionOccupy: ");
                EventBus.getDefault().post(new DNGameEventMessage(DNGameEventMessage.DNGameEvent.SESSION_OCCUPY));
            }

            @Override
            public void onSessionKeepAlive(String s) {
//                Log.i(TAG, "onSessionKeepAlive: "+s);
                EventBus.getDefault().post(new DNGameEventMessage(s,DNGameEventMessage.DNGameEvent.SESSION_KEEP_ALIVE));
            }

            @Override
            public void onJoinGameRoomSuccess() {
                Log.i(TAG, "onJoinGameRoomSuccess: ");
                EventBus.getDefault().post(new DNGameEventMessage(DNGameEventMessage.DNGameEvent.JOIN_SUCCESS));
            }

            @Override
            public void onJoinGameRoomFailed(String s) {
                Log.i(TAG, "onJoinGameRoomFailed: "+s);
                EventBus.getDefault().post(new DNGameEventMessage(s,DNGameEventMessage.DNGameEvent.JOIN_FAILED));
            }

            @Override
            public void onMatchGameSuccess() {
                Log.i(TAG, "onMatchGameSuccess: ");
                EventBus.getDefault().post(new DNGameEventMessage(DNGameEventMessage.DNGameEvent.MATCH_SUCCESS));
            }

            @Override
            public void onGameMessage(String s) {
                Log.i(TAG, "onGameMessage: "+s);
                // {"type":"leave","data":{},"game":"hexTris"}
                EventBus.getDefault().post(new DNGameEventMessage(s,DNGameEventMessage.DNGameEvent.GAME_MESSAGE));
            }

            @Override
            public void onGameStat(VAGameStat vaGameStat, String s) {
                Log.i(TAG, "onGameStat: "+s);
                EventBus.getDefault().post(new DNGameEventMessage(s,vaGameStat,DNGameEventMessage.DNGameEvent.GAME_STAT));
            }

            @Override
            public void onVideoChatStart(long l) {
                Log.i(TAG, "onVideoChatStart: "+l);
                EventBus.getDefault().post(new DNGameEventMessage(l,DNGameEventMessage.DNGameEvent.VIDEO_CHAT_START));
            }

            @Override
            public void onVideoChatFinish(int i) {
                Log.i(TAG, "onVideoChatFinish: "+i);
                EventBus.getDefault().post(new DNGameEventMessage(i,DNGameEventMessage.DNGameEvent.VIDEO_CHAT_FINISH));
            }

            @Override
            public void onVideoChatTerminate(int i) {
                Log.i(TAG, "onVideoChatTerminate: "+i);
                EventBus.getDefault().post(new DNGameEventMessage(i,DNGameEventMessage.DNGameEvent.VIDEO_CHAT_TERMINATE));
            }

            @Override
            public void onVideoChatFail() {
                Log.i(TAG, "onVideoChatFail: ");
                EventBus.getDefault().post(new DNGameEventMessage(DNGameEventMessage.DNGameEvent.VIDEO_CHAT_FAIL));
            }

            @Override
            public void onGotGift(long l) {
                Log.i(TAG, "onGotGift: "+l);
                EventBus.getDefault().post(new DNGameEventMessage(l,DNGameEventMessage.DNGameEvent.GOT_GIFT));
            }

            @Override
            public void onErrorMessage(String s) {
                Log.i(TAG, "onErrorMessage: "+s);
                EventBus.getDefault().post(new DNGameEventMessage(s,DNGameEventMessage.DNGameEvent.ERROR_MESSAGE));
            }
        });

    }

}
