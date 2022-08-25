package util.jar;

import com.google.common.collect.Lists;
import org.apache.commons.collections.EnumerationUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import util.constants.FileSuffixConstants;
import util.constants.ProtocolConstants;
import util.constants.SeparatorConstants;
import util.filter.JarEntryFilter;
import util.net.UrlUtils;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Jar Utility class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see JarEntry
 * @see JarFile
 */
public class JarUtils {

    /**
     * Create a {@link JarFile} from specified {@link URL} of {@link JarFile}
     *
     * @param jarUrl {@link URL} of {@link JarFile} or {@link JarEntry}
     * @return JarFile
     * @throws IOException If {@link JarFile jar file} is invalid, see {@link JarFile#JarFile(String)}
     */
    public static JarFile toJarFile(URL jarUrl) throws IOException {
        JarFile jarFile;
        final String jarAbsolutePath = resolveJarAbsolutePath(jarUrl);
        jarFile = new JarFile(jarAbsolutePath);
        return jarFile;
    }

    /**
     * Assert <code>jarUrl</code> argument is valid , only supported protocols:
     * {@link ProtocolConstants#JAR jar} and
     * {@link ProtocolConstants#FILE file}
     *
     * @param jarUrl {@link URL} of {@link JarFile} or {@link JarEntry}
     * @throws NullPointerException     If <code>jarUrl</code> is <code>null</code>
     * @throws IllegalArgumentException If {@link URL#getProtocol()} is not {@link ProtocolConstants#JAR jar} or {@link ProtocolConstants#FILE
     *                                  file}
     */
    protected static void assertJarUrlProtocol(URL jarUrl) throws NullPointerException, IllegalArgumentException {
        // NPE check
        final String protocol = jarUrl.getProtocol();
        if (!ProtocolConstants.JAR.equals(protocol) && !ProtocolConstants.FILE.equals(protocol)) {
            String message = String.format("jarUrl Protocol[%s] is unsupported ,except %s and %s ", protocol, ProtocolConstants.JAR, ProtocolConstants.FILE);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Resolve Relative path from Jar URL
     *
     * @param jarUrl {@link URL} of {@link JarFile} or {@link JarEntry}
     * @return Non-null
     * @throws NullPointerException     see {@link #assertJarUrlProtocol(URL)}
     * @throws IllegalArgumentException see {@link #assertJarUrlProtocol(URL)}
     */
    @Nonnull
    public static String resolveRelativePath(URL jarUrl) throws NullPointerException, IllegalArgumentException {
        assertJarUrlProtocol(jarUrl);
        String form = jarUrl.toExternalForm();
        String relativePath = StringUtils.substringAfter(form, SeparatorConstants.ARCHIVE_ENTITY);
        relativePath = UrlUtils.resolvePath(relativePath);
        return UrlUtils.decode(relativePath);
    }

    /**
     * Resolve absolute path from the {@link URL} of {@link JarEntry}
     *
     * @param jarUrl {@link URL} of {@link JarFile} or {@link JarEntry}
     * @return If {@link URL#getProtocol()} equals <code>jar</code> or <code>file</code> , resolves absolute path, or
     * return <code>null</code>
     * @throws NullPointerException     see {@link #assertJarUrlProtocol(URL)}
     * @throws IllegalArgumentException see {@link #assertJarUrlProtocol(URL)}
     */
    @Nonnull
    public static String resolveJarAbsolutePath(URL jarUrl) throws NullPointerException, IllegalArgumentException {
        assertJarUrlProtocol(jarUrl);
        File archiveFile = UrlUtils.resolveArchiveFile(jarUrl, FileSuffixConstants.JAR);
        return archiveFile == null ? "" : archiveFile.getAbsolutePath();
    }

    /**
     * Filter {@link JarEntry} list from {@link JarFile}
     *
     * @param jarFile        {@link JarFile}
     * @param jarEntryFilter {@link JarEntryFilter}
     * @return Read-only List
     */
    @Nonnull
    public static List<JarEntry> filter(JarFile jarFile, JarEntryFilter jarEntryFilter) {
        if (jarFile == null) {
            return Collections.emptyList();
        }
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        List<JarEntry> jarEntriesList = EnumerationUtils.toList(jarEntries);
        return doFilter(jarEntriesList, jarEntryFilter);
    }

    protected static List<JarEntry> doFilter(Iterable<JarEntry> jarEntries, JarEntryFilter jarEntryFilter) {
        List<JarEntry> jarEntriesList = Lists.newLinkedList();
        for (JarEntry jarEntry : jarEntries) {
            if (jarEntryFilter == null || jarEntryFilter.accept(jarEntry)) {
                jarEntriesList.add(jarEntry);
            }
        }
        return Collections.unmodifiableList(jarEntriesList);
    }

    /**
     * Find {@link JarEntry} from specified <code>url</code>
     *
     * @param jarUrl jar resource url
     * @return If found , return {@link JarEntry}
     */
    public static JarEntry findJarEntry(URL jarUrl) throws IOException {
        JarFile jarFile = JarUtils.toJarFile(jarUrl);
        final String relativePath = JarUtils.resolveRelativePath(jarUrl);
        return jarFile.getJarEntry(relativePath);
    }


    /**
     * Extract the source {@link JarFile} to target directory
     *
     * @param jarSourceFile   the source {@link JarFile}
     * @param targetDirectory target directory
     * @throws IOException When the source jar file is an invalid {@link JarFile}
     */
    public static void extract(File jarSourceFile, File targetDirectory) throws IOException {
        extract(jarSourceFile, targetDirectory, null);
    }

    /**
     * Extract the source {@link JarFile} to target directory with specified {@link JarEntryFilter}
     *
     * @param jarSourceFile   the source {@link JarFile}
     * @param targetDirectory target directory
     * @param jarEntryFilter  {@link JarEntryFilter}
     * @throws IOException When the source jar file is an invalid {@link JarFile}
     */
    public static void extract(File jarSourceFile, File targetDirectory, JarEntryFilter jarEntryFilter) throws IOException {

        final JarFile jarFile = new JarFile(jarSourceFile);

        extract(jarFile, targetDirectory, jarEntryFilter);
    }

    /**
     * Extract the source {@link JarFile} to target directory with specified {@link JarEntryFilter}
     *
     * @param jarFile         the source {@link JarFile}
     * @param targetDirectory target directory
     * @param jarEntryFilter  {@link JarEntryFilter}
     * @throws IOException When the source jar file is an invalid {@link JarFile}
     */
    public static void extract(JarFile jarFile, File targetDirectory, JarEntryFilter jarEntryFilter) throws IOException {
        List<JarEntry> jarEntriesList = filter(jarFile, jarEntryFilter);
        doExtract(jarFile, jarEntriesList, targetDirectory);
    }

    /**
     * Extract the source {@link JarFile} to target directory with specified {@link JarEntryFilter}
     *
     * @param jarResourceUrl  The resource URL of {@link JarFile} or {@link JarEntry}
     * @param targetDirectory target directory
     * @param jarEntryFilter  {@link JarEntryFilter}
     * @throws IOException When the source jar file is an invalid {@link JarFile}
     */
    public static void extract(URL jarResourceUrl, File targetDirectory, JarEntryFilter jarEntryFilter) throws IOException {
        final JarFile jarFile = JarUtils.toJarFile(jarResourceUrl);
        final String relativePath = JarUtils.resolveRelativePath(jarResourceUrl);
        final JarEntry jarEntry = jarFile.getJarEntry(relativePath);
        final boolean isDirectory = jarEntry.isDirectory();
        List<JarEntry> jarEntriesList = filter(jarFile, new JarEntryFilter() {
            @Override
            public boolean accept(JarEntry filteredObject) {
                String name = filteredObject.getName();
                if (isDirectory && name.equals(relativePath)) {
                    return true;
                } else if (name.startsWith(relativePath)) {
                    return true;
                }
                return false;
            }
        });

        jarEntriesList = doFilter(jarEntriesList, jarEntryFilter);

        doExtract(jarFile, jarEntriesList, targetDirectory);
    }

    protected static void doExtract(JarFile jarFile, Iterable<JarEntry> jarEntries, File targetDirectory) throws IOException {
        if (jarEntries != null) {
            for (JarEntry jarEntry : jarEntries) {
                String jarEntryName = jarEntry.getName();
                File targetFile = new File(targetDirectory, jarEntryName);
                if (jarEntry.isDirectory()) {
                    targetFile.mkdirs();
                } else {
                    try (InputStream inputStream = jarFile.getInputStream(jarEntry);) {
                        if (inputStream != null) {
                            File parentFile = targetFile.getParentFile();
                            if (!parentFile.exists()) {
                                parentFile.mkdirs();
                            }
                            try (OutputStream outputStream = new FileOutputStream(targetFile)) {
                                IOUtils.copy(inputStream, outputStream);
                            }
                        }
                    }
                }
            }
        }
    }
}
