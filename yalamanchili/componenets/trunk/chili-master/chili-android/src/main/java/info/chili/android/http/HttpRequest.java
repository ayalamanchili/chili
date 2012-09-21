package info.chili.android.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	protected String url;
	protected String body;
	protected Map<String, String> headers;

	public HttpRequest(String url, String body, Map<String, String> headers) {
		super();
		this.url = url;
		this.body = body;
		this.headers = headers;
	}

	public HttpRequest() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void addHeader(String key, String value) {
		getHeaders().put(key, value);
	}
}
