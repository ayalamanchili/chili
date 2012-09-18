package info.chili.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
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

    public static void reset() {
        httpclient = null;
    }

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
