package com.kachat.game.utils.widgets;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kachat.game.ui.shop.ShopActivity;

/**
 *
 */
public class DialogTextView extends AppCompatTextView {


    public DialogTextView(Context context, String msg) {
        super(context);
        init(msg);
    }


    public void init(String msg){
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity= Gravity.CENTER;
        setTextColor(Color.WHITE);
        setTextSize(14f);
        setText(msg);
        setLayoutParams(params);
    }
}
