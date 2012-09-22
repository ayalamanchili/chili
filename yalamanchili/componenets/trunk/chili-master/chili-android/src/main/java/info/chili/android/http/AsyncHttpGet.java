package info.chili.android.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * http://labs.makemachine.net/2010/05/android-asynctask-example/
 */
/**
 * http://www.brighthub.com/mobile/google-android/articles/82805.aspx
 */
public abstract class AsyncHttpGet extends AsyncTask<HttpRequest, Integer, String> {

    protected DefaultHttpClient httpclient;
    protected HttpResponse response;
    protected ProgressDialog dialog;
    protected Map<String, String> headers = new HashMap<String, String>();

    public AsyncHttpGet(Activity activity) {
        dialog = new ProgressDialog(activity);
        httpclient = HttpHelper.getHttpClient();
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading...");
        dialog.show();
    }

    /**
     * do stuff in different thread
     */
    @Override
    protected String doInBackground(HttpRequest... requestParams) {
        if (requestParams == null || requestParams.length > 1
                || requestParams[0].getHeaders() == null
                || requestParams[0].getUrl() == null) {
            throw new IllegalArgumentException("Invalid request object");
        }
        String result = "";
        HttpRequest request = requestParams[0];
        Log.d(AsyncHttpPut.class.getName(), "HttpGetURL:" + request.getUrl());
        Log.d(AsyncHttpPut.class.getName(), "Headers:" + request.getHeaders());

        this.publishProgress(0);
        try {
            HttpGet getRequest = new HttpGet(request.getUrl());
            for (String headerKey : request.getHeaders().keySet()) {
                getRequest.setHeader(headerKey, request.getHeaders().get(headerKey));
            }
            response = httpclient.execute(getRequest);
        } catch (Exception e) {
            throw new RuntimeException("Http Get called failed for uri:"
                    + request.getUrl() + e);
        }
        return result;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();
        StatusLine status = response.getStatusLine();
        Log.d("debug", "HttpGet Response code" + status.getStatusCode());
        /* http response success */
        if (status.getStatusCode() >= 200 && status.getStatusCode() <= 399) {
            result = HttpHelper.convertResponseBody(response);
            onResponse(result);
        } /* http response failure */ else {
            throw new RuntimeException("http get call failed with status code:"
                    + status.getStatusCode());
        }
    }

    protected abstract void onResponse(String result);

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
}
