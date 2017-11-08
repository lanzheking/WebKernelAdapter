package main.java.com.honestwalker.android.webkerneladapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.WebResourceRequest;

import com.honestwalker.android.webkerneladapter.WebAdapterView;
import com.honestwalker.android.webkerneladapter.WebResourceClientAdapter;
import com.honestwalker.android.webkerneladapter.WebResourceResponseAdapter;

/**
 * Created by lanzhe on 17-5-27.
 */

public abstract class InterceptRequest {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean interceptRule(WebAdapterView view, WebResourceClientAdapter webViewClient, WebResourceRequest request) {
        return interceptRule(view, webViewClient, request.getUrl().getPath());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WebResourceResponseAdapter getResponse(WebAdapterView view, WebResourceClientAdapter webViewClient, WebResourceRequest request) {
        return getResponse(view, webViewClient, request.getUrl().getPath());
    }

    /**
     * android 4.x 会调用此方法
     * @param view
     * @param webViewClient
     * @param url
     * @return
     */
    public abstract WebResourceResponseAdapter getResponse(WebAdapterView view, WebResourceClientAdapter webViewClient, String url);

    public abstract boolean interceptRule(WebAdapterView view, WebResourceClientAdapter webViewClient, String url);

}
