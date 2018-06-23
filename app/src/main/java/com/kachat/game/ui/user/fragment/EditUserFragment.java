package com.kachat.game.ui.user.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.base.BaseDialogFragment;
import com.kachat.game.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EditUserFragment extends BaseDialogFragment {

    public static final String TAG = "EditUserFragment";
    private static String mTitle="";

    @BindView(R.id.acTV_Hint_bg)
    AppCompatTextView mAcTVHintBg;
    @BindView(R.id.acIV_Close)
    SimpleDraweeView mAcIVClose;
    @BindView(R.id.fl_Container)
    FrameLayout mFlContainer;
    @BindView(R.id.acTv_Sure)
    AppCompatTextView mAcTvSure;

    public static EditUserFragment getInstance(String title) {
        mTitle=title;
        return EditUserFragmentHolder.instance;
    }

    private static class EditUserFragmentHolder {
        @SuppressLint("StaticFieldLeak")
        public static final EditUserFragment instance = new EditUserFragment();
    }

    @Override
    protected int setResLayoutId() {
        return R.layout.dialog_base_bg;
    }

    @Override
    protected void initView(View view) {
        mAcTVHintBg.setText(mTitle);
        mAcIVClose.setOnClickListener(v -> dismiss());
        mAcTvSure.setOnClickListener(v -> {

            dismiss();
        });
        View containerView=LayoutInflater.from(getContext()).inflate(R.layout.dialog_user_edit,null);

        mFlContainer.addView(containerView);
    }


}
