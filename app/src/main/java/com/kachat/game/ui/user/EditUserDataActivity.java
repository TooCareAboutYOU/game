package com.kachat.game.ui.user;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditUserDataActivity extends BaseActivity {

    private static final String TAG = "EditUserDataActivity";

    private DatePickerDialog datePickerDialog;
    private DateFormat format =  DateFormat.getDateTimeInstance();//获取日期格式器对象
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);//获取日期格式器对象
    private int yy = calendar.get(Calendar.YEAR);
    private int mm = calendar.get(Calendar.MONTH);
    private int dd = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_edit_user_data;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {
        findViewById(R.id.ivJump_UserAge).setOnClickListener(v -> SelectDate());
    }


    //日期选择器
    private void SelectDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog=new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                //修改日历控件的年，月，日
                //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
    //            calendar.set(Calendar.YEAR,year);
    //            calendar.set(Calendar.MONTH,month);
    //            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            },yy, mm, dd);
            datePickerDialog.show();
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                Log.i(TAG, "SelectDate: "+year+"\t\t"+(month+1)+"\t\t"+dayOfMonth+"\n"+(calendar.getTime()));

            });
        }else {

        }
    }


    @Override
    protected void onDestroy() {
        if (datePickerDialog != null) {
            datePickerDialog=null;
        }
        super.onDestroy();
    }
}
