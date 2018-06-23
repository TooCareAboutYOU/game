package com.kachat.game.ui.user.fragment;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseDialogFragment;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.PropsBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.PropsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.lemon.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class PropsFragment extends BaseDialogFragment {

    public static final String TAG = "PropsFragment";
    @BindView(R.id.acTV_Hint_bg)
    AppCompatTextView mAcTVHintBg;
    @BindView(R.id.acIV_Close)
    SimpleDraweeView mAcIVClose;
    @BindView(R.id.fl_Container)
    FrameLayout mFlContainer;
    @BindView(R.id.acTv_Sure)
    AppCompatTextView mAcTvSure;

    private static String mTitle="";
    public PropsFragment() {
    }

    public static PropsFragment getInstance(String title) {
        mTitle=title;
        return PropsFragmentHolder.instance;
    }

    private static class PropsFragmentHolder {
        @SuppressLint("StaticFieldLeak")
        public static final PropsFragment instance = new PropsFragment();
    }

    RecyclerView mRecyclerView;

    private PropsPresenter mPresenter = null;
    private List<PropsBean.ResultBean.ChildPropsBean> mPropsBeanList;
    private PropsAdapter adapter = null;


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        Log.i(TAG, "show: ");

    }

    @Override
    protected int setResLayoutId() {
        return R.layout.dialog_base_bg;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initView(View view) {
        mAcTVHintBg.setText(mTitle);
        mAcIVClose.setOnClickListener(v -> dismiss());
        mAcTvSure.setVisibility(View.GONE);


        View listView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_recyclerview,null);
        mRecyclerView=listView.findViewById(R.id.rv_Prop);
        mFlContainer.addView(listView);

        mPropsBeanList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0, 10, 0, 0));
        adapter = new PropsAdapter();
        mRecyclerView.setAdapter(adapter);

        mPresenter = new PropsPresenter(new PropCallBack());
        mPresenter.attachPresenter();
    }

    public class PropsAdapter extends RecyclerView.Adapter<PropsAdapter.ItemViewHolder> {

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_props, null));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            if (mPropsBeanList.get(position).getProp() != null) {
                if (!TextUtils.isEmpty(mPropsBeanList.get(position).getProp().getImage_url())) {
                    holder.mSdvPropIcon.setImageURI(Uri.parse(mPropsBeanList.get(position).getProp().getImage_url()));
                }
            }
            holder.mAcTvPropCount.setText("X" + mPropsBeanList.get(position).getNumber());
        }

        @Override
        public int getItemCount() {
            return mPropsBeanList.size() > 0 ? mPropsBeanList.size() : 0;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            SimpleDraweeView mSdvPropIcon;
            AppCompatTextView mAcTvPropCount;

            ItemViewHolder(View itemView) {
                super(itemView);
                mSdvPropIcon = itemView.findViewById(R.id.sdv_PropIcon);
                mAcTvPropCount = itemView.findViewById(R.id.acTv_PropCount);
            }
        }
    }

    private class PropCallBack implements OnPresenterListeners.OnViewListener<PropsBean> {

        @Override
        public void onSuccess(PropsBean result) {
            Log.i(TAG, "onSuccess: " + result.toString());
            if (result.getResult() != null && result.getResult().getProps() != null && result.getResult().getProps().size() > 0) {
                mPropsBeanList.addAll(result.getResult().getProps());
                adapter.notifyDataSetChanged();
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

        if (mPropsBeanList != null) {
            mPropsBeanList.clear();
            mPropsBeanList = null;
        }

        if (adapter != null) {
            adapter = null;
        }

        if (mPresenter != null) {
            mPresenter.detachPresenter();
            mPresenter = null;
        }
        super.onDestroyView();
    }
}
