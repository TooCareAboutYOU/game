package com.kachat.game.ui.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.events.PublicEventMessage;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewGuidesLineActivity extends BaseActivity {

    private static final String TAG = "NewGuidesLineActivity";
    @BindView(R.id.sdv_ToolBar_Base_Back)
    SimpleDraweeView mSdvToolBarBaseBack;
    @BindView(R.id.atv_ToolBar_Base_Title)
    AppCompatTextView mAtvToolBarBaseTitle;
    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;

    @BindView(R.id.sdv_GuideThree)
    SimpleDraweeView mSdvGuideThree;
    @BindView(R.id.sdv_GuideTwo)
    SimpleDraweeView mSdvGuideTwo;
    @BindView(R.id.sdv_GuideOne)
    SimpleDraweeView mSdvGuideOne;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, NewGuidesLineActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int onSetResourceLayout() {  return R.layout.activity_new_guides_line;  }

    @Override
    protected boolean onSetStatusBar() {  return true;  }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().titleBar(mToolbarBase).transparentBar().init();
        }
    }

    @Override
    protected void onInitView() {
        mSdvToolBarBaseBack.setOnClickListener(v -> finish());
        mAtvToolBarBaseTitle.setTextColor(Color.WHITE);
        mSdvGuideOne.setOnClickListener(v -> mSdvGuideOne.setVisibility(View.GONE));
        mSdvGuideTwo.setOnClickListener(v -> mSdvGuideTwo.setVisibility(View.GONE));
        mSdvGuideThree.setOnClickListener(v -> {
            finish();
        });
    }


//    int broken = 0;
//    @SuppressLint("InflateParams")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(DNGameEventMessage event) {
//        switch (event.getEvent()) {
//            case SESSION_BROKEN: {
//                Log.i(TAG, "onEvent: SESSION_BROKEN");
//                broken++;
//                if (broken == 7) {
//                    AlterDialogBuilder dialogOccupy = new AlterDialogBuilder(this, new DialogTextView(this, "连接异常，请重新登录！"), "退出").hideClose();
//                    dialogOccupy.getRootSure().setOnClickListener(v -> {
//                        dialogOccupy.dismiss();
//                        PublicEventMessage.ExitAccount(this);
//                        finish();
//                    });
//                }
//                break;
//            }
//            case SESSION_OCCUPY: {
//                Log.i(TAG, "onEvent: SESSION_OCCUPY");
//                AlterDialogBuilder dialogOccupy = new AlterDialogBuilder(this, new DialogTextView(this, "账号异地登录，请重新登录！"), "退出").hideClose();
//                dialogOccupy.getRootSure().setOnClickListener(v -> {
//                    dialogOccupy.dismiss();
//                    PublicEventMessage.ExitAccount(this);
//                    finish();
//                });
//                break;
//            }
//        }
//    }


    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
//        broken=0;
        super.onDestroy();
    }
}
