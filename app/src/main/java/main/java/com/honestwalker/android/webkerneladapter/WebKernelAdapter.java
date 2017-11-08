package main.java.com.honestwalker.android.webkerneladapter;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by lanzhe on 17-7-27.
 */

abstract class WebKernelAdapter extends LinearLayout {

    private AttributeSet attrs;
    private int defStyleAttr;
    private int defStyleRes;

    public WebKernelAdapter(Context context) {
        super(context);
    }

    public WebKernelAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
    }

    public WebKernelAdapter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebKernelAdapter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
        this.defStyleRes = defStyleRes;
    }

    protected AttributeSet getAttrs() {
        return attrs;
    }

    protected int getDefStyleAttr() {
        return defStyleAttr;
    }

    protected int getDefStyleRes() {
        return defStyleRes;
    }

    public abstract void loadUrl(String url);

}
