package com.kachat.game.ui.game.fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.kachat.game.R;
import com.kachat.game.base.BaseDialogFragment;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitingFriendsFragment extends BaseDialogFragment {

    public InvitingFriendsFragment(){}
    public static InvitingFriendsFragment getInstance(){
        return InvitingFriendsFragmentHolder.instance;
    }
    private static class InvitingFriendsFragmentHolder{
        @SuppressLint("StaticFieldLeak")
        static final InvitingFriendsFragment instance=new InvitingFriendsFragment();
    }

    @BindView(R.id.acInv_close)
    AppCompatImageView mAcInvClose;
    @BindView(R.id.rl_friends)
    RecyclerView mRlFriends;

    @Override
    protected int setResLayoutId() {
        return R.layout.fragment_inviting_friends;
    }

    @Override
    protected void initView(View view) {

    }

    @OnClick(R.id.acInv_close)
    public void onViewClicked() {
        this.dismiss();
    }
}
