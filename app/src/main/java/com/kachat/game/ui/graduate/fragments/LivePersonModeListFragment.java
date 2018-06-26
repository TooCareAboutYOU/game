package com.kachat.game.ui.graduate.fragments;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.events.PublicEventMessage;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.LivesBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.LivesPresenter;
import com.kachat.game.model.Live2DModel;
import com.kachat.game.ui.graduate.GraduateSchoolActivity;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import cn.lemon.view.SpaceItemDecoration;

public class LivePersonModeListFragment extends BaseFragment {
    private static final String TAG = "LivePersonModeList";

    @BindView(R.id.rv_switch_bg)
    RecyclerView mRvSwitchBg;

    private List<Live2DModel> mListLocal;
    private List<LivesBean.ResultBean.ChildLivesBean> mListOnline = null;
    private Live2DBgAdapter mAdapter = null;

    private LivesPresenter mLivesPresenter = null;

    public LivePersonModeListFragment() {
    }

    public static LivePersonModeListFragment getInstance() {
        return Live2DModeListFragmentHolder.instance;
    }

    private static class Live2DModeListFragmentHolder {
        @SuppressLint("StaticFieldLeak")
        private static final LivePersonModeListFragment instance = new LivePersonModeListFragment();
    }

    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_live2_mode_list;
    }

    @Override
    public void onInitView(@NonNull View view) {
//        mRvSwitchBg = view.findViewById(R.id.rv_switch_bg);
        mListLocal = new ArrayList<>();
        mListOnline = new ArrayList<>();
        mLivesPresenter = new LivesPresenter(new MaskCallBack());
        mLivesPresenter.attachPresenter();

        mListLocal.add(new Live2DModel("aLaiKeSi", R.drawable.icon_person_alaikesi_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("haru", R.drawable.icon_person_haru_greete_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("kaPa", R.drawable.icon_person_kapa_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("lanTiYa", R.drawable.icon_person_landiya_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("murahana", R.drawable.icon_person_jianniang_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("neiLin", R.drawable.icon_person_zhishii_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("natori", R.drawable.icon_person_natori_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("tiYaNa", R.drawable.icon_person_tiyana_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("weiKeTa", R.drawable.icon_person_weikeya_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("xingChen", R.drawable.icon_person_xingchen_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("yuLu", R.drawable.icon_person_yulu_default, false, true, 0, 0));
        mListLocal.add(new Live2DModel("yangYan", R.drawable.icon_person_yangyan_default, false, true, 0, 0));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        mRvSwitchBg.setLayoutManager(manager);
        mRvSwitchBg.addItemDecoration(new SpaceItemDecoration(2, 0, 2, 0));
        mAdapter = new Live2DBgAdapter();
        mRvSwitchBg.setAdapter(mAdapter);
    }

    private int LAYOUT_UNCLOCKED=0;
    private int LAYOUT_CLOCKED=1;

    public class Live2DBgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public int getItemViewType(int position) {
            if (mListLocal.get(position).isClocked()) {
                return LAYOUT_CLOCKED;
            }
            return LAYOUT_UNCLOCKED;
        }

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == LAYOUT_UNCLOCKED) {
                return new UNClockViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_figures_mask_unclock, null));
            }else {
                return new ClockViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_figures_mask_clock, null));
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof UNClockViewHolder) {
                ((UNClockViewHolder) holder).mSdvLive2d.setImageResource(mListLocal.get(position).getImg());
                if (mListLocal.get(position).isFlag()) {
                    ((UNClockViewHolder) holder).mLayoutCompat.setBackgroundResource(R.drawable.radius_5_light_white);
                } else {
                    ((UNClockViewHolder) holder).mLayoutCompat.setBackgroundResource(R.drawable.radius_5);
                }
            }else if (holder instanceof ClockViewHolder) {
                ((ClockViewHolder) holder).mSdvLive2d.setImageResource(mListLocal.get(position).getImg());
                if (mListLocal.get(position).isFlag()) {
                    ((ClockViewHolder) holder).mLayoutCompat.setBackgroundResource(R.drawable.radius_5_light_white);
                } else {
                    ((ClockViewHolder) holder).mLayoutCompat.setBackgroundResource(R.drawable.radius_5);
                }
                ((ClockViewHolder) holder).acTvNumbers.setText(mListLocal.get(position).getIve_chip_number() + "/" + mListLocal.get(position).getUnlock_chip());

            }


            holder.itemView.setOnClickListener(v -> {
                if (mListLocal.get(position).isClocked()) {
                    AlterDialogBuilder dialogBuilder = new AlterDialogBuilder(Objects.requireNonNull(getContext()), new DialogTextView(getContext(), "碎片不足!"));
                    dialogBuilder.getRootSure().setOnClickListener(v1 -> dialogBuilder.dismiss());
                } else {
                    EventBus.getDefault().post(new PublicEventMessage.OnGraduateEvent(GraduateSchoolActivity.LAYOUT_FIGURES_MASK,mListLocal.get(position).getName()));
                }

                for (int i = 0; i < mListLocal.size(); i++) {
                    if (i == position) {
                        mListLocal.get(i).setFlag(true);
                    } else {
                        mListLocal.get(i).setFlag(false);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return (mListLocal.size() > 0) ? mListLocal.size() : 0;
        }

        class UNClockViewHolder extends RecyclerView.ViewHolder { //解锁
            LinearLayoutCompat mLayoutCompat;
            SimpleDraweeView mSdvLive2d;

            UNClockViewHolder(View itemView) {
                super(itemView);
                mLayoutCompat = itemView.findViewById(R.id.ll_Item_Container);
                mSdvLive2d = itemView.findViewById(R.id.sdv_live2d);
            }

        }

        class ClockViewHolder extends RecyclerView.ViewHolder {  //未解锁
            LinearLayoutCompat mLayoutCompat;
            SimpleDraweeView mSdvLive2d;
            RelativeLayout rlMask;
            AppCompatTextView acTvNumbers;

            ClockViewHolder(View itemView) {
                super(itemView);
                mLayoutCompat = itemView.findViewById(R.id.ll_Item_Container);
                mSdvLive2d = itemView.findViewById(R.id.sdv_live2d);
                rlMask = itemView.findViewById(R.id.rl_MaskClock);
                acTvNumbers = itemView.findViewById(R.id.acTv_numbers);
            }

        }

    }


    private class MaskCallBack implements OnPresenterListeners.OnViewListener<LivesBean> {
        @Override
        public void onSuccess(LivesBean result) {
            if (result.getResult() != null && result.getResult().getLives() != null && result.getResult().getLives().size() > 0) {
                mListOnline.addAll(result.getResult().getLives());

                int localSize = mListLocal.size();
                int onlineSize = mListOnline.size();
                for (int i = 0; i < localSize; i++) {
                    for (int j = 0; j <onlineSize; j++) {
                        if (mListOnline.get(j) != null) {
                            if (mListOnline.get(j).getLive() != null) {
                                if (!TextUtils.isEmpty(mListOnline.get(j).getLive().getName())) {
                                    if (mListOnline.get(j).getLive().getName().equals(mListLocal.get(i).getName())) { // 本地与线上同时存在，即可解锁
                                        mListLocal.get(i).setClocked(false);
                                    }
                                }
                                mListLocal.get(i).setIve_chip_number(mListOnline.get(j).getLive_chip_number()); //  拥有该遮罩碎片个数
                                mListLocal.get(i).setUnlock_chip(mListOnline.get(j).getLive().getUnlock_chip()); // 解锁遮罩需要的碎片数量
                            }
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null) {
                Logger(error.getToast());
                Toast(error.getToast());
            }
        }

        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Logger(e.getMessage());
                Toast(e.getMessage());
            }
        }
    }

    @Override
    public void onDestroyView() {

        if (mLivesPresenter != null) {
            mLivesPresenter.detachPresenter();
            mLivesPresenter = null;
        }

        if (mListLocal != null) {
            mListLocal.clear();
            mListLocal = null;
        }

        if (mListOnline != null) {
            mListOnline.clear();
            mListOnline = null;
        }

        super.onDestroyView();
    }
}
