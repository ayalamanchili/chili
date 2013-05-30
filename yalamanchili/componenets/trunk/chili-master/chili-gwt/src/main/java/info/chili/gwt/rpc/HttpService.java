package info.chili.gwt.rpc;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/httpService")
public interface HttpService extends RemoteService {

    public String login(String username, String password) throws Exception;

    public String doPut(String url, String body, Map<String, String> headers, boolean newClient) throws Exception;

    public String doGet(String url, Map<String, String> headers, boolean newClient) throws Exception;

    public void logout() throws Exception;

    /**
     * proxy
     */
    public static class HttpServiceAsync {

        private static info.chili.gwt.rpc.HttpServiceAsync service;

        public static synchronized info.chili.gwt.rpc.HttpServiceAsync instance() {
            if (service == null) {
                service = (info.chili.gwt.rpc.HttpServiceAsync) GWT.create(HttpService.class);
            }
            return service;
        }
    }
}
