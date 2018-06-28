package com.kachat.game.utils.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;

/**
 *
 */
public class AlterDialogBuilder extends AlertDialog.Builder{


    private View rootView=null;
    private SimpleDraweeView ivClose=null;
    private AlertDialog dialog=null;
    private AppCompatTextView mAcTvSure;


    public AlterDialogBuilder(@NonNull Context context,  @NonNull View containerView) {
        super(context,R.style.AlertDialogStyle);
        initView(context,"","",containerView);
    }

    public AlterDialogBuilder(@NonNull Context context, String title, @NonNull View containerView) {
        super(context,R.style.AlertDialogStyle);
        initView(context,title,"",containerView);
    }

    public AlterDialogBuilder(@NonNull Context context, @NonNull View containerView,String actionTxt) {
        super(context,R.style.AlertDialogStyle);
        initView(context,"",actionTxt,containerView);
    }

    public AlterDialogBuilder(@NonNull Context context, String title, @NonNull View containerView, String actionTxt) {
        super(context,R.style.AlertDialogStyle);
        initView(context,title,actionTxt,containerView);
    }

    private void initView(Context context,String title, String actionTxt,View containerView) {
        synchronized (this) {
            @SuppressLint("InflateParams")
            View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_base_bg, null);
            AppCompatTextView bgHintMsg = rootView.findViewById(R.id.acTV_Hint_bg);
            ivClose = rootView.findViewById(R.id.acIV_Close);
            ivClose.setOnClickListener(v -> dismiss());

            mAcTvSure = rootView.findViewById(R.id.acTv_Sure);
            if (!TextUtils.isEmpty(actionTxt)) {
                mAcTvSure.setText(actionTxt);
            }

            FrameLayout childContainer = rootView.findViewById(R.id.fl_Container);
            childContainer.addView(containerView);

            setView(rootView);
            setCancelable(false);
            create();
            dialog = show();
            if (!TextUtils.isEmpty(title)) {
                bgHintMsg.setText(title);
            }

            dialog.setOnDismissListener(dialog1 -> childContainer.removeAllViews());
        }
    }

    public View getRootView(){
        if (rootView == null) {
            ToastUtils.showShort("the ivClose is null");
            return null;
        }
        return rootView;
    }

    public SimpleDraweeView getRootClose(){
        if (ivClose == null) {
            ToastUtils.showShort("the ivClose is null");
            return null;
        }
        return ivClose;
    }

    public AppCompatTextView getRootSure() {
        if (mAcTvSure == null) {
            ToastUtils.showShort("the mAcTvSure is null");
            return null;
        }
        return mAcTvSure;
    }

    public AlterDialogBuilder hideClose(){
        if (ivClose == null) {
            ToastUtils.showShort("the ivClose is null");
            return null;
        }
        ivClose.setVisibility(View.GONE);
        return this;
    }

    public AlterDialogBuilder hideRootSure() {
        if (mAcTvSure == null) {
            ToastUtils.showShort("the mAcTvSure is null");
            return null;
        }
        mAcTvSure.setVisibility(View.GONE);
        return this;
    }

    public AlterDialogBuilder dismiss(){
        if (dialog == null) {
            ToastUtils.showShort("the dialog is null");
            return null;
        }
        dialog.dismiss();
        return this;
    }

}
