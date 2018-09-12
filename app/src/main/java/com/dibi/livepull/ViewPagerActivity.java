package com.dibi.livepull;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dibi.livepull.Manager.OkHttp3Manager;
import com.dibi.livepull.bean.AllUrlBean;
import com.dibi.livepull.bean.TokenBean;
import com.dibi.livepull.dialog.LoadingDialog;
import com.dibi.livepull.dialog.MyAlertDialog;
import com.dibi.livepull.fragment.FragmentVPAdapter;
import com.dibi.livepull.fragment.TestFm;
import com.dibi.livepull.global.GlobalContants;
import com.dibi.livepull.view.NoPreloadViewPager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewPagerActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity" ;
    private AllUrlBean allUrlBean;

    private NoPreloadViewPager vp;
    TextView tv_add_video_url ;
    private List<String> contentList = new ArrayList<String>(); //内容链表
    private List<TestFm> fragmentList = new ArrayList<TestFm>(); //碎片链表

    private int currentPosition;

    TextView tv_num;
    TextView tv_num1;
    TextView tv_num2;
    TextView tv_num3;
    TextView tv_num4;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_pager);
        vp = (NoPreloadViewPager) findViewById(R.id.viewPager);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);
        tv_num2 = (TextView) findViewById(R.id.tv_num2);
        tv_num3 = (TextView) findViewById(R.id.tv_num3);
        tv_num4 = (TextView) findViewById(R.id.tv_num4);




        vp.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //tv_num.setText(((position+1)/allUrlBean.getData().size())+"");
                Log.e("-------",position+"");
                Log.e("-------",positionOffsetPixels+"");
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                tv_num.setText(((position+1)+"/"+allUrlBean.getData().size())+"");
                vp.setCurrentItem(position);
                if(position==0){
                    tv_num1.setTextColor(Color.parseColor("#ff0000"));
                    tv_num2.setTextColor(Color.parseColor("#ffffff"));
                    tv_num3.setTextColor(Color.parseColor("#ffffff"));
                    tv_num4.setTextColor(Color.parseColor("#ffffff"));
                }else if(position==1){
                    tv_num1.setTextColor(Color.parseColor("#ffffff"));
                    tv_num2.setTextColor(Color.parseColor("#ff0000"));
                    tv_num3.setTextColor(Color.parseColor("#ffffff"));
                    tv_num4.setTextColor(Color.parseColor("#ffffff"));
                }else if(position==2){
                    tv_num1.setTextColor(Color.parseColor("#ffffff"));
                    tv_num2.setTextColor(Color.parseColor("#ffffff"));
                    tv_num3.setTextColor(Color.parseColor("#ff0000"));
                    tv_num4.setTextColor(Color.parseColor("#ffffff"));
                }else if(position==3){
                    tv_num1.setTextColor(Color.parseColor("#ffffff"));
                    tv_num2.setTextColor(Color.parseColor("#ffffff"));
                    tv_num3.setTextColor(Color.parseColor("#ffffff"));
                    tv_num4.setTextColor(Color.parseColor("#ff0000"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_add_video_url = (TextView) findViewById(R.id.tv_add_video_url);


        tv_add_video_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //niubiAlertDialog();
                //Toast.makeText(ViewPagerActivity.this,currentPosition+"",Toast.LENGTH_LONG).show();
                niubiAlertDialog();
            }
        });


        //弹出对话框配置服务器地址
//        niubiAlertDialog11();

        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();

        GlobalContants.Token = szImei;
        token = GlobalContants.SERVER_URL+"/latui/user/checkUser?token=" +GlobalContants.Token;
//                    okhttpGetToken();
        okhttpGetToken1(token);



        tv_num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(0,false);
            }
        });
        tv_num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(1,false);
            }
        });
        tv_num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(2,false);
            }
        });
        tv_num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(3,false);
            }
        });

