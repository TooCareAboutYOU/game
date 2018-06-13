package com.kachat.game.ui;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.kachat.game.R;



public class MapFragment extends Fragment {

    public MapFragment(){}
    public static MapFragment newInstance(){
        return MapFragmentHolder.instance;
    }
    private static class MapFragmentHolder{
        @SuppressLint("StaticFieldLeak")
        private static final MapFragment instance=new MapFragment();
    }

    private View rootView=null;

    private WebView webView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_map, container, false);
        initWebView();

        return rootView;
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void initWebView(){
        webView=rootView.findViewById(R.id.webview_map);
        WebSettings webSettings=webView.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setNeedInitialFocus(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);  //允许webview对文件的操作
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setJavaScriptEnabled(true);  //用于js调用Android
        webSettings.setDefaultTextEncodingName("utf-8");  //设置编码方式
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getActivity().getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);

        webView.setWebChromeClient(new ChromeClient());
        webView.loadUrl("file:///android_asset/h5/tower_game/index.html");
//        webView.loadUrl("file:///android_asset/h5/popstar/index.html");
//        webView.loadUrl("file:///android_asset/h5/h5-hextris/index.html");
//        loadHomeMap();
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100){
                //页面加载完成执行的操作
//                String path= "file:///android_asset/img_map.png";
//                String action="javascript:aa('"+path+"')";
//                new AlertDialog.Builder(getActivity())
//                        .setMessage(action)
//                        .show();
//                getActivity().runOnUiThread(() -> webView.loadUrl(action));
            }
            super.onProgressChanged(view, newProgress);
        }
    }


    private void loadHomeMap(){
        webView.addJavascriptInterface(new AndroidAndJsInterface(),"Android");
        webView.loadUrl("file:///android_asset/h5/Home_map.html");
    }
    private class AndroidAndJsInterface {
        /**
         * 该方法将被js调用,用于加载数据
         */
        @JavascriptInterface
        public void showcontacts() {
            // 下面的代码建议在子线程中调用
//            MeActivity.newInstance(getActivity());
//            GameActivity.newInstance(getActivity());
//            GraduateSchoolActivity.newInstance(getActivity());
        }


    }


}
