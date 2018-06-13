package com.kachat.game.ui.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.GamesBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.GameListPresenter;
import com.kachat.game.ui.user.MeActivity;
import com.kachat.game.utils.widgets.AlterDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.lemon.view.SpaceItemDecoration;

public class GameActivity extends BaseActivity {

    private static final String TAG = "GameActivity";

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, GameActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar_base)
    Toolbar mToolbar;
    @BindView(R.id.sdv_UserLogo)
    SimpleDraweeView mSdvUserLogo;
    @BindView(R.id.acTv_UserName)
    AppCompatTextView mAcTvUserName;
    @BindView(R.id.acTv_UserLevel)
    AppCompatTextView mAcTvUserLevel;
    @BindView(R.id.acTv_UserDiamonds)
    AppCompatTextView mAcTvUserDiamonds;
    @BindView(R.id.acTv_UserSport)
    AppCompatTextView mAcTvUserSport;
    @BindView(R.id.acTv_UserGold)
    AppCompatTextView mAcTvUserGold;
    @BindView(R.id.acTv_UserCharm)
    AppCompatTextView mAcTvUserCharm;
    @BindView(R.id.rv_GameList)
    RecyclerView mRvGameList;

    private List<GamesBean.ResultBean.GameBean> mList = null;
    private GameListAdapter mAdapter;
    private LinearLayoutManager manager;

    private GameListPresenter mPresenter;
    private String GameURL="";

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_game;
    }

    @Override
    protected boolean onSetStatusBar() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().navigationBarColor(R.color.black).titleBar(mToolbar).transparentStatusBar().init();
        }
    }

    @Override
    protected void onInitView() {
        getToolBarBack().setOnClickListener(v -> this.finish());
        mList = new ArrayList<>();
        mAdapter = new GameListAdapter();
        manager = new LinearLayoutManager(this);
        mRvGameList.addItemDecoration(new SpaceItemDecoration(0, 10, 0, 0));
        mRvGameList.setLayoutManager(manager);
        mRvGameList.setAdapter(mAdapter);
        getToolbarMenu().setOnClickListener(v -> MeActivity.newInstance(this));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        DbUserBean dbUserBean = DaoQuery.queryUserData();
        if (dbUserBean != null) {
            mAcTvUserName.setText(dbUserBean.getUsername());
            mAcTvUserLevel.setText("LV" + dbUserBean.getLevel() + "");
            mAcTvUserSport.setText(dbUserBean.getHp() + "");
            mAcTvUserDiamonds.setText(dbUserBean.getDiamond() + "");
            mAcTvUserGold.setText(dbUserBean.getGold() + "");
            mAcTvUserCharm.setText(dbUserBean.getCharm() + "");
        }

        mPresenter = new GameListPresenter(new GameListCallBack());
        mPresenter.attachPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mList.clear();
    }


    private class GameListCallBack implements OnPresenterListeners.OnViewListener<GamesBean> {
        @Override
        public void onSuccess(GamesBean result) {
            if (result != null && result.getResult().getGames() != null && result.getResult().getGames().size() > 0) {
                mList.addAll(result.getResult().getGames());
                mAdapter.notifyDataSetChanged();
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

    private class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder> {

        @NonNull
        @Override
        public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GameViewHolder(LayoutInflater.from(GameActivity.this).inflate(R.layout.item_game_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
            holder.sdvMark.setOnClickListener(v -> {
                View containerView = LayoutInflater.from(GameActivity.this).inflate(R.layout.dialog_game_introductions, null);
                new AlterDialogBuilder(GameActivity.this, "游戏说明", containerView);
            });

            GameURL="http://demo.e3webrtc.com:900"+(position+1);
            holder.itemView.setOnClickListener(v -> loadH5Game());
        }

        @Override
        public int getItemCount() {
            return (mList.size() > 0) ? mList.size() : 0;
        }

        class GameViewHolder extends RecyclerView.ViewHolder {
            SimpleDraweeView sdvImg, sdvMark, sdvStart;

            GameViewHolder(View itemView) {
                super(itemView);
                sdvImg = itemView.findViewById(R.id.sdv_img);
                sdvMark = itemView.findViewById(R.id.sdv_mark);
                sdvStart = itemView.findViewById(R.id.sdv_Start);
            }
        }
    }


    private void loadH5Game() {
        @SuppressLint("InflateParams")
        View containerView = LayoutInflater.from(this).inflate(R.layout.dialog_find_friends_condition, null);
        AlterDialogBuilder parentDialog=new AlterDialogBuilder(this, "寻找你的伙伴", containerView);

        AppCompatTextView acTvSure = containerView.findViewById(R.id.acTv_sure);
        SwitchCompat switch1 = containerView.findViewById(R.id.switch_1);
        SimpleDraweeView sdvCondition1 = containerView.findViewById(R.id.sdv_Condition1);
        SwitchCompat switch2 = containerView.findViewById(R.id.switch_2);
        SimpleDraweeView sdvCondition2 = containerView.findViewById(R.id.sdv_Condition2);
        SwitchCompat switch3 = containerView.findViewById(R.id.switch_3);
        SimpleDraweeView sdvCondition3 = containerView.findViewById(R.id.sdv_Condition3);
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "switch1: " + isChecked);
            if (isChecked) {

            }
        });
        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "switch2: " + isChecked);
            if (isChecked) {

            }
        });
        switch3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "switch3: " + isChecked);
            if (isChecked) {

            }
        });

        sdvCondition1.setOnClickListener(v -> {

        });

        sdvCondition2.setOnClickListener(v -> {
            @SuppressLint("InflateParams")
            View sexView = LayoutInflater.from(this).inflate(R.layout.dialog_matching_sex, null);
            AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(this, "性别匹配", sexView);

            AppCompatTextView sexSure = sexView.findViewById(R.id.acTv_sure);
            sexSure.setText("确定");
            sexSure.setOnClickListener(vSex -> dialogBuilder.dismiss());
            sexSure.setOnClickListener(v1 -> {
                // TODO: 2018/6/11  临时保存选中性别
                dialogBuilder.dismiss();
            });

            RadioGroup rg =  sexView.findViewById(R.id.rg_);
            rg.setOnCheckedChangeListener((group, checkedId) -> {
                // TODO: 2018/6/11 选择性别
                switch (checkedId) {
                    case R.id.acRbtn_Male:{break;}
                    case R.id.acRbtn_Remale:{break;}
                }
            });

        });

        sdvCondition3.setOnClickListener(v -> {

        });

        acTvSure.setOnClickListener(v -> {
            Bundle bundle=new Bundle();
            bundle.putString(GameRoomActivity.Html_Url,GameURL);
            GameRoomActivity.newInstance(GameActivity.this,bundle);
            parentDialog.dismiss();
        });
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachPresenter();
            mPresenter = null;
        }
        if (mList != null) {
            mList.clear();
            mList = null;
        }

        if (manager != null) {
            manager = null;
        }
        if (mAdapter != null) {
            manager = null;
        }

        super.onDestroy();
    }
}
