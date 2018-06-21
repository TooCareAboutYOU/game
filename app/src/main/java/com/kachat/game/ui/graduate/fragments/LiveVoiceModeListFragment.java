package com.kachat.game.ui.graduate.fragments;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.model.Live2DModel;
import com.kachat.game.utils.widgets.AlterDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.lemon.view.SpaceItemDecoration;

public class LiveVoiceModeListFragment extends BaseFragment {


    private List<Live2DModel> mList;

    public LiveVoiceModeListFragment() { }

    public static LiveVoiceModeListFragment getInstance() {
        return Live2DModeListFragmentHolder.instance;
    }

    private static class Live2DModeListFragmentHolder {
        @SuppressLint("StaticFieldLeak")
        private static final LiveVoiceModeListFragment instance = new LiveVoiceModeListFragment();
    }

    public interface OnSwitchListener {
        void onLiveVoiceEvent(String fileName);
    }

    private OnSwitchListener mSwitchListener=null;
    public void setOnSwitchListener(OnSwitchListener listener) {
        this.mSwitchListener = listener;
    }


    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_live2_mode_list;
    }

    @Override
    public void onInitView(@NonNull View view) {
        RecyclerView rvSwitchBg = view.findViewById(R.id.rv_switch_bg);
        mList = new ArrayList<>();
        mList.add(new Live2DModel("level_1",R.drawable.icon_voice_level_1_default,true));
        mList.add(new Live2DModel("level_2",R.drawable.icon_voice_level_2_default,false));
        mList.add(new Live2DModel("level_3",R.drawable.icon_voice_level_3_default,false));
        mList.add(new Live2DModel("level_4",R.drawable.icon_voice_level_4_default,false));
        mList.add(new Live2DModel("level_0",R.drawable.icon_voice_default,false));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rvSwitchBg.setLayoutManager(manager);
//        mRvSwitchBg.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL));
        rvSwitchBg.addItemDecoration(new SpaceItemDecoration(2,0,2,0));

        rvSwitchBg.setAdapter(new Live2DBgAdapter());
    }

    public class Live2DBgAdapter extends RecyclerView.Adapter<Live2DBgAdapter.BgBackGround> {

        @NonNull
        @Override
        public BgBackGround onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BgBackGround(LayoutInflater.from(getContext()).inflate(R.layout.layout_live2d_item, null));
        }

        @Override
        public void onBindViewHolder(@NonNull BgBackGround holder, int position) {
            holder.mSdvLive2d.setImageResource(mList.get(position).getImg());
            if (mList.get(position).isFlag()) {
                holder.mLayoutCompat.setBackgroundResource(R.drawable.radius_5_light_white);
            }else {
                holder.mLayoutCompat.setBackgroundResource(R.drawable.radius_5);
            }

            holder.itemView.setOnClickListener(v -> {
                if (mSwitchListener != null) {
                    mSwitchListener.onLiveVoiceEvent(mList.get(position).getName());
                }
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        mList.get(i).setFlag(true);
                    } else {
                        mList.get(i).setFlag(false);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return (mList.size() > 0) ? mList.size() : 0;
        }

        class BgBackGround extends RecyclerView.ViewHolder {
            LinearLayoutCompat mLayoutCompat;
            SimpleDraweeView mSdvLive2d;
            BgBackGround(View itemView) {
                super(itemView);
                mLayoutCompat=itemView.findViewById(R.id.ll_Item_Container);
                mSdvLive2d=itemView.findViewById(R.id.sdv_live2d);
            }

        }

    }

    @Override
    public void onDestroyView() {
        if (mSwitchListener != null) {
            mSwitchListener=null;
        }

        if (mList!= null) {
            mList.clear();
            mList=null;
        }

        super.onDestroyView();
    }
}
