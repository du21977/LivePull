package com.dibi.livepull.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by du成文 on 2017/3/24.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:549914283
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class OkHttp3Manager {

    /**
     * 静态实例
     */
    private static OkHttp3Manager sOkHttpManager;

    /**
     * okhttpclient实例
     */
    private OkHttpClient mClient;

    /**
     * 因为我们请求数据一般都是子线程中请求，在这里我们使用了handler
     * 一种是handler.sendMessage。发一个消息，再根据消息，执行相关任务代码。
		另一种是handler.post(r)。r是要执行的任务代码。意思就是说r的代码实际是在UI线程执行的。可以写更新UI的代码。（工作线程是不能更新UI的）
     */
    private Handler mHandler;

    /**
     * 构造方法
     */
    private OkHttp3Manager(Context ctx) {
    	
    	//不用管cookie
    	//mClient = new OkHttpClient();

        /*
        //管理cookie,登录持久化
    	final PersistentCookieStore cookieStore = new PersistentCookieStore(ctx);
        mClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {

        	@Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    for (Cookie cookie : cookies) {
                        cookieStore.add(url, cookie);
                        Log.e("cookie Name: ",cookie.name());
                        Log.e("cookie Value:",cookie.value());
                        Log.e("cookie  Path:",cookie.path());
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies;
            }
        	
		}).build();
		*/


        /**
         *  //管理cookie,登录持久化----cookie存入本地---退出后cookie依然存在
         */
        final SharedPreferences sp = ctx.getSharedPreferences("myCookie",Context.MODE_PRIVATE);

        mClient=new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                        cookieStore.put(HttpUrl.parse("http://blockchain.cubelive.cn/app/user/register/step1"), cookies);
                        for(Cookie cookie:cookies){
                            Log.e("cookie Name: ",cookie.name());
                            Log.e("cookie Value:",cookie.value());
                            Log.e("cookie  Path:",cookie.path());
                        }
                        //List<String> list = new ArrayList<String>();
                        Gson gson = new Gson();
                        String data = gson.toJson(cookies);
                        sp.edit().putString("listStr", data).commit();
                       // Log.e("gson 存入:",data);
                        Log.e("gson 存入:",data);

                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                      //  List<Cookie> cookies = cookieStore.get(HttpUrl.parse("http://blockchain.cubelive.cn/app/user/register/step1"));

                        String data = sp.getString("listStr", "");
                        Log.e("gson 获取:",data);
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Cookie>>() { }.getType();
                        List<Cookie> cookies = gson.fromJson(data, listType);

                        if(cookies==null){
                            System.out.println("没加载到cookie");
                            Log.e("cookie  Path:","没加载到cookie");
                        }else {
                            Log.e("cookie  Path:","加载到cookie");
                        }
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();

        /*
         //管理cookie,登录持久化----cookie存入内存---退出后cookie在内存中消失
        mClient=new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                        cookieStore.put(HttpUrl.parse("http://blockchain.cubelive.cn/app/user/register/step1"), cookies);
                        for(Cookie cookie:cookies){
                            Log.e("cookie Name: ",cookie.name());
                            Log.e("cookie Value:",cookie.value());
                            Log.e("cookie  Path:",cookie.path());
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse("http://blockchain.cubelive.cn/app/user/register/step1"));
                        if(cookies==null){
                            System.out.println("没加载到cookie");
                            Log.e("cookie  Path:","没加载到cookie");
                        }
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
                */




       
        /**
         * 在这里直接设置连接超时.读取超时，写入超时
         */
        mClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);

        /**
         * 如果是用的3.0之前的版本  使用以下直接设置连接超时.读取超时，写入超时
         */

        //client.setConnectTimeout(10, TimeUnit.SECONDS);
        //client.setWriteTimeout(10, TimeUnit.SECONDS);
        //client.setReadTimeout(30, TimeUnit.SECONDS);


        /**
         * 初始化handler
         */
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 单例模式  获取OkHttpManager实例
     *
     * @return
     */
    public static OkHttp3Manager getInstance(Context ctx) {

        if (sOkHttpManager == null) {
            sOkHttpManager = new OkHttp3Manager(ctx);
        }
        return sOkHttpManager;
    }

    //-------------------------同步的方式请求数据--------------------------

    /**
     * 对外提供的get方法,同步的方式
     *
     * @param url 传入的地址
     * @return
     */
    public static Response getSync(String url) {

        //通过获取到的实例来调用内部方法
        return sOkHttpManager.inner_getSync(url);
    }

    /**
     * GET方式请求的内部逻辑处理方式，同步的方式
     *
     * @param url
     * @return
     */
    private Response inner_getSync(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            //同步请求返回的是response对象
            response = mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 对外提供的同步获取String的方法
     *
     * @param url
     * @return
     */
    public static String getSyncString(String url) {
        return sOkHttpManager.inner_getSyncString(url);
    }


    /**
     * 同步方法
     */
    private String inner_getSyncString(String url) {
        String result = null;
        try {
            /**
             * 把取得到的结果转为字符串，这里最好用string()
             */
            result = inner_getSync(url).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //-------------------------异步的方式请求数据--------------------------
    public static void getAsync(Context ctx,String url, DataCallBack callBack) {
        getInstance(ctx).inner_getAsync(ctx,url, callBack);
    }

    /**
     * 内部逻辑请求的方法
     *
     * @param url
     * @param callBack
     * @return
     */
    private void inner_getAsync(final Context ctx,String url, final DataCallBack callBack) {
        //不加请求头
        //final Request request = new Request.Builder().url(url).build();
        //加请求头
        final Request.Builder builder = new Request.Builder().url(url);
        //modify by du 2017-04-26
        //  Java.io.IOException: unexpected end of stream on okhttp3.Address@178de5cc
        //builder.addHeader("Connection", "close");  //将请求头以键值对形式添加，可添加多个请求头 ,关闭连接,不然出现上面错误
        //增加token认证
       // builder.addHeader("AUTHORIZATION", "BsWmzLa9p8lOjpW6");
        final Request request = builder.build();


        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            	
                deliverDataFailure(ctx,request, e, callBack);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result = null;
                if(response.code()==200){
                	try {
                        result = response.body().string();
                        deliverDataSuccess(result, callBack);
                    } catch (IOException e) {
                        deliverDataFailure(ctx,request, e, callBack);
                    }

                }else{
                    //返回responseCode
                	failureRespondsCode(ctx, response, callBack,result);
                }
                
            }
        });
    }


    /**
     * 分发失败的时候调用
     *
     * @param request
     * @param e
     * @param callBack
     */
    private void deliverDataFailure(final Context ctx,final Request request, final IOException e, final DataCallBack callBack) {
        /**
         * 在这里使用异步处理
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                	if(NetWorkIsAvailableUtils.isNetworkAvailable(ctx)){
                	//	StringUtil.showToast(ctx, ""+e);
                        callBack.requestFailure(request, e);
                	}else{
                		StringUtil.showToast(ctx, "当前网络不可用");
                		callBack.requestFailure(request, e);
                	}
                	
                }
            }
        });
    }
    
    /**
     * 分发失败的时候调用,有返回码
     *
     * @param
     * @param
     * @param callBack
     */
    private void failureRespondsCode(final Context ctx,final Response response, final DataCallBack callBack,final String result) {
        /**
         * 在这里使用异步处理
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestFailureResCode(response.code());
                    Log.e("异常","错误码"+response.code());
                }
            }
        });
    }

    /**
     * 分发成功的时候调用
     *
     * @param result
     * @param callBack
     */
    private void deliverDataSuccess(final String result, final DataCallBack callBack) {
        /**
         * 在这里使用异步线程处理
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.requestSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("deliverDataSuccess异常",e+"");
                    }
                }
            }
        });
    }

    /**
     * 数据回调接口
     */
    public interface DataCallBack {
        void requestFailure(Request request, IOException e);
        
        void requestFailureResCode(int responsecode);

        void requestSuccess(String result) throws Exception;
    }




    //-------------------------提交表单--------------------------
    
    /**
//   * 参数类
//   */
  public static class Param {
      private String key;
      private String value;
      public Param(){
      }
      public Param(String key, String value){
          this.key = key;
          this.value = value;
      }
  }

  /**
   * 参数转化，map转params
   */
  private Param[] map2Params(Map<String,Object> map) {
      if(map == null) {
          return new Param[0];
      }
      int size = map.size();
      Param[] params = new Param[size];
      int i = 0;
      for(Map.Entry<String, Object> entry : map.entrySet()){
          params[i] = new Param();
          params[i].key = entry.getKey();
          params[i].value = entry.getValue()+"";
          i++;
      }
      return params;
  }


    public static void postAsync(Context ctx,String url, Map<String, Object> map, DataCallBack callBack) {
       // Log.e("post请求参数-服务器返回","url = " +url + " /params" + map.toString());

        getInstance(ctx).inner_postAsync(ctx,url, map, callBack);
    }
    public static void postAsync_json(Context ctx,String url, String json, DataCallBack callBack) {
        // Log.e("post请求参数-服务器返回","url = " +url + " /params" + map.toString());

        getInstance(ctx).inner_postAsync_json(ctx,url, json, callBack);
    }

    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    private void inner_postAsync(final Context ctx,String url, Map<String, Object> params, final DataCallBack callBack) {

//        RequestBody requestBody = null;
//        Param[] params = map2Params(map);
//
//        /**
//         * 如果是3.0之前版本的，构建表单数据是下面的一句
//         */
//        //FormEncodingBuilder builder = new FormEncodingBuilder();
//
//        /**
//         * 3.0之后版本
//         */
//        FormBody.Builder builder = new FormBody.Builder();
//
//        /**
//         * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查找
//         */
//        for(Param param : params){
//          builder.add(param.key,param.value);
//      }
//
//
//        requestBody = builder.build();
//        //结果返回
//        final Request request = new Request.Builder().url(url).post(requestBody).build();
        final String jointUrl = jointParams(url,params);  //打印
        //Log.e("Post请求路径：",jointUrl);
        JSONObject jsonParam = new JSONObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                jsonParam.put(entry.getKey(),entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RequestBody requestBody = RequestBody.create(JSON, jsonParam.toString());

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Log.e("Post请求路径：",url + jsonParam.toString());

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //往这里添加回调
//                deliverDataFailure(ctx,request, e, callBack);
                deliverDataFailure(ctx,request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //往这里添加回调
//                String result = response.body().string();
//                deliverDataSuccess(result, callBack);

                String result = null;
                if(response.code()==200){
                    try {
                        result = response.body().string();
                    } catch (IOException e) {

                        deliverDataFailure(ctx,request, e, callBack);
                    }
                    deliverDataSuccess(result, callBack);
                }else{
                    try {
                        result = response.body().string();
                    } catch (IOException e) {

                        deliverDataFailure(ctx,request, e, callBack);
                    }
                    failureRespondsCode(ctx, response, callBack,result);
                }

            }


        });
    }

    /**
     * 自己传入json
     * @param ctx
     * @param url
     * @param callBack
     */
    private void inner_postAsync_json(final Context ctx,String url,String json, final DataCallBack callBack) {

//        RequestBody requestBody = null;
//        Param[] params = map2Params(map);
//
//        /**
//         * 如果是3.0之前版本的，构建表单数据是下面的一句
//         */
//        //FormEncodingBuilder builder = new FormEncodingBuilder();
//
//        /**
//         * 3.0之后版本
//         */
//        FormBody.Builder builder = new FormBody.Builder();
//
//        /**
//         * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查找
//         */
//        for(Param param : params){
//          builder.add(param.key,param.value);
//      }
//
//
//        requestBody = builder.build();
//        //结果返回
//        final Request request = new Request.Builder().url(url).post(requestBody).build();

        RequestBody requestBody = RequestBody.create(JSON, json);

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Log.e("Post请求路径：",url + json);

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //往这里添加回调
//                deliverDataFailure(ctx,request, e, callBack);
                deliverDataFailure(ctx,request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //往这里添加回调
//                String result = response.body().string();
//                deliverDataSuccess(result, callBack);

                String result = null;
                if(response.code()==200){
                    try {
                        result = response.body().string();
                    } catch (IOException e) {

                        deliverDataFailure(ctx,request, e, callBack);
                    }
                    deliverDataSuccess(result, callBack);
                }else{
//                	 mHandler.post(new Runnable() {
//                		 public void run() {
//                		Log.e("网络返回错误reponseCode", ""+response.code());
//                		StringUtil.showToast(ctx, ""+response.code()+"错误");
//
//
//                		 };
//                	 });
                    try {
                        result = response.body().string();
                    } catch (IOException e) {

                        deliverDataFailure(ctx,request, e, callBack);
                    }
                    failureRespondsCode(ctx, response, callBack,result);
                }

            }


        });
    }


    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }


}
