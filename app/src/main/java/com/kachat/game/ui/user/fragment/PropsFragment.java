package com.kachat.game.ui.user.fragment;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.kachat.game.R;
import com.kachat.game.base.BaseDialogFragment;

import butterknife.BindView;
import cn.lemon.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class PropsFragment extends BaseDialogFragment {

    public PropsFragment(){}
    public static PropsFragment getInstance(){ return PropsFragmentHolder.instance; }
    private static class PropsFragmentHolder{
        @SuppressLint("StaticFieldLeak")
        public static final PropsFragment instance=new PropsFragment();
    }

    @BindView(R.id.acTV_Hint_bg)
    AppCompatTextView tvTitle;
    @BindView(R.id.acIV_Close)
    AppCompatImageView ivClose;
    @BindView(R.id.rv_Prop)
    RecyclerView mRecyclerView;

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);

    }

    @Override
    protected int setResLayoutId() {
        return R.layout.fragment_props;
    }

    @Override
    protected void initView(View view) {
        tvTitle.setText("道具");
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        PropsAdapter adapter=new PropsAdapter();
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0,10,0,0));
        mRecyclerView.setAdapter(adapter);
        ivClose.setOnClickListener(v -> dismiss());
    }

    private class PropsAdapter extends RecyclerView.Adapter<PropsAdapter.ItemViewHolder>{

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_props,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.itemView.setOnClickListener(v -> ToastUtils.showShort("点击了："+(position+1)));
        }

        @Override
        public int getItemCount() {  return 20;  }

        class ItemViewHolder extends RecyclerView.ViewHolder{

            ItemViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
