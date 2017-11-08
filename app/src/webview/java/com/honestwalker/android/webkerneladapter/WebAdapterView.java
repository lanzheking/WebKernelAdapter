package webview.java.com.honestwalker.android.webkerneladapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.honestwalker.android.commons.views.HtmlWebView.JSParam;
import com.honestwalker.android.webkerneladapter.event.WebProgressChangedEvent;
import com.honestwalker.android.webkerneladapter.jscallback.JSCallbackSupport;
import com.honestwalker.androidutils.IO.LogCat;
import com.honestwalker.androidutils.equipment.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * web 内核适配器
 * Created by lanzhe on 17-7-27.
 */
public class WebAdapterView extends ExtWebView implements IWebView  {

    public WebAdapterView(Context context) {
        super((Activity) context);
        init();
    }

    public WebAdapterView(Context context, AttributeSet attrs) {
        super((Activity)context, attrs);
        init();
    }

    private void init() {
        setWebViewClient(webViewClient);
        setDefaultUIClient();
        defaultSetting();
        initFileChose();
    }

    private WebResourceClientAdapter webViewClient = new WebResourceClientAdapter() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

            String url = request.getUrl().toString();

            try {
                if(interceptRequests != null) {
                    for (InterceptRequest interceptRequest : interceptRequests) {
                        if(interceptRequest.interceptRule(WebAdapterView.this, this, url)) {
                            LogCat.d("URL", "拦截URL " + url);
                            WebResourceResponse webResourceResponse = interceptRequest.getResponse(WebAdapterView.this, this, url);
                            if(webResourceResponse != null) {
                                return webResourceResponse;
                            } else {
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {}

            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

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
            view.loadUrl(url);
            return true;
        }

    };

    private void setDefaultUIClient() {
        final WebUIClient<WebChromeClient, WebView> uiClient = new WebUIClient<>(getContext());
        LogCat.d("FILE", "设置默认webchromeclient");
        super.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                String url = view.getUrl();
                LogCat.d("URL", url + " @ " + newProgress);
                HermesEventBus.getDefault().post(new WebProgressChangedEvent(WebAdapterView.this, newProgress));
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return uiClient.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return uiClient.onJsAlert(view, url, message, result);
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                LogCat.d("FILE", "openFileChooser 3.0+");
                if(fileChooseCallback != null) {
                    fileChooseCallback.openFileChooser(uploadMsg);
                }
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                LogCat.d("FILE", "openFileChooser 3.0+ " + acceptType);
                if(fileChooseCallback != null) {
                    fileChooseCallback.openFileChooser(uploadMsg, acceptType);
                }
            }

            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                LogCat.d("FILE", "openFileChooser 4.1+ " + acceptType + " " + capture);
                if(fileChooseCallback != null) {
                    fileChooseCallback.openFileChooser(uploadMsg, acceptType, capture);
                }
            }

            //For Android  >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {

                LogCat.d("FILE", "openFileChooser 5.0+ " + Thread.currentThread().getId());
                if(fileChooseCallback != null) {
                    fileChooseCallback.openFileChooser(uploadMsg, fileChooserParams);
                }
                return true;
            }

        });
    }

    @Override
    public void addJSCallback(JSCallbackSupport jsCallbackSupport) {
        super.addJavascriptInterface(jsCallbackSupport, "jsApiBridge");
    }

    /**
     * 添加 js callback 配置  , 自定义名称
     * @param jsCallbackSupport
     */
    public void addJSCallback(JSCallbackSupport jsCallbackSupport, String name) {
        super.addJavascriptInterface(jsCallbackSupport, name);
    }

    @Override
    public void setUserAgent(String userAgent) {
        super.getSettings().setUserAgentString(userAgent);
    }

    public void addUserAgent(String userAgent) {
        String fixUserAgent = getSettings().getUserAgentString() + " " + userAgent +
                " width=" + DisplayUtil.getWidth(context) + " height=" + DisplayUtil.getHeight(context);
        getSettings().setUserAgentString(fixUserAgent);
    }

    @Override
    public void execJS(String method, JSParam paramKVMap) {
        JSExecutor.exeJS(this, method, paramKVMap);
    }

    public void testJSCallback(String action, HashMap<String , Object> params) {
        if(params == null) params = new HashMap<>();
        params.put("action", action);
        execJS("window.jsApiBridge.app_callback('" + new Gson().toJson(params) + "')");
    }

    /**
     * 请求拦截机器列表
     */
    private List<InterceptRequest> interceptRequests = new ArrayList<>();
    /**
     * 添加请求拦截机器
     */
    @Override
    public void addInterceptRequest(InterceptRequest interceptRequest) {
        if(interceptRequest != null) {
            interceptRequests.add(interceptRequest);
        }
    }
    /**
     * 删除请求拦截机器
     */
    public void removeInterceptRequest(InterceptRequest interceptRequest) {
        if(interceptRequest != null) {
            interceptRequests.remove(interceptRequest);
        }
    }

    private void defaultSetting() {
        getSettings().setSupportZoom(false);
        getSettings().setAllowFileAccess(true);
        getSettings().setCacheMode(getSettings().LOAD_CACHE_ELSE_NETWORK);
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setAllowContentAccess(true);
        getSettings().setTextZoom(100);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setCacheMode(getSettings().LOAD_NO_CACHE);
    }

    /** ======================================================
     *
     *                      文件选择相关
     *
     ========================================================*/
    private FileChooser fileChooser;
    private void initFileChose() {
        this.setFileChoseCallback(new FileChooseCallback() {
            @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                fileChooser = new FileChooser(context, WebAdapterView.this, uploadMsg);
                fileChooser.choose();
            }

            @Override
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                fileChooser = new FileChooser(context, WebAdapterView.this, uploadMsg , acceptType, null);
                fileChooser.choose();
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                fileChooser = new FileChooser(context, WebAdapterView.this, uploadMsg , acceptType, capture);
                fileChooser.choose();
            }

            @Override
            public void openFileChooser(ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
                fileChooser = new FileChooser(context, WebAdapterView.this, uploadMsg);
                fileChooser.choose();
            }

        });
    }
    public FileChooser getFileChooser() {
        return this.fileChooser;
    }
    public void resetFileChose() {
        if(fileChooser != null) fileChooser.resetFileChose();
    }
    public ValueCallback getUploadMessage() {
        return fileChooser == null ? null : fileChooser.getUploadMessage();
    }

    private FileChooseCallback fileChooseCallback;
    public void setFileChoseCallback(FileChooseCallback fileChooseCallback) {
        this.fileChooseCallback = fileChooseCallback;
    }

}
