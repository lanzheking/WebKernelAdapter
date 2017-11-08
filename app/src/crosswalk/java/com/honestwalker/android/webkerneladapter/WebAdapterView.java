package crosswalk.java.com.honestwalker.android.webkerneladapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import com.google.gson.Gson;
import com.honestwalker.android.commons.views.HtmlWebView.JSParam;
import com.honestwalker.android.crosswalk.CrossWalkView;
import com.honestwalker.android.webkerneladapter.event.WebProgressChangedEvent;
import com.honestwalker.android.webkerneladapter.jscallback.JSCallbackSupport;
import com.honestwalker.androidutils.IO.LogCat;
import com.honestwalker.androidutils.UIHandler;
import com.honestwalker.androidutils.equipment.DisplayUtil;

import org.xwalk.core.XWalkCookieManager;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceRequest;
import org.xwalk.core.XWalkWebResourceResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * web 内核适配器
 * Created by lanzhe on 17-7-27.
 */
public class WebAdapterView extends CrossWalkView implements IWebView {

    private XWalkCookieManager xWalkCookieManager = new XWalkCookieManager();

    public WebAdapterView(Context context) {
        super((Activity) context);
        init();
    }

    public WebAdapterView(Context context, AttributeSet attrs) {
        super((Activity)context, attrs);
        init();
    }

    public void init() {
        xWalkCookieManager.setAcceptCookie(true);
        xWalkCookieManager.setAcceptFileSchemeCookies(true);

        setResourceClient(new WebResourceClientAdapter(this) {

            @Override
            public void onProgressChanged(XWalkView view, int progressInPercent) {
                super.onProgressChanged(view, progressInPercent);
                String url = view.getUrl();
                LogCat.d("URL", url + " @ " + progressInPercent);
                HermesEventBus.getDefault().post(new WebProgressChangedEvent(WebAdapterView.this, progressInPercent));
            }

            @Override
            public void onLoadStarted(XWalkView view, String url) {
                super.onLoadStarted(view, url);
                xWalkCookieManager.flushCookieStore();
                isCookieChange();
            }

            @Override
            public void onLoadFinished(XWalkView view, String url) {
                super.onLoadFinished(view, url);
            }

            @Override
            public XWalkWebResourceResponse shouldInterceptLoadRequest(XWalkView view, XWalkWebResourceRequest request) {

                try {
                    if(interceptRequests != null) {
                        for (InterceptRequest interceptRequest : interceptRequests) {
                            if(interceptRequest.interceptRule(WebAdapterView.this, this, request.getUrl().toString())) {
                                LogCat.d("URL", "拦截URL " + request.getUrl().toString());
                                WebResourceResponseAdapter webResourceResponse = interceptRequest.getResponse(WebAdapterView.this, this, request.getUrl().toString());
                                if(webResourceResponse != null) {
                                    return webResourceResponse;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {}

                return super.shouldInterceptLoadRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(XWalkView view, String url) {

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

                view.loadUrl(url);
                return true;
            }
        });

        initFileChose();

    }

    /** ======================================================
     *
     *                      文件选择相关
     *
     ========================================================*/
    private FileChooser fileChooser;
    private void initFileChose() {
        super.setFileChoseCallback(new FileChooseCallback() {
            @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                fileChooser = new FileChooser(context, WebAdapterView.this, uploadMsg);
                fileChooser.choose();
            }

            @Override
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                fileChooser = new FileChooser(context, WebAdapterView.this, uploadMsg, acceptType, null);
                fileChooser.choose();
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                fileChooser = new FileChooser(context, WebAdapterView.this, uploadMsg, acceptType, capture);
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

    private String cookieCache = null;

    private void isCookieChange() {
        String cookie = WebViewContext.getInstance().getWebViewCookie("http://m.baidu.com");
        if(cookieCache == null) {
            cookieCache = cookie;
        } else {
            if(cookie != null && !cookie.equals(cookieCache)) {
                LogCat.d("COOKIE2", "cookie 改变 ");
                LogCat.d("COOKIE2", "cookie=" + cookie);
                LogCat.d("COOKIE2", "cookieCache=" + cookieCache);
                UIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        WebViewContext.getInstance().saveWebViewCookieToLocal(getContext(), "http://m.baidu.com");
                    }
                });
            }
        }
    }

    /**
     * 添加 js callback 配置 默认名称  jsApiBridge
     * @param jsCallbackSupport
     */
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

//    public void addJsCallback(int configResId, String name) {
//        super.setJSCallback(new JSCallbackSupport((Activity) getContext(), WebAdapterView.this, configResId), name);
//    }

    /**
     * 重写USER AGENT
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        super.setUseragent(userAgent);
    }

    /**
     * 保留当前浏览器内核USER AGENT，并添加新字符串
     * @param userAgent
     */
    public void addUserAgent(String userAgent) {
        String fixUserAgent = getSettings().getUserAgentString() + " " + userAgent +
                " width=" + DisplayUtil.getWidth(context) + " height=" + DisplayUtil.getHeight(context);
        LogCat.d("UA", fixUserAgent);
        super.setUserAgentString(fixUserAgent);
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
     * 添加请求拦截机器
     */
    List<InterceptRequest> interceptRequests = new ArrayList<>();
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

    private synchronized void syncCookie(String url) {
//        String cookie = WebViewContext.getWebViewCookie(url);
//        WebViewContext.syncCookie(url, cookie);
//        WebViewContext.syncCookie(url);
        LogCat.d("COOKIE", "");
        LogCat.d("COOKIE", url);
        LogCat.d("COOKIE", "[COOKIE]: " + "  " + xWalkCookieManager.getCookie(url));

        LogCat.d("COOKIE", "");
    }

}
