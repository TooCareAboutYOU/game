package com.kachat.game.utils.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.kachat.game.R;

/**
 *
 */
public class AlterDialogBuilder extends AlertDialog.Builder{


    private View bgView=null;
    private AppCompatImageView ivClose=null;
    private AlertDialog dialog=null;

    public AlterDialogBuilder(@NonNull Context context, String title, @NonNull View containerView) {
        super(context,R.style.AlertDialogStyle);
        initView(context,title,containerView);
    }

    private void initView(Context context,String title,View containerView) {
        @SuppressLint("InflateParams")
        View bgView = LayoutInflater.from(context).inflate(R.layout.dialog_base_bg,null);
        AppCompatTextView bgHintMsg=bgView.findViewById(R.id.acTV_Hint_bg);
        ivClose=bgView.findViewById(R.id.acIV_Close);
        FrameLayout childContainer=bgView.findViewById(R.id.fl_Container);
        childContainer.addView(containerView);
        setView(bgView);
        setCancelable(false);
        create();
        dialog=show();
        bgHintMsg.setText(title);
        ivClose.setOnClickListener(v -> dismiss());
        dialog.setOnDismissListener(dialog1 -> childContainer.removeAllViews());
    }

    public View getRootView(){
        if (bgView != null) {
            return bgView;
        }
        return null;
    }

    public AppCompatImageView getRootClose(){
        if (ivClose != null) {
            return ivClose;
        }
        return null;
    }

    public void dismiss(){
        dialog.dismiss();
    }

}
