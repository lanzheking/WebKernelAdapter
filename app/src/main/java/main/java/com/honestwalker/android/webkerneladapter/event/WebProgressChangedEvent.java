package main.java.com.honestwalker.android.webkerneladapter.event;

import com.honestwalker.android.webkerneladapter.WebAdapterView;

/**
 * Created by lanzhe on 17-10-26.
 */

public class WebProgressChangedEvent {

    private WebAdapterView webView;
    private int newProgress;

    public WebProgressChangedEvent(WebAdapterView webView, int newProgress) {
        this.webView = webView;
        this.newProgress = newProgress;
    }

    public WebAdapterView getWebView() {
        return webView;
    }

    public void setWebView(WebAdapterView webView) {
        this.webView = webView;
    }

    public int getNewProgress() {
        return newProgress;
    }

    public void setNewProgress(int newProgress) {
        this.newProgress = newProgress;
    }
}
