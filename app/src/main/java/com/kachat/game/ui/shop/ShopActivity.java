package com.kachat.game.ui.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioGroup;

import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.events.PublicEventMessage;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.CategoryListBean;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.UpdateUserData;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.BuyGoodsPresenter;
import com.kachat.game.libdata.mvp.presenters.CategoriesPresenter;
import com.kachat.game.libdata.mvp.presenters.UpdateUserPresenter;
import com.kachat.game.ui.shop.fragments.AccessoryExpressionFragment;
import com.kachat.game.ui.shop.fragments.ConsumePropsFragment;
import com.kachat.game.ui.shop.fragments.FiguresMaskFragment;
import com.kachat.game.ui.shop.fragments.GoldsFragment;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.BindView;

public class ShopActivity extends BaseActivity {

    private static final String TAG = "ShopActivity";

    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;

    @BindView(R.id.rg_GoodsType)
    RadioGroup mRgGoodsType;
    @BindView(R.id.acRbtn_golds)
    AppCompatRadioButton mAcRbtnGolds;
    @BindView(R.id.acRbtn_Props)
    AppCompatRadioButton mAcRbtnProps;
    @BindView(R.id.acRbtn_Persons)
    AppCompatRadioButton mAcRbtnPersons;
    @BindView(R.id.acRbtn_Faces)
    AppCompatRadioButton mAcRbtnFaces;


    private Fragment goldFragment = GoldsFragment.newInstance(1);
    private Fragment propsFragment = ConsumePropsFragment.newInstance(2);
    private Fragment maskFragment = FiguresMaskFragment.newInstance(3);
    private Fragment aeFragment = AccessoryExpressionFragment.newInstance(4);

    private CategoriesPresenter mPresenter=null;
    private BuyGoodsPresenter mBuyGoodsPresenter=null;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, ShopActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_shop;
    }

    @Override
    protected boolean onSetStatusBar() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().titleBar(mToolbarBase).transparentStatusBar().init();
        }
    }

    @Override
    protected void onInitView() {
        getToolBarTitle().setTextColor(Color.BLACK);
        getToolBarBack().setOnClickListener(v -> finish());
//        getToolbarMenu().setImageResource(R.drawable.icon_recharge);
//        getToolbarMenu().setOnClickListener(v -> { });

        mBuyGoodsPresenter=new BuyGoodsPresenter(new BuyCallBack());
        mPresenter = new CategoriesPresenter(new CategoriesCallBack());
        mPresenter.attachPresenter();

        mRgGoodsType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.acRbtn_golds:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container, goldFragment).commit();
                    break;
                case R.id.acRbtn_Props:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container, propsFragment).commit();
                    break;
                case R.id.acRbtn_Persons:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container, maskFragment).commit();
                    break;
                case R.id.acRbtn_Faces:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container, aeFragment).commit();
                    break;
            }
        });
    }

    private class CategoriesCallBack implements OnPresenterListeners.OnViewListener<CategoryTypeBean> {
        @Override
        public void onSuccess(CategoryTypeBean result) {
            if (result.getCategories() != null && result.getCategories().size() > 0) {
                for (CategoryTypeBean.CategoriesBean categoriesBean : result.getCategories()) {
                    switch (categoriesBean.getIndex()) {
                        case 1:
                            mAcRbtnGolds.setText(categoriesBean.getName());
                            break;
                        case 2:
                            mAcRbtnProps.setText(categoriesBean.getName());
                            break;
                        case 3:
                            mAcRbtnPersons.setText(categoriesBean.getName());
                            break;
                        case 4:
                            mAcRbtnFaces.setText(categoriesBean.getName());
                            break;
                    }
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container, GoldsFragment.newInstance(1)).commit();
            }
        }
        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (!TextUtils.isEmpty(error.getToast())) {
                Toast(error.getToast());
            }
        }
        @Override
        public void onError(Throwable e) { if (e != null) { Toast(e.getMessage()); } }
    }

    int broken=0;
    @SuppressLint("InflateParams")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DNGameEventMessage event) {
        switch (event.getEvent()) {
            case SESSION_BROKEN: {
                Log.i(TAG, "onEvent: SESSION_BROKEN");
                broken++;
                if (broken==7) {
                    AlterDialogBuilder dialogOccupy=new AlterDialogBuilder(this, new DialogTextView(this, "连接异常，请重新登录！"),"退出").hideClose();
                    dialogOccupy.getRootSure().setOnClickListener(v -> {
                        broken=0;
                        dialogOccupy.dismiss();
                        PublicEventMessage.ExitAccount(this);
                        finish();
                    });
                }
                break;
            }
            case SESSION_OCCUPY: {
                Log.i(TAG, "onEvent: SESSION_OCCUPY");
                AlterDialogBuilder dialogOccupy=new AlterDialogBuilder(this, new DialogTextView(this, "账号异地登录，请重新登录！"),"退出").hideClose();
                dialogOccupy.getRootSure().setOnClickListener(v -> {
                    dialogOccupy.dismiss();
                    PublicEventMessage.ExitAccount(this);
                    finish();
                });
                break;
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        update();
    }

    private UpdateUserPresenter userPresenter = null;

    public void update() {
        userPresenter = new UpdateUserPresenter(new OnPresenterListeners.OnViewListener<UpdateUserData>() {
            @Override
            public void onSuccess(UpdateUserData result) {
                Log.i(TAG, "onSuccess: " + result.toString());
            }

            @Override
            public void onFailed(int errorCode, ErrorBean error) {
                Log.i(TAG, "onFailed: " + error.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
            }
        });
        userPresenter.attachPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachPresenter();
        }

        if (userPresenter != null) {
            userPresenter.detachPresenter();
            userPresenter = null;
        }

        if (mBuyGoodsPresenter != null) {
            mBuyGoodsPresenter.detachPresenter();
            mBuyGoodsPresenter=null;
        }

        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PublicEventMessage.ShopBuy event) {
        if (event.getGoodsBean() != null) {
            loadBuyGoods(event.getGoodsBean());
        }
    }

    private void loadBuyGoods(CategoryListBean.GoodsBean data){

        AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(Objects.requireNonNull(this),
                new DialogTextView(ShopActivity.this,"确定要购买？"));
        dialogBuilder.getRootSure().setOnClickListener(v1 -> {
            mBuyGoodsPresenter.attachPresenter(data.getGood_id(),1);
            dialogBuilder.dismiss();
        });


    }

    private class BuyCallBack implements OnPresenterListeners.OnViewListener<MessageBean>{

        @Override
        public void onSuccess(MessageBean result) {
            AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(ShopActivity.this,
                    new DialogTextView(ShopActivity.this,"恭喜,购买成功！"));
            dialogBuilder.getRootSure().setOnClickListener(v1 -> {
                dialogBuilder.dismiss();
            });
            
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (!TextUtils.isEmpty(error.getToast())) {
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

}
