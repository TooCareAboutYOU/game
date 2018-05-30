package com.kachat.game.ui.game.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class Live2DModeListFragment extends BaseFragment {

    @BindView(R.id.rv_switch_bg)
    RecyclerView mRvSwitchBg;
    private List<String> mList;

    public Live2DModeListFragment(){}
    public static Live2DModeListFragment getInstance(){
        return Live2DModeListFragmentHolder.instance;
    }
    private static class Live2DModeListFragmentHolder{
        @SuppressLint("StaticFieldLeak")
        private static final Live2DModeListFragment instance=new Live2DModeListFragment();
    }

    public interface OnSwitchListener{
        void onEvent(String filePath,String fileName);
    }
    private OnSwitchListener mSwitchListener;
    public void setOnSwitchListener(OnSwitchListener listener){ this.mSwitchListener=listener; }


    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_live2_mode_list;
    }

    @Override
    public void onInitView(@NonNull View view) {
        mList=new ArrayList<>();
        mList.add("cobori10");
        mList.add("miku_f");
        mList.add("miyo");
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        mRvSwitchBg.setLayoutManager(manager);
        mRvSwitchBg.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),DividerItemDecoration.HORIZONTAL));
        mRvSwitchBg.setAdapter(new Live2DBgAdapter());
    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {

    }

    public class Live2DBgAdapter extends RecyclerView.Adapter<Live2DBgAdapter.BgBackGround> {

        @NonNull
        @Override
        public BgBackGround onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BgBackGround(LayoutInflater.from(getContext()).inflate(R.layout.layout_live2d_item,null));
        }

        @Override
        public void onBindViewHolder(@NonNull BgBackGround holder, int position) {
            holder.itemView.setOnClickListener(v -> {
                if (mSwitchListener != null) {
                    mSwitchListener.onEvent("",mList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return (mList.size() > 0) ? mList.size() : 0;
        }

        class BgBackGround extends RecyclerView.ViewHolder{
            BgBackGround(View itemView) {
                super(itemView);
            }
        }
    }

}
