package util.klass;

import com.google.common.collect.Sets;
import object.entity.User;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * {@link ClassPathUtils}
 *
 * @author zqw
 */
public class ClassPathUtils {
    private ClassPathUtils() {}

    protected static final RuntimeMXBean RUNTIME_MX_BEAN = ManagementFactory.getRuntimeMXBean();

    private static final Set<String> BOOTSTRAP_CLASS_PATHS = initBootstrapClassPaths();

    private static final Set<String> CLASS_PATHS = initClassPaths();


    private static Set<String> initBootstrapClassPaths() {
        Set<String> bootstrapClassPaths = Collections.emptySet();
        if (RUNTIME_MX_BEAN.isBootClassPathSupported()) {
            bootstrapClassPaths = resolveClassPaths(RUNTIME_MX_BEAN.getBootClassPath());
        }
        return bootstrapClassPaths;
    }

    private static Set<String> initClassPaths() {
        return resolveClassPaths(RUNTIME_MX_BEAN.getClassPath());
    }

    private static Set<String> resolveClassPaths(String classPath) {
        Set<String> classPaths = Sets.newLinkedHashSet();
        // Deprecated : SystemUtils.PATH_SEPARATOR
        String[] classPathsArray = StringUtils.split(classPath,  File.pathSeparator);
        classPaths.addAll(Arrays.asList(classPathsArray));
        return Collections.unmodifiableSet(classPaths);
    }


    /**
     * Get Bootstrap Class Paths {@link Set}
     *
     * @return If {@link RuntimeMXBean#isBootClassPathSupported()} == <code>false</code>, will return empty set.
     **/
    @Nonnull
    public static Set<String> getBootstrapClassPaths() {
        return BOOTSTRAP_CLASS_PATHS;
    }

    /**
     * Get {@link #CLASS_PATHS}
     *
     * @return Class Paths {@link Set}
     **/
    @Nonnull
    public static Set<String> getClassPaths() {
        return CLASS_PATHS;
    }

    /**
     * Get Class Location URL from specified class name at runtime
     *
     * @param className class name
     * @return If <code>className</code> associated class is loaded on {@link Thread#getContextClassLoader() Thread
     * context ClassLoader} , return class location URL, or return <code>null</code>
     * @see #getRuntimeClassLocation(Class)
     */
    public static URL getRuntimeClassLocation(String className) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL location = null;
        if (classLoader != null) {
            if (ClassLoaderUtils.isLoadedClass(classLoader, className)) {
                try {
                    location = getRuntimeClassLocation(classLoader.loadClass(className));
                } catch (ClassNotFoundException ignored) {
                }
            }
        }
        return location;
    }

    /**
     * Get Class Location URL from specified {@link Class} at runtime
     *
     * @param type {@link Class}
     * @return If <code>type</code> is <code>{@link Class#isPrimitive() primitive type}</code>, <code>{@link
     * Class#isArray() array type}</code>, <code>{@link Class#isSynthetic() synthetic type}</code> or {a security
     * manager exists and its <code>checkPermission</code> method doesn't allow getting the ProtectionDomain., return
     * <code>null</code>
     */
    public static URL getRuntimeClassLocation(Class<?> type) {
        ClassLoader classLoader = type.getClassLoader();
        URL location = null;
        // Non-Bootstrap
        if (classLoader != null) {
            try {
                ProtectionDomain protectionDomain = type.getProtectionDomain();
                CodeSource codeSource = protectionDomain.getCodeSource();
                location = codeSource == null ? null : codeSource.getLocation();
            } catch (SecurityException exception) {
                ///
            }
            // Bootstrap ClassLoader
        } else if (!type.isPrimitive() && !type.isArray() && !type.isSynthetic()) {
            // Class was loaded by Bootstrap ClassLoader
            location = ClassLoaderUtils.getClassResource(ClassLoader.getSystemClassLoader(), type.getName());
        }
        return location;
    }

    public static void main(String[] args) {
        System.out.println(getRuntimeClassLocation("object.entity.User"));
        System.out.println(getRuntimeClassLocation(User.class));
    }
}
