package com.kachat.game.ui.bar.fragments;

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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseDialogFragment;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.CharmRankPresenter;
import com.kachat.game.ui.bar.MurphyBarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemon.view.SpaceItemDecoration;

/**
 *
 */
public class CharmRankListFragment extends BaseDialogFragment{

    public static final String TAG = "CharmRankListFragment";

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
    
    private static List<RankingListBean.ResultBean.RanksBean> mRankList = null;
    private CharmRankPresenter mCharmRankPresenter=null;
    private CharmListAdapter mCharmListAdapter=null;
    
    
    @SuppressLint("StaticFieldLeak")
    public static CharmRankListFragment getInstance(){  return CharmRankListFragmentHolder.instance;  }
    private static class CharmRankListFragmentHolder{
        @SuppressLint("StaticFieldLeak")
        private static final CharmRankListFragment instance=new CharmRankListFragment();
    }
    
    @Override
    protected int setResLayoutId() { return R.layout.dialog_base_bg; }

    @SuppressLint("InflateParams")
    @Override
    protected void initView(View view) {
        mAcTVHintBg.setText("魅力");
        mAcTvSure.setVisibility(View.GONE);
        mRelativeLayout.setVisibility(View.VISIBLE);
        mAcIVClose.setOnClickListener(v -> dismiss());
        mSdvUserIndex.setVisibility(View.INVISIBLE);
        mSdvCharm.setVisibility(View.VISIBLE);
        
        mRankList=new ArrayList<>();
        mCharmListAdapter=new CharmListAdapter();
        mCharmRankPresenter=new CharmRankPresenter(new CharmListCallBack());
        mCharmRankPresenter.attachPresenter(0);

        View listView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_recyclerview, null);
        RecyclerView mCharmRecyclerView = listView.findViewById(R.id.rv_Prop);
        RadioGroup charmRg=listView.findViewById(R.id.rg_Charm);
        charmRg.setVisibility(View.VISIBLE);
        mCharmRankPresenter.attachPresenter(0);
        charmRg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.acRbtn_Total: mCharmRankPresenter.attachPresenter(0);break;
                case R.id.acRbtn_Week: mCharmRankPresenter.attachPresenter(1);break;
            }
        });
        
        ViewGroup.LayoutParams params=mCharmRecyclerView.getLayoutParams();
        params.height=1044;
        mCharmRecyclerView.setLayoutParams(params);
        mCharmRecyclerView.addItemDecoration(new SpaceItemDecoration(0, 10, 0, 0));
        mCharmRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCharmRecyclerView.setAdapter(mCharmListAdapter);
        mFlContainer.addView(listView);
    }

    private class CharmListCallBack implements OnPresenterListeners.OnViewListener<RankingListBean>{

        @SuppressLint("SetTextI18n")
        @Override
        public void onSuccess(RankingListBean result) {
            if (result.getResult() != null) {
                if (result.getResult().getCount() > 0 && result.getResult().getRanks() != null && result.getResult().getRanks().size() >0) {
                    Log.i(TAG, "onSuccess: 1"+result.toString());
                    if (mRankList != null) {
                        mRankList.clear();
                    }

                    mRankList.addAll(result.getResult().getRanks());
                    mCharmListAdapter.notifyDataSetChanged();
                }
                if (result.getResult().getRank() != null){
                    Log.i(TAG, "onSuccess: "+result.getResult().getRank());
                    mAcTvMyIndex.setText(result.getResult().getRank().getIndex()+"");
                    if (!TextUtils.isEmpty(result.getResult().getRank().getUsername())) {
                        mAcTvMyName.setText(result.getResult().getRank().getUsername());
                    }

                    if (result.getResult().getRank().getGender().equals("male")) {
                        mSdvMySex.setBackgroundResource(R.drawable.icon_item_exprank_male);
                    }
                    if (result.getResult().getRank().getUser_detail() != null) {
                        mAcTvMyLevel.setText("LV"+result.getResult().getRank().getUser_detail().getLevel());
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

    private int LAYOUT_NO1=0;
    private int LAYOUT_NORMAL=1;
    public class CharmListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
                ((No1ViewHolder)holder).mSdvCharm.setVisibility(View.VISIBLE);
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
                ((NormalViewHolder)holder).mSdvCharm.setVisibility(View.VISIBLE);
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
            @BindView(R.id.sdv_Charm)
            SimpleDraweeView mSdvCharm;


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
            @BindView(R.id.sdv_Charm)
            SimpleDraweeView mSdvCharm;
            NormalViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: ");
        if (mRankList != null) {
            mRankList.clear();
            mRankList=null;
        }
        if (mCharmRankPresenter != null) {
            mCharmRankPresenter.detachPresenter();
            mCharmRankPresenter=null;
        }

        if (mCharmListAdapter != null) {
            mCharmListAdapter=null;
        }
        
        super.onDestroyView();
    }
}
