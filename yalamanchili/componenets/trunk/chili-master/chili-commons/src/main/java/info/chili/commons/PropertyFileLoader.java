/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyLoader.
 */
public class PropertyFileLoader {

    private static final Log log = LogFactory.getLog(PropertyFileLoader.class);

    public static Properties loadProperties(String name, ClassLoader loader) {
        if (name == null) {
            throw new IllegalArgumentException("null input: name");
        }

        if (name.startsWith("/")) {
            name = name.substring(1);
        }

        if (name.endsWith(SUFFIX)) {
            name = name.substring(0, name.length() - SUFFIX.length());
        }

        Properties result = null;

        InputStream in = null;
        try {
            if (loader == null) {
                loader = ClassLoader.getSystemClassLoader();
            }

            if (LOAD_AS_RESOURCE_BUNDLE) {
                name = name.replace('/', '.');
                // Throws MissingResourceException on lookup failures:
                final ResourceBundle rb = ResourceBundle.getBundle(name, Locale
                        .getDefault(), loader);

                result = new Properties();
                for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
                    final String key = (String) keys.nextElement();
                    final String value = rb.getString(key);

                    result.put(key, value);
                }
            } else {
                name = name.replace('.', '/');

                if (!name.endsWith(SUFFIX)) {
                    name = name.concat(SUFFIX);
                }

                // Returns null on lookup failures:
                in = loader.getResourceAsStream(name);
                if (in != null) {
                    result = new Properties();
                    result.load(in); // Can throw IOException
                }
            }
        } catch (Exception e) {
            result = null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Throwable ignore) {
                }
            }
        }

        if (THROW_ON_LOAD_FAILURE && (result == null)) {
            throw new IllegalArgumentException("could not load ["
                    + name
                    + "]"
                    + " as "
                    + (LOAD_AS_RESOURCE_BUNDLE ? "a resource bundle"
                    : "a classloader resource"));
        }

        return result;
    }

    public static Properties loadProperties(final String name) {
        return loadProperties(name, Thread.currentThread()
                .getContextClassLoader());
    }
    /**
     * The Constant THROW_ON_LOAD_FAILURE.
     */
    private static final boolean THROW_ON_LOAD_FAILURE = true;
    /**
     * The Constant LOAD_AS_RESOURCE_BUNDLE.
     */
    private static final boolean LOAD_AS_RESOURCE_BUNDLE = false;
    /**
     * The Constant SUFFIX.
     */
    private static final String SUFFIX = ".properties";
}
