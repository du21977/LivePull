package com.dibi.livepull;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dibi.livepull.bean.AllUrlBean;
import com.dibi.livepull.bean.DefaultApiBean;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.dibi.livepull.R.id.ijkplayer;

public class ThreePull0831Activity extends AppCompatActivity {

    private static final String TAG = "RecycleView--item" ;
    RecyclerView mRecyclerView;
    private ViewPagerLayoutManager mLayoutManager;
    private AllUrlBean allUrlBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_pull22);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new ViewPagerLayoutManager(ThreePull0831Activity.this, OrientationHelper.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);




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
                releaseVideo(index);

            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                Log.e(TAG, "选中位置:" + position + "  是否是滑动到底部:" + isBottom);
                playVideo(0);
            }
        });


        okhttpGet();

    }

    /**
     * 请求首页默认接口
     */
    private void okhttpGet() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.0.131:8090/latui/getAll").build();
//        Request request = new Request.Builder().url("http://192.168.199.131:8090/latui/defaultApi").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("ThreePull0831Activity--",result);
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






}



