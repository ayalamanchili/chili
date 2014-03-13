package info.chili.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHelper {

    private final static Logger logger = Logger.getLogger(HttpHelper.class.getName());
    protected static DefaultHttpClient httpclient;

    public static DefaultHttpClient getHttpClient() {
        if (httpclient == null) {
            httpclient = new DefaultHttpClient();
            return httpclient;
        } else {
            return httpclient;
        }
    }

    public static DefaultHttpClient getHttpClient(boolean newClient) {
        if (newClient || httpclient == null) {
            httpclient = new DefaultHttpClient();
        }
        return httpclient;
    }

    public static void copyHeaders(HttpServletRequest servletReq, HttpRequestBase request, String... ingoreHeaders) {
        Enumeration<String> headers = servletReq.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            String headerValue = servletReq.getHeader(headerName);
            if (!toLowerCase(Arrays.asList(ingoreHeaders)).contains(headerName)) {
                request.addHeader(headerName, headerValue);
            }
        }
    }
//TODO use third party lib

    protected static List<String> toLowerCase(List<String> array) {
        List<String> res = new ArrayList<String>();
        for (String str : array) {
            res.add(str.toLowerCase());
        }
        return res;
    }

    public static void copyBody(HttpServletRequest servletReq, HttpEntityEnclosingRequestBase request) {
        int contentLength = servletReq.getContentLength();
        InputStreamEntity entity = null;
        try {
            entity = new InputStreamEntity(servletReq.getInputStream(), contentLength);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        request.setEntity(entity);
    }

    public static void copyStatusAndHeaders(HttpServletResponse servletRes, HttpResponse response, String... ingoreHeaders) {
        StatusLine status = response.getStatusLine();
        servletRes.setStatus(status.getStatusCode());
        Header[] responseHeaders = response.getAllHeaders();
        for (int i = 0; i < responseHeaders.length; i++) {
            Header header = responseHeaders[i];
            servletRes.addHeader(header.getName(), header.getValue());
        }
    }

    public static void copyBody(HttpServletResponse servletRes, HttpResponse response) {
        InputStream input = null;
        OutputStream output = null;
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return;
        }
        try {
            input = entity.getContent();
            output = servletRes.getOutputStream();
            int b = input.read();
            while (b != -1) {
                output.write(b);
                b = input.read();
            }

            input.close();
            output.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void reset() {
        httpclient = null;
    }

    /**
     * Set basic auth credentials
     */
    public static void setCredentials(String username, String password) {
        getHttpClient().getCredentialsProvider().setCredentials(new AuthScope(null, -1),
                new UsernamePasswordCredentials(username, password));
    }

    public static String convertResponse(HttpResponse response) {
        StatusLine status = response.getStatusLine();
        String result = "";
        BufferedReader reader = null;
        InputStream in = null;
        /* http response success */
        logger.info("response code:" + status.getStatusCode());
        try {
            if (response.getEntity() == null || response.getEntity().getContent() == null) {
                return null;
            }
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
        if (status.getStatusCode() >= 200 && status.getStatusCode() <= 399) {
            return result;
        } /* http response failure */ else {
            logger.info("**********call failed****************:" + status.getStatusCode());
            HttpHelper.reset();
            throw new RuntimeException(result);
        }
    }
}
