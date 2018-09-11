package com.dibi.livepull.Manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetWorkIsAvailableUtils {

	public static boolean isNetworkAvailable(Context context) {
		// 获取当前的网络连接服务 
		ConnectivityManager connMgr = (ConnectivityManager)
		 context.getSystemService(Context.CONNECTIVITY_SERVICE); //获取活动的网络连接信息
		 NetworkInfo info = connMgr.getActiveNetworkInfo(); //判断
		 if(info==null) { 
			 //当前没有已激活的网络连接（表示用户关闭了数据流量服务，也没有开启WiFi等别的数据服务）
			 ToastUtils.showToastSafe(context,"没有网络，请检查网络设置");
			 return false;
		 }
		 else { 
			 //当前有已激活的网络连接，但是否可用还需判断 
			 boolean isAlive = info.isAvailable();
			 if(isAlive){
				 return true;
			 }else{
				 ToastUtils.showToastSafe(context,"没有网络，请检查网络设置");
				 return false;
			 }
			 
		 }
	}
}
