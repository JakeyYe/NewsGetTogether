package com.example.mrye.newsgettogether.widget;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Mr.Ye on 2016/11/20.
 */

public class ProgressWebView extends WebView {//自定义带ProgressBar的WebView

    private WebViewProgressBar progressBar;
    private Handler handler;
    private WebView _this;

    public ProgressWebView(Context context) {
        super(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressBar = new WebViewProgressBar(context);
        progressBar.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                           ViewGroup.LayoutParams.WRAP_CONTENT));
        progressBar.setVisibility(GONE);
        addView(progressBar);

        handler = new Handler();
        _this = this;
        setWebViewClient(new MyWebClient());//调用WebViewClient,不使用系统自带的浏览器
        setWebChromeClient(new MyWebChromeClient());//设置WebChromeClient,辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
    }


    private class MyWebClient extends WebViewClient {//辅助WebView处理各种通知和请求工作
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //是否使用第三方浏览器 返回true不调用 返回false调用
            _this.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {//辅助WebView处理页面的js等
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setProgress(100);
                handler.postDelayed(runnable, 200);
            } else if (progressBar.getVisibility() == GONE) {
                progressBar.setVisibility(VISIBLE);
            }
            if (newProgress < 5) {
                newProgress = 5;
            }
            progressBar.setProgress(newProgress);//设置自定义的progress的进度
            super.onProgressChanged(view, newProgress);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(View.GONE);
        }
    };
}
