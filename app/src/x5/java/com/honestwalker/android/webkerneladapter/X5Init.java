package x5.java.com.honestwalker.android.webkerneladapter;

import android.content.Context;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by lanzhe on 17-9-27.
 */

public class X5Init {

    private static boolean isInit = false;

    public static void init(Context context) {
        if(isInit) return;
        isInit = true;
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        QbSdk.initX5Environment(context,  cb);
    }

}
