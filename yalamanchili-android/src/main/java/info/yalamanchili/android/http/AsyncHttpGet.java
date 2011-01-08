package info.yalamanchili.android.http;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public abstract class AsyncHttpGet extends AsyncTask<String, Integer, String> {
	protected DefaultHttpClient httpclient;
	protected HttpResponse response;
	protected ProgressDialog dialog;

	public AsyncHttpGet(Activity activity) {
		dialog = new ProgressDialog(activity);
		httpclient = HttpHelper.getHttpClient();
	}

	@Override
	protected void onPreExecute() {
		dialog.setMessage("Loading...");
		dialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		Log.d("debug", "HttpGetURI" + arg0[0]);
		String result = "";
		this.publishProgress(0);
		try {
			response = httpclient.execute(new HttpGet(arg0[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected void onProgressUpdate(Integer... progress) {

	}

	protected void onPostExecute(String result) {
		dialog.dismiss();
		StatusLine status = response.getStatusLine();
		Log.d("debug", "HttpGet Response code" + status.getStatusCode());
		/* http response success */
		if (status.getStatusCode() >= 200 && status.getStatusCode() <= 300) {
		//TODO FIX this is needed for get(working) for put this is not necessary
			result = HttpHelper.request(response);
			onResponse(result);
		} /* http response failure */
		else {
			throw new RuntimeException("http call failed with status code:"
					+ status.getStatusCode());
		}
	}

	protected abstract void onResponse(String result);

}