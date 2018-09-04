package com.dibi.livepull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dibi.livepull.bean.AllUrlBean;
import com.dibi.livepull.bean.DefaultApiBean;
import com.dibi.livepull.dialog.LoadingDialog;
import com.dibi.livepull.dialog.MyAlertDialog;
import com.dibi.livepull.view.MyIjkVideoView;
import com.dibi.livepull.view.OnDoubleClickListener;
import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
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

import static com.dibi.livepull.R.id.ijkplayer;

public class ThreePull0831Activity extends AppCompatActivity {

    TextView tv_add_video_url ;
    private static final String TAG = "ThreePull0831" ;
    RecyclerView mRecyclerView;
    private ViewPagerLayoutManager mLayoutManager;
    private AllUrlBean allUrlBean;

    boolean isisBottom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_pull0831);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new ViewPagerLayoutManager(ThreePull0831Activity.this, OrientationHelper.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        tv_add_video_url = (TextView) findViewById(R.id.tv_add_video_url);

        tv_add_video_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                niubiAlertDialog();
            }
        });

        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                Log.e(TAG, "onInitComplete");
                playVideo(0);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                Log.e(TAG, "释放位置:  " +index);
                releaseVideo(index);

            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                Log.e(TAG, "选中位置:" + position + "  是否是滑动到底部:" + isBottom);

                if(allUrlBean.getData().size()==position-1){

                }else {
                    playVideo(position);
                }
//                    if(!isBottom){
//
//                    }


            }
        });



        okhttpGetAllId1();

    }

    /**
     * 请求首页默认接口
     */
    private void okhttpGetAllId1() {
        LoadingDialog.showDialogForLoading(ThreePull0831Activity.this);
        OkHttpClient okHttpClient = new OkHttpClient();

//        Request request = new Request.Builder().url("http://192.168.0.131:8090/latui/getAll").build();
        Request request = new Request.Builder().url("http://gaolatui.kfcit.com/latui/getAll?i=1").build();
//        Request request = new Request.Builder().url("http://192.168.199.131:8090/latui/defaultApi").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("getAll--",""+e);
                LoadingDialog.cancelDialogForLoading();
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
                            MyAdapter myAdapter = new MyAdapter();
                            mRecyclerView.setAdapter(myAdapter);
                        }
                    }
                });
            }
        });

    }

    private void playVideo(int position) {
        if(allUrlBean.getData().get(position).size()==3){
            final boolean[] isPause3 = {false};
            //获取第一个可见的item
            View itemView = mRecyclerView.getChildAt(0);
            RelativeLayout rl_item_total = itemView.findViewById(R.id.rl_item_total);

            final List<MyIjkVideoView[]> list = new ArrayList<MyIjkVideoView[]>() ;
            //在这里搞事情
            // 1.添加左边的播放器
            final RelativeLayout.LayoutParams   layoutParams_left = new RelativeLayout.LayoutParams(dp2px(400), RelativeLayout.LayoutParams.MATCH_PARENT);
            final MyIjkVideoView[] myIjkVideoView_1 = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull0831Activity.this)};
            myIjkVideoView_1[0].setBackgroundColor(Color.BLUE);
            myIjkVideoView_1[0].setLayoutParams(layoutParams_left);

            //2.添加右上角的播放器
            final RelativeLayout.LayoutParams layoutParams_right_top = new RelativeLayout.LayoutParams(dp2px(200), dp2px(200));
            layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            final MyIjkVideoView[] myIjkVideoView_2 = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull0831Activity.this)};
            myIjkVideoView_2[0].setBackgroundColor(Color.WHITE);
            myIjkVideoView_2[0].setLayoutParams(layoutParams_right_top);



            //3.添加右下角的播放器
            final RelativeLayout.LayoutParams layoutParams_right_bottom = new RelativeLayout.LayoutParams(dp2px(200), dp2px(200));
            layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            final MyIjkVideoView[] myIjkVideoView_3  = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull0831Activity.this)};
            myIjkVideoView_3[0].setBackgroundColor(Color.BLACK);
            myIjkVideoView_3[0].setLayoutParams(layoutParams_right_bottom);

            TextView textView1 = new TextView(ThreePull0831Activity.this);
            TextView textView2 = new TextView(ThreePull0831Activity.this);
            textView1.setLayoutParams(layoutParams_right_top);
            textView2.setLayoutParams(layoutParams_right_bottom);

            rl_item_total.addView(myIjkVideoView_1[0]);
            rl_item_total.addView(myIjkVideoView_2[0]);
            rl_item_total.addView(myIjkVideoView_3[0]);
            rl_item_total.addView(textView1);
            rl_item_total.addView(textView2);

            list.add(myIjkVideoView_1);
            list.add(myIjkVideoView_2);
            list.add(myIjkVideoView_3);


