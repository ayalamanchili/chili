/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.server;

import info.chili.http.HttpHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

/**
 *
 * @author ayalamanchili
 */
public abstract class AbstractFileServiceServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger(AbstractFileServiceServlet.class.getName());
    public final static String AUTH_HEADER_ATTR = "auth-header";
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "in FileService Post");
        //prepare request
        HttpPost post = new HttpPost(getServiceBaseUrl() + "file/upload");
        HttpHelper.copyHeaders(request, post, "Content-Length", "Host");
        HttpHelper.copyBody(request, post);
        addAuthenticationHeader(post, request);
        //Make call
        HttpResponse resp = HttpHelper.getHttpClient(true).execute(post);
        //process and map response back
        HttpHelper.copyStatusAndHeaders(response, resp);
        HttpHelper.copyBody(response, resp);
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "in FileService Get");
        //prepare request
        String report = request.getParameter("report");
        String url = getServiceBaseUrl();
        if (request.getParameter(report) != null) {
            url = url.concat("file/download?") + request.getQueryString();
        } else {
            url = url.concat(request.getParameter("path"));
        }
        logger.info("FileServiceServlet URL" + url);
        HttpGet get = new HttpGet(url);
        HttpHelper.copyHeaders(request, get, "Content-Length", "Host");
        addAuthenticationHeader(get, request);
        //TODO change to false
        //Make call
        HttpResponse resp = HttpHelper.getHttpClient(true).execute(get);
        //process and map response back
        HttpHelper.copyStatusAndHeaders(response, resp);
        HttpHelper.copyBody(response, resp);
    }
    
    protected void addAuthenticationHeader(HttpRequestBase body, HttpServletRequest request) {
        if (request.getSession(false).getAttribute(AUTH_HEADER_ATTR) != null) {
            body.addHeader("Authorization", (String) request.getSession().getAttribute(AUTH_HEADER_ATTR));
        }
    }
    
    protected abstract String getServiceBaseUrl();
}
