package com.example.mrye.newsgettogether.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mr.Ye on 2016/12/6.
 */

public class ToastUtil {

    private static Toast mToast;
    public  static void showToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    //在用户按下返回键后不再显示Toast,重写父类Activity的onBackPressed()方法，在该方法里取消Toast的显示
    public static void cancelToast(){
        if (mToast!=null){
            mToast.cancel();
        }
    }
}