//        String PULL_0 = Setup1Activity.pull__0;
//        String PULL_1 = Setup1Activity.pull__1;
//        String PULL_2 = Setup1Activity.pull__2;
            String PULL_0 = allUrlBean.getData().get(position).get(0).getPath();
            String PULL_1 = allUrlBean.getData().get(position).get(1).getPath();
            String PULL_2 = allUrlBean.getData().get(position).get(2).getPath();
            myIjkVideoView_1[0].setVideoPath(PULL_1);
            myIjkVideoView_2[0].setVideoPath(PULL_0);
            myIjkVideoView_3[0].setVideoPath(PULL_2);

            myIjkVideoView_1[0].start();
            myIjkVideoView_2[0].start();
            myIjkVideoView_3[0].start();

            myIjkVideoView_1[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_1[0].setVolume(20.0f,20.0f);
                }
            });
            myIjkVideoView_2[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_2[0].setVolume(0.0f,0.0f);
                }
            });
            myIjkVideoView_3[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_3[0].setVolume(0.0f,0.0f);
                }
            });
//        myIjkVideoView_1[0].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isPause3[0]){
//                    myIjkVideoView_1[0].onResume();
//                    isPause3[0] = false;
//                    Log.e("haha", "恢复");
//                }else {
//                    myIjkVideoView_1[0].pause();
//                    isPause3[0] = true;
//                    Log.e("haha", "暂停:");
//                }
//            }
//        });

            textView1.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick() {
                    //  Toast.makeText(ThreePull2Activity.this,"双击11",Toast.LENGTH_LONG).show();

                    MyIjkVideoView myIjkVideoView = new MyIjkVideoView(ThreePull0831Activity.this);
                    myIjkVideoView = list.get(0)[0];
                    list.get(0)[0] =list.get(1)[0];
                    list.get(1)[0] = myIjkVideoView;

                    list.get(0)[0].setLayoutParams(layoutParams_left);
                    list.get(1)[0].setLayoutParams(layoutParams_right_top);
                    list.get(0)[0].setVolume(20.0f,20.0f);
                    list.get(1)[0].setVolume(0.0f,0.0f);
                    list.get(2)[0].setVolume(0.0f,0.0f);



                }
            }));

            textView2.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick() {
                    //  Toast.makeText(ThreePull2Activity.this,"双击22",Toast.LENGTH_LONG).show();
                    MyIjkVideoView myIjkVideoView = new MyIjkVideoView(ThreePull0831Activity.this);
                    myIjkVideoView = list.get(0)[0];
                    list.get(0)[0] =list.get(2)[0];
                    list.get(2)[0] = myIjkVideoView;

                    list.get(0)[0].setLayoutParams(layoutParams_left);
                    list.get(2)[0].setLayoutParams(layoutParams_right_bottom);
                    // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 1);
                    list.get(0)[0].setVolume(20.0f,20.0f);
                    list.get(1)[0].setVolume(0.0f,0.0f);
                    list.get(2)[0].setVolume(0.0f,0.0f);
                }
            }));
        }else if(allUrlBean.getData().get(position).size()==4){
            final boolean[] isPause3 = {false};
            //获取第一个可见的item
            View itemView = mRecyclerView.getChildAt(0);
            RelativeLayout rl_item_total = itemView.findViewById(R.id.rl_item_total);

            final List<MyIjkVideoView[]> list = new ArrayList<MyIjkVideoView[]>() ;
            //在这里搞事情
            // 1.添加左边的播放器
            final RelativeLayout.LayoutParams   layoutParams_left = new RelativeLayout.LayoutParams(dp2px(400), RelativeLayout.LayoutParams.MATCH_PARENT);
            final MyIjkVideoView[] myIjkVideoView_1 = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull0831Activity.this)};
            myIjkVideoView_1[0].setBackgroundColor(Color.BLUE);
            myIjkVideoView_1[0].setLayoutParams(layoutParams_left);

            //2.添加右上角的播放器
            final RelativeLayout.LayoutParams layoutParams_right_top = new RelativeLayout.LayoutParams(dp2px(200), dp2px(120));
            layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            final MyIjkVideoView[] myIjkVideoView_2 = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull0831Activity.this)};
//            myIjkVideoView_2[0].setId(R.id.myIjkVideoView_2);
            myIjkVideoView_2[0].setBackgroundColor(Color.WHITE);
            myIjkVideoView_2[0].setLayoutParams(layoutParams_right_top);




            //3.添加右下角的播放器
            final RelativeLayout.LayoutParams layoutParams_right_bottom = new RelativeLayout.LayoutParams(dp2px(200), dp2px(120));
            layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            final MyIjkVideoView[] myIjkVideoView_3  = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull0831Activity.this)};
            myIjkVideoView_3[0].setBackgroundColor(Color.BLACK);
            myIjkVideoView_3[0].setLayoutParams(layoutParams_right_bottom);

            TextView textView1 = new TextView(ThreePull0831Activity.this);
            TextView textView2 = new TextView(ThreePull0831Activity.this);
            textView1.setId(R.id.myIjkVideoView_2);
            textView1.setLayoutParams(layoutParams_right_top);
            textView2.setLayoutParams(layoutParams_right_bottom);



            //中间的
            final RelativeLayout.LayoutParams layoutParams_middle = new RelativeLayout.LayoutParams(dp2px(200), dp2px(120));
            layoutParams_middle.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams_middle.addRule(RelativeLayout.BELOW,R.id.myIjkVideoView_2);

            final MyIjkVideoView[] myIjkVideoView_middle = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull0831Activity.this)};
            myIjkVideoView_middle[0].setBackgroundColor(Color.GREEN);
            myIjkVideoView_middle[0].setLayoutParams(layoutParams_middle);

            TextView textView_middle = new TextView(ThreePull0831Activity.this);
            textView_middle.setLayoutParams(layoutParams_middle);


            rl_item_total.addView(myIjkVideoView_1[0]);
            rl_item_total.addView(myIjkVideoView_2[0]);
            rl_item_total.addView(myIjkVideoView_3[0]);
            rl_item_total.addView(myIjkVideoView_middle[0]);
            rl_item_total.addView(textView1);
            rl_item_total.addView(textView2);
            rl_item_total.addView(textView_middle);

            list.add(myIjkVideoView_1);
            list.add(myIjkVideoView_2);
            list.add(myIjkVideoView_3);
            list.add(myIjkVideoView_middle);


//        String PULL_0 = Setup1Activity.pull__0;
//        String PULL_1 = Setup1Activity.pull__1;
//        String PULL_2 = Setup1Activity.pull__2;
            String PULL_0 = allUrlBean.getData().get(position).get(0).getPath();
            String PULL_1 = allUrlBean.getData().get(position).get(1).getPath();
            String PULL_2 = allUrlBean.getData().get(position).get(2).getPath();
            String PULL_3 = allUrlBean.getData().get(position).get(3).getPath();
            myIjkVideoView_1[0].setVideoPath(PULL_1);
            myIjkVideoView_2[0].setVideoPath(PULL_0);
            myIjkVideoView_3[0].setVideoPath(PULL_2);
            myIjkVideoView_middle[0].setVideoPath(PULL_3);

            myIjkVideoView_1[0].start();
            myIjkVideoView_2[0].start();
            myIjkVideoView_3[0].start();
            myIjkVideoView_middle[0].start();


            myIjkVideoView_1[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_1[0].setVolume(20.0f,20.0f);
                }
            });
            myIjkVideoView_2[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_2[0].setVolume(0.0f,0.0f);
                }
            });
            myIjkVideoView_3[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_3[0].setVolume(0.0f,0.0f);
                }
            });
            myIjkVideoView_middle[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_middle[0].setVolume(0.0f,0.0f);
                }
            });


