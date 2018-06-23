package com.kachat.game.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    
    public abstract int onSetResourceLayout();
    public abstract void onInitView(@NonNull View view);
    public  void onActivityCreate(@Nullable Bundle savedInstanceState){}
    

    private View rootView;
    /** rootView是否初始化标志，防止回调函数在rootView为空的时候触发 */
    private boolean hasCreateView;
    protected boolean isPrepared=false;  //标识位，标识Fragment已经初始化完成
    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) { }
    /**  当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发  */
    private boolean isFragmentVisible;
    protected boolean isVisible; //是否可见
    public void onVisible(){}
    public void onInVisible(){}


    protected View getRootView(){
        if (rootView == null) {
            throw new NullPointerException("the rootView is null");
        }else {
            return rootView;
        }
    }

    /*回退*/
    public void getBackPrevious(){
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }else {
            getActivity().finish();
        }
    }



    private Unbinder mUnbinder;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView=true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }

        if (getUserVisibleHint()) {
            isVisible=true;
            onVisible();
        }else {
            isVisible=false;
            onInVisible();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        hasCreateView=false;
        isFragmentVisible=false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (onSetResourceLayout() == 0) {
            rootView=inflater.inflate(R.layout.layout_empty,container,false);
        }else {
            rootView=inflater.inflate(onSetResourceLayout(),container,false);
        }
        mUnbinder= ButterKnife.bind(this,rootView);
        isPrepared=true;

        onInitView(rootView);
        
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onActivityCreate(savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible=true;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void Logger(@SuppressLint("SupportAnnotationUsage") @StringRes String msg){
        if (Config.isDebug) {
            Logger.i(msg);
        }
    }

    public void Toast(String msg){  ToastUtils.showShort(msg);  }
    public void Toast(int msg){  ToastUtils.showShort(msg);  }
}
