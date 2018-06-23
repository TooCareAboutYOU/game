package com.kachat.game.ui.user;

import android.support.v7.widget.Toolbar;

import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;

import butterknife.BindView;


public class ResetPwdActivity extends BaseActivity {


    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_reset_pass_word;
    }

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
        getToolBarBack().setOnClickListener(v -> finish());
        mToolbarBase.setBackgroundResource(R.color.colorNormal);

    }

}
