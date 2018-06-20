package com.kachat.game.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RadioGroup;

import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.UpdateUserData;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.CategoriesPresenter;
import com.kachat.game.libdata.mvp.presenters.UpdateUserPresenter;
import com.kachat.game.ui.shop.fragments.AccessoryExpressionFragment;
import com.kachat.game.ui.shop.fragments.ConsumePropsFragment;
import com.kachat.game.ui.shop.fragments.FiguresMaskFragment;
import com.kachat.game.ui.shop.fragments.GoldsFragment;
import com.kachat.game.ui.user.login.LoginActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private CategoriesPresenter mPresenter;

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
        mToolbarBase.setBackgroundResource(R.color.colorNormal);
        getToolBarBack().setOnClickListener(v -> finish());

        mPresenter = new CategoriesPresenter(new CategoriesCallBack());
        mPresenter.attachPresenter();

        mRgGoodsType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.acRbtn_golds:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container, GoldsFragment.newInstance(1)).commit();
                    break;
                case R.id.acRbtn_Props:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container, ConsumePropsFragment.newInstance(2)).commit();
                    break;
                case R.id.acRbtn_Persons:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container, FiguresMaskFragment.newInstance(3)).commit();
                    break;
                case R.id.acRbtn_Faces:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_Container, AccessoryExpressionFragment.newInstance(4)).commit();
                    break;
            }
        });
    }

    private class CategoriesCallBack implements OnPresenterListeners.OnViewListener<CategoryTypeBean> {
        @Override
        public void onSuccess(CategoryTypeBean result) {
            if (result.getResult() != null && result.getResult().getCategories() != null && result.getResult().getCategories().size() > 0) {
                for (CategoryTypeBean.ResultBean.CategoriesBean categoriesBean : result.getResult().getCategories()) {
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
            if (error != null) {
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
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        update();
    }

    private UpdateUserPresenter userPresenter=null;
    public void update(){
        userPresenter=new UpdateUserPresenter(new OnPresenterListeners.OnViewListener<UpdateUserData>() {
            @Override
            public void onSuccess(UpdateUserData result) {
                Log.i(TAG, "onSuccess: "+result.getResult().toString());
            }

            @Override
            public void onFailed(int errorCode, ErrorBean error) {
                Log.i(TAG, "onFailed: "+error.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e.getMessage());
            }
        });
        userPresenter.attachPresenter();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachPresenter();
        }

        if (userPresenter != null) {
            userPresenter.detachPresenter();
            userPresenter=null;
        }
        super.onDestroy();
    }
}
