package com.dibi.livepull;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dibi.livepull.bean.TokenBean;
import com.dibi.livepull.dialog.LoadingDialog;
import com.dibi.livepull.global.GlobalContants;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();

        token = GlobalContants.SERVER_URL+"/latui/user/checkUser?token=" +szImei;
        okhttpGetToken();
    }


    /**
     * 请求接口----获取token
     */
    private void okhttpGetToken() {
        // LoadingDialog.showDialogForLoading(ViewPagerActivity.this);
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.e("-------", token);
//        Request request = new Request.Builder().url("http://192.168.0.131:8090/latui/getAll").build();
        Request request = new Request.Builder().url(token).build();
//        Request request = new Request.Builder().url("http://192.168.199.131:8090/latui/defaultApi").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("-------", e + "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("-------", result);
                TokenBean tokenBean = new Gson().fromJson(result,TokenBean.class);
                startActivity(new Intent(SplashActivity.this,ViewPagerActivity.class));
                finish();
            }
        });
    }


}
