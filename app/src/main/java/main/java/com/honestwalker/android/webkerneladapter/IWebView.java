package main.java.com.honestwalker.android.webkerneladapter;

import com.honestwalker.android.commons.views.HtmlWebView.JSParam;
import com.honestwalker.android.webkerneladapter.jscallback.JSCallbackSupport;

/**
 * Created by lanzhe on 17-9-27.
 */
public interface IWebView {

    void addJSCallback(JSCallbackSupport jsCallbackSupport);

    void addJSCallback(JSCallbackSupport jsCallbackSupport, String name);

    void setUserAgent(String userAgent);

    void execJS(String method, JSParam paramKVMap);

    void addInterceptRequest(InterceptRequest interceptRequest);

}
