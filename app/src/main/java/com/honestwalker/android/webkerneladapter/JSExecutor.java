package com.honestwalker.android.webkerneladapter;

import android.util.Log;

import com.honestwalker.android.webkerneladapter.WebAdapterView;

/**
 * Created by lanzhe on 17-10-20.
 */

public class JSExecutor {

    public static void exeJS(final WebAdapterView webAdapterView, final String method, final JSParam paramKVMap) {
        String newMethod = method;
        if(newMethod == null) {
            Log.d("JS", "callback null");
            return;
        }
        if(paramKVMap != null) {
            for (String key : paramKVMap.getParams().keySet()) {
                if(key instanceof String) {
                    newMethod = newMethod.replace("${" + key + "}", "'" + paramKVMap.getParams().get(key) + "'");
                } else {
                    newMethod = newMethod.replace("${" + key + "}", paramKVMap.getParams().get(key) + "");
                }
            }
        }
        Log.d("JS", "执行 " + newMethod);
        webAdapterView.loadUrl("javascript: " + newMethod);
    }

}
