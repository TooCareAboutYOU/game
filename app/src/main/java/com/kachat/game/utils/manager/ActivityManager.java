package com.kachat.game.utils.manager;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.kachat.game.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ActivityManager {

    private static final String TAG = "ActivityManager";

    private CopyOnWriteArrayList<Activity> mActivityList=new CopyOnWriteArrayList<Activity>();

    private static ActivityManager instance;
    public ActivityManager(){}

    private volatile static ActivityManager singleton;
    public static synchronized ActivityManager getInstance() {
        if (singleton == null) {
             synchronized (ActivityManager.class) {
                  if (singleton == null) {
                      singleton = new ActivityManager();
                  }
             }
         }
        return singleton;
    }

    public void addActivity(Activity activity){
        if (mActivityList == null) {
            mActivityList=new CopyOnWriteArrayList<>();
        }
        if (!mActivityList.contains(activity)){
            mActivityList.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        if (activity != null) {
            if (mActivityList.contains(activity)){
                mActivityList.remove(activity);
            }
            activity.finish();
            mActivityList.remove(activity);
        }
    }

    public void removeActivity(String activityName){
        Log.i(TAG, "个数："+mActivityList.size());
        if(mActivityList.size() > 0) {
            for (Activity activity : mActivityList) {
                synchronized (this) {
                    if (activity.getClass().getSimpleName().equals(activityName)) {
                        removeActivity(activity);
                        activity.finish();
                    }
                }
            }
        }
    }
    
    public void exitSystem(){
        if(mActivityList.size() > 0) {
            for (Activity activity : mActivityList) {
                if (activity.getClass().getSimpleName().equals("SplashActivity") || activity.getClass().getSimpleName().equals("LoginActivity") || activity.getClass().getSimpleName().equals("MainActivity")) {
                    activity.finish();
                    removeActivity(activity);
                    System.exit(0);
                }
            }
        }
    }
}
