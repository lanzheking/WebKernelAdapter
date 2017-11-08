package com.honestwalker.android.webkerneladapter;

/**
 * Created by lanzhe on 17-9-27.
 */
public interface IWebView {

    void setUserAgent(String userAgent);

    void execJS(String method, JSParam paramKVMap);

    void addInterceptRequest(InterceptRequest interceptRequest);

}
