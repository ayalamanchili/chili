package info.chili.android.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHelper {
	protected static DefaultHttpClient httpclient;

	public static DefaultHttpClient getHttpClient() {
		if (httpclient == null) {
			httpclient = new DefaultHttpClient();
			return httpclient;
		} else {
			return httpclient;
		}
	}

	public static void setCredentials(String username, String password) {
		getHttpClient().getCredentialsProvider().setCredentials(
				new AuthScope(null, -1),
				new UsernamePasswordCredentials(username, password));
	}

	public static String convertResponseBody(HttpResponse response) {
		String result = "";
		BufferedReader reader = null;
		InputStream in = null;
		try {
			in = response.getEntity().getContent();
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}
			reader.close();
			in.close();
			result = str.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
