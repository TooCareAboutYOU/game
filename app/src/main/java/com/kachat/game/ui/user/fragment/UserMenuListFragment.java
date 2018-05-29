package com.kachat.game.ui.user.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.base.BaseFragment;

import butterknife.BindView;

public class UserMenuListFragment extends BaseFragment {

    @BindView(R.id.llc_container)
    LinearLayoutCompat mLllContainer;

    public UserMenuListFragment(){}
    public static UserMenuListFragment getInstance(){  return UserMenuListFragmentHolder.instance;  }
    private static class UserMenuListFragmentHolder{
        @SuppressLint("StaticFieldLeak")
        static final UserMenuListFragment instance=new UserMenuListFragment();
    }

    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_usermenulist;
    }

    @Override
    public void onInitView() {

    }

    @Override
    public void onInitData() {

    }

}
