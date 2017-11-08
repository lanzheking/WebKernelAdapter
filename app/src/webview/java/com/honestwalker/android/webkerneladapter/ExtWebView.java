package webview.java.com.honestwalker.android.webkerneladapter;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.honestwalker.android.webkerneladapter.event.WebProgressChangedEvent;
import com.honestwalker.androidutils.IO.LogCat;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by lanzhe on 17-10-25.
 */
public class ExtWebView extends WebView {

    protected Activity context;

    public ExtWebView(Activity context) {
        super(context);
        this.context = context;
        init();
    }

    public ExtWebView(Activity context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ExtWebView(Activity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ExtWebView(Activity context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
    }

}
