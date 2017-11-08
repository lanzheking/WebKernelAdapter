package main.java.com.honestwalker.android.webkerneladapter;

import android.util.Log;

/**
 * Created by lanzhe on 17-11-8.
 */
class ExceptionUtil {

    public final static String TAG = "AndroidRuntime";

    public static void showException(String tag, Throwable throwable) {
        StackTraceElement[] stes = throwable.getStackTrace();
        Log.e(tag,"<=======================KancartException====================>");
        Log.e(tag,throwable.getMessage() + " " + throwable.toString());
        for (StackTraceElement ste : stes) {
            Log.e(tag,ste.toString());
        }

        if(throwable.getCause() != null) {
            Log.e(tag,"<================KancartException Cause=====================>");
            Log.e(tag,throwable.getCause().toString());
            StackTraceElement[] stesCause = throwable.getCause().getStackTrace();
            if(stesCause != null) {
                for (StackTraceElement ste : stesCause) {
                    Log.e(tag,ste.toString());
                }
            }
        }
        Log.e(tag,"<============================================================>");
    }

    public static void showException(String tag,Exception e) {
        StackTraceElement[] stes = e.getStackTrace();
        Log.e(tag,"<=======================KancartException====================>");
        Log.e(tag,e.getMessage() + " " + e.toString());
        for (StackTraceElement ste : stes) {
            Log.e(tag,ste.toString());
        }

        if(e.getCause() != null) {
            Log.e(tag,"<================KancartException Cause=====================>");
            Log.e(tag,e.getCause().toString());
            StackTraceElement[] stesCause = e.getCause().getStackTrace();
            if(stesCause != null) {
                for (StackTraceElement ste : stesCause) {
                    Log.e(tag,ste.toString());
                }
            }
        }
        Log.e(tag,"<============================================================>");
    }

    public static void showException(Exception e) {
        showException(TAG, e);
    }

    public static void showException(Throwable e) {
        showException(TAG, e);
    }
}
