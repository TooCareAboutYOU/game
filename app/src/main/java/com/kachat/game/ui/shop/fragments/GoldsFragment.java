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
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.CategoryListBean;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.BuyGoodsPresenter;
import com.kachat.game.libdata.mvp.presenters.CategoryGoodsPresenter;
import com.kachat.game.utils.widgets.AlterDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GoldsFragment extends BaseFragment {

    private static final String TAG = "GoldsFragment";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private GoldsAdapter mGoldsAdapter;
    private LinearLayoutManager manager;

    private List<CategoryListBean.ResultBean.GoodsBean> mGoodsBeanList;
    private CategoryGoodsPresenter mPresenter;
    private BuyGoodsPresenter mBuyGoodsPresenter;

    public static GoldsFragment newInstance(int index) {
        GoldsFragment goldsFragment = new GoldsFragment();
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

        mBuyGoodsPresenter=new BuyGoodsPresenter(new BuyCallBack());

        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mGoldsAdapter = new GoldsAdapter();
        mRecyclerView.setAdapter(mGoldsAdapter);
    }


    public class GoldsAdapter extends RecyclerView.Adapter<GoldsAdapter.ItemViewHolder> {
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_shop_golds, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            if (!TextUtils.isEmpty(mGoodsBeanList.get(position).getImage_url())) {
                holder.mSdvIcon.setImageURI(Uri.parse(mGoodsBeanList.get(position).getImage_url()));
            }
            holder.mAcTvAmount.setText("X\t"+mGoodsBeanList.get(position).getAmount());
            holder.mAcTvPrice.setText(mGoodsBeanList.get(position).getPrice()+"");
            holder.itemView.setOnClickListener(v ->{
                // TODO: 2018/6/20  弹框提示购买
                @SuppressLint("InflateParams")
                View containerView=LayoutInflater.from(getContext()).inflate(R.layout.dailog_hint_cilck,null);
                AppCompatTextView tvTitle=containerView.findViewById(R.id.acTv_hint_info);
                tvTitle.setText("确定要购买？");
                AppCompatTextView acTvText=containerView.findViewById(R.id.acTv_sure);
                acTvText.setText("确定");
                AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(Objects.requireNonNull(getContext()),"提示",containerView);

                acTvText.setOnClickListener(v1 -> {
                    int goodId=mGoodsBeanList.get(position).getGood_id();
                    mBuyGoodsPresenter.attachPresenter(goodId,mGoodsBeanList.get(position).getAmount());
                    dialogBuilder.dismiss();
                });

            });
        }

        @Override
        public int getItemCount() {
            return mGoodsBeanList.size() > 0 ? mGoodsBeanList.size() : 0;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.acTv_Amount)
            AppCompatTextView mAcTvAmount;
            @BindView(R.id.acTv_Price)
            AppCompatTextView mAcTvPrice;
            @BindView(R.id.sdv_Icon)
            SimpleDraweeView mSdvIcon;

            ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    private class CategoryListCallBack implements OnPresenterListeners.OnViewListener<CategoryListBean> {

        @Override
        public void onSuccess(CategoryListBean result) {
            if (result.getResult() != null && result.getResult().getGoods() != null && result.getResult().getGoods().size() > 0) {
                mGoodsBeanList.addAll(result.getResult().getGoods());
                mGoldsAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null) {
                Log.i(TAG, "onFailed: "+error.getToast());
                Toast(error.getToast());
            }
        }

        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Log.i(TAG, "onError: "+e.getMessage());
                Toast(e.getMessage());
            }
        }
    }

    private class BuyCallBack implements OnPresenterListeners.OnViewListener<MessageBean>{

        @Override
        public void onSuccess(MessageBean result) {
            if (result.getResult() != null) {

                @SuppressLint("InflateParams")
                View containerView=LayoutInflater.from(getContext()).inflate(R.layout.dailog_hint_cilck,null);
                AppCompatTextView tvTitle=containerView.findViewById(R.id.acTv_hint_info);
                tvTitle.setText("恭喜,购买成功！");
                AppCompatTextView acTvText=containerView.findViewById(R.id.acTv_sure);
                acTvText.setText("确定");
                AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(Objects.requireNonNull(getContext()),"提示",containerView);
                acTvText.setOnClickListener(v1 -> dialogBuilder.dismiss());
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Log.i(TAG, "onFailed: "+ error.getToast());

            if (errorCode == CodeType.CODE_RESPONSE_WALLET) {

            }
            Toast(error.getToast());
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
            mPresenter.detachPresenter();
            mPresenter = null;
        }

        if (mBuyGoodsPresenter != null) {
            mBuyGoodsPresenter.detachPresenter();
            mBuyGoodsPresenter=null;
        }

        super.onDestroy();
    }
}