//        myIjkVideoView_1[0].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isPause3[0]){
//                    myIjkVideoView_1[0].onResume();
//                    isPause3[0] = false;
//                    Log.e("haha", "恢复");
//                }else {
//                    myIjkVideoView_1[0].pause();
//                    isPause3[0] = true;
//                    Log.e("haha", "暂停:");
//                }
//            }
//        });

            textView1.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick() {
                    //  Toast.makeText(ThreePull2Activity.this,"双击11",Toast.LENGTH_LONG).show();

                    MyIjkVideoView myIjkVideoView = new MyIjkVideoView(ThreePull0831Activity.this);
                    myIjkVideoView = list.get(0)[0];
                    list.get(0)[0] =list.get(1)[0];
                    list.get(1)[0] = myIjkVideoView;

                    list.get(0)[0].setLayoutParams(layoutParams_left);
                    list.get(1)[0].setLayoutParams(layoutParams_right_top);
                    list.get(0)[0].setVolume(20.0f,20.0f);
                    list.get(1)[0].setVolume(0.0f,0.0f);
                    list.get(2)[0].setVolume(0.0f,0.0f);
                    list.get(3)[0].setVolume(0.0f,0.0f);



                }
            }));

            textView2.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick() {
                    //  Toast.makeText(ThreePull2Activity.this,"双击22",Toast.LENGTH_LONG).show();
                    MyIjkVideoView myIjkVideoView = new MyIjkVideoView(ThreePull0831Activity.this);
                    myIjkVideoView = list.get(0)[0];
                    list.get(0)[0] =list.get(2)[0];
                    list.get(2)[0] = myIjkVideoView;

                    list.get(0)[0].setLayoutParams(layoutParams_left);
                    list.get(2)[0].setLayoutParams(layoutParams_right_bottom);
                    // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 1);
                    list.get(0)[0].setVolume(20.0f,20.0f);
                    list.get(1)[0].setVolume(0.0f,0.0f);
                    list.get(2)[0].setVolume(0.0f,0.0f);
                    list.get(3)[0].setVolume(0.0f,0.0f);
                }
            }));

            textView_middle.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick() {
                    //  Toast.makeText(ThreePull2Activity.this,"双击22",Toast.LENGTH_LONG).show();
                    MyIjkVideoView myIjkVideoView = new MyIjkVideoView(ThreePull0831Activity.this);
                    myIjkVideoView = list.get(0)[0];
                    list.get(0)[0] =list.get(3)[0];
                    list.get(3)[0] = myIjkVideoView;

                    list.get(0)[0].setLayoutParams(layoutParams_left);
                    list.get(3)[0].setLayoutParams(layoutParams_middle);
                    // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 1);
                    list.get(0)[0].setVolume(20.0f,20.0f);
                    list.get(1)[0].setVolume(0.0f,0.0f);
                    list.get(2)[0].setVolume(0.0f,0.0f);
                    list.get(3)[0].setVolume(0.0f,0.0f);
                }
            }));
        }


    }

    private void releaseVideo(int index) {
        View itemView = mRecyclerView.getChildAt(index);
        RelativeLayout rl_item_total = itemView.findViewById(R.id.rl_item_total);
        for (int i=0;i<3;i++){
            MyIjkVideoView myijkplayer1 = (MyIjkVideoView) rl_item_total.getChildAt(i);
            myijkplayer1.setVolume(0.0f,0.0f);
            myijkplayer1.stopPlayback();
//            myijkplayer1.pause();
//
            Log.e(TAG,"播放器释放啦"+i);
        }


    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            /*
            final List<MyIjkVideoView[]> list = new ArrayList<MyIjkVideoView[]>() ;
            //在这里搞事情
            // 1.添加左边的播放器
            final RelativeLayout.LayoutParams   layoutParams_left = new RelativeLayout.LayoutParams(dp2px(400), RelativeLayout.LayoutParams.MATCH_PARENT);
            final MyIjkVideoView[] myIjkVideoView_1 = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull22Activity.this)};
            myIjkVideoView_1[0].setBackgroundColor(Color.BLUE);
            myIjkVideoView_1[0].setLayoutParams(layoutParams_left);

            //2.添加右上角的播放器
            final RelativeLayout.LayoutParams layoutParams_right_top = new RelativeLayout.LayoutParams(dp2px(200), dp2px(200));
            layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            final MyIjkVideoView[] myIjkVideoView_2 = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull22Activity.this)};
            myIjkVideoView_2[0].setBackgroundColor(Color.WHITE);
            myIjkVideoView_2[0].setLayoutParams(layoutParams_right_top);

            //3.添加右下角的播放器
            final RelativeLayout.LayoutParams layoutParams_right_bottom = new RelativeLayout.LayoutParams(dp2px(200), dp2px(200));
            layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            final MyIjkVideoView[] myIjkVideoView_3  = new MyIjkVideoView[]{new MyIjkVideoView(ThreePull22Activity.this)};
            myIjkVideoView_3[0].setBackgroundColor(Color.BLACK);
            myIjkVideoView_3[0].setLayoutParams(layoutParams_right_bottom);

            TextView textView1 = new TextView(ThreePull22Activity.this);
            TextView textView2 = new TextView(ThreePull22Activity.this);
            textView1.setLayoutParams(layoutParams_right_top);
            textView2.setLayoutParams(layoutParams_right_bottom);

            holder.rl_item_total.addView(myIjkVideoView_1[0]);
            holder.rl_item_total.addView(myIjkVideoView_2[0]);
            holder.rl_item_total.addView(myIjkVideoView_3[0]);
            holder.rl_item_total.addView(textView1);
            holder.rl_item_total.addView(textView2);

            list.add(myIjkVideoView_1);
            list.add(myIjkVideoView_2);
            list.add(myIjkVideoView_3);

            String PULL_0 = Setup1Activity.pull__0;
            String PULL_1 = Setup1Activity.pull__1;
            String PULL_2 = Setup1Activity.pull__2;
            myIjkVideoView_1[0].setVideoPath(PULL_1);
            myIjkVideoView_2[0].setVideoPath(PULL_0);
            myIjkVideoView_3[0].setVideoPath(PULL_2);

            myIjkVideoView_1[0].start();
            myIjkVideoView_2[0].start();
            myIjkVideoView_3[0].start();

            myIjkVideoView_1[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_1[0].setVolume(20.0f,20.0f);
                }
            });
            myIjkVideoView_2[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_2[0].setVolume(0.0f,0.0f);
                }
            });
            myIjkVideoView_3[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
                @Override
                public void setVol() {
                    myIjkVideoView_3[0].setVolume(0.0f,0.0f);
                }
            });

            textView1.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick() {
                    //  Toast.makeText(ThreePull2Activity.this,"双击11",Toast.LENGTH_LONG).show();

                    MyIjkVideoView myIjkVideoView = new MyIjkVideoView(ThreePull22Activity.this);
                    myIjkVideoView = list.get(0)[0];
                    list.get(0)[0] =list.get(1)[0];
                    list.get(1)[0] = myIjkVideoView;

                    list.get(0)[0].setLayoutParams(layoutParams_left);
                    list.get(1)[0].setLayoutParams(layoutParams_right_top);
                    list.get(0)[0].setVolume(20.0f,20.0f);
                    list.get(1)[0].setVolume(0.0f,0.0f);
                    list.get(2)[0].setVolume(0.0f,0.0f);



                }
            }));

            textView2.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick() {
                    //  Toast.makeText(ThreePull2Activity.this,"双击22",Toast.LENGTH_LONG).show();
                    MyIjkVideoView myIjkVideoView = new MyIjkVideoView(ThreePull22Activity.this);
                    myIjkVideoView = list.get(0)[0];
                    list.get(0)[0] =list.get(2)[0];
                    list.get(2)[0] = myIjkVideoView;

                    list.get(0)[0].setLayoutParams(layoutParams_left);
                    list.get(2)[0].setLayoutParams(layoutParams_right_bottom);
                    // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 1);
                    list.get(0)[0].setVolume(20.0f,20.0f);
                    list.get(1)[0].setVolume(0.0f,0.0f);
                    list.get(2)[0].setVolume(0.0f,0.0f);
                }
            }));*/


        }

        @Override
        public int getItemCount() {
            Log.e("----",allUrlBean.getData().size()+"");
            return allUrlBean.getData().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            RelativeLayout rl_item_total;
            public ViewHolder(View itemView) {
                super(itemView);
                rl_item_total = itemView.findViewById(R.id.rl_item_total);
            }
        }
    }



    public  int dp2px( float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
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
                    Toast.makeText(ThreePull0831Activity.this,"请输入前三个地址哦",Toast.LENGTH_LONG).show();
                }else {
                    if (et_4.getText().toString().trim().equals("")){
                        Toast.makeText(ThreePull0831Activity.this,"添加啦",Toast.LENGTH_LONG).show();
                        String parames = 0 +","+ et_1.getText().toString().trim()  +","+0  +","+1+"?" +0  +","+ et_2.getText().toString().trim()  +","+0  +","+1+"?" +0  +","+ et_3.getText().toString().trim() +"," +0 +"," +1;
                        Log.e(TAG,"添加url-3个--"+parames);
                        okhttpPost(parames);
                    }else {
                        Toast.makeText(ThreePull0831Activity.this,"添加啦",Toast.LENGTH_LONG).show();
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
                Toast.makeText(ThreePull0831Activity.this,"button----1",Toast.LENGTH_LONG).show();
            }
        });
        niubiDialog.setOnclickListener(R.id.btn_common_2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThreePull0831Activity.this,"button----2",Toast.LENGTH_LONG).show();
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
        LoadingDialog.showDialogForLoading(ThreePull0831Activity.this);
        Log.e("请求参数拼接--",parames);
        OkHttpClient okHttpClient = new OkHttpClient();
        //表单---此处没有添加什么头信息
        FormBody formBody = new FormBody.Builder()
                .add("paramter",parames)
                .build();

