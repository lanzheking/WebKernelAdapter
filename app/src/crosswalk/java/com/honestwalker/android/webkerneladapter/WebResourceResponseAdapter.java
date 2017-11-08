package crosswalk.java.com.honestwalker.android.webkerneladapter;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceResponse;

import java.io.InputStream;

/**
 * Created by lanzhe on 17-10-16.
 */

public class WebResourceResponseAdapter extends XWalkWebResourceResponse {

//    String mimeType, String encoding,
//    InputStream data

    public WebResourceResponseAdapter(String mimeType, String encoding, InputStream data) {
        super(null);
        super.setMimeType(mimeType);
        super.setEncoding(encoding);
        super.setData(data);
    }

    public WebResourceResponseAdapter(String mimeType, String encoding, InputStream data, Object bridge) {
        super(bridge);
        super.setMimeType(mimeType);
        super.setEncoding(encoding);
        super.setData(data);
    }
}
