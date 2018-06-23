package com.kachat.game.ui.graduate.fragments;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.model.Live2DModel;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.SpaceItemDecoration;

public class LiveBackGroundModeListFragment extends BaseFragment {

    private RecyclerView mRvSwitchBg;
    private List<Live2DModel> mList;

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
        mRvSwitchBg=view.findViewById(R.id.rv_switch_bg);
        mList = new ArrayList<>();
        mList.add(new Live2DModel("bg_1.png", R.drawable.icon_bg_1_default,true,true,0,0));
        mList.add(new Live2DModel("bg_2.png", R.drawable.icon_bg_2_default,false,true,0,0));
        mList.add(new Live2DModel("bg_3.png", R.drawable.icon_bg_3_default,false,true,0,0));
        mList.add(new Live2DModel("bg_4.png", R.drawable.icon_bg_4_default,false,true,0,0));
        mList.add(new Live2DModel("bg_5.png", R.drawable.icon_bg_5_default,false,true,0,0));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        mRvSwitchBg.setLayoutManager(manager);
//        mRvSwitchBg.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL));
        mRvSwitchBg.addItemDecoration(new SpaceItemDecoration(2,0,2,0));
        mRvSwitchBg.setAdapter(new Live2DBgAdapter());
    }

    public class Live2DBgAdapter extends RecyclerView.Adapter<Live2DBgAdapter.BgBackGround> {

        @NonNull
        @Override
        public BgBackGround onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BgBackGround(LayoutInflater.from(getContext()).inflate(R.layout.item_figures_mask_unclock, null));
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
                    mSwitchListener.onLiveBackGroundEvent(mList.get(position).getName());
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
                mSdvLive2d=itemView.findViewById(R.id.sdv_live2d);
                mLayoutCompat=itemView.findViewById(R.id.ll_Item_Container);
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
