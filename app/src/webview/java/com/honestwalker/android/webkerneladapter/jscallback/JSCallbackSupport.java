package webview.java.com.honestwalker.android.webkerneladapter.jscallback;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.honestwalker.android.commons.jscallback.JSActionExecutor;
import com.honestwalker.android.webkerneladapter.WebAdapterView;
import com.honestwalker.androidutils.IO.LogCat;
import com.honestwalker.androidutils.UIHandler;


/**
 * 支持JS Callback addJavascriptInterface 对象，自动根据action寻找实现类
 * Created by honestwalker on 15-6-2.
 */
public class JSCallbackSupport {

    private Activity context;

    private WebAdapterView webAdapterView;

    public JSCallbackSupport(Activity context, WebAdapterView webAdapterView, int configResId) {
        this.context = context;
        this.webAdapterView = webAdapterView;
        JSActionExecutor.init(context, configResId);
    }

    @JavascriptInterface
    public String app_callback(final String json) {
        LogCat.d("JS", json);
        return JSActionExecutor.execute(context, webAdapterView ,json);
    }

}
