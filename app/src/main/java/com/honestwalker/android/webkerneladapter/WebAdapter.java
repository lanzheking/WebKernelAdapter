package com.honestwalker.android.webkerneladapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.honestwalker.android.webkerneladapter.BuildConfig;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import com.honestwalker.android.webkerneladapter.utils.ClassUtil;
import com.honestwalker.android.webkerneladapter.utils.ExceptionUtil;

/**
 * web 内核适配器
 * Created by lanzhe on 17-7-27.
 */
public class WebAdapter extends WebKernelAdapter {

    private final static String TAG = "WEB";

    /** 系统webview内核 */
    public static final String KERNEL_WEBVIEW = "webView";

    /** 腾讯x5内核 */
    public static final String KERNEL_X5 = "x5";

    /** crosswalker内核 */
    public static final String KERNEL_CROSSWALKER = "crosswalker";

    public static final String PCK_WEBVIEW = "com.honestwalker.android.WebKernel.HtmlWebView";
    public static final String PCK_X5 = "";
    public static final String PCK_CROSSWALKER = "com.honestwalker.android.crosswalk.CrossWalkView";

    public WebAdapter(Context context) {
        super(context);
        init();
    }

    public WebAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebAdapter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public WebAdapter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /** 获取当前选用的web内核，改内核在build.gradle中配置 */
    public static String getKernelId() {
        return  BuildConfig.WEB_KERNEL;
    }

    /** 获取web kernel 对应的view 的 package */
    public static String getKernelPck() {
        if(KERNEL_X5.equals(getKernelId())) {
            return PCK_X5;
        } else if(KERNEL_CROSSWALKER.equals(getKernelId())) {
            return PCK_CROSSWALKER;
        } else {
            return PCK_WEBVIEW;
        }
    }

    private View adapterWebView = null;

    /**
     * 获取当前web内核
     * @return
     */
    public View getWebView() {
        return adapterWebView;
    }

    private void init() {
        View webView = buildWebKernel();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        this.addView(webView);
    }

    private View buildWebKernel() {
        String webKernelPck = getKernelPck();
        try {
            Class webKernelClass = Class.forName(webKernelPck);

            List<Class> constructorClassesList = new ArrayList<>();
            List<Object> constructorList = new ArrayList<>(); // 记录view参数列表
            constructorList.add(getContext());                // 第一参数context
            constructorClassesList.add(Context.class);

            // 添加参数
            if(getAttrs() != null)         {
                constructorList.add(getAttrs());
                constructorClassesList.add(AttributeSet.class);
                if(getDefStyleAttr() > 0) {
                    constructorList.add(getDefStyleAttr());
                    constructorClassesList.add(int.class);
                    if(getDefStyleRes()  > 0) {
                        constructorList.add(getDefStyleRes());
                        constructorClassesList.add(int.class);
                    }
                }
            }

            Object[] constructors = constructorList.toArray();
            //  记录参数类型
            Class[]  constructorsClasses = new Class[constructorClassesList.size()];
            constructorClassesList.toArray(constructorsClasses);

            Constructor constructor = webKernelClass.getDeclaredConstructor(constructorsClasses);
            constructor.setAccessible(true);
            return adapterWebView = (View) constructor.newInstance(constructors);
        } catch (Exception e) {
            ExceptionUtil.showException(TAG, e);
        }
        return adapterWebView = new View(getContext());
    }

    /***************************************************
     *
     *
     *           web kernel interface implements
     *
     * @param url
     **************************************************/

    @Override
    public void loadUrl(String url) {
//        WebView webView = (WebView) getWebView();
//        webView.loadUrl(url);
        try {
            ClassUtil.callMethod(getWebView(), "loadUrl", url);
        } catch (Exception e) {
            ExceptionUtil.showException(TAG, e);
        }
    }

    public void getWebClient(){}

}
