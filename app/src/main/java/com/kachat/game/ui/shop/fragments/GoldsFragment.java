package com.kachat.game.ui.shop.fragments;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.utils.GridDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemon.view.SpaceItemDecoration;


public class GoldsFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private GoldsAdapter mGoldsAdapter;
    private GridLayoutManager manager;


    @SuppressLint("StaticFieldLeak")
    private static GoldsFragment instance = new GoldsFragment();
    public GoldsFragment() { }
    public static GoldsFragment getInstance() {  return instance;  }


    @Override
    public int onSetResourceLayout() {  return R.layout.fragment_shop;  }

    @Override
    public void onInitView(@NonNull View view) {
        manager=new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL,false);
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(30, Color.WHITE));
        mRecyclerView.setLayoutManager(manager);
        mGoldsAdapter=new GoldsAdapter();
        mRecyclerView.setAdapter(mGoldsAdapter);

    }

    public class GoldsAdapter extends RecyclerView.Adapter<GoldsAdapter.ItemViewHolder> {

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_shop_golds, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.mSdvBuy.setOnClickListener(v -> Toast.makeText(getContext(), "点击了"+position, Toast.LENGTH_SHORT).show());
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.sdv_Icon)
            SimpleDraweeView mSdvIcon;
            @BindView(R.id.sdv_buy)
            SimpleDraweeView mSdvBuy;
            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mGoldsAdapter != null) {
            mGoldsAdapter=null;
        }
        if (manager != null) {
            manager=null;
        }
        super.onDestroy();
    }
}
