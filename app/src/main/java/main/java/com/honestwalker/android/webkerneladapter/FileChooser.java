package main.java.com.honestwalker.android.webkerneladapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.honestwalker.android.webkerneladapter.main.BuildConfig;
import com.honestwalker.androidutils.IO.LogCat;

/**
 * Created by lanzhe on 17-6-22.
 */
public class FileChooser {

    private WebAdapterView webView;
    private Activity activity;
    private String acceptType;
    private String capture;
    private ValueCallback uploadMessage;

    private static final String TAG = "FILE";

    public static final int WEB_FILE_CHOOSE_REQUEST_CODE = 702;

    public FileChooser(Activity context, WebAdapterView WebAdapterView, ValueCallback uploadMessage) {
        this(context, WebAdapterView, uploadMessage, null, null);
    }

    public FileChooser(Activity context, WebAdapterView webView, ValueCallback uploadMessage, String acceptType, String capture) {
        this.activity = context;
        this.uploadMessage = uploadMessage;
        this.acceptType = acceptType;
        this.capture = capture;
        this.webView = webView;
    }

    public void choose() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), WEB_FILE_CHOOSE_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == WEB_FILE_CHOOSE_REQUEST_CODE) {
            if(resultCode == activity.RESULT_OK) {
                if( uploadMessage != null ) {
                    Uri uri = intent.getData();
                    if("webview".equals(BuildConfig.WEB_KERNEL) && Build.VERSION.SDK_INT >= 21) {   // 5.0 以后是Uri[]
                        Uri[] uris = new Uri[1];
                        uris[0] = uri;
                        uploadMessage.onReceiveValue(uris);
                    } else {
                        uploadMessage.onReceiveValue(uri);
                    }
                    LogCat.i(TAG, "uri = "+ uri);
                }
            } else {
                uploadMessage.onReceiveValue(null);
            }
        }
    }

    public void resetFileChose() {
        if(uploadMessage != null) uploadMessage.onReceiveValue(null);
    }

    public ValueCallback getUploadMessage() {
        return this.uploadMessage;
    }

}
