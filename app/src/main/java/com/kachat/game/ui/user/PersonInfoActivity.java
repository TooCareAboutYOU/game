package com.kachat.game.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.ui.user.fragment.UserMenuListFragment;

public class PersonInfoActivity extends BaseActivity {


    private Fragment mFragment;
    private FragmentTransaction transaction;

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_person_info;
    }

    @Override
    protected void onInitView() {
//        mFragment=UserMenuListFragment.getInstance();
//        transaction=getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.fl_MenuList, mFragment);
//        transaction.commit();
    }


    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {
        findViewById(R.id.aTv_Edit).setOnClickListener(v -> startActivity(new Intent(this,EditUserDataActivity.class)));
    }

    @Override
    protected void onDestroy() {
//        transaction.remove(mFragment);
        super.onDestroy();
    }

}
