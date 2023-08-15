package util.resource;

import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link PropertyResourceBundle} {@link ResourceBundle.Control} Implementation which supports encoding {@link
 * Properties} files
 *
 * @author zqw
 * @see PropertyResourceBundle
 * @see ResourceBundle.Control
 */
public class PropertyResourceBundleControl extends ResourceBundle.Control {

    private static final ConcurrentMap<String, ResourceBundle.Control> ENCODING_CONTROL_MAP = new ConcurrentHashMap<>();

    static {
        addEncodingControlMap(newControl(SystemUtils.FILE_ENCODING));
        addEncodingControlMap(newControl("UTF-8"));
    }

    private final String encoding;

    protected PropertyResourceBundleControl(final String encoding) {
        this.encoding = encoding;
    }

    protected PropertyResourceBundleControl() {
        this(SystemUtils.FILE_ENCODING);
    }

    private static void addEncodingControlMap(ResourceBundle.Control control) {
        PropertyResourceBundleControl ctrl = (PropertyResourceBundleControl) control;
        ENCODING_CONTROL_MAP.putIfAbsent(ctrl.getEncoding(), ctrl);
    }

    /**
     * Gets an existed instance of {@link PropertyResourceBundleControl}.
     *
     * @param encoding {@code encoding}
     * @return an existed instance of {@link PropertyResourceBundleControl}.
     */
    private static ResourceBundle.Control getControl(final String encoding) {
        return ENCODING_CONTROL_MAP.get(encoding);
    }

    /**
     * Creates a new instance of {@link PropertyResourceBundleControl} if absent.
     *
     * @param encoding
     *         Encoding
     * @return Control
     * @throws UnsupportedCharsetException
     *         If <code>encoding</code> is not supported
     */
    public static ResourceBundle.Control newControl(final String encoding) throws UnsupportedCharsetException {
        // check encoding
        Charset.forName(encoding);
        final ResourceBundle.Control existedControl = getControl(encoding);
        ResourceBundle.Control control = existedControl;
        if (existedControl == null) {
            control = new PropertyResourceBundleControl(encoding);
            ENCODING_CONTROL_MAP.put(encoding, control);
        }
        return control;
    }

    @Override
    public final List<String> getFormats(String baseName) {
        if (baseName == null) {
            throw new NullPointerException();
        }
        return FORMAT_PROPERTIES;
    }


    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, final ClassLoader classLoader, final boolean reload) throws IOException {
        String bundleName = super.toBundleName(baseName, locale);
        final String resourceName = super.toResourceName(bundleName, "properties");
        InputStream stream;
        ResourceBundle bundle = null;
        try {
            stream = AccessController.doPrivileged((PrivilegedExceptionAction<InputStream>) () -> {
                InputStream is = null;
                if (reload) {
                    URL url = classLoader.getResource(resourceName);
                    if (url != null) {
                        URLConnection connection = url.openConnection();
                        if (connection != null) {
                            // Disable caches to get fresh data for
                            // reloading.
                            connection.setUseCaches(false);
                            is = connection.getInputStream();
                        }
                    }
                } else {
                    is = classLoader.getResourceAsStream(resourceName);
                }
                return is;
            });
        } catch (PrivilegedActionException e) {
            throw (IOException) e.getException();
        }

        if (stream != null) {

            try ( Reader reader = new InputStreamReader(stream, this.getEncoding())){
                 bundle = new PropertyResourceBundle(reader);
            }
        }
        return bundle;
    }

    /**
     * Sets the encoding of properties file.
     *
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }
}
