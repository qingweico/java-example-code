package util.io.scanner;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import util.constants.PathConstants;
import util.filter.JarEntryFilter;
import util.jar.JarUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Simple {@link JarEntry} Scanner
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 */
public class SimpleJarEntryScanner {

    /**
     * Singleton
     */
    public static final SimpleJarEntryScanner INSTANCE = new SimpleJarEntryScanner();

    protected SimpleJarEntryScanner() {

    }

    /**
     * @param jarUrl    {@link URL} of {@link JarFile} or {@link JarEntry}
     * @param recursive recursive
     * @return Read-only {@link Set}
     * @throws NullPointerException     If argument <code>null</code>
     * @throws IllegalArgumentException <ul> <li>{@link JarUtils#resolveRelativePath(URL)}
     * @throws IOException              <ul> <li>{@link JarUtils#toJarFile(URL)}
     * @since 1.0.0
     */
    @Nonnull
    public Set<JarEntry> scan(URL jarUrl, final boolean recursive) throws NullPointerException, IllegalArgumentException, IOException {
        return scan(jarUrl, recursive, null);
    }

    /**
     * @param jarUrl         {@link URL} of {@link JarFile} or {@link JarEntry}
     * @param recursive      recursive
     * @param jarEntryFilter {@link JarEntryFilter}
     * @return Read-only {@link Set}
     * @throws NullPointerException     If argument <code>null</code>
     * @throws IllegalArgumentException see {@link JarUtils#resolveJarAbsolutePath(URL)}
     * @throws IOException              see {@link JarUtils#toJarFile(URL)}
     * @see JarEntryFilter
     * @since 1.0.0
     */
    @Nonnull
    public Set<JarEntry> scan(URL jarUrl, final boolean recursive, JarEntryFilter jarEntryFilter) throws NullPointerException, IllegalArgumentException, IOException {
        String relativePath = JarUtils.resolveRelativePath(jarUrl);
        JarFile jarFile = JarUtils.toJarFile(jarUrl);
        return scan(jarFile, relativePath, recursive, jarEntryFilter);
    }


    /**
     * @param jarFile   //
     * @param recursive //
     * @return Set<JarEntry>
     * @throws NullPointerException     //
     * @throws IllegalArgumentException //
     * @throws IOException              //
     */
    public Set<JarEntry> scan(JarFile jarFile, final boolean recursive) throws NullPointerException, IllegalArgumentException, IOException {
        return scan(jarFile, recursive, null);
    }

    /**
     * @param jarFile        //
     * @param recursive      //
     * @param jarEntryFilter //
     * @return Set<JarEntry>
     * @throws NullPointerException     //
     * @throws IllegalArgumentException //
     * @throws IOException              //
     */
    public Set<JarEntry> scan(JarFile jarFile, final boolean recursive, JarEntryFilter jarEntryFilter) throws NullPointerException, IllegalArgumentException, IOException {
        return scan(jarFile, StringUtils.EMPTY, recursive, jarEntryFilter);
    }

    protected Set<JarEntry> scan(JarFile jarFile, String relativePath, final boolean recursive, JarEntryFilter jarEntryFilter) throws NullPointerException, IllegalArgumentException, IOException {
        Set<JarEntry> jarEntriesSet = Sets.newLinkedHashSet();
        List<JarEntry> jarEntriesList = JarUtils.filter(jarFile, jarEntryFilter);

        for (JarEntry jarEntry : jarEntriesList) {
            String jarEntryName = jarEntry.getName();

            boolean accept = false;
            if (recursive) {
                accept = jarEntryName.startsWith(relativePath);
            } else {
                if (jarEntry.isDirectory()) {
                    accept = jarEntryName.equals(relativePath);
                } else {
                    int beginIndex = jarEntryName.indexOf(relativePath);
                    if (beginIndex == 0) {
                        accept = jarEntryName.indexOf(PathConstants.SLASH, relativePath.length()) < 0;
                    }
                }
            }
            if (accept) {
                jarEntriesSet.add(jarEntry);
            }
        }
        return Collections.unmodifiableSet(jarEntriesSet);
    }
}
