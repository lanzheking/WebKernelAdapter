package main.java.com.honestwalker.android.webkerneladapter.utils;

import android.util.Log;

/**
 * Log中输出错误信息
 * @author Administrator
 *
 */
public class ExceptionUtil {
	public static Boolean show = false;
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
		if (e != null) {
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
	}

	public static void showException(Exception e) {
		showException(TAG, e);
	}

	public static void showException(Throwable e) {
		showException(TAG, e);
	}
}