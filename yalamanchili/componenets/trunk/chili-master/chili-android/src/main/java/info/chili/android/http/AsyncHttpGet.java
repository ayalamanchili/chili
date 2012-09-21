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

/** http://labs.makemachine.net/2010/05/android-asynctask-example/ */
/** http://www.brighthub.com/mobile/google-android/articles/82805.aspx */
public abstract class AsyncHttpGet extends AsyncTask<String, Integer, String> {
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

	/** do stuff in different thread */
	@Override
	protected String doInBackground(String... params) {
		Log.d("y-android", "HttpGetURI:" + params[0]);
		String result = "";
		this.publishProgress(0);
		try {
			HttpGet getRequest = new HttpGet(params[0]);
			// DEFAULT
			// TODO need option to override
			getRequest.addHeader("Accept", "application/json");
			response = httpclient.execute(getRequest);
		} catch (Exception e) {
			e.printStackTrace();
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
		} /* http response failure */
		else {
			throw new RuntimeException("http get call failed with status code:"
					+ status.getStatusCode());
		}
	}

	protected abstract void onResponse(String result);

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

}
