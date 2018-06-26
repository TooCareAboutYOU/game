package com.kachat.game.utils;

import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kachat.game.application.KaChatApplication;

/**
 *
 */
public abstract class OnCheckNetClickListener implements View.OnClickListener {

    public abstract void onMultiClick(View v);

    @Override
    public void onClick(View v) {

        if (NetworkUtils.isConnected()) {
            if (NetworkUtils.isAvailableByPing()) {
                onMultiClick(v);
            }else {
                ToastUtils.showShort("当前网络不可用！");
            }
        }else {
            ToastUtils.showShort("网络已断开！");
        }

    }
}