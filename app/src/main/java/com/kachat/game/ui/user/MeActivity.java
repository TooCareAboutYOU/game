package com.kachat.game.ui.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.AppUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.controls.DaoDelete;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.ui.user.fragment.PropsFragment;
import com.kachat.game.ui.user.login.LoginActivity;
import com.kachat.game.utils.manager.ActivityManager;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MeActivity extends BaseActivity {

    private static final String TAG = "MeActivity";

    public static void newInstance(Context context){
        Intent intent =new Intent(context,MeActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar_base)
    Toolbar mToolbar;

    @BindView(R.id.sdv_UserLogo)
    SimpleDraweeView mSdvUserLogo;
    @BindView(R.id.acTv_UserName)
    AppCompatTextView mAcTvUserName;
    @BindView(R.id.sdv_UserSex)
    SimpleDraweeView mSdvUserSex;
    @BindView(R.id.acTv_UserAccountID)
    AppCompatTextView mAcTvUserAccountID;
    
    @BindView(R.id.acTv_UserLevel)
    AppCompatTextView mAcTvUserLevel;
    @BindView(R.id.progressBar_Level)
    ContentLoadingProgressBar mProgressBarLevel;
    
    @BindView(R.id.acTv_UserDiamonds)
    AppCompatTextView mAcTvUserDiamonds;
    @BindView(R.id.acTv_UserSport)
    AppCompatTextView acTvUserCharmport;
    @BindView(R.id.acTv_UserGold)
    AppCompatTextView mAcTvUserGold;
    @BindView(R.id.acTv_UserCharm)
    AppCompatTextView acTvUserCharm;
    
    @BindView(R.id.rv_UserMenu)
    RecyclerView mRvUserMenu;

    private List<MenuBean> mList = null;
    private MenuAdapter mMenuAdapter;
    private GridLayoutManager manager;

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_me;
    }

    @Override
    protected boolean onSetStatusBar() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().navigationBarColor(R.color.black).titleBar(mToolbar).transparentBar().init();
        }
    }

    @Override
    protected void onInitView() {
        getToolBarBack().setOnClickListener(v -> finish());
        getToolbarMenu().setOnClickListener(v -> UserDialog(7,"编辑"));
        mList = new ArrayList<>();
        mList.add(new MenuBean(R.mipmap.ic_launcher, "道具"));
        mList.add(new MenuBean(R.mipmap.ic_launcher, "新手指引"));
        mList.add(new MenuBean(R.mipmap.ic_launcher, "邀请好友"));
        mList.add(new MenuBean(R.mipmap.ic_launcher, "意见反馈"));
        mList.add(new MenuBean(R.mipmap.ic_launcher, "关于我们"));
        mList.add(new MenuBean(R.mipmap.ic_launcher, "用户协议"));
        mList.add(new MenuBean(R.mipmap.ic_launcher, "退出登录"));
        mMenuAdapter = new MenuAdapter();
        manager = new GridLayoutManager(this, 3,GridLayoutManager.VERTICAL,false);
        mRvUserMenu.setLayoutManager(manager);
        mRvUserMenu.setAdapter(mMenuAdapter);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        DbUserBean dbUserBean= DaoQuery.queryUserData();
        if (dbUserBean != null) {
            mAcTvUserName.setText(dbUserBean.getUsername());
            if (dbUserBean.getGender().equals("male")) {
//                mSdvUserSex.setImageResource();
            }else {
//                mSdvUserSex.setImageResource();
            }
            mAcTvUserAccountID.setText(dbUserBean.getNumber()+"");
            mAcTvUserLevel.setText("LV"+dbUserBean.getLevel()+"");
            mProgressBarLevel.setProgress(dbUserBean.getLevel());
            mAcTvUserDiamonds.setText(dbUserBean.getDiamond()+"");
            acTvUserCharmport.setText(dbUserBean.getHp()+"");
            mAcTvUserGold.setText(dbUserBean.getGold()+"");
            acTvUserCharm.setText(dbUserBean.getCharm()+"");
        }

        //根据屏幕方向
//        WindowManager manager = this.getWindowManager();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        manager.getDefaultDisplay().getMetrics(outMetrics);
//        int width = outMetrics.widthPixels;
//        int height = outMetrics.heightPixels;
//
//        SurfaceView surfaceView=new SurfaceView(this);
//        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams((int)(width * 0.22),(int)(height * 0.22));
//        surfaceView.setLayoutParams(params);

    }


    private class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MenuViewHolder(LayoutInflater.from(MeActivity.this).inflate(R.layout.item_user_menu, null));
        }

        @Override
        public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
            holder.sdvIcon.setBackgroundResource(mList.get(position).getMenuIcon());
            holder.tvTitle.setText(mList.get(position).getMenuTitle());
            holder.itemView.setOnClickListener(v -> UserDialog(position,mList.get(position).getMenuTitle()));
        }

        @Override
        public int getItemCount() {   return (mList.size() > 0) ? mList.size() : 0;   }

        class MenuViewHolder extends RecyclerView.ViewHolder {
            AppCompatImageView sdvIcon;
            AppCompatTextView tvTitle;
            MenuViewHolder(View itemView) {
                super(itemView);
                sdvIcon=itemView.findViewById(R.id.sdv_MenuIcon);
                tvTitle=itemView.findViewById(R.id.acTv_MenuTitle);
            }
        }
    }

    private class MenuBean {
        private int menuIcon;
        private String menuTitle;

        MenuBean(int menuIcon, String menuTitle) {
            this.menuIcon = menuIcon;
            this.menuTitle = menuTitle;
        }
        public int getMenuIcon() {   return menuIcon;   }
        public String getMenuTitle() {   return menuTitle;  }
    }

    @SuppressLint("InflateParams")
    private void UserDialog(int type,String title){
        switch (type) {
            case 0:{
                AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(MeActivity.this, new DialogTextView(MeActivity.this,"目前邀请好友功能暂未开放，\n敬请期待！"),"前往");
                dialogBuilder.getRootSure().setOnClickListener(v -> {
                    dialogBuilder.dismiss();
                    PropsFragment.getInstance(title).show(getSupportFragmentManager(),"PropsFragment");
                });

                break;
            }
            case 1:{

                break;
            }
            case 2:{
                new AlterDialogBuilder(MeActivity.this, new DialogTextView(MeActivity.this,"目前邀请好友功能暂未开放，\n敬请期待！"));
                break;
            }
            case 3:{

                break;
            }
            case 4:{
                View containerView=LayoutInflater.from(this).inflate(R.layout.dialog_about_us,null);
                AppCompatTextView version=containerView.findViewById(R.id.acTv_AppVersion);
                version.setText(AppUtils.getAppVersionName());
                AppCompatTextView name=containerView.findViewById(R.id.acTv_AppName);
                name.setText(AppUtils.getAppName());
                AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(MeActivity.this,title,containerView);
                dialogBuilder.getRootSure().setVisibility(View.GONE);

                break;
            }
            case 5:{
                View containerView=LayoutInflater.from(this).inflate(R.layout.dialog_introductions,null);
                AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(this,title,containerView,"同意");
                dialogBuilder.getRootSure().setOnClickListener(v -> dialogBuilder.dismiss());
                break;
            }
            case 6:{  //退出登录
                if (DaoDelete.deleteUserAll()) {
//                    CleanUtils.cleanInternalDbByName(ApplicationHelper.DB_NAME);
                    ActivityManager.getInstance().removeActivity("MainActivity");
                    LoginActivity.newInstance(MeActivity.this);
                    this.finish();
                }

                break;
            }
            case 7:{
                View containerView=LayoutInflater.from(this).inflate(R.layout.dialog_user_edit,null);
                new AlterDialogBuilder(this,title,containerView);
                break;
            }
        }
    }
    
}
