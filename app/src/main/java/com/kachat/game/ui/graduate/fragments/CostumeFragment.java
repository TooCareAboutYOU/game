package com.kachat.game.ui.graduate.fragments;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostumeFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    public static CostumeFragment getInstance(){  return CostumeFragmentHolder.instance;  }
    private static class CostumeFragmentHolder{
        @SuppressLint("StaticFieldLeak")
        private static final CostumeFragment instance=new CostumeFragment();
    }

    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_costume;
    }

    @Override
    public void onInitView(@NonNull View view) {

    }


}
