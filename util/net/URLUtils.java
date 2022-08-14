package util.net;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import util.constants.Symbol;
import util.constants.PathConstants;
import util.constants.ProtocolConstants;
import util.constants.SeparatorConstants;
import util.jar.JarUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * {@link URL} Utility class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see URL
 * @see URLEncoder
 * @see URLDecoder
 */
public abstract class URLUtils {


    /**
     * default encoding - utf8
     */
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Resolve Relative path from Archive File URL
     *
     * @param archiveFileURL Archive File URL
     * @return Relative path in archive
     * @throws NullPointerException <code>archiveFileURL</code> is <code>null</code>
     */
    public static String resolveRelativePath(URL archiveFileURL) throws NullPointerException {
        // NPE check
        String path = archiveFileURL.getPath();
        if (path.contains(SeparatorConstants.ARCHIVE_ENTITY)) {
            String relativePath = StringUtils.substringAfterLast(path, SeparatorConstants.ARCHIVE_ENTITY);
            return decode(relativePath);
        }
        return null;
    }

    /**
     * Resolve archive file
     *
     * @param archiveFileURL           archive file  URL
     * @param archiveFileExtensionName archive file extension name
     * @return Resolve archive file If exists
     * @throws NullPointerException An {@code NullPointerException} may be thrown.
     */
    public static File resolveArchiveFile(URL archiveFileURL, String archiveFileExtensionName) throws NullPointerException {
        String archiveFilePath = archiveFileURL.getPath();
        String prefix = ":/";
        boolean hasJarEntryPath = archiveFilePath.contains(SeparatorConstants.ARCHIVE_ENTITY);
        String suffix = hasJarEntryPath ? SeparatorConstants.ARCHIVE_ENTITY : archiveFileExtensionName;
        String jarPath = StringUtils.substringBetween(archiveFilePath, prefix, suffix);
        File archiveFile = null;
        if (StringUtils.isNotBlank(jarPath)) {
            jarPath = PathConstants.SLASH + URLUtils.decode(jarPath);
            archiveFile = new File(jarPath);
            archiveFile = archiveFile.exists() ? archiveFile : null;
        }
        return archiveFile;
    }


    /**
     * Resolve parameters {@link Map} from specified URL，The parameter name as key ，parameter value list as key
     *
     * @param url URL
     * @return Non-null and Read-only {@link Map} , the order of parameters is determined by query string
     */
    @Nonnull
    public static Map<String, List<String>> resolveParametersMap(String url) {
        String queryString = StringUtils.substringAfterLast(url, SeparatorConstants.QUERY_STRING);
        if (StringUtils.isNotBlank(queryString)) {
            Map<String, List<String>> parametersMap = Maps.newLinkedHashMap();
            String[] queryParams = StringUtils.split(queryString, Symbol.AND);
            if (queryParams != null) {
                for (String queryParam : queryParams) {
                    String[] paramNameAndValue = StringUtils.split(queryParam, Symbol.EQUAL);
                    if (paramNameAndValue.length > 0) {
                        String paramName = paramNameAndValue[0];
                        String paramValue = paramNameAndValue.length > 1 ? paramNameAndValue[1] : StringUtils.EMPTY;
                        List<String> paramValueList = parametersMap.computeIfAbsent(paramName, k -> Lists.newLinkedList());
                        paramValueList.add(paramValue);
                    }
                }
            }
            return Collections.unmodifiableMap(parametersMap);
        } else {
            return Collections.emptyMap();
        }
    }

    /**
     * Normalize Path(maybe from File or URL), will remove duplicated slash or backslash from path. For example,
     * <p/>
     * <code> resolvePath("C:\\Windows\\\\temp") == "C:/Windows/temp"; resolvePath("C:\\\\\Windows\\/temp") ==
     * "C:/Windows/temp"; resolvePath("/home/////index.html") == "/home/index.html"; </code>
     *
     * @param path Path
     * @return a newly resolved path
     */
    public static String resolvePath(final String path) {

        if (StringUtils.isBlank(path)) {
            return path;
        }

        String resolvedPath = path.trim();

        while (resolvedPath.contains(PathConstants.BACK_SLASH)) {
            resolvedPath = StringUtils.replace(resolvedPath, PathConstants.BACK_SLASH, PathConstants.SLASH);
        }

        while (resolvedPath.contains(PathConstants.DOUBLE_SLASH)) {
            resolvedPath = StringUtils.replace(resolvedPath, PathConstants.DOUBLE_SLASH, PathConstants.SLASH);
        }
        return resolvedPath;
    }

