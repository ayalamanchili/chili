/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.server;

import com.google.gwt.thirdparty.json.JSONObject;
import info.chili.gwt.rpc.HttpService;
import info.chili.http.SyncHttp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ayalamanchili
 */
@RequestMapping("/**/httpService")
public abstract class AbstractHttpService extends BaseRemoteService implements HttpService {
    
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger(AbstractHttpService.class.getName());
    
    @Override
    public String login(String username, String password) throws Exception {
        populateAuthorizationHeader(username, password);
        JSONObject user = new JSONObject();
        user.put("username", username.toLowerCase());
        user.put("passwordHash", password);
        return doPut(getLoginPath(), user.toString(), addHeaders(new HashMap<String, String>(), ""), true);
    }
    
    @Override
    public String doPut(String url, String body, Map<String, String> headers, boolean newClient) {
        return SyncHttp.httpPut(getServicesRootURL() + url, body,
                addHeaders(headers, url), newClient);
    }
    
    @Override
    public String doGet(String url, Map<String, String> headers, boolean newClient) {
        return SyncHttp.httpGet(getServicesRootURL() + url,
                addHeaders(headers, url), newClient);
    }
    
    @Override
    public void logout() throws Exception {
        this.getThreadLocalRequest().getSession().invalidate();
    }
    
    protected Map<String, String> addHeaders(Map<String, String> headers, String url) {
        for (String attr : headers.keySet()) {
            headers.put(attr, headers.get(attr));
        }
        //TODO this should be set on client
        headers.put("Content-Type", "application/json");
        if (this.getThreadLocalRequest().getSession().getAttribute(AbstractFileServiceServlet.AUTH_HEADER_ATTR) != null && !url.contains("/public/")) {
            headers.put("Authorization", (String) this.getThreadLocalRequest().getSession().getAttribute(AbstractFileServiceServlet.AUTH_HEADER_ATTR));
        }
        headers.put("User-Agent-X", this.getThreadLocalRequest().getHeader("User-Agent"));
        return headers;
    }
    
    protected void populateAuthorizationHeader(String username, String password) {
        this.getThreadLocalRequest().getSession().removeAttribute(AbstractFileServiceServlet.AUTH_HEADER_ATTR);
        this.getThreadLocalRequest().getSession().setAttribute(AbstractFileServiceServlet.AUTH_HEADER_ATTR, "Basic " + new String(Base64.encodeBase64((username.toLowerCase() + ":" + password).getBytes())));
    }
    
    protected abstract String getServicesRootURL();
    
    protected abstract String getLoginPath();
    
    protected abstract String getPublicUrlPath();
}
