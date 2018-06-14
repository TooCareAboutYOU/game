package com.kachat.game.libdata.http;

import android.os.Handler;
import android.util.Log;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 配合 MVP 模式使用
 */

public abstract class BaseModel {

    private CompositeSubscription mCompositeSubscription;
    private Handler mHandler;

    public Handler LocalHandler() {
        return (mHandler == null) ? (new Handler()): mHandler;
    }

    protected CompositeSubscription getCompositeSubscription(){
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription=new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    public void addCompositeSubscription(Subscription subscription){
        if (this.mCompositeSubscription == null){
            this.mCompositeSubscription=new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    public void delCompositeSubscription(){
        if (this.mCompositeSubscription != null && this.mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
        if (mHandler != null) {
            mHandler=null;
        }
    }

}
