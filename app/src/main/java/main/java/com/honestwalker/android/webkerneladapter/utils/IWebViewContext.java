package main.java.com.honestwalker.android.webkerneladapter.utils;

import android.content.Context;

/**
 * Created by lanzhe on 17-10-17.
 */

public interface IWebViewContext<C> {

    C getCookieManager();

    String getWebViewCookie(String url);

    /**
     * 把当前url cookie 保存到本地
     * @param context
     * @param url
     * @return
     */
    String saveWebViewCookieToLocal(Context context, String url);

    /**
     * 保存Cookie到本地
     * @param context
     * @param host
     * @param cookie
     */
    void saveCookieToLocal(Context context, String host, String cookie);

    /**
     * 删除本地Cookie
     * @param context
     * @param host
     */
    void removeLocalCookie(Context context, String host);

    /**
     * 读取本地cookie
     * @param url
     * @return
     */
    String getLocalCookie(String url);

    void syncCookie(String url, String cookie);

}
