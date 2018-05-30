package com.kachat.game.ui.user.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserMenuListFragment extends BaseFragment {

    @BindView(R.id.llc_container)
    RecyclerView mRecyclerView;
    private List<MenuBean> mMenuBeanList;
    private MenuListAdapter mAdapter;


    public UserMenuListFragment(){}
    public static UserMenuListFragment getInstance(){  return UserMenuListFragmentHolder.instance;  }
    private static class UserMenuListFragmentHolder{
        @SuppressLint("StaticFieldLeak")
        static final UserMenuListFragment instance=new UserMenuListFragment();
    }

    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_usermenulist;
    }

    @Override
    public void onInitView(@NonNull View view) {
        mMenuBeanList=new ArrayList<>();
        mMenuBeanList.add(new MenuBean("音乐开关",R.mipmap.ic_launcher));
        mMenuBeanList.add(new MenuBean("我的成就",R.mipmap.ic_launcher));
        mMenuBeanList.add(new MenuBean("邀请好友",R.mipmap.ic_launcher));
        mMenuBeanList.add(new MenuBean("意见反馈",R.mipmap.ic_launcher));
        mMenuBeanList.add(new MenuBean("关于我们",R.mipmap.ic_launcher));
        mMenuBeanList.add(new MenuBean("用户协议",R.mipmap.ic_launcher));
        mMenuBeanList.add(new MenuBean("退出登录",R.mipmap.ic_launcher));
        mAdapter=new MenuListAdapter();
        GridLayoutManager manager=new GridLayoutManager(getContext(),3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {

    }

    private class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuItemViewHodel> {
        @NonNull
        @Override
        public MenuItemViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull MenuItemViewHodel holder, int position) {

        }

        @Override
        public int getItemCount() {
            return (mMenuBeanList.size() >0) ? mMenuBeanList.size() : 0;
        }

        class MenuItemViewHodel extends RecyclerView.ViewHolder{
            public MenuItemViewHodel(View itemView) {
                super(itemView);
            }
        }
    }

    private class MenuBean{
        private String menuTitle;
        private int menuIcon;

        public MenuBean(String menuTitle, int menuIcon) {
            this.menuTitle = menuTitle;
            this.menuIcon = menuIcon;
        }

        public String getMenuTitle() {
            return menuTitle;
        }

        public void setMenuTitle(String menuTitle) {
            this.menuTitle = menuTitle;
        }

        public int getMenuIcon() {
            return menuIcon;
        }

        public void setMenuIcon(int menuIcon) {
            this.menuIcon = menuIcon;
        }
    }



}
