package main.java.com.honestwalker.android.webkerneladapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.webkit.ValueCallback;

import com.honestwalker.android.webkerneladapter.WebAdapterView;

import main.java.com.honestwalker.android.webkerneladapter.utils.ClassUtil;

/**
 * Created by lanzhe on 17-10-17.
 */

public class WebUIClient<C,WEBVIEW> {

    private Context context;

    public WebUIClient(Context context) {
        this.context = context;
    }

    private C uiClient;
    public void setUIClient(C uiClient) {
        this.uiClient = uiClient;
    }

    private void invokeMethod(Object object, String methodName, Object... params) {
        try {
            ClassUtil.callMethod(uiClient, "onJsAlert", params);
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
    }

    public boolean onJsAlert(WEBVIEW view, String url, String message, Object result) {
        boolean intercept = false;
        if(uiClient != null) {
            try {
                intercept = (boolean) ClassUtil.callMethod(uiClient, "onJsAlert", view, url, message, result);
            } catch (Exception e) {
            }
        }
        if(intercept) return true;

        DialogHelper.alert(context, "", message);

        return true;
    }

    
    public boolean onJsConfirm(WebAdapterView view, String url, String message, final Object result) {

        boolean intercept = false;
        if(uiClient != null) {
            try {
                intercept = (boolean) ClassUtil.callMethod(uiClient, "onJsConfirm", view, url, message, result);
            } catch (Exception e) {
            }
        }
        if(intercept) return true;

        DialogHelper.alert(view.getContext(), "", message, "确定", new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    ClassUtil.callMethod(result, "confirm");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, true, "取消", new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    ClassUtil.callMethod(result, "cancel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    
//    public boolean onJsPrompt(XWalkView view, String url, String message, String defaultValue, XWalkJavascriptResult result) {
//        return super.onJsPrompt(view, url, message, defaultValue, result);
//    }

    // For Android 3.0+
    public void openFileChooser(Activity activity, ValueCallback<Uri> uploadMsg) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), 2000);

    }

    // For Android 3.0+
    public void openFileChooser(Activity activity, ValueCallback uploadMsg, String acceptType) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        activity.startActivityForResult(
                Intent.createChooser(i, "File Browser"),
                3000);
    }

    //For Android 4.1
    public void openFileChooser(Activity activity, ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), 10000);
    }

}
