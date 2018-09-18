package com.dibi.livepull;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dibi.livepull.global.GlobalContants;

public class WebViewActivity extends AppCompatActivity {

    WebView mWebView;
    private ProgressBar pbProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_view);
        mWebView = (WebView) findViewById(R.id.webview);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);// 表示支持js
        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        settings.setUseWideViewPort(true);// 支持双击缩放

        mWebView.getSettings().setJavaScriptEnabled(true);//启用js
        mWebView.getSettings().setBlockNetworkImage(false);//解决图片不显示

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


//        String url = "http://gaolatui.kfcit.com/latui/list.html?id=748c5388e8674e50b02e3e5085fef597";
        String url = GlobalContants.SERVER_URL + "/latui/list.html?id="+GlobalContants.BackToken;

//        String url = "http://www.baidu.com";

        Log.e("----------url",url);
        mWebView.setWebViewClient(new WebViewClient() {

            /**
             * 网页开始加载
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("网页开始加载");
                pbProgress.setVisibility(View.VISIBLE);
            }

            /**
             * 网页加载结束
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页开始结束");

                pbProgress.setVisibility(View.GONE);
            }

            /**
             * 所有跳转的链接都会在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // tel:110
                System.out.println("跳转url:" + url);
                view.loadUrl(url);

                return true;
                // return super.shouldOverrideUrlLoading(view, url);
            }
        });
        // mWebView.goBack()

        mWebView.setWebChromeClient(new WebChromeClient() {

            /**
             * 进度发生变化
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                System.out.println("加载进度:" + newProgress);
                super.onProgressChanged(view, newProgress);
            }

            /**
             * 获取网页标题
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("网页标题:" + title);
                super.onReceivedTitle(view, title);
            }
        });

        mWebView.loadUrl(url);// 加载网页
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(WebViewActivity.this,ViewPagerActivity.class));
        finish();
    }
}
