package com.kachat.game.ui.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.ui.shop.fragments.GoldsFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopActivity extends BaseActivity {


    @BindView(R.id.sdv_ToolBar_Base_Back)
    SimpleDraweeView mSdvToolBarBaseBack;
    @BindView(R.id.atv_ToolBar_Base_Title)
    AppCompatTextView mAtvToolBarBaseTitle;
    @BindView(R.id.sdv_Menu)
    SimpleDraweeView mSdvMenu;
    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;

    @BindView(R.id.rg_GoodsType)
    RadioGroup mRgGoodsType;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, ShopActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int onSetResourceLayout() {  return R.layout.activity_shop;  }

    @Override
    protected boolean onSetStatusBar() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().titleBar(mToolbarBase).transparentStatusBar().init();
        }
    }

    @Override
    protected void onInitView() {
        getBack().setOnClickListener(v -> finish());
        mToolbarBase.setBackgroundResource(R.color.colorNormal);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container,GoldsFragment.getInstance()).commit();

        mRgGoodsType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.acRbtn_golds: getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container,GoldsFragment.getInstance()).commit();  break;
                case R.id.acRbtn_Props: getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container,GoldsFragment.getInstance()).commit();  break;
                case R.id.acRbtn_Persons: getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container,GoldsFragment.getInstance()).commit();  break;
                case R.id.acRbtn_Faces: getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container,GoldsFragment.getInstance()).commit();  break;
            }
        });

    }

}
