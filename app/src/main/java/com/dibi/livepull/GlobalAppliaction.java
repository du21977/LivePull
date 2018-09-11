package com.dibi.livepull;

import android.app.Application;
import android.content.Context;



/**
 * Created by Admin on 2018/5/29.
 * 用于维护应用全局信息
 */

public class GlobalAppliaction extends Application {

    private static Context mContext;
    //登陆
    public static String token ="";
    public static boolean isLogin = false;



    @Override
    public void onCreate() {
        super.onCreate();

       // x.Ext.init(this);
        mContext = getApplicationContext();

        //LeakCanary ----内存泄露
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);



    }

    public static Context getCurrentContext() {
        return mContext;
    }








}
