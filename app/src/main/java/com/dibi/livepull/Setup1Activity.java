package com.dibi.livepull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dibi.livepull.bean.DefaultApiBean;
import com.dibi.livepull.dialog.LoadingDialog;
import com.dibi.livepull.global.GlobalContants;
import com.google.gson.Gson;

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

    EditText edit_1;//第一个东西

   // static  String pull__0 = "rtmp://119.131.176.169/live/test2";
   static  String pull__0 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//    static  String pull__0 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    static  String pull__1 = "rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp";
    static  String pull__2 = "rtmp://202.69.69.180:443/webcast/bshdlive-pc";

    private DefaultApiBean defaultApiBean;

    String parames =null;

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
       // et_pull0 = (EditText) findViewById(R.id.et_pull0);
        et_pull1 = (EditText) findViewById(R.id.et_pull1);
        et_pull2 = (EditText) findViewById(R.id.et_pull2);
        tv_play = (TextView) findViewById(R.id.tv_play);
        tv_add = (TextView) findViewById(R.id.tv_add);
        ll_sb = (LinearLayout) findViewById(R.id.ll_sb);
        edit_1 = (EditText) findViewById(R.id.edit_1);


        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp2px(50));
       // layoutParams.setMargins(dp2px(10),);
        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pull__0 = et_pull0.getText().toString().trim();
//                pull__1 = et_pull1.getText().toString().trim();
//                pull__2 = et_pull2.getText().toString().trim();
//                startActivity(new Intent(Setup1Activity.this,ThreePull2Activity.class));
                ll_sb.getChildCount();
                Log.e("孩子总数",ll_sb.getChildCount()+"");

                if(defaultApiBean!=null){
//                    if(defaultApiBean.getData().size()==3||defaultApiBean.getData().size()==4){
                        parames = defaultApiBean.getData().get(0).getId()+","+edit_1.getText().toString().trim()+","+defaultApiBean.getData().get(0).getGid()+","+defaultApiBean.getData().get(0).getStyleType();
                        if(editText2!=null){
                            parames = parames +"?"+defaultApiBean.getData().get(1).getId()+","+editText2.getText().toString().trim()+","+defaultApiBean.getData().get(1).getGid()+","+defaultApiBean.getData().get(1).getStyleType();
                        }
                        if(editText3!=null){
                            parames = parames +"?"+defaultApiBean.getData().get(2).getId()+","+editText3.getText().toString().trim()+","+defaultApiBean.getData().get(2).getGid()+","+defaultApiBean.getData().get(2).getStyleType();
                        }
                        if(editText4!=null){
                            parames = parames +"?"+0+","+editText4.getText().toString().trim()+","+1+","+1;
                        }

//                    }
                    okhttpPost(parames);
                }



            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               EditText editText =  new EditText(Setup1Activity.this);
//
//                editText.setBackgroundColor(Color.WHITE);
//                editText.setLayoutParams(layoutParams);
//                ll_sb.addView(editText);

               // LinearLayout linearLayout = new LinearLayout(Setup1Activity.this);

