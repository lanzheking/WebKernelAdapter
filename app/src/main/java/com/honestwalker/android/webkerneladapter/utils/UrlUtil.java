package com.honestwalker.android.webkerneladapter.utils;

/**
 * Created by lanzhe on 17-10-17.
 */

public class UrlUtil {

    /**
     * 整理url
     * @param url
     * @return
     */
    public static String fixUrl(String url) {
        if(url.endsWith("/")) url = url.substring(0, url.length() - 1);
        return url;
    }

    /**
     * 获取url host
     * @param url
     * @return
     */
    String getHost(String url) {
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
