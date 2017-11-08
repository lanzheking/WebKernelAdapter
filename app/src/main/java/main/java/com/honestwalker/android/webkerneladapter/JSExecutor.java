package main.java.com.honestwalker.android.webkerneladapter;

import com.honestwalker.android.commons.views.HtmlWebView.JSParam;
import com.honestwalker.androidutils.IO.LogCat;
import com.honestwalker.androidutils.UIHandler;

/**
 * Created by lanzhe on 17-10-20.
 */

public class JSExecutor {

    public static void exeJS(final WebAdapterView webAdapterView, final String method, final JSParam paramKVMap) {
        UIHandler.post(new Runnable() {
            @Override
            public void run() {
                String newMethod = method;
                if(newMethod == null) {
                    LogCat.d("JS", "callback null");
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
                LogCat.d("JS", "执行 " + newMethod);
                webAdapterView.loadUrl("javascript: " + newMethod);
            }
        });
    }

}
