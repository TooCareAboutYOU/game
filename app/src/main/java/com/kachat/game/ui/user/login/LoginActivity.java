package com.kachat.game.ui.user.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.controls.DaoDelete;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.ui.MainActivity;
import com.kachat.game.ui.user.login.fragments.CheckMobileFragment;
import com.kachat.game.ui.user.login.fragments.InputPwdFragment;
import com.kachat.game.ui.user.register.RegisterActivity;


public class LoginActivity extends BaseActivity {

    private static FragmentTransaction mTransaction;

    public static void newInstance(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }



    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean onSetStatusBar() {
        return false;
    }


    @SuppressLint("CommitTransaction")
    @Override
    protected void onInitView() {
        DaoDelete.deleteUserAll();
        initCheckAccount();
    }

    public void initCheckAccount(){
        mTransaction=getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.fl_Container, CheckMobileFragment.getInstance());
        mTransaction.commit();
    }

    public void initInputPwd(){
        Log.i("CheckMobileFragment", "initInputPwd: ");
        mTransaction=getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.fl_Container, InputPwdFragment.getInstance());
        mTransaction.commit();
    }

    public void JumpToMain(){
        MainActivity.getInstance(this);
        mTransaction.remove(CheckMobileFragment.getInstance());
        mTransaction.remove(InputPwdFragment.getInstance());
//        mTransaction.commit();
        this.finish();
    }

    public void JumpToRegister(){
        RegisterActivity.newInstance(this);
        mTransaction.remove(CheckMobileFragment.getInstance());
        mTransaction.remove(InputPwdFragment.getInstance());
//        mTransaction.commit();
        this.finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
        if (DaoQuery.queryLoginListSize() == 0) {
            Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
    }



}

