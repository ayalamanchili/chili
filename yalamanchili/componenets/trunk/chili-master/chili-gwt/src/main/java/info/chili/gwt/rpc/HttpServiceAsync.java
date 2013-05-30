package info.chili.gwt.rpc;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HttpServiceAsync {

    public void login(String username, String password,
            AsyncCallback<String> response);

    public void doPut(String url, String body, Map<String, String> headers, boolean newClient,
            AsyncCallback<String> response);

    public void doGet(String url, Map<String, String> headers, boolean newClient, AsyncCallback<String> response);

    public void logout(AsyncCallback<Void> response);
}
