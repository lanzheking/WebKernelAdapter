package com.honestwalker.android.crosswalk;


import android.app.Activity;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ValueCallback;

import com.honestwalker.android.webkerneladapter.BuildConfig;

import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import main.java.com.honestwalker.android.webkerneladapter.FileChooseCallback;
import main.java.com.honestwalker.android.webkerneladapter.WebUIClient;

/**
 * Created by lanzhe on 17-9-25.
 */
public class CrossWalkView extends XWalkView {

    protected Activity context;

    public CrossWalkView(Activity context) {
        super(context);
        this.context = context;
        init();
    }

    public CrossWalkView(Activity context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        try {
            XWalkPreferences.setValue(XWalkPreferences.ANIMATABLE_XWALK_VIEW, false);      //关闭crosswalk内核动画，避免键盘开关时的页面闪烁
        } catch (Exception e) {
        }
        setDebug(BuildConfig.DEBUG);
        Log.d("crosswalk", "crosswalk init");
        defaultSetting();
        setDefaultXWalkUIClient();
    }

    private void setDefaultXWalkUIClient() {
        final WebUIClient<XWalkUIClient, XWalkView> uiClient = new WebUIClient<>(getContext());
        super.setUIClient(new XWalkUIClient(this) {

            @Override
            public boolean onJsAlert(XWalkView view, String url, String message, XWalkJavascriptResult result) {
                return uiClient.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(XWalkView view, String url, String message, XWalkJavascriptResult result) {
                return uiClient.onJsAlert(view, url, message, result);
            }

            @Override
            public void openFileChooser(XWalkView view, ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                super.openFileChooser(view, uploadFile, acceptType, capture);
                Log.d("FILE", "openFileChooser " + acceptType + " " + capture);
                if(fileChooseCallback != null) {
                    fileChooseCallback.openFileChooser(uploadMsg, acceptType, capture);
                }
            }

        });
    }

    private FileChooseCallback fileChooseCallback;
    public void setFileChoseCallback(FileChooseCallback fileChooseCallback) {
        this.fileChooseCallback = fileChooseCallback;
    }

    private void defaultSetting() {
        getSettings().setSupportZoom(false);
        getSettings().setAllowFileAccess(true);
        getSettings().setCacheMode(getSettings().LOAD_CACHE_ELSE_NETWORK);
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setAllowContentAccess(true);
        getSettings().setTextZoom(100);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setCacheMode(XWalkSettings.LOAD_NO_CACHE);
    }

    public void setDebug(boolean enable) {
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, enable);
    }

    public void loadUrl(final String url) {
        super.loadUrl(url);
    }

    public void destroy(){
        onDestroy();
    }

    public void reload(){
        super.reload(1);
    }

    public boolean canGoBack(){
        return super.getNavigationHistory().canGoBack();
    }

    public void goBack() {
        super.getNavigationHistory().navigate(XWalkNavigationHistory.Direction.BACKWARD, 1);
    }

    public void clearHistory(){
        super.getNavigationHistory().clear();
    }

    public void setUseragent(String userAgent) {
        super.setUserAgentString(userAgent);
    }

    public void execJS(String cmd) {
        loadUrl("javascript: " + cmd);
    }

}
