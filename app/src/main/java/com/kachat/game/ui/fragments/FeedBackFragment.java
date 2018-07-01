package com.kachat.game.ui.fragments;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseDialogFragment;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.FeedBacksBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.CharmRankPresenter;
import com.kachat.game.libdata.mvp.presenters.FeedBacksPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemon.view.SpaceItemDecoration;

/**
 *
 */
public class FeedBackFragment extends BaseDialogFragment{

    public static final String TAG = "FeedBackFragment";

    @BindView(R.id.fl_Container)
    FrameLayout mFlContainer;

    @BindView(R.id.acTV_Hint_bg)
    AppCompatTextView mAcTVHintBg;
    @BindView(R.id.acIV_Close)
    SimpleDraweeView mAcIVClose;

    @BindView(R.id.acTv_Sure)
    AppCompatTextView mAcTvSure;
    @BindView(R.id.rl_MyRank)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.sdv_UserIndex)
    SimpleDraweeView mSdvUserIndex;
    @BindView(R.id.acTv_Index)
    AppCompatTextView mAcTvMyIndex;
    @BindView(R.id.acTv_UserName)
    AppCompatTextView mAcTvMyName;
    @BindView(R.id.sdv_UserSex)
    SimpleDraweeView mSdvMySex;
    @BindView(R.id.acTv_UserLevel)
    AppCompatTextView mAcTvMyLevel;
    @BindView(R.id.sdv_Charm)
    SimpleDraweeView mSdvCharm;


    private int type = 1;
    private FeedBacksPresenter mFeedBacksPresenter=null;
    private AppCompatEditText acDt;
    
    
    @SuppressLint("StaticFieldLeak")
    public static FeedBackFragment getInstance(){  return CharmRankListFragmentHolder.instance;  }
    private static class CharmRankListFragmentHolder{
        @SuppressLint("StaticFieldLeak")
        private static final FeedBackFragment instance=new FeedBackFragment();
    }
    
    @Override
    protected int setResLayoutId() { return R.layout.dialog_base_bg; }

    @SuppressLint("InflateParams")
    @Override
    protected void initView(View view) {
        mAcTVHintBg.setText("意见反馈");
        mAcTvSure.setText("提交");
        mAcIVClose.setOnClickListener(v -> dismiss());

        mFeedBacksPresenter=new FeedBacksPresenter(new FeedBackCallBack());
        View childView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_feedback, null);
        RadioGroup rgFeedBack=childView.findViewById(R.id.rg_FeedBack);
        rgFeedBack.setVisibility(View.VISIBLE);
        acDt=childView.findViewById(R.id.ac_Dt);


        rgFeedBack.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.acRbtn_Question: type =1; acDt.setHint("请输入你的问题");break;
                case R.id.acRbtn_Idea: type =0; acDt.setHint("请输入你的意见");break;
            }
        });
        mFlContainer.addView(childView);

        mAcTvSure.setOnClickListener(v -> {
            if (TextUtils.isEmpty(acDt.getText().toString().trim())) {
                Toast("内容不能为空！");
                return;
            }
            if (acDt.getText().length() < 10 ) {
                Toast("内容不足");
                return;
            }

            if (acDt.getText().length() > 200) {
                Toast("超出内容最大数量");
                return;
            }
            mFeedBacksPresenter.attachPresenter(type,acDt.getText().toString());
        });
    }

    private class FeedBackCallBack implements OnPresenterListeners.OnViewListener<FeedBacksBean>{

        @Override
        public void onSuccess(FeedBacksBean result) {
            Toast("提交成功");
            acDt.setText("");
            dismiss();
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null && error != null && !TextUtils.isEmpty(error.getToast())) {
                Toast(error.getToast());
            }

        }

        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Toast(e.getMessage());
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (mFeedBacksPresenter != null) {
            mFeedBacksPresenter.detachPresenter();
            mFeedBacksPresenter=null;
        }
        super.onDestroyView();
    }
}
