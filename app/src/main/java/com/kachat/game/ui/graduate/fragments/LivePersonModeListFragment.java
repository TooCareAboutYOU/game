package com.kachat.game.ui.graduate.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class LivePersonModeListFragment extends BaseFragment {
    private static final String TAG = "LivePersonModeList";

    private RecyclerView mRvSwitchBg;
    private List<String> mList;
    private Live2DBgAdapter mAdapter;

    public LivePersonModeListFragment() { }

    public static LivePersonModeListFragment getInstance() {
        return Live2DModeListFragmentHolder.instance;
    }

    private static class Live2DModeListFragmentHolder {
        @SuppressLint("StaticFieldLeak")
        private static final LivePersonModeListFragment instance = new LivePersonModeListFragment();
    }

    public interface OnSwitchListener {
        void onLivePersonEvent(String fileName);
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
        mList.add("battlesister");
        mList.add("child");
        mList.add("elf");
        mList.add("game");
        mList.add("zhishi");
        mList.add("cobori10");
        mList.add("miku_f");
        mList.add("miyo");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        mRvSwitchBg.setLayoutManager(manager);
        mRvSwitchBg.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL));

        mAdapter = new Live2DBgAdapter();
        mRvSwitchBg.setAdapter(mAdapter);
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
                    mSwitchListener.onLivePersonEvent(mList.get(position));
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
            holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        ViewCompat.animate(v).scaleX(1.17f).scaleY(1.17f).translationZ(1).start();
                    }else {
                        ViewCompat.animate(v).scaleX(1.17f).scaleY(1.17f).start();
                        ViewGroup group= (ViewGroup) v.getParent();
                        group.requestLayout();
                        group.invalidate();
                    }
                }
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

    @Override
    public void onDestroyView() {
        if (mAdapter != null) {
            mAdapter=null;
        }
        if (mList!= null) {
            mList.clear();
            mList=null;
        }

        super.onDestroyView();
    }
}
