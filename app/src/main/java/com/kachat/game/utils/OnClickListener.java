package com.kachat.game.utils;

import android.view.View;

/**
 *
 */
public abstract class OnClickListener implements View.OnClickListener {

    public abstract void onMultiClick(View v);

    @Override
    public void onClick(View v) {
        onMultiClick(v);
    }
}
