package com.kachat.game.ui.user.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPassWordActivity extends BaseActivity {


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