//        //添加内容
//        contentList.add("页面一");
//        contentList.add("页面二");
//        contentList.add("页面三");
//        contentList.add("页面四");
//        contentList.add("页面五");
//        contentList.add("页面六");
//        //有多少个标题就有多少个碎片，动态添加
//        for(int i=0;i<6;i++){
//            TestFm testFm = new TestFm().newInstance(contentList, i);
//            fragmentList.add(testFm);
//        }
//        vp.setAdapter(new FragmentVPAdapter(getSupportFragmentManager(), (ArrayList<TestFm>) fragmentList));

    }



    /**
     * 请求首页默认接口
     */
    private void okhttpGetAllId1() {
        LoadingDialog.showDialogForLoading(ViewPagerActivity.this);
        OkHttpClient okHttpClient = new OkHttpClient();

//        Request request = new Request.Builder().url("http://192.168.0.131:8090/latui/getAll").build();
//        Request request = new Request.Builder().url("http://gaolatui.kfcit.com/latui/getAll?i=1").build();
//        Request request = new Request.Builder().url("http://192.168.199.131:8090/latui/defaultApi").build();
        Log.e("域名11--",GlobalContants.SERVER_URL+"/latui/getAll?i=1");
        Request request = new Request.Builder().url(GlobalContants.SERVER_URL+"/latui/getAll?i=1").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e("getAll--",""+e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toast.makeText(ViewPagerActivity.this,"错误"+e,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Log.e(TAG,result);
                        allUrlBean = new Gson().fromJson(result,AllUrlBean.class);
                        if (allUrlBean !=null&& allUrlBean.getData().size()>0){
//                            ThreePull0831Activity.MyAdapter myAdapter = new ThreePull0831Activity.MyAdapter();
//                            mRecyclerView.setAdapter(myAdapter);
                            fragmentList = new ArrayList<TestFm>();
                            for(int i=0;i<allUrlBean.getData().size();i++){
                                TestFm testFm = new TestFm().newInstance(result, i);
                                fragmentList.add(testFm);
                            }
                            vp.setAdapter(new FragmentVPAdapter(getSupportFragmentManager(), (ArrayList<TestFm>) fragmentList));

                            if(allUrlBean.getData().size()==1){
                                tv_num1.setVisibility(View.VISIBLE);
                                tv_num2.setVisibility(View.GONE);
                                tv_num3.setVisibility(View.GONE);
                                tv_num4.setVisibility(View.GONE);
                                if(allUrlBean.getData().get(0)!=null&&allUrlBean.getData().get(0).size()>0){
                                    tv_num1.setText(allUrlBean.getData().get(0).get(0).getGName());
                                }

                            }else if(allUrlBean.getData().size()==2){
                                tv_num1.setVisibility(View.VISIBLE);
                                tv_num2.setVisibility(View.VISIBLE);
                                tv_num3.setVisibility(View.GONE);
                                tv_num4.setVisibility(View.GONE);
                                if(allUrlBean.getData().get(0)!=null&&allUrlBean.getData().get(0).size()>0){
                                    tv_num1.setText(allUrlBean.getData().get(0).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(1)!=null&&allUrlBean.getData().get(1).size()>0){
                                    tv_num2.setText(allUrlBean.getData().get(1).get(0).getGName());
                                }

                            }else if(allUrlBean.getData().size()==3){
                                tv_num1.setVisibility(View.VISIBLE);
                                tv_num2.setVisibility(View.VISIBLE);
                                tv_num3.setVisibility(View.VISIBLE);
                                tv_num4.setVisibility(View.GONE);
                                if(allUrlBean.getData().get(0)!=null&&allUrlBean.getData().get(0).size()>0){
                                    tv_num1.setText(allUrlBean.getData().get(0).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(1)!=null&&allUrlBean.getData().get(1).size()>0){
                                    tv_num2.setText(allUrlBean.getData().get(1).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(2)!=null&&allUrlBean.getData().get(2).size()>0){
                                    tv_num3.setText(allUrlBean.getData().get(2).get(0).getGName());
                                }


                            }else if(allUrlBean.getData().size()==4){
                                tv_num1.setVisibility(View.VISIBLE);
                                tv_num2.setVisibility(View.VISIBLE);
                                tv_num3.setVisibility(View.VISIBLE);
                                tv_num4.setVisibility(View.VISIBLE);
                                if(allUrlBean.getData().get(0)!=null&&allUrlBean.getData().get(0).size()>0){
                                    tv_num1.setText(allUrlBean.getData().get(0).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(1)!=null&&allUrlBean.getData().get(1).size()>0){
                                    tv_num2.setText(allUrlBean.getData().get(1).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(2)!=null&&allUrlBean.getData().get(2).size()>0){
                                    tv_num3.setText(allUrlBean.getData().get(2).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(3)!=null&&allUrlBean.getData().get(3).size()>0){
                                    tv_num4.setText(allUrlBean.getData().get(3).get(0).getGName());
                                }

                            }
                        }
                    }
                });
            }
        });

    }

    MyAlertDialog niubiDialog;
    private void niubiAlertDialog() {
        niubiDialog = new MyAlertDialog.Builder(this)
                .setContentView(R.layout.alertdialog_commom)
                .show();
        //.formBottom(true).fullWidth().show();
//        Button button1 = niubiDialog.getView(R.id.btn_common_1);
//        Button button2 = niubiDialog.getView(R.id.btn_common_2);

        final EditText et_1 = niubiDialog.getView(R.id.et_1);
        final EditText et_2 = niubiDialog.getView(R.id.et_2);
        final EditText et_3 = niubiDialog.getView(R.id.et_3);
        final EditText et_4 = niubiDialog.getView(R.id.et_4);

        niubiDialog.setText(R.id.btn_common_1,"哈哈");

        if (allUrlBean !=null&& allUrlBean.getData().size()>0){
            Log.e(TAG,allUrlBean.getData().get(currentPosition).size()+"");
            if(allUrlBean.getData().get(currentPosition).size()==3){
                et_1.setText(allUrlBean.getData().get(currentPosition).get(0).getPath());
                et_2.setText(allUrlBean.getData().get(currentPosition).get(1).getPath());
                et_3.setText(allUrlBean.getData().get(currentPosition).get(2).getPath());
                et_4.setVisibility(View.GONE);
            }else if(allUrlBean.getData().get(currentPosition).size()>=4){
                et_1.setText(allUrlBean.getData().get(currentPosition).get(0).getPath());
                et_2.setText(allUrlBean.getData().get(currentPosition).get(1).getPath());
                et_3.setText(allUrlBean.getData().get(currentPosition).get(2).getPath());
                et_4.setVisibility(View.VISIBLE);
                et_4.setText(allUrlBean.getData().get(currentPosition).get(3).getPath());
            }
        }

    }


    /**
     * 万能的Dialog----弹出输入地址上传服务器
     */

    /*
    MyAlertDialog niubiDialog;
    private void niubiAlertDialog() {
        niubiDialog = new MyAlertDialog.Builder(this)
                .setContentView(R.layout.alertdialog_commom)
                .show();
        //.formBottom(true).fullWidth().show();
//        Button button1 = niubiDialog.getView(R.id.btn_common_1);
//        Button button2 = niubiDialog.getView(R.id.btn_common_2);

        final EditText et_1 = niubiDialog.getView(R.id.et_1);
        final EditText et_2 = niubiDialog.getView(R.id.et_2);
        final EditText et_3 = niubiDialog.getView(R.id.et_3);
        final EditText et_4 = niubiDialog.getView(R.id.et_4);

        niubiDialog.setText(R.id.btn_common_1,"哈哈");


        niubiDialog.setOnclickListener(R.id.tv_dialog_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_1.getText().toString().trim().equals("")||et_2.getText().toString().trim().equals("")||et_2.getText().toString().trim().equals("")){
                    Toast.makeText(ViewPagerActivity.this,"请输入前三个地址哦",Toast.LENGTH_LONG).show();
                }else {
                    if (et_4.getText().toString().trim().equals("")){
                        Toast.makeText(ViewPagerActivity.this,"添加啦",Toast.LENGTH_LONG).show();
                        String parames = 0 +","+ et_1.getText().toString().trim()  +","+0  +","+1+"?" +0  +","+ et_2.getText().toString().trim()  +","+0  +","+1+"?" +0  +","+ et_3.getText().toString().trim() +"," +0 +"," +1;
                        Log.e(TAG,"添加url-3个--"+parames);
                        okhttpPost(parames);
                    }else {
                        Toast.makeText(ViewPagerActivity.this,"添加啦",Toast.LENGTH_LONG).show();
                        String parames = 0 +","+ et_1.getText().toString().trim()  +","+0  +","+1+"?" +0  +","+ et_2.getText().toString().trim()  +","+0  +","+1+"?" +0  +","+ et_3.getText().toString().trim() +"," +0 +"," +1+"?" +0  +","+ et_4.getText().toString().trim() +"," +0 +"," +1;
                        Log.e(TAG,"添加url--4个-"+parames);
                        okhttpPost(parames);
                    }
                }

            }
        });
        niubiDialog.setOnclickListener(R.id.btn_common_1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewPagerActivity.this,"button----1",Toast.LENGTH_LONG).show();
            }
        });
        niubiDialog.setOnclickListener(R.id.btn_common_2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewPagerActivity.this,"button----2",Toast.LENGTH_LONG).show();
                niubiDialog.dismiss();
            }
        });
    }*/


    /**
     * 提交----
     * 成功后才开始跳转播放页面
     * @param parames
     */
    private void okhttpPost(String parames) {
        LoadingDialog.showDialogForLoading(ViewPagerActivity.this);
        Log.e("请求参数拼接--",parames);
        OkHttpClient okHttpClient = new OkHttpClient();
        //表单---此处没有添加什么头信息
        FormBody formBody = new FormBody.Builder()
                .add("paramter",parames)
                .build();

//        Request request = new Request.Builder().post(formBody).url("http://192.168.0.131:8090/latui/addPage").build();
//        Request request = new Request.Builder().post(formBody).url("http://gaolatui.kfcit.com/latui/addPage").build();
        Request request = new Request.Builder().post(formBody).url(GlobalContants.AddPage).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e("ThreePull0831--POST----",""+e);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toast.makeText(ViewPagerActivity.this,"错误"+e,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("ThreePull0831-p-success",""+response.body().string());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        niubiDialog.dismiss();
                        //请求成功后------
                        okhttpGetAll();
                    }
                });

            }
        });
    }

    /**
     * 请求首页默认接口
     */
    private void okhttpGetAll() {
        LoadingDialog.showDialogForLoading(ViewPagerActivity.this);
        OkHttpClient okHttpClient = new OkHttpClient();

//        Request request = new Request.Builder().url("http://192.168.0.131:8090/latui/getAll").build();
        Request request = new Request.Builder().url(" http://gaolatui.kfcit.com/latui/getAll").build();
//        Request request = new Request.Builder().url("http://192.168.199.131:8090/latui/defaultApi").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e("getAll--",""+e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toast.makeText(ViewPagerActivity.this,"错误"+e,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Log.e(TAG,result);
                        allUrlBean = new Gson().fromJson(result,AllUrlBean.class);
                        if (allUrlBean !=null&& allUrlBean.getData().size()>0){
//                            ThreePull0831Activity.MyAdapter myAdapter = new ThreePull0831Activity.MyAdapter();
//                            mRecyclerView.setAdapter(myAdapter);
//                            fragmentList = new ArrayList<TestFm>();
//                            for(int i=0;i<allUrlBean.getData().size();i++){
//                                TestFm testFm = new TestFm().newInstance(result, i);
//                                fragmentList.add(testFm);
//                            }
//                            vp.setAdapter(new FragmentVPAdapter(getSupportFragmentManager(), (ArrayList<TestFm>) fragmentList));
//                            vp.setCurrentItem(0);

                            startActivity(new Intent(ViewPagerActivity.this,ViewPager1Activity.class));
                            finish();
                        }
                    }
                });
            }
        });

    }

    /**
     * 万能的Dialog-----配置域名
     */
    MyAlertDialog niubiDialog1;
    private void niubiAlertDialog11() {
        niubiDialog1 = new MyAlertDialog.Builder(this)
                .setContentView(R.layout.alertdialog_yuming)
                .setCancelable(false)
                .show();
        //.formBottom(true).fullWidth().show();
//        Button button1 = niubiDialog.getView(R.id.btn_common_1);
//        Button button2 = niubiDialog.getView(R.id.btn_common_2);

        final EditText et_yuming = niubiDialog1.getView(R.id.et_yuming);



        niubiDialog1.setOnclickListener(R.id.tv_dialog_yuming, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_yuming.getText().toString().trim().equals("")){
                    Toast.makeText(ViewPagerActivity.this,"域名不能为空哦",Toast.LENGTH_LONG).show();
                }else {
                    niubiDialog1.dismiss();
                    tv_add_video_url.setVisibility(View.VISIBLE);
                    tv_num1.setVisibility(View.VISIBLE);
                    tv_num2.setVisibility(View.VISIBLE);
                    tv_num3.setVisibility(View.VISIBLE);
                    tv_num4.setVisibility(View.VISIBLE);
                    GlobalContants.SERVER_URL = et_yuming.getText().toString().trim();
                    Log.e("域名--",GlobalContants.SERVER_URL);

//                    TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
//                    String szImei = TelephonyMgr.getDeviceId();
//
//                    GlobalContants.Token = szImei;
//                    token = GlobalContants.SERVER_URL+"/latui/user/checkUser?token=" +GlobalContants.Token;
////                    okhttpGetToken();
//                    okhttpGetToken1(token);

                }
            }
        });

    }


    /**
     * 请求接口----获取token
     */
    private void okhttpGetToken() {
        // LoadingDialog.showDialogForLoading(ViewPagerActivity.this);
        LoadingDialog.showDialogForLoading(ViewPagerActivity.this);
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.e("-------", token);
//        Request request = new Request.Builder().url("http://192.168.0.131:8090/latui/getAll").build();
        Request request = new Request.Builder().url(token).build();
//        Request request = new Request.Builder().url("http://192.168.199.131:8090/latui/defaultApi").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e("-------", e + "");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toast.makeText(ViewPagerActivity.this,"错误"+e,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e("-------", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        TokenBean tokenBean = new Gson().fromJson(result,TokenBean.class);
                        okhttpGetAllId1();
                    }
                });

            }
        });
    }

    private void okhttpGetToken1(String token1) {
        LoadingDialog.showDialogForLoading(ViewPagerActivity.this);
        OkHttp3Manager.getAsync(ViewPagerActivity.this, token1, new OkHttp3Manager.DataCallBack() {
            @Override
            public void requestFailure(Request request, final IOException e) {
                Log.e("-------1111-----",e+"");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toast.makeText(ViewPagerActivity.this,"错误"+e,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void requestFailureResCode(final int responsecode) {
                Log.e("-------1111-----",responsecode+"");

                        LoadingDialog.cancelDialogForLoading();
                        Toast.makeText(ViewPagerActivity.this,"错误码"+responsecode,Toast.LENGTH_LONG).show();

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.e("-------1111-----",result);
                LoadingDialog.cancelDialogForLoading();
                TokenBean tokenBean = new Gson().fromJson(result,TokenBean.class);
                okhttpGetAll_1();
            }
        });

    }

    private void okhttpGetAll_1() {
        LoadingDialog.showDialogForLoading(ViewPagerActivity.this);
        OkHttp3Manager.getAsync(ViewPagerActivity.this, GlobalContants.SERVER_URL+"/latui/getAll?i=1", new OkHttp3Manager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.e("-------1111-----",e+"");
                LoadingDialog.cancelDialogForLoading();
                Toast.makeText(ViewPagerActivity.this,"错误"+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestFailureResCode(int responsecode) {
                Log.e("-------1111-----",responsecode+"");
                LoadingDialog.cancelDialogForLoading();
                Toast.makeText(ViewPagerActivity.this,"错误码"+responsecode,Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result1) throws Exception {
                Log.e("-------1111-----",result1);
                final String result = result1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Log.e(TAG,result);
                        allUrlBean = new Gson().fromJson(result,AllUrlBean.class);
                        if (allUrlBean !=null&& allUrlBean.getData().size()>0){
//                            ThreePull0831Activity.MyAdapter myAdapter = new ThreePull0831Activity.MyAdapter();
//                            mRecyclerView.setAdapter(myAdapter);
                            fragmentList = new ArrayList<TestFm>();
                            for(int i=0;i<allUrlBean.getData().size();i++){
                                TestFm testFm = new TestFm().newInstance(result, i);
                                fragmentList.add(testFm);
                            }
                            vp.setAdapter(new FragmentVPAdapter(getSupportFragmentManager(), (ArrayList<TestFm>) fragmentList));

                            if(allUrlBean.getData().size()==1){
                                tv_num1.setVisibility(View.VISIBLE);
                                tv_num2.setVisibility(View.GONE);
                                tv_num3.setVisibility(View.GONE);
                                tv_num4.setVisibility(View.GONE);
                                if(allUrlBean.getData().get(0)!=null&&allUrlBean.getData().get(0).size()>0){
                                    tv_num1.setText(allUrlBean.getData().get(0).get(0).getGName());
                                }

                            }else if(allUrlBean.getData().size()==2){
                                tv_num1.setVisibility(View.VISIBLE);
                                tv_num2.setVisibility(View.VISIBLE);
                                tv_num3.setVisibility(View.GONE);
                                tv_num4.setVisibility(View.GONE);
                                if(allUrlBean.getData().get(0)!=null&&allUrlBean.getData().get(0).size()>0){
                                    tv_num1.setText(allUrlBean.getData().get(0).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(1)!=null&&allUrlBean.getData().get(1).size()>0){
                                    tv_num2.setText(allUrlBean.getData().get(1).get(0).getGName());
                                }

                            }else if(allUrlBean.getData().size()==3){
                                tv_num1.setVisibility(View.VISIBLE);
                                tv_num2.setVisibility(View.VISIBLE);
                                tv_num3.setVisibility(View.VISIBLE);
                                tv_num4.setVisibility(View.GONE);
                                if(allUrlBean.getData().get(0)!=null&&allUrlBean.getData().get(0).size()>0){
                                    tv_num1.setText(allUrlBean.getData().get(0).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(1)!=null&&allUrlBean.getData().get(1).size()>0){
                                    tv_num2.setText(allUrlBean.getData().get(1).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(2)!=null&&allUrlBean.getData().get(2).size()>0){
                                    tv_num3.setText(allUrlBean.getData().get(2).get(0).getGName());
                                }


                            }else if(allUrlBean.getData().size()>=4){
                                tv_num1.setVisibility(View.VISIBLE);
                                tv_num2.setVisibility(View.VISIBLE);
                                tv_num3.setVisibility(View.VISIBLE);
                                tv_num4.setVisibility(View.VISIBLE);
                                if(allUrlBean.getData().get(0)!=null&&allUrlBean.getData().get(0).size()>0){
                                    tv_num1.setText(allUrlBean.getData().get(0).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(1)!=null&&allUrlBean.getData().get(1).size()>0){
                                    tv_num2.setText(allUrlBean.getData().get(1).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(2)!=null&&allUrlBean.getData().get(2).size()>0){
                                    tv_num3.setText(allUrlBean.getData().get(2).get(0).getGName());
                                }
                                if(allUrlBean.getData().get(3)!=null&&allUrlBean.getData().get(3).size()>0){
                                    tv_num4.setText(allUrlBean.getData().get(3).get(0).getGName());
                                }

                            }
                        }
                    }
                });
            }
        });
    }

}
