package com.kachat.game.ui;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.kachat.game.libdata.controls.DaoUpdate;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.UpdateUserData;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.UpdateUserPresenter;

/**
 *
 */
public class UpdateUserDB {

    private static final String TAG = "UpdateUserData";

    private static UpdateUserPresenter userPresenter = null;

    public static void update(){
        userPresenter = new UpdateUserPresenter(new OnPresenterListeners.OnViewListener<UpdateUserData>() {
            @Override
            public void onSuccess(UpdateUserData result) {
                Log.i(TAG, "onSuccess: " + result.toString());
                DaoUpdate.updateUser(result.getUsername(),
                        result.getGender(),
                        result.getUid(),
                        result.getAge(),
                        result.getDetail().getLevel(),
                        result.getDetail().getHp(),
                        result.getDetail().getExp_to_level_up(),
                        result.getDetail().getExp(),
                        result.getDetail().getNumber(),
                        result.getDetail().getDiamond(),
                        result.getDetail().getCharm(),
                        result.getDetail().getGold());
            }

            @Override
            public void onFailed(int errorCode, ErrorBean error) {
                Log.i(TAG, "onFailed: " + error.toString());
                ToastUtils.showShort(error.getToast());
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
                ToastUtils.showShort(e.getMessage());
            }
        });
        userPresenter.attachPresenter();
    }

    public static void unload(){
        if (userPresenter != null) {
            userPresenter.detachPresenter();
            userPresenter=null;
        }
    }

}
