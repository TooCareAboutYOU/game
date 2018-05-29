package com.kachat.game.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kachat.game.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    
    public abstract int onSetResourceLayout();
    public abstract void onInitView();
    public void onSavedInstanceState(@Nullable Bundle savedInstanceState){}
    public abstract void onInitData();
    
    
    
    private View rootView;
    
    protected View getRootView(){
        if (rootView == null) {
            throw new NullPointerException("the rootView is null");
        }else {
            return rootView;
        }
    }
    
    
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        if (savedInstanceState != null) {
            onSavedInstanceState(savedInstanceState);
        }
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

        Log.i(TAG, "onCreateView: ");
        onInitView();
        
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
        onInitData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
