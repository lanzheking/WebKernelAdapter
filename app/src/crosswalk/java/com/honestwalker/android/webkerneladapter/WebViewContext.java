package com.honestwalker.android.webkerneladapter;

import android.content.Context;
import android.util.Log;

import org.xwalk.core.XWalkCookieManager;

import com.honestwalker.android.webkerneladapter.SharedPreferencesLoader;
import com.honestwalker.android.webkerneladapter.utils.IWebViewContext;

/**
 * Created by lanzhe on 17-9-27.
 */

public class WebViewContext implements IWebViewContext<XWalkCookieManager> {

    public final String COOKIE = "cookie";

    private String TAG = "COOKIE";

    private static XWalkCookieManager xWalkCookieManager;

    private static WebViewContext instance;

    public static WebViewContext getInstance() {
        if(instance == null) {
            instance = new WebViewContext();
        }
        return instance;
    }

    @Override
    public XWalkCookieManager getCookieManager() {
        if(xWalkCookieManager == null) {
            xWalkCookieManager = new XWalkCookieManager();
        }
        return xWalkCookieManager;
    }

    public String getWebViewCookie(String url) {
        return getCookieManager().getCookie(url);
    }

    /**
     * 把当前url cookie 保存到本地
     * @param context
     * @param url
     * @return
     */
    public String saveWebViewCookieToLocal(Context context , String url) {
        url = fixUrl(url);

        String cookie = getCookieManager().getCookie(url);
        SharedPreferencesLoader.getInstance(context).putString("COOKIE_" + url, cookie);
        Log.d(COOKIE, "cookie=" + cookie);
        return cookie;
    }

    /**
     * 保存Cookie到本地
     * @param context
     * @param host
     * @param cookie
     */
    public void saveCookieToLocal(Context context, String host, String cookie) {
        SharedPreferencesLoader.getInstance(context).putString("COOKIE_" + host, cookie);
    }

    /**
     * 删除本地Cookie
     * @param context
     * @param host
     */
    public void removeLocalCookie(Context context, String host) {
        SharedPreferencesLoader.getInstance(context).putString("COOKIE_" + host, "");
    }

    /**
     * 读取本地cookie
     * @param url
     * @return
     */
    public String getLocalCookie(String url) {
        url = fixUrl(url);
        return SharedPreferencesLoader.getString(url, "");
    }

    public void syncCookie(String url, String cookie) {
        getCookieManager().setCookie(url, cookie);
        Log.d(TAG, "sync cookie " + url + "  " + cookie);
    }

    /**
     * 整理url
     * @param url
     * @return
     */
    private String fixUrl(String url) {
        if(url.endsWith("/")) url = url.substring(0, url.length() - 1);
        return url;
    }

    /**
     * 获取url host
     * @param url
     * @return
     */
    public String getHost(String url) {
        String protocol = "";
        if(url.indexOf("://") > 0) {
            protocol = url.substring(0, url.indexOf("://") + 3);
        }
        url = url.substring(protocol.length());
        if(url.indexOf("/") > 0) {
            url = url.substring(0, url.indexOf("/"));
        }
        return url;
    }

}
