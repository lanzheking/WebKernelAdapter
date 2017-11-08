package crosswalk.java.com.honestwalker.android.webkerneladapter.jscallback;//package com.honestwalker.android.webkerneladapter.jscallback;
//
//import android.app.Activity;
//
//import com.honestwalker.android.commons.jscallback.JSActionExecutor;
//import com.honestwalker.android.webkerneladapter.WebAdapterView;
//import com.honestwalker.androidutils.IO.LogCat;
//import com.honestwalker.androidutils.UIHandler;
//
//import org.xwalk.core.JavascriptInterface;
//
///**
// * 支持JS Callback addJavascriptInterface 对象，自动根据action寻找实现类
// * Created by honestwalker on 15-6-2.
// */
//public class JSCallbackObjectFilter {
//
//    private Activity context;
//    private WebAdapterView webView;
//    private String applicationId;
//
//    public JSCallbackObjectFilter(Activity context, WebAdapterView webView, String applicationId) {
//        this.context = context;
//        this.webView = webView;
//        this.applicationId = applicationId;
//    }
//
//    @JavascriptInterface
//    public void app_callback(final String json) {
//        LogCat.d("JS", json);
//        UIHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                JSActionExecutor.execute(context, applicationId, webView, json);
//            }
//        });
//    }
//
//}
