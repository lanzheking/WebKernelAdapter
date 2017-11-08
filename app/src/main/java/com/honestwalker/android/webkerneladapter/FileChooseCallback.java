package com.honestwalker.android.webkerneladapter;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by lanzhe on 17-10-25.
 */

public interface FileChooseCallback {

    /** For Android 3.0+ */
    void openFileChooser(ValueCallback<Uri> uploadMsg);

    /** For Android 3.0+ */
    void openFileChooser(ValueCallback uploadMsg, String acceptType);

    /** For Android 4.1+ */
    void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture);

    /** For Android 5.0+ */
    void openFileChooser(ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams);

}
