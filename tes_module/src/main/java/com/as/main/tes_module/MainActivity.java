package com.as.main.tes_module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kachat.game.libdata.model.ErrorBean;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CaptchaPresenterTest";

    private AppCompatEditText mEditText,m;
    CaptchaPresenterTest mPresenterTest ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText=findViewById(R.id.acDt_);
    }

    public void onCheck(View view) {
        mPresenterTest=new CaptchaPresenterTest(new CaptchaCallBack());
        mPresenterTest.attachPresenter(mEditText.getText().toString());
    }
    
    private class CaptchaCallBack implements OnPresenterListenersTest.OnViewListenerTest<GetCaptchaBeanTest>{

        @Override
        public void onSuccess(GetCaptchaBeanTest result) {
            Log.i(TAG, "onSuccess: "+result.toString());
            Toast.makeText(MainActivity.this, result.getCaptcha(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed(int errorCode, ErrorBeanTest error) {
            Log.i(TAG, "onFailed: "+error.toString());
            Toast.makeText(MainActivity.this, error.getToast(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, "onError: "+e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenterTest != null) {
            mPresenterTest.detachPresenter();
            mPresenterTest=null;
        }
        super.onDestroy();
    }
}
