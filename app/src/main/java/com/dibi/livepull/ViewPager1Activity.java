package com.dibi.livepull;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dibi.livepull.bean.AllUrlBean;
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

public class ViewPager1Activity extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity" ;
    private AllUrlBean allUrlBean;

    private NoPreloadViewPager vp;
    TextView tv_add_video_url ;
    private List<String> contentList = new ArrayList<String>(); //内容链表
    private List<TestFm> fragmentList = new ArrayList<TestFm>(); //碎片链表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        vp = (NoPreloadViewPager) findViewById(R.id.viewPager);
        vp.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vp.setCurrentItem(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_add_video_url = (TextView) findViewById(R.id.tv_add_video_url);

        tv_add_video_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                niubiAlertDialog();
            }
        });

        okhttpGetAllId1();

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
        LoadingDialog.showDialogForLoading(ViewPager1Activity.this);
        OkHttpClient okHttpClient = new OkHttpClient();

//        Request request = new Request.Builder().url("http://192.168.0.131:8090/latui/getAll").build();
//        Request request = new Request.Builder().url("http://gaolatui.kfcit.com/latui/getAll").build();
//        Request request = new Request.Builder().url("http://192.168.199.131:8090/latui/defaultApi").build();

        Request request = new Request.Builder().url(GlobalContants.GetAll).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e("getAll--",""+e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toast.makeText(ViewPager1Activity.this,"错误"+e,Toast.LENGTH_LONG).show();
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
                        Log.e("ThreePull0831--getAll",result);
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
                        }
                    }
                });
            }
        });

    }

    /**
     * 万能的Dialog
     */
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
                    Toast.makeText(ViewPager1Activity.this,"请输入前三个地址哦",Toast.LENGTH_LONG).show();
                }else {
                    if (et_4.getText().toString().trim().equals("")){
                        Toast.makeText(ViewPager1Activity.this,"添加啦",Toast.LENGTH_LONG).show();
                        String parames = 0 +","+ et_1.getText().toString().trim()  +","+0  +","+1+"?" +0  +","+ et_2.getText().toString().trim()  +","+0  +","+1+"?" +0  +","+ et_3.getText().toString().trim() +"," +0 +"," +1;
                        Log.e(TAG,"添加url-3个--"+parames);
                        okhttpPost(parames);
                    }else {
                        Toast.makeText(ViewPager1Activity.this,"添加啦",Toast.LENGTH_LONG).show();
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
                Toast.makeText(ViewPager1Activity.this,"button----1",Toast.LENGTH_LONG).show();
            }
        });
        niubiDialog.setOnclickListener(R.id.btn_common_2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewPager1Activity.this,"button----2",Toast.LENGTH_LONG).show();
                niubiDialog.dismiss();
            }
        });
    }


    /**
     * 提交----
     * 成功后才开始跳转播放页面
     * @param parames
     */
    private void okhttpPost(String parames) {
        LoadingDialog.showDialogForLoading(ViewPager1Activity.this);
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
                        Toast.makeText(ViewPager1Activity.this,"错误"+e,Toast.LENGTH_LONG).show();
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
        LoadingDialog.showDialogForLoading(ViewPager1Activity.this);
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
                        Toast.makeText(ViewPager1Activity.this,"错误"+e,Toast.LENGTH_LONG).show();
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

                            startActivity(new Intent(ViewPager1Activity.this,ViewPager1Activity.class));
                            finish();
                        }
                    }
                });
            }
        });

    }
}
