package com.kachat.game.utils.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CrashHandlerManager implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandlerManager";

    private static final boolean DEBUG=true;

    private static final String PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ALog";
    private static final String FIFL_NAME="crash";
    private static final String FILE_NAME_SUFFIX=".txt"; // trace

    @SuppressLint("StaticFieldLeak")
    private static CrashHandlerManager sInstance=new CrashHandlerManager();
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    private Context mContext;

    private CrashHandlerManager() { }

    public static CrashHandlerManager getInstance(){ return sInstance; }

    public void init(Context context){
        Log.i(TAG, "init: ");
        mContext=context.getApplicationContext();
        mUncaughtExceptionHandler=Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /**
     * 当程序有未被捕获的异常，系统将会自动调用uncaughtException方法
     * @param thread  为出现未捕获异常的线程
     * @param throwable 为未捕获的异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.i(TAG, "uncaughtException: "+throwable.getMessage());
        try {
            //导出异常信息到SD卡中
            dumpExceptionToSDCard(throwable);
            //上传log信息到服务器
            uploadExceptionToServer();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
        //如果系统提供了默认的异常处理器，则交给系统结束，否则自己结束
        if (mUncaughtExceptionHandler != null) {
            mUncaughtExceptionHandler.uncaughtException(thread,throwable);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable ex) throws PackageManager.NameNotFoundException {
        Log.i(TAG, "dumpExceptionToSDCard: ");
        //如果SD卡不存在或者无法使用，则无法把异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmounted,skip dump exception: ");
                return;
            }
        }

        File dir=new File(PATH);
        if (dir.exists()) {
            dir.delete();
            Log.i(TAG, "dir   delete: ");
        }
        dir.mkdirs();

        long current=System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") String time=new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date(current));
        File file=new File(PATH+"/"+FIFL_NAME+time+FILE_NAME_SUFFIX);
        try {
            Log.i(TAG, "save  : ");
            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.print(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "dump crash info failed: ");
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException{
        Log.i(TAG, "dumpPhoneInfo: ");
        PackageManager pm=mContext.getPackageManager();
        PackageInfo pi=pm.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
        pw.print("\nApp vision：");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);

        //android版本号
        pw.print("\nOS Version：");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.print(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("\nModel：");
        pw.print(Build.MODEL);

        //CPU架构
        pw.print("\nCPU ABI：");
        pw.print(Build.CPU_ABI);
    }

    private void uploadExceptionToServer(){
        Log.i(TAG, "uploadExceptionToServer: ");
    }

}
