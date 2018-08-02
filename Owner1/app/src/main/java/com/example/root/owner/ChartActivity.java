package com.example.root.owner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ChartActivity extends AppCompatActivity {

    private WebView webViewChart;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        String url = "file:///android_asset/index.html";
        webViewChart = (WebView) findViewById(R.id.webViewChart);
        webViewChart.getSettings().setJavaScriptEnabled(true);
        webViewChart.getSettings().setDomStorageEnabled(true);
        webViewChart.getSettings().setBuiltInZoomControls(true);
        webViewChart.loadUrl(url);
        webViewChart.setWebViewClient(new MyBrowser());
        webViewChart.addJavascriptInterface(new WebAppInterface(this), "Android");
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            webViewChart.loadUrl(url);
            return true;
        }
    }

    public class WebAppInterface{
        Context mContext;
        String data;

        public WebAppInterface(Context mContext) {
            this.mContext = mContext;
        }

        public String getData() {
            return data;
        }
    }
}
