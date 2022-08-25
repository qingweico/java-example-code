package util.io.scanner;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import util.filter.FilterUtils;
import util.filter.PackageNameClassNameFilter;
import util.klass.ClassLoaderUtils;
import util.klass.ClassUtils;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple {@link Class} Scanner
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 */
public class SimpleClassScanner {

    /**
     * Singleton
     */
    public final static SimpleClassScanner INSTANCE = new SimpleClassScanner();

    protected SimpleClassScanner() {

    }

    /**
     * It's equal to invoke {@link #scan(ClassLoader, String, boolean, boolean)} method with
     * <code>requiredLoad=false</code> and <code>recursive=false</code>
     *
     * @param classLoader {@link ClassLoader}
     * @param packageName the name of package
     * @return {@link #scan(ClassLoader, String, boolean, boolean)} method with <code>requiredLoad=false</code>
     * @throws IllegalArgumentException scanned source is not legal
     * @throws IllegalStateException    scanned source's state is not valid
     */
    public Set<Class<?>> scan(ClassLoader classLoader, String packageName) throws IllegalArgumentException, IllegalStateException {
        return scan(classLoader, packageName, false);
    }

    /**
     * It's equal to invoke {@link #scan(ClassLoader, String, boolean, boolean)} method with
     * <code>requiredLoad=false</code>
     *
     * @param classLoader {@link ClassLoader}
     * @param packageName the name of package
     * @param recursive   included sub-package
     * @return {@link #scan(ClassLoader, String, boolean, boolean)} method with <code>requiredLoad=false</code>
     * @throws IllegalArgumentException scanned source is not legal
     * @throws IllegalStateException    scanned source's state is not valid
     */
    public Set<Class<?>> scan(ClassLoader classLoader, String packageName, boolean recursive) throws IllegalArgumentException, IllegalStateException {
        return scan(classLoader, packageName, recursive, false);
    }


    /**
     * scan {@link Class} set under specified package name or its' sub-packages in {@link ClassLoader}, if
     * <code>requiredLoad</code> indicates <code>true</code> , try to load those classes.
     *
     * @param classLoader  {@link ClassLoader}
     * @param packageName  the name of package
     * @param recursive    included sub-package
     * @param requiredLoad try to load those classes or not
     * @return //
     * @throws IllegalArgumentException //
     * @throws IllegalStateException    //
     */
    public Set<Class<?>> scan(ClassLoader classLoader, String packageName, final boolean recursive, boolean requiredLoad) throws IllegalArgumentException, IllegalStateException {
        Set<Class<?>> classesSet = Sets.newLinkedHashSet();

        final String packageResourceName = ClassLoaderUtils.ResourceType.PACKAGE.resolve(packageName);

        try {
            Set<String> classNames = Sets.newLinkedHashSet();
            // Find in class loader
            Set<URL> resourceUrls = ClassLoaderUtils.getResources(classLoader, ClassLoaderUtils.ResourceType.PACKAGE, packageName);

            if (resourceUrls.isEmpty()) {
                //Find in class path
                List<String> classNamesInPackage = Lists.newArrayList(ClassUtils.getClassNamesInPackage(packageName));

                if (!classNamesInPackage.isEmpty()) {
                    String classPath = ClassUtils.findClassPath(classNamesInPackage.get(0));
                    if (classPath == null) {
                        System.err.println("class path is null!");
                        return new HashSet<>();
                    }
                    URL resourceUrl = new File(classPath).toURI().toURL();
                    resourceUrls = Sets.newHashSet(resourceUrl);
                }
            }

            for (URL resourceUrl : resourceUrls) {
                URL classPathUrl = resolveClassPathUrl(resourceUrl, packageResourceName);
                String classPath = classPathUrl.getFile();
                Set<String> classNamesInClassPath = ClassUtils.findClassNamesInClassPath(classPath, true);
                classNames.addAll(filterClassNames(classNamesInClassPath, packageName, recursive));
            }

            for (String className : classNames) {
                Class<?> cls = requiredLoad ? ClassLoaderUtils.loadClass(classLoader, className) : ClassLoaderUtils.findLoadedClass(classLoader, className);
                if (cls != null) {
                    classesSet.add(cls);
                }
            }

        } catch (IOException e) {
            ///
        }
        return Collections.unmodifiableSet(classesSet);
    }

    private Set<String> filterClassNames(Set<String> classNames, String packageName, boolean recursive) {
        PackageNameClassNameFilter packageNameClassNameFilter = new PackageNameClassNameFilter(packageName, recursive);
        return Sets.newLinkedHashSet(FilterUtils.filter(classNames, packageNameClassNameFilter));
    }


    private URL resolveClassPathUrl(URL resourceUrl, String packageResourceName) {
        String resource = resourceUrl.toExternalForm();
        String classPath = StringUtils.substringBefore(resource, packageResourceName);
        URL classPathUrl;
        try {
            classPathUrl = new URL(classPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return classPathUrl;
    }


}
