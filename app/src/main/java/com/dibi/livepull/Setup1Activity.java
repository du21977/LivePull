package com.dibi.livepull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dibi.livepull.dialog.MyAlertDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Setup1Activity extends AppCompatActivity {

    EditText et_pull0;
    EditText et_pull1;
    EditText et_pull2;
    LinearLayout ll_sb;

    TextView tv_play;
    TextView tv_add;
    static  String pull__0 = "rtmp://119.131.176.169/live/test2";
    static  String pull__1 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    static  String pull__2 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    static  String pull__2444 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    /*
    <EditText
    android:id="@+id/et_pull1"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    android:background="@drawable/app_background"
    android:hint="请输入拉流地址"
    android:textColor="#333333"
    android:paddingLeft="0dp"
    android:layout_marginTop="10dp"
    android:textColorHint="#999999"
    android:textSize="14sp"
    android:layout_marginLeft="10dp"
    android:layout_marginRight="10dp"
    android:text="rtmp://202.69.69.180:443/webcast/bshdlive-pc"
            />
            */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        et_pull0 = (EditText) findViewById(R.id.et_pull0);
        et_pull1 = (EditText) findViewById(R.id.et_pull1);
        et_pull2 = (EditText) findViewById(R.id.et_pull2);
        tv_play = (TextView) findViewById(R.id.tv_play);
        tv_add = (TextView) findViewById(R.id.tv_add);
        ll_sb = (LinearLayout) findViewById(R.id.ll_sb);

        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp2px(50));
       // layoutParams.setMargins(dp2px(10),);
        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pull__0 = et_pull0.getText().toString().trim();
                pull__1 = et_pull1.getText().toString().trim();
                pull__2 = et_pull2.getText().toString().trim();
                startActivity(new Intent(Setup1Activity.this,ThreePull2Activity.class));
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               EditText editText =  new EditText(Setup1Activity.this);

                editText.setBackgroundColor(Color.WHITE);
                editText.setLayoutParams(layoutParams);
                ll_sb.addView(editText);
            }
        });


        okhttpGet();
        okhttpPost();
    }

    private void okhttpPost() {
        OkHttpClient okHttpClient = new OkHttpClient();
        //表单---此处没有添加什么头信息
        FormBody formBody = new FormBody.Builder()
                .add("path","1")
                .add("gid","1")
                .add("styleType","1")
                .build();
        Request request = new Request.Builder().post(formBody).url("http://192.168.0.132:8090/latui/addLatui").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("POST----",""+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("POST---success-",""+response.body().string());
            }
        });
    }

    private void okhttpGet() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.0.132:8090/latui/getLaTuiList?gid=1").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("get----",""+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e("get----",""+response.body().string());
            }
        });
    }


    public  int dp2px( float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

}
