package com.kachat.game.ui.game;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.events.PublicEventMessage;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.RankListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.GameRankPresenter;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RankListActivity extends BaseActivity {

    private static final String TAG = "RankListActivity";

    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;
    @BindView(R.id.rg_GameType)
    RadioGroup mRgGameType;

    @BindView(R.id.rg_TimeType)
    RadioGroup mRgTimeType;
    @BindView(R.id.rv_RankList)
    RecyclerView mRvRankList;

    @BindView(R.id.acTv_MyNumber)
    AppCompatTextView mAcTvMyNumber;
    @BindView(R.id.acTv_MyName)
    AppCompatTextView mAcTvMyName;
    @BindView(R.id.sdv_MySex)
    SimpleDraweeView mSdvMySex;
    @BindView(R.id.acTv_MyScore)
    AppCompatTextView mAcTvMyScore;

    private DbUserBean mDbUserBean=DaoQuery.queryUserData();
    private GameRankPresenter mRankPresenter = null;
    private RankListAdapter mAdapter = null;
    private List<RankListBean.ResultBean.RanksBean> mRankList;
    private int GameIndex = 901, ScoreType = 0;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, RankListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_rank_list;
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
        mRankPresenter = new GameRankPresenter(new RankListCallBack());
        mRankList = new ArrayList<>();
        mAdapter = new RankListAdapter();
        mRvRankList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvRankList.setAdapter(mAdapter);
        loadData(GameIndex, ScoreType);
        mRgGameType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.acRbtn_Start:

                    GameIndex = 901;
                    loadData(GameIndex, ScoreType);
                    break;
                case R.id.acRbtn_Tower:
                    GameIndex = 900;
                    loadData(GameIndex, ScoreType);
                    break;
                case R.id.acRbtn_Hextris:
                    GameIndex = 902;
                    loadData(GameIndex, ScoreType);
                    break;
            }
        });

        mRgTimeType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.acRbtn_Total:
                    ScoreType = 0;
                    loadData(GameIndex, ScoreType);
                    break;
                case R.id.acRbtn_Week:
                    ScoreType = 1;
                    loadData(GameIndex, ScoreType);
                    break;
            }
        });
    }

    private void loadData(int GameIndex, int ScoreType) {
        mRankPresenter.attachPresenter(GameIndex, ScoreType);
    }

    private class RankListCallBack implements OnPresenterListeners.OnViewListener<RankListBean> {

        @SuppressLint("SetTextI18n")
        @Override
        public void onSuccess(RankListBean result) {
            if (result != null) {
                if (result.getResult() != null) {
                    if(result.getResult().getCount() > 0 && result.getResult().getRanks() != null && result.getResult().getRanks().size() > 0) {
                        mRankList.clear();
                        mRankList.addAll(result.getResult().getRanks());
                        mAdapter.notifyDataSetChanged();
                    }
                    if (result.getResult().getRank() != null) {
                        Log.i(TAG, "onSuccess: 1："+result.getResult().getRank().toString());
                        mAcTvMyNumber.setText(result.getResult().getRank().getIndex()+"");
                        if (result.getResult().getRank().getUser() != null) {
                            Log.i(TAG, "onSuccess: 2："+result.getResult().getRank().getUser().toString());
                            if (!TextUtils.isEmpty(result.getResult().getRank().getUser().getUsername())) {
                                mAcTvMyName.setText(result.getResult().getRank().getUser().getUsername());
                            }
                            if (!TextUtils.isEmpty(result.getResult().getRank().getUser().getGender()) && result.getResult().getRank().getUser().getGender().equals("female")) {
                                mSdvMySex.setBackgroundResource(R.drawable.icon_ranklist_female);
                            }
                        }else {
                            if (mDbUserBean != null) {
                                Log.i(TAG, "onSuccess: 3");
                                if (!TextUtils.isEmpty(mDbUserBean.getUsername())) {
                                    mAcTvMyName.setText(mDbUserBean.getUsername());
                                }
                                if (!TextUtils.isEmpty(mDbUserBean.getGender()) && mDbUserBean.getGender().equals("female")) {
                                    mSdvMySex.setBackgroundResource(R.drawable.icon_ranklist_female);
                                }
                            }
                        }
                        mAcTvMyScore.setText(result.getResult().getRank().getScore()+"");
                    }
                }
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null) {
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

    public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.ItemViewHolder> {

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(RankListActivity.this).inflate(R.layout.item_ranklist, null));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.mAcTvUserNumber.setText(mRankList.get(position).getIndex() + "");
            if (!TextUtils.isEmpty(mRankList.get(position).getUser().getUsername())) {
                holder.mAcTvUserName.setText(mRankList.get(position).getUser().getUsername());
            }
            if (mRankList.get(position).getUser() != null) {
                if (!TextUtils.isEmpty(mRankList.get(position).getUser().getGender()) && mRankList.get(position).getUser().getGender().equals("female")) {
                    holder.mSdvUserSex.setBackgroundResource(R.drawable.icon_ranklist_female);
                }
            }

            holder.mAcTvUserScore.setText(mRankList.get(position).getScore() + "");
        }

        @Override
        public int getItemCount() {
            return mRankList.size() > 0 ? mRankList.size() : 0;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            AppCompatTextView mAcTvUserNumber;
            AppCompatTextView mAcTvUserName;
            SimpleDraweeView mSdvUserSex;
            AppCompatTextView mAcTvUserScore;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mAcTvUserNumber = itemView.findViewById(R.id.acTv_userNumber);
                mAcTvUserName = itemView.findViewById(R.id.acTv_UserName);
                mSdvUserSex = itemView.findViewById(R.id.sdv_UserSex);
                mAcTvUserScore = itemView.findViewById(R.id.acTv_UserScore);
            }
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    int broken = 0;

    @SuppressLint("InflateParams")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DNGameEventMessage event) {
        switch (event.getEvent()) {
            case SESSION_BROKEN: {
                Log.i(TAG, "onEvent: SESSION_BROKEN");
                broken++;
                if (broken == 7) {
                    AlterDialogBuilder dialogOccupy = new AlterDialogBuilder(this, new DialogTextView(this, "连接异常，请重新登录！"), "退出").hideClose();
                    dialogOccupy.getRootSure().setOnClickListener(v -> {
                        broken = 0;
                        dialogOccupy.dismiss();
                        PublicEventMessage.ExitAccount(this);
                        finish();
                    });
                }
                break;
            }
            case SESSION_OCCUPY: {
                Log.i(TAG, "onEvent: SESSION_OCCUPY");
                AlterDialogBuilder dialogOccupy = new AlterDialogBuilder(this, new DialogTextView(this, "账号异地登录，请重新登录！"), "退出").hideClose();
                dialogOccupy.getRootSure().setOnClickListener(v -> {
                    dialogOccupy.dismiss();
                    PublicEventMessage.ExitAccount(this);
                    finish();
                });
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        if (mRankPresenter != null) {
            mRankPresenter.detachPresenter();
            mRankPresenter = null;
        }

        if (mRankList != null) {
            mRankList.clear();
            mRankList = null;
        }

        if (mAdapter != null) {
            mAdapter = null;
        }

        super.onDestroy();
    }
}
