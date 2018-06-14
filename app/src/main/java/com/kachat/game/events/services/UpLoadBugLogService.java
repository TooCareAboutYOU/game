package com.kachat.game.events.services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ZipUtils;
import com.kachat.game.Constant;
import com.kachat.game.libdata.apiServices.FileApi;
import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.PostFileBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.ui.MainActivity;
import com.orhanobut.logger.Logger;

import org.webrtc.ThreadUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observer;
import rx.Subscription;

import static android.provider.CallLog.Calls.TYPE;

/**
 * 上传日志
 */
public class UpLoadBugLogService extends IntentService {

    private static final String TAG = "UpLoadBugLogService";

    private static final String ACTION_FOO = "com.kachat.game.events.services.action.FOO";
    private static final String ACTION_BAZ = "com.kachat.game.events.services.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.kachat.game.events.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.kachat.game.events.services.extra.PARAM2";
    private boolean sSuccessful;

    public UpLoadBugLogService() {
        super("UpLoadBugLogService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Log.i(TAG, "startActionFoo: ");
        Intent intent = new Intent(context, UpLoadBugLogService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Log.i(TAG, "startActionBaz: ");
        Intent intent = new Intent(context, UpLoadBugLogService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {  //子线程
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                Log.i(TAG, "onHandleIntent: ");
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                Log.i(TAG, "onHandleIntent: ");
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    private void handleActionFoo(String param1, String param2) {
        Log.i(TAG, "handleActionFoo: param1==" + param1 + "\t\tparam2==" + param2);
    }

    private void handleActionBaz(String param1, String param2) {
        Log.i(TAG, "handleActionBaz: param1==" + param1 + "\t\tparam2==" + param2);
        initLog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    //    private static String filePath="/sdcard/log.txt";
    private static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ALog/log.txt";
    private static String zipFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ALog/log.zip";
    private static File mFileZIP = null;

    private static boolean initLog() {
        Log.i(TAG, "判断文件夹是否存在，存在则删除");
        if (FileUtils.getFileByPath(zipFilePath) != null) {
            FileUtils.deleteFile(zipFilePath);
        }

        if (FileUtils.createFileByDeleteOldFile(filePath)) { // 判断文件是否存在，存在则在创建之前删除
            Log.i(TAG, "根据文件路径获取文件");
            if (FileUtils.getFileByPath(filePath) != null) { // 根据文件路径获取文件
                Log.i(TAG, "创建成功");
                return true;
            }
        }

        return false;
    }

    public enum DeBugType {
        err, warn, info, debug
    }

    public static boolean writeLog(@IntegerRes int processID, @IntegerRes int threadID, @NonNull DeBugType deBugType, String content) {
        Log.i(TAG, "正在写入.... ");
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Log.d(TAG, "reCreate the file:" + filePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String type = "null";
            switch (deBugType) {
                case err:   type = "err";   break;
                case warn:  type = "warn";   break;
                case debug:  type = "debug";  break;
                case info:   type = "info";  break;
                default:   type = "null";   break;
            }

            String logTXT = "[" + simpleDateFormat.format(date) + "]-[" + type + "]-[" + processID + " : " + threadID + "]--->>>" + content + "\r\n";

            if (FileIOUtils.writeFileFromString(file, logTXT, true)) {
                Log.i(TAG, "writeLog: 写入成功：" + logTXT);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void toZip(String mobile) {
        if (initLog()) {
            try {
                boolean isZip = ZipUtils.zipFile(filePath, zipFilePath);
                if (isZip && FileUtils.getFileByPath(zipFilePath) != null) {
                    mFileZIP = FileUtils.getFileByPath(zipFilePath);
                    Log.i(TAG, "压缩: 上传");
                    uploadFile(mobile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void uploadFile(String mobile) {
        Log.w(TAG, "uploadFile: mobile==" + mobile);
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), mFileZIP);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", mFileZIP.getName(), fileBody);
        Log.i(TAG, "uploadFile: ");
        FileApi.postLogFile(part, mobile, 0, new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
            }
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: " + s);
            }
        });
    }
}