    /**
     * Translates a string into <code>application/x-www-form-urlencoded</code> format using a specific encoding scheme.
     * This method uses the supplied encoding scheme to obtain the bytes for unsafe characters.
     * <p/>
     * <em><strong>Note:</strong> The <a href= "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars"> World
     * Wide Web Consortium Recommendation</a> states that UTF-8 should be used. Not doing so may introduce
     * incompatibilities.</em>
     *
     * @param value    <code>String</code> to be translated.
     * @param encoding The name of a supported character encoding</a>.
     * @return the translated <code>String</code>.
     * @throws IllegalArgumentException If the named encoding is not supported
     * @see URLDecoder#decode(java.lang.String, java.lang.String)
     * @since 1.4
     */
    public static String encode(String value, String encoding) throws IllegalArgumentException {
        String encodedValue;
        try {
            encodedValue = URLEncoder.encode(value, encoding);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return encodedValue;
    }

    /**
     * {@link #encode(String, String)} with "UTF-8" encoding
     *
     * @param value the <code>String</code> to decode
     * @return the newly encoded <code>String</code>
     */
    public static String encode(String value) {
        return encode(value, DEFAULT_ENCODING);
    }

    /**
     * {@link #decode(String, String)} with "UTF-8" encoding
     *
     * @param value the <code>String</code> to decode
     * @return the newly decoded <code>String</code>
     */
    public static String decode(String value) {
        return decode(value, DEFAULT_ENCODING);
    }

    /**
     * Decodes a <code>application/x-www-form-urlencoded</code> string using a specific encoding scheme. The supplied
     * encoding is used to determine what characters are represented by any consecutive sequences of the form
     * "<code>%<i>xy</i></code>".
     * <p/>
     * <em><strong>Note:</strong> The <a href= "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars"> World
     * Wide Web Consortium Recommendation</a> states that UTF-8 should be used. Not doing so may introduce
     * incompatibilities.</em>
     *
     * @param value    the <code>String</code> to decode
     * @param encoding The name of a supported encoding
     * @return the newly decoded <code>String</code>
     * @throws IllegalArgumentException If character encoding needs to be consulted, but named character encoding is not supported
     */
    public static String decode(String value, String encoding) throws IllegalArgumentException {
        String decodedValue = null;
        try {
            decodedValue = URLDecoder.decode(value, encoding);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return decodedValue;
    }

    /**
     * Is directory URL?
     *
     * @param url URL
     * @return if directory, return <code>true</code>
     */
    public static boolean isDirectoryURL(URL url) {
        boolean isDirectory = false;
        if (url != null) {
            String protocol = url.getProtocol();
            try {
                if (ProtocolConstants.JAR.equals(protocol)) {
                    // Test whether valid jar or not
                    JarFile jarFile = JarUtils.toJarFile(url);
                    final String relativePath = JarUtils.resolveRelativePath(url);
                    // root directory in jar
                    if (StringUtils.EMPTY.equals(relativePath)) {
                        isDirectory = true;
                    } else {
                        JarEntry jarEntry = jarFile.getJarEntry(relativePath);
                        isDirectory = jarEntry != null && jarEntry.isDirectory();
                    }
                } else if (ProtocolConstants.FILE.equals(protocol)) {
                    File classPathFile = new File(url.toURI());
                    isDirectory = classPathFile.isDirectory();
                }
            } catch (Exception e) {
                isDirectory = false;
            }
        }
        return isDirectory;
    }

    /**
     * Is Jar URL?
     *
     * @param url URL
     * @return If jar , return <code>true</code>
     */
    public static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        boolean flag = false;
        if (ProtocolConstants.FILE.equals(protocol)) {
            try {
                File file = new File(url.toURI());
                JarFile jarFile = new JarFile(file);
                flag = true;
            } catch (Exception e) {
                ///
            }
        } else if (ProtocolConstants.JAR.equals(protocol)) {
            flag = true;
        }
        return flag;
    }

    /**
     * Build multiple paths to URI
     *
     * @param paths multiple paths
     * @return URI
     */
    public static String buildURI(String... paths) {
        int length = ArrayUtils.getLength(paths);
        if (length < 1) {
            return PathConstants.SLASH;
        }

        StringBuilder uriBuilder = new StringBuilder(PathConstants.SLASH);
        for (int i = 0; i < length; i++) {
            String path = paths[i];
            uriBuilder.append(path);
            if (i < length - 1) {
                uriBuilder.append(PathConstants.SLASH);
            }
        }

        return resolvePath(uriBuilder.toString());
    }
}
