package cn.wilson.hotblogs.dao;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by KingFlyer on 2015/6/30.
 */
public class XMLVolleyRequest extends Request<XmlPullParser> {
    private Response.Listener<XmlPullParser> mListener;

    public XMLVolleyRequest(int method, String url, Response.Listener<XmlPullParser> xmlRequestListener,Response.ErrorListener listener) {
        super(method, url, listener);
        this.mListener = xmlRequestListener;
    }

    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
        try {
            String xmlString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlString));
            return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (XmlPullParserException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(XmlPullParser response) {
        mListener.onResponse(response);
    }

    /**
     * 设置超时
     */
    @Override
    public RetryPolicy getRetryPolicy() {
        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return retryPolicy;
    }

}