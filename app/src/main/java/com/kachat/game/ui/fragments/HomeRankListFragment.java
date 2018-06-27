package com.kachat.game.ui.fragments;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseDialogFragment;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.RankListBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.ExperienceRankPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemon.view.SpaceItemDecoration;

public class HomeRankListFragment extends BaseDialogFragment {

    public static final String TAG = "HomeRankListFragment";

    @BindView(R.id.acTV_Hint_bg)
    AppCompatTextView mAcTVHintBg;
    @BindView(R.id.acIV_Close)
    SimpleDraweeView mAcIVClose;
    @BindView(R.id.fl_Container)
    FrameLayout mFlContainer;
    @BindView(R.id.acTv_Sure)
    AppCompatTextView mAcTvSure;
    @BindView(R.id.rl_MyRank)
    RelativeLayout mRelativeLayout;

    @BindView(R.id.sdv_UserIndex)
    SimpleDraweeView mSdvUserIndex;
    @BindView(R.id.acTv_Index)
    AppCompatTextView mAcTvIndex;
    @BindView(R.id.acTv_UserName)
    AppCompatTextView mAcTvUserName;
    @BindView(R.id.sdv_UserSex)
    SimpleDraweeView mSdvUserSex;
    @BindView(R.id.acTv_UserLevel)
    AppCompatTextView mAcTvUserLevel;

    private RecyclerView mRecyclerView;

    private static List<RankingListBean.RanksBean> mRankList = null;
    private ExperienceRankPresenter mPresenter=null;
    private ExpLevelAdapter mExpLevelAdapter = null;
    private static class EditUserFragmentHolder {
        @SuppressLint("StaticFieldLeak")
        public static final HomeRankListFragment instance = new HomeRankListFragment();
    }

    public static HomeRankListFragment getInstance() {
        return EditUserFragmentHolder.instance;
    }

    @Override
    protected int setResLayoutId() {
        return R.layout.dialog_base_bg;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    protected void initView(View view) {
        mAcTVHintBg.setText("等级");
        mAcIVClose.setOnClickListener(v -> dismiss());
        mAcTvSure.setVisibility(View.GONE);
        mRelativeLayout.setVisibility(View.VISIBLE);
        mSdvUserIndex.setVisibility(View.INVISIBLE);
        mRankList = new ArrayList<>();
        mPresenter = new ExperienceRankPresenter(new ExpLevelCallBack());
        mPresenter.attachPresenter();

        mExpLevelAdapter = new ExpLevelAdapter();

        View listView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_recyclerview, null);
        mRecyclerView = listView.findViewById(R.id.rv_Prop);
//        ViewGroup.LayoutParams params=mRecyclerView.getLayoutParams();
//        params.height=1044;
//        mRecyclerView.setLayoutParams(params);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0, 10, 0, 0));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mExpLevelAdapter);
        mFlContainer.addView(listView);
    }

    private class ExpLevelCallBack implements OnPresenterListeners.OnViewListener<RankingListBean>{

        @SuppressLint("SetTextI18n")
        @Override
        public void onSuccess(RankingListBean result) {
            if (result.getCount() > 0 && result.getRanks() != null && result.getRanks().size() >0) {
                mRankList.addAll(result.getRanks());
                mExpLevelAdapter.notifyDataSetChanged();
            }
            if (result.getRank() != null){
                mAcTvIndex.setText(result.getRank().getIndex()+"");
                if (!TextUtils.isEmpty(result.getRank().getUsername())) {
                    mAcTvUserName.setText(result.getRank().getUsername());
                }

                if (result.getRank().getGender().equals("male")) {
                    mSdvUserSex.setBackgroundResource(R.drawable.icon_item_exprank_male);
                }
                if (result.getRank().getUser_detail() != null) {
                    mAcTvUserLevel.setText("LV"+result.getRank().getUser_detail().getLevel());
                }
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null && error.getToast() != null && !TextUtils.isEmpty(error.getToast())) {
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

    private int LAYOUT_NO1=0;
    private int LAYOUT_NORMAL=1;

    public class ExpLevelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public int getItemViewType(int position) {
            if (mRankList.get(position).getIndex() == 1) {
                return LAYOUT_NO1;
            }
            return LAYOUT_NORMAL;
        }

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == LAYOUT_NO1) {
                return new No1ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_expranklist_no1, parent,false));
            }
            return new NormalViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_expranklist_normal, parent,false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof No1ViewHolder) {
                ((No1ViewHolder)holder).mAcTvIndex.setText(mRankList.get(position).getIndex() + "");

                if (!TextUtils.isEmpty(mRankList.get(position).getUsername())) {
                    ((No1ViewHolder)holder).mAcTvUserName.setText(mRankList.get(position).getUsername());
                }

                if (mRankList.get(position).getGender().equals("male")) {
                    ((No1ViewHolder)holder).mSdvUserSex.setBackgroundResource(R.drawable.icon_item_exprank_male);
                }
                if (mRankList.get(position).getUser_detail() != null) {
                    ((No1ViewHolder)holder).mAcTvUserLevel.setText("LV"+mRankList.get(position).getUser_detail().getLevel());
                }
            }else if (holder instanceof NormalViewHolder) {

                ((NormalViewHolder)holder).mAcTvIndex.setText(mRankList.get(position).getIndex() + "");

                if (!TextUtils.isEmpty(mRankList.get(position).getUsername())) {
                    ((NormalViewHolder)holder).mAcTvUserName.setText(mRankList.get(position).getUsername());
                }

                if (mRankList.get(position).getGender().equals("male")) {
                    ((NormalViewHolder)holder).mSdvUserSex.setBackgroundResource(R.drawable.icon_item_exprank_male);
                }
                if (mRankList.get(position).getUser_detail() != null) {
                    ((NormalViewHolder)holder).mAcTvUserLevel.setText("LV"+mRankList.get(position).getUser_detail().getLevel());
                }
            }

        }

        @Override
        public int getItemCount() {
            return mRankList.size() > 0 ? mRankList.size() : 0;
        }

        class No1ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.sdv_UserIndex)
            SimpleDraweeView mSdvUserIndex;
            @BindView(R.id.acTv_Index)
            AppCompatTextView mAcTvIndex;
            @BindView(R.id.acTv_UserName)
            AppCompatTextView mAcTvUserName;
            @BindView(R.id.sdv_UserSex)
            SimpleDraweeView mSdvUserSex;
            @BindView(R.id.acTv_UserLevel)
            AppCompatTextView mAcTvUserLevel;
            No1ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }

        class NormalViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.sdv_UserIndex)
            SimpleDraweeView mSdvUserIndex;
            @BindView(R.id.acTv_Index)
            AppCompatTextView mAcTvIndex;
            @BindView(R.id.acTv_UserName)
            AppCompatTextView mAcTvUserName;
            @BindView(R.id.sdv_UserSex)
            SimpleDraweeView mSdvUserSex;
            @BindView(R.id.acTv_UserLevel)
            AppCompatTextView mAcTvUserLevel;
            NormalViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }


    @Override
    public void onDestroyView() {
        if (mRankList != null) {
            mRankList.clear();
            mRankList = null;
        }

        if (mPresenter != null) {
            mPresenter.detachPresenter();
            mPresenter=null;
        }

        if (mExpLevelAdapter != null) {
            mExpLevelAdapter=null;
        }
        super.onDestroyView();
    }
}
