package util.filter;


import util.constants.FileSuffixConstants;

import java.util.jar.JarEntry;

/**
 * Class File {@link JarEntryFilter}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 */
public class ClassFileJarEntryFilter implements JarEntryFilter {

    /**
     * {@link ClassFileJarEntryFilter} Singleton instance
     */
    public static final ClassFileJarEntryFilter INSTANCE = new ClassFileJarEntryFilter();

    protected ClassFileJarEntryFilter() {

    }

    @Override
    public boolean accept(JarEntry jarEntry) {
        return !jarEntry.isDirectory() && jarEntry.getName().endsWith(FileSuffixConstants.CLASS);
    }
}
