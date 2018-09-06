package com.dibi.livepull.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dibi.livepull.R;
import com.dibi.livepull.ThreePull0831Activity;
import com.dibi.livepull.bean.AllUrlBean;
import com.dibi.livepull.view.MyIjkVideoView;
import com.dibi.livepull.view.OnDoubleClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TestFm extends Fragment{

	private List<String> list = new ArrayList<String>();
	private String  result;
	private int flag;
	private TextView tv;
	//
	private AllUrlBean allUrlBean;

	private RelativeLayout rl_item_total;
	private MyIjkVideoView[] myIjkVideoView_1;
	private MyIjkVideoView[] myIjkVideoView_2;
	private MyIjkVideoView[] myIjkVideoView_3;
	private MyIjkVideoView[] myIjkVideoView_41;
	private MyIjkVideoView[] myIjkVideoView_42;
	private MyIjkVideoView[] myIjkVideoView_43;
	private MyIjkVideoView[] myIjkVideoView_4middle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.e("Fragment","--onCreate");
		Bundle bundle = this.getArguments();
		if(bundle != null){
//			list = bundle.getStringArrayList("content");
			result = bundle.getString("content");
			flag = bundle.getInt("flag");
			allUrlBean = new Gson().fromJson(result,AllUrlBean.class);
//			Log.e("vvvv---",result+"res");
			if (allUrlBean !=null&& allUrlBean.getData().size()>0){
				Log.e("vvvv---",allUrlBean.getData().get(flag).get(0).getPath());
			}
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("Fragment","--onCreateView");
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fm_test, container,false);

		initView(view);
		return view;
	}
	
	public void initView(View view){
		tv = (TextView)view.findViewById(R.id.tv);
//		tv.setText("我擦");
//		tv.setText(list.get(flag));
//		tv.setText(allUrlBean.getData().get(flag).get(0).getPath());

		rl_item_total = view.findViewById(R.id.rl_item_total);
		if(allUrlBean.getData().get(flag).size()==3){
			final List<MyIjkVideoView[]> list = new ArrayList<MyIjkVideoView[]>() ;
			//在这里搞事情
			// 1.添加左边的播放器
			final RelativeLayout.LayoutParams   layoutParams_left = new RelativeLayout.LayoutParams(dp2px(400), RelativeLayout.LayoutParams.MATCH_PARENT);
			myIjkVideoView_1 = new MyIjkVideoView[]{new MyIjkVideoView(getActivity())};
			myIjkVideoView_1[0].setBackgroundColor(Color.BLUE);
			myIjkVideoView_1[0].setLayoutParams(layoutParams_left);

			//2.添加右上角的播放器
			final RelativeLayout.LayoutParams layoutParams_right_top = new RelativeLayout.LayoutParams(dp2px(200), dp2px(200));
			layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			myIjkVideoView_2 = new MyIjkVideoView[]{new MyIjkVideoView(getActivity())};
			myIjkVideoView_2[0].setBackgroundColor(Color.WHITE);
			myIjkVideoView_2[0].setLayoutParams(layoutParams_right_top);



			//3.添加右下角的播放器
			final RelativeLayout.LayoutParams layoutParams_right_bottom = new RelativeLayout.LayoutParams(dp2px(200), dp2px(200));
			layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			myIjkVideoView_3 = new MyIjkVideoView[]{new MyIjkVideoView(getActivity())};
			myIjkVideoView_3[0].setBackgroundColor(Color.BLACK);
			myIjkVideoView_3[0].setLayoutParams(layoutParams_right_bottom);

			TextView textView1 = new TextView(getActivity());
			TextView textView2 = new TextView(getActivity());
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
			String PULL_0 = allUrlBean.getData().get(flag).get(0).getPath();
			String PULL_1 = allUrlBean.getData().get(flag).get(1).getPath();
			String PULL_2 = allUrlBean.getData().get(flag).get(2).getPath();
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

					MyIjkVideoView myIjkVideoView = new MyIjkVideoView(getActivity());
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
					MyIjkVideoView myIjkVideoView = new MyIjkVideoView(getActivity());
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

///////////////////////////////////////////////////下面是4个/////////////////////////////////////////////////////////////////////////////////////
		}else if(allUrlBean.getData().get(flag).size()>3){
			final List<MyIjkVideoView[]> list = new ArrayList<MyIjkVideoView[]>() ;
			//在这里搞事情
			// 1.添加左边的播放器
			final RelativeLayout.LayoutParams   layoutParams_left = new RelativeLayout.LayoutParams(dp2px(400), RelativeLayout.LayoutParams.MATCH_PARENT);
			myIjkVideoView_41 = new MyIjkVideoView[]{new MyIjkVideoView(getActivity())};
			myIjkVideoView_41[0].setBackgroundColor(Color.BLUE);
			myIjkVideoView_41[0].setLayoutParams(layoutParams_left);

			//2.添加右上角的播放器
			final RelativeLayout.LayoutParams layoutParams_right_top = new RelativeLayout.LayoutParams(dp2px(200), dp2px(120));
			layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams_right_top.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			myIjkVideoView_42 = new MyIjkVideoView[]{new MyIjkVideoView(getActivity())};
			myIjkVideoView_42[0].setBackgroundColor(Color.WHITE);
			myIjkVideoView_42[0].setLayoutParams(layoutParams_right_top);

			//3.添加右下角的播放器
			final RelativeLayout.LayoutParams layoutParams_right_bottom = new RelativeLayout.LayoutParams(dp2px(200), dp2px(120));
			layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams_right_bottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			myIjkVideoView_43 = new MyIjkVideoView[]{new MyIjkVideoView(getActivity())};
			myIjkVideoView_43[0].setBackgroundColor(Color.BLACK);
			myIjkVideoView_43[0].setLayoutParams(layoutParams_right_bottom);

			TextView textView41 = new TextView(getActivity());
			TextView textView42 = new TextView(getActivity());
			textView41.setId(R.id.myIjkVideoView_2);
			textView41.setLayoutParams(layoutParams_right_top);
			textView42.setLayoutParams(layoutParams_right_bottom);



			//中间的
			final RelativeLayout.LayoutParams layoutParams_middle = new RelativeLayout.LayoutParams(dp2px(200), dp2px(120));
			layoutParams_middle.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams_middle.addRule(RelativeLayout.BELOW,R.id.myIjkVideoView_2);

			myIjkVideoView_4middle = new MyIjkVideoView[]{new MyIjkVideoView(getActivity())};
			myIjkVideoView_4middle[0].setBackgroundColor(Color.GREEN);
			myIjkVideoView_4middle[0].setLayoutParams(layoutParams_middle);

			TextView textView_middle = new TextView(getActivity());
			textView_middle.setLayoutParams(layoutParams_middle);


			rl_item_total.addView(myIjkVideoView_41[0]);
			rl_item_total.addView(myIjkVideoView_42[0]);
			rl_item_total.addView(myIjkVideoView_43[0]);
			rl_item_total.addView(myIjkVideoView_4middle[0]);
			rl_item_total.addView(textView41);
			rl_item_total.addView(textView42);
			rl_item_total.addView(textView_middle);

			list.add(myIjkVideoView_41);
			list.add(myIjkVideoView_42);
			list.add(myIjkVideoView_43);
			list.add(myIjkVideoView_4middle);

			String PULL_0 = allUrlBean.getData().get(flag).get(0).getPath();
			String PULL_1 = allUrlBean.getData().get(flag).get(1).getPath();
			String PULL_2 = allUrlBean.getData().get(flag).get(2).getPath();
			String PULL_3 = allUrlBean.getData().get(flag).get(3).getPath();
			myIjkVideoView_41[0].setVideoPath(PULL_1);
			myIjkVideoView_42[0].setVideoPath(PULL_0);
			myIjkVideoView_43[0].setVideoPath(PULL_2);
			myIjkVideoView_4middle[0].setVideoPath(PULL_3);

			myIjkVideoView_41[0].start();
			myIjkVideoView_42[0].start();
			myIjkVideoView_43[0].start();
			myIjkVideoView_4middle[0].start();


			myIjkVideoView_41[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
				@Override
				public void setVol() {
					myIjkVideoView_41[0].setVolume(20.0f,20.0f);
				}
			});
			myIjkVideoView_42[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
				@Override
				public void setVol() {
					myIjkVideoView_42[0].setVolume(0.0f,0.0f);
				}
			});
			myIjkVideoView_43[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
				@Override
				public void setVol() {
					myIjkVideoView_43[0].setVolume(0.0f,0.0f);
				}
			});
			myIjkVideoView_4middle[0].SetVolumeListener(new MyIjkVideoView.VolListener() {
				@Override
				public void setVol() {
					myIjkVideoView_4middle[0].setVolume(0.0f,0.0f);
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

			textView41.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
				@Override
				public void onDoubleClick() {
					//  Toast.makeText(ThreePull2Activity.this,"双击11",Toast.LENGTH_LONG).show();

					MyIjkVideoView myIjkVideoView = new MyIjkVideoView(getActivity());
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

			textView42.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
				@Override
				public void onDoubleClick() {
					//  Toast.makeText(ThreePull2Activity.this,"双击22",Toast.LENGTH_LONG).show();
					MyIjkVideoView myIjkVideoView = new MyIjkVideoView(getActivity());
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
					MyIjkVideoView myIjkVideoView = new MyIjkVideoView(getActivity());
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
	
	
//	public static TestFm newInstance(List<String> contentList,int flag){
//		Bundle bundle = new Bundle();
//		bundle.putStringArrayList("content", (ArrayList<String>) contentList);
//		bundle.putInt("flag", flag);
//		TestFm testFm = new TestFm();
//		testFm.setArguments(bundle);
//		return testFm;
//
//	}

	/**
	 * 静态的--归类所有--不属于对象
	 * @param result
	 * @param flag
	 * @return
	 */
	public static TestFm newInstance(String result, int flag){
		Bundle bundle = new Bundle();
		//bundle.putStringArrayList("content", result);
//		Log.e("vvvv---",result+"---");
		bundle.putString("content",result);
		bundle.putInt("flag", flag);
		TestFm testFm = new TestFm();
		testFm.setArguments(bundle);
		return testFm;

	}

	public  int dp2px( float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, getResources().getDisplayMetrics());
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.e("Fragment","--onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e("Fragment","--onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.e("Fragment","--onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.e("Fragment","--onStop");
		if(myIjkVideoView_1!=null){
			myIjkVideoView_1[0].stopPlayback();
		}
		 if(myIjkVideoView_2!=null){
			 myIjkVideoView_2[0].stopPlayback();
		 }

		 if(myIjkVideoView_3!=null){
			 myIjkVideoView_3[0].stopPlayback();
		 }
		 if(myIjkVideoView_41!=null){
			 myIjkVideoView_41[0].stopPlayback();
		 }

		 if(myIjkVideoView_42!=null){
			 myIjkVideoView_42[0].stopPlayback();
		 }

		if(myIjkVideoView_43!=null){
			myIjkVideoView_43[0].stopPlayback();
		}
		if(myIjkVideoView_4middle!=null){
			myIjkVideoView_4middle[0].stopPlayback();
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("Fragment","--onDestroy");
	}
}
