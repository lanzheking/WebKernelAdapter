package x5.java.com.honestwalker.android.webkerneladapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import com.example.test_webview_demo.utils.X5WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * web 内核适配器
 * Created by lanzhe on 17-7-27.
 */
public class WebAdapterView extends X5WebView {

    public WebAdapterView(Context context) {
        super(context);
        init();
    }

    public WebAdapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        X5Init.init(getContext());
        setWebViewClient(webViewClient);
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String url) {

            if(url.startsWith("tel:")) {
                // 不需要权限，跳转到"拔号"中。
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                getContext().startActivity(callIntent);
                return true;
            }
            if(url.startsWith("mailto:")) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                getContext().startActivity(mailIntent);
                return true;
            }

            Log.d("WebAdapter", "重定向:" + url);
            webView.loadUrl(url);

            return true;
        }

    };

}