//        Request request = new Request.Builder().post(formBody).url("http://192.168.0.131:8090/latui/secondApi").build();
        Request request = new Request.Builder().post(formBody).url("http://gaolatui.kfcit.com/latui/secondApi").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ThreePull0831--POST----",""+e);
                LoadingDialog.cancelDialogForLoading();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("ThreePull0831-p-success",""+response.body().string());
                LoadingDialog.cancelDialogForLoading();
                niubiDialog.dismiss();
                //请求成功后------
                okhttpGetAll();
            }
        });
    }

    /**
     * 请求首页默认接口
     */
    private void okhttpGetAll() {
        LoadingDialog.showDialogForLoading(ThreePull0831Activity.this);
        OkHttpClient okHttpClient = new OkHttpClient();

//        Request request = new Request.Builder().url("http://192.168.0.131:8090/latui/getAll").build();
        Request request = new Request.Builder().url(" http://gaolatui.kfcit.com/latui/getAll").build();
//        Request request = new Request.Builder().url("http://192.168.199.131:8090/latui/defaultApi").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("getAll--",""+e);
                LoadingDialog.cancelDialogForLoading();
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
                            MyAdapter myAdapter = new MyAdapter();
                            mRecyclerView.setAdapter(myAdapter);
                        }
                    }
                });
            }
        });

    }

}



