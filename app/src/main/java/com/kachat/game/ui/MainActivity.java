package com.kachat.game.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.Toast;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.events.services.UpLoadBugLogService;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.ui.chat.ChatActivity;
import com.kachat.game.ui.game.GameActivity;
import com.kachat.game.ui.graduate.GraduateSchoolActivity;
import com.kachat.game.ui.shop.ShopActivity;
import com.kachat.game.ui.user.MeActivity;

public class MainActivity extends BaseActivity {

    public static void getInstance(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean onSetStatusBar() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().transparentStatusBar().init();
        }
    }

    @Override
    protected void onInitView() {
        findViewById(R.id.btn_graduate).setOnClickListener(v -> {
            GraduateSchoolActivity.newInstance(this);
        });

        findViewById(R.id.btn_Live2D).setOnClickListener(v -> {
            ChatActivity.newInstance(this);
        });

        findViewById(R.id.btn_H5Game).setOnClickListener(v -> {
            GameActivity.newInstance(this);
        });
        findViewById(R.id.btn_Me).setOnClickListener(v -> {
            MeActivity.newInstance(this);
        });
        findViewById(R.id.btn_Shop).setOnClickListener(v -> {
//            ShopActivity.newInstance(this);
            UpLoadBugLogService.toZip(DaoQuery.queryUserData().getMobile());
        });

    }

    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        KaChatApplication.getInstance().stop();
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
