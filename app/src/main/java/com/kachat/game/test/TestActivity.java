package com.kachat.game.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.RtcVideoProcess.FaceRigItf;
import com.RtcVideoProcess.VideoProcessItf;
import com.dnion.RenderProxy;
import com.dnion.SharedRTCEnv;
import com.dnion.VAChatAPI;
import com.dnion.VAGameAPI;
import com.kachat.game.Constant;
import com.kachat.game.R;
import com.kachat.game.base.videos.SdkApi;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.ui.graduate.GraduateSchoolActivity;

import java.util.Objects;

public class TestActivity extends AppCompatActivity {

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

    private VideoProcessItf videoProcessorToCamera;
    private FaceRigItf faceRigItf;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
    }

    private void init(){
        DbUserBean mDbUserBean=DaoQuery.queryUserData();
        FrameLayout mLocalLayout=findViewById(R.id.fl_Container1);
//        FrameLayout mRemoteLayout=findViewById(R.id.fl_Container2);
        SdkApi.getInstance().sdkLogin(Objects.requireNonNull(mDbUserBean).getUid(), mDbUserBean.getToken());
        SdkApi.getInstance().create();
        SdkApi.getInstance().loadLocalView(this,mLocalLayout);
//        SdkApi.getInstance().loadLocalView(this,mRemoteLayout);
        SdkApi.getInstance().enableVideoView();
        SdkApi.getInstance().loadFaceRigItf("live2d/yuLu","yuLu","livebg","bg_1.png");
        SdkApi.getInstance().startGameMatch(0);
    }


    @Override
    protected void onDestroy() {
        SdkApi.getInstance().destroy(false);
        SdkApi.getInstance().sdkExit();
        super.onDestroy();
    }
}