//                AddUrlView addUrlView = new AddUrlView(Setup1Activity.this);
//                addUrlView.setLayoutParams(layoutParams);
//                ll_sb.addView(addUrlView);
                if(ll_sb.getChildCount()>3){
                    Toast.makeText(Setup1Activity.this,"不能超过4个哦",Toast.LENGTH_LONG).show();
                    return;
                }
                if(ll_sb.getChildCount()==1){
                    addsecondEdit("");
                    return;
                }
                if(ll_sb.getChildCount()==2){
                    addThirdEdit("");
                    return;
                }
                if(ll_sb.getChildCount()==3){
                    addfourthEdit("");
                    return;
                }
                /*
                final LinearLayout.LayoutParams layoutParams_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams_1.setMargins(dp2px(10),0,dp2px(10),dp2px(10));
                RelativeLayout relativeLayout =new RelativeLayout(Setup1Activity.this);
                relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                EditText editText =  new EditText(Setup1Activity.this);
                TextView textView = new TextView(Setup1Activity.this);
                //EditText视频地址的url参数
                RelativeLayout.LayoutParams layoutParams111 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp2px(50));
                layoutParams111.addRule(RelativeLayout.CENTER_VERTICAL);
                layoutParams111.setMargins(0,0,dp2px(30),0);
                editText.setLayoutParams(layoutParams111);
                editText.setText("rtmp://live.hkstv.hk.lxdns.com/live/hks");
                editText.setTextSize(sp2px(7));
                editText.setBackground(null);
                //删除的布局参数
                RelativeLayout.LayoutParams layoutParams222 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams222.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams222.addRule(RelativeLayout.CENTER_VERTICAL);
                textView.setLayoutParams(layoutParams222);
                textView.setText("删除");

                relativeLayout.addView(editText);
                relativeLayout.addView(textView);

                relativeLayout.setLayoutParams(layoutParams_1);
                ll_sb.addView(relativeLayout);
                */



            }
        });


        okhttpGet();
        //okhttpPost();
    }

    /**
     * 提交----
     * 成功后才开始跳转播放页面
     * @param parames
     */
    private void okhttpPost(String parames) {
        LoadingDialog.showDialogForLoading(Setup1Activity.this);
        Log.e("请求参数拼接--",parames);
        OkHttpClient okHttpClient = new OkHttpClient();
        //表单---此处没有添加什么头信息
        FormBody formBody = new FormBody.Builder()
                .add("paramter",parames)
                .build();

//        Request request = new Request.Builder().post(formBody).url("http://192.168.0.131:8090/latui/secondApi").build();
//        Request request = new Request.Builder().post(formBody).url("http://gaolatui.kfcit.com/latui/secondApi").build();
        Request request = new Request.Builder().post(formBody).url(GlobalContants.Second_Api).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("POST----",""+e);
                LoadingDialog.cancelDialogForLoading();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("POST---success-",""+response.body().string());
                LoadingDialog.cancelDialogForLoading();
//                startActivity(new Intent(Setup1Activity.this,ThreePull0831Activity.class));
                startActivity(new Intent(Setup1Activity.this,ViewPagerActivity.class));
            }
        });
    }

    /**
     * 请求首页默认接口
     */
    private void okhttpGet() {
        LoadingDialog.showDialogForLoading(Setup1Activity.this);
        OkHttpClient okHttpClient = new OkHttpClient();

//        Request request = new Request.Builder().url("http://gaolatui.kfcit.com/latui/defaultApi").build();
//        Request request = new Request.Builder().url("http://192.168.0.131:8090/latui/defaultApi").build();
//        Request request = new Request.Builder().url("http://192.168.199.131:8090/latui/defaultApi").build();
        Request request = new Request.Builder().url(GlobalContants.Default_Api).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("get----",""+e);
                LoadingDialog.cancelDialogForLoading();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        defaultApiBean = new Gson().fromJson(result,DefaultApiBean.class);
                        Log.e("get----",""+result);
                        Log.e("get----",""+ defaultApiBean.getData().size()+"");
                        Log.e("get----",""+ defaultApiBean.getData().get(0).getPath()+"");
                        if(defaultApiBean ==null){
                            return;
                        }
                        if(defaultApiBean.getData().size()==0){

                        }else if(defaultApiBean.getData().size()==1){
                            edit_1.setText(defaultApiBean.getData().get(0).getPath());
                        }else if(defaultApiBean.getData().size()==2){
                            edit_1.setText(defaultApiBean.getData().get(0).getPath());
                            addsecondEdit(defaultApiBean.getData().get(1).getPath());

                        }else if(defaultApiBean.getData().size()==3){
                            edit_1.setText(defaultApiBean.getData().get(0).getPath());
                            addsecondEdit(defaultApiBean.getData().get(1).getPath());
                            addThirdEdit(defaultApiBean.getData().get(2).getPath());


                        }else if(defaultApiBean.getData().size()>3){
                            edit_1.setText(defaultApiBean.getData().get(0).getPath());
                            addsecondEdit(defaultApiBean.getData().get(1).getPath());
                            addThirdEdit(defaultApiBean.getData().get(2).getPath());
                            addfourthEdit(defaultApiBean.getData().get(3).getPath());

                        }
                    }
                });


            }
        });
    }


    public  int dp2px( float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    public  int sp2px( float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }


    EditText editText2;
    TextView textView2;
    public void addsecondEdit(String url){
        final LinearLayout.LayoutParams layoutParams_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_1.setMargins(dp2px(10),0,dp2px(10),dp2px(10));
        final RelativeLayout relativeLayout =new RelativeLayout(Setup1Activity.this);
        relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
         editText2 =  new EditText(Setup1Activity.this);
         textView2 = new TextView(Setup1Activity.this);
        //EditText视频地址的url参数
        RelativeLayout.LayoutParams layoutParams111 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp2px(50));
        layoutParams111.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams111.setMargins(0,0,dp2px(30),0);
        editText2.setLayoutParams(layoutParams111);
        editText2.setText(url);
//        editText2.setTextSize(sp2px(7));
        editText2.setBackground(null);
        //删除的布局参数
        RelativeLayout.LayoutParams layoutParams222 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams222.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams222.addRule(RelativeLayout.CENTER_VERTICAL);
        textView2.setLayoutParams(layoutParams222);
        textView2.setText("删除");
        relativeLayout.addView(editText2);
        relativeLayout.addView(textView2);
        relativeLayout.setLayoutParams(layoutParams_1);
        ll_sb.addView(relativeLayout);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2=null;
                ll_sb.removeView(relativeLayout);
            }
        });

    }

    EditText editText3;
    TextView textView3;
    public void addThirdEdit(String url){
        final LinearLayout.LayoutParams layoutParams_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_1.setMargins(dp2px(10),0,dp2px(10),dp2px(10));
        final RelativeLayout relativeLayout =new RelativeLayout(Setup1Activity.this);
        relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        editText3 =  new EditText(Setup1Activity.this);
        textView3 = new TextView(Setup1Activity.this);
        //EditText视频地址的url参数
        RelativeLayout.LayoutParams layoutParams111 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp2px(50));
        layoutParams111.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams111.setMargins(0,0,dp2px(30),0);
        editText3.setLayoutParams(layoutParams111);
        editText3.setText(url);
        editText3.setTextSize(sp2px(7));
        editText3.setBackground(null);
        //删除的布局参数
        RelativeLayout.LayoutParams layoutParams222 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams222.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams222.addRule(RelativeLayout.CENTER_VERTICAL);
        textView3.setLayoutParams(layoutParams222);
        textView3.setText("删除");
        relativeLayout.addView(editText3);
        relativeLayout.addView(textView3);
        relativeLayout.setLayoutParams(layoutParams_1);
        ll_sb.addView(relativeLayout);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3=null;
                ll_sb.removeView(relativeLayout);
            }
        });
    }

    EditText editText4;
    TextView textView4;
    public void addfourthEdit(String url){
        final LinearLayout.LayoutParams layoutParams_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_1.setMargins(dp2px(10),0,dp2px(10),dp2px(10));
        final RelativeLayout relativeLayout =new RelativeLayout(Setup1Activity.this);
        relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        editText4 =  new EditText(Setup1Activity.this);
        textView4 = new TextView(Setup1Activity.this);
        //EditText视频地址的url参数
        RelativeLayout.LayoutParams layoutParams111 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp2px(50));
        layoutParams111.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams111.setMargins(0,0,dp2px(30),0);
        editText4.setLayoutParams(layoutParams111);
        editText4.setText(url);
        editText4.setTextSize(sp2px(7));
        editText4.setBackground(null);
        //删除的布局参数
        RelativeLayout.LayoutParams layoutParams222 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams222.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams222.addRule(RelativeLayout.CENTER_VERTICAL);
        textView4.setLayoutParams(layoutParams222);
        textView4.setText("删除");

        relativeLayout.addView(editText4);
        relativeLayout.addView(textView4);

        relativeLayout.setLayoutParams(layoutParams_1);
        ll_sb.addView(relativeLayout);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText4=null;
                ll_sb.removeView(relativeLayout);
            }
        });
    }

}
