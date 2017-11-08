package webview.java.com.honestwalker.android.webkerneladapter;

import android.webkit.WebResourceResponse;

import java.io.InputStream;

/**
 * Created by lanzhe on 17-10-26.
 */

public class WebResourceResponseAdapter extends WebResourceResponse {

    public WebResourceResponseAdapter(String mimeType, String encoding, InputStream data) {
        super(mimeType, encoding, data);
    }

}
