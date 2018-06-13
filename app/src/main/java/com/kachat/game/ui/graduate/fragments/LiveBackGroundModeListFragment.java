package com.kachat.game.ui.graduate.fragments;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.utils.widgets.AlterDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiveBackGroundModeListFragment extends BaseFragment {

    private RecyclerView mRvSwitchBg;
    private List<String> mList;

    public LiveBackGroundModeListFragment() { }

    public static LiveBackGroundModeListFragment getInstance() {
        return LiveBackGroundModeListFragmentHolder.instance;
    }

    private static class LiveBackGroundModeListFragmentHolder {
        @SuppressLint("StaticFieldLeak")
        private static final LiveBackGroundModeListFragment instance = new LiveBackGroundModeListFragment();
    }

    public interface OnSwitchListener {
        void onLiveBackGroundEvent(String fileName);
    }

    private OnSwitchListener mSwitchListener;

    public void setOnSwitchListener(OnSwitchListener listener) {
        this.mSwitchListener = listener;
    }


    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_live2_mode_list;
    }

    @Override
    public void onInitView(@NonNull View view) {
        mRvSwitchBg=view.findViewById(R.id.rv_switch_bg);
        mList = new ArrayList<>();
        mList.add("bg1");
        mList.add("bg2");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        mRvSwitchBg.setLayoutManager(manager);
        mRvSwitchBg.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL));
        mRvSwitchBg.setAdapter(new Live2DBgAdapter());
    }

    public class Live2DBgAdapter extends RecyclerView.Adapter<Live2DBgAdapter.BgBackGround> {

        @NonNull
        @Override
        public BgBackGround onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BgBackGround(LayoutInflater.from(getContext()).inflate(R.layout.layout_live2d_item, null));
        }

        @Override
        public void onBindViewHolder(@NonNull BgBackGround holder, int position) {
//            holder.mSdvLive2d.setImageResource();
            holder.mAvTvTitle.setText(mList.get(position));

            holder.itemView.setOnClickListener(v -> {
                if (mSwitchListener != null) {
                    mSwitchListener.onLiveBackGroundEvent(mList.get(position));
                }
            });
            holder.itemView.setOnLongClickListener(v -> {
                View containerView=LayoutInflater.from(getContext()).inflate(R.layout.dailog_hint_cilck,null);
                AppCompatTextView tvTitle=containerView.findViewById(R.id.acTv_hint_info);
                tvTitle.setText("碎片不足！快去集齐碎片召唤神龙吧！");
                AppCompatTextView acTvText=containerView.findViewById(R.id.acTv_sure);
                acTvText.setText("前往");
                acTvText.setOnClickListener(v1 -> {
                    ToastUtils.showShort("前往");
                });
                new AlterDialogBuilder(Objects.requireNonNull(getActivity()),"提示",containerView);
                return false;
            });
        }

        @Override
        public int getItemCount() {
            return (mList.size() > 0) ? mList.size() : 0;
        }

        class BgBackGround extends RecyclerView.ViewHolder {
            SimpleDraweeView mSdvLive2d;
            AppCompatTextView mAvTvTitle;
            BgBackGround(View itemView) {
                super(itemView);
                mSdvLive2d=itemView.findViewById(R.id.sdv_live2d);
                mAvTvTitle=itemView.findViewById(R.id.avTv_title);
            }

        }
    }

}
