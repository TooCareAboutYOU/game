package com.kachat.game.ui.shop.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.events.PublicEventMessage;
import com.kachat.game.libdata.model.CategoryListBean;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.CategoryGoodsPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 遮罩
 */
public class FiguresMaskFragment extends BaseFragment {

    private static final String TAG = "FiguresMask";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private GoldsAdapter mGoldsAdapter;
    private LinearLayoutManager manager;

    private List<CategoryListBean.GoodsBean> mGoodsBeanList;
    private CategoryGoodsPresenter mPresenter;

    public static FiguresMaskFragment newInstance(int index) {
        FiguresMaskFragment goldsFragment = new FiguresMaskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, index);
        goldsFragment.setArguments(bundle);
        return goldsFragment;
    }

    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_shop;
    }

    @Override
    public void onInitView(@NonNull View view) {
        mGoodsBeanList = new ArrayList<>();

        int index = (int) Objects.requireNonNull(getArguments()).get(TAG);
        mPresenter = new CategoryGoodsPresenter(new CategoryListCallBack());
        if (index != 0) {
            mPresenter.attachPresenter(index);
        }

        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mGoldsAdapter = new GoldsAdapter();
        mRecyclerView.setAdapter(mGoldsAdapter);
    }


    public class GoldsAdapter extends RecyclerView.Adapter<GoldsAdapter.ItemViewHolder> {
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_shop, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            if (!TextUtils.isEmpty(mGoodsBeanList.get(position).getImage_url())) {
                holder.mSdvIcon.setImageURI(Uri.parse(mGoodsBeanList.get(position).getImage_url()));
            }
            if (!TextUtils.isEmpty(mGoodsBeanList.get(position).getName())) {
                holder.mAcTvName.setText(mGoodsBeanList.get(position).getName());
            }
            holder.mAcTvAmount.setText("X\t"+mGoodsBeanList.get(position).getAmount());
            holder.mAcTvPrice.setText(mGoodsBeanList.get(position).getPrice()+"");
            holder.mAcTvMark.setText("每日限购10个");
            holder.mSdvSmall.setImageResource(R.drawable.icon_diamond);
            holder.itemView.setOnClickListener(v -> EventBus.getDefault().post(new PublicEventMessage.ShopBuy(mGoodsBeanList.get(position))));

        }

        @Override
        public int getItemCount() {
            return mGoodsBeanList.size() > 0 ? mGoodsBeanList.size() : 0;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.sdv_Icon)
            SimpleDraweeView mSdvIcon;
            @BindView(R.id.acTv_Name)
            AppCompatTextView mAcTvName;
            @BindView(R.id.acTv_Amount)
            AppCompatTextView mAcTvAmount;
            @BindView(R.id.acTv_Price)
            AppCompatTextView mAcTvPrice;
            @BindView(R.id.acTv_Mark)
            AppCompatTextView mAcTvMark;
            @BindView(R.id.sdv_Small)
            SimpleDraweeView mSdvSmall;

            ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    private class CategoryListCallBack implements OnPresenterListeners.OnViewListener<CategoryListBean> {

        @Override
        public void onSuccess(CategoryListBean result) {
            Log.i(TAG, "onSuccess: " + result.toString());
            if (result != null && result.getGoods() != null && result.getGoods().size() > 0) {
                if (mGoodsBeanList != null) {
                    mGoodsBeanList.clear();
                }
                mGoodsBeanList.addAll(result.getGoods());
                mGoldsAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null && !TextUtils.isEmpty(error.getToast())) {
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


    @Override
    public void onDestroy() {

        if (mGoodsBeanList != null) {
            mGoodsBeanList.clear();
            mGoodsBeanList = null;
        }

        if (mGoldsAdapter != null) {
            mGoldsAdapter = null;
        }
        if (manager != null) {
            manager = null;
        }

        if (mPresenter != null) {
            mPresenter = null;
        }

        super.onDestroy();
    }
}
