package util.klass;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.EnumerationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import util.constants.Symbol;
import util.constants.FileSuffixConstants;
import util.constants.PathConstants;
import util.net.UrlUtils;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.jar.JarFile;

/**
 * {@link ClassLoader} Utility
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ClassLoader
 */
public class ClassLoaderUtils {

    private ClassLoaderUtils(){}

    protected static final ClassLoadingMXBean CLASSLOADING_MX_BEAN = ManagementFactory.getClassLoadingMXBean();

    private static final Method FIND_LOADED_CLASS_METHOD = initFindLoadedClassMethod();


    /**
     * Initializes {@link Method} for {@see ClassLoader#findLoadedClass(String)}
     *
     * @return {@link Method} for {@see ClassLoader#findLoadedClass(String)}
     */
    private static Method initFindLoadedClassMethod() {
        final Method findLoadedClassMethod;
        try {
            findLoadedClassMethod = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            findLoadedClassMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw jvmUnsupportedOperationException(e);
        }
        return findLoadedClassMethod;
    }

    private static UnsupportedOperationException jvmUnsupportedOperationException(Throwable throwable) {
        String stackTrace = ExceptionUtils.getStackTrace(throwable);
        String message = String.format("Current JVM[ Implementation : %s , Version : %s ] does not supported ! " + "Stack Trace : %s", SystemUtils.JAVA_VENDOR, SystemUtils.JAVA_VERSION, stackTrace);
        throw new UnsupportedOperationException(message);
    }


    /**
     * Returns the number of classes that are currently loaded in the Java virtual machine.
     *
     * @return the number of currently loaded classes.
     */
    public static int getLoadedClassCount() {
        return CLASSLOADING_MX_BEAN.getLoadedClassCount();
    }

    /**
     * Returns the total number of classes unloaded since the Java virtual machine has started execution.
     *
     * @return the total number of unloaded classes.
     */
    public static long getUnloadedClassCount() {
        return CLASSLOADING_MX_BEAN.getUnloadedClassCount();
    }

    /**
     * Tests if the verbose output for the class loading system is enabled.
     *
     * @return <tt>true</tt> if the verbose output for the class loading system is enabled; <tt>false</tt> otherwise.
     */
    public static boolean isVerbose() {
        return CLASSLOADING_MX_BEAN.isVerbose();
    }

    /**
     * Enables or disables the verbose output for the class loading system.  The verbose output information and the
     * output stream to which the verbose information is emitted are implementation dependent.  Typically, a Java
     * virtual machine implementation prints a message each time a class file is loaded.
     * <p/>
     * <p>This method can be called by multiple threads concurrently. Each invocation of this method enables or disables
     * the verbose output globally.
     *
     * @param value <tt>true</tt> to enable the verbose output; <tt>false</tt> to disable.
     * @throws SecurityException if a security manager exists and the caller does not have ManagementPermission("control").
     */
    public static void setVerbose(boolean value) {
        CLASSLOADING_MX_BEAN.setVerbose(value);
    }

    /**
     * Returns the total number of classes that have been loaded since the Java virtual machine has started execution.
     *
     * @return the total number of classes loaded.
     */
    public static long getTotalLoadedClassCount() {
        return CLASSLOADING_MX_BEAN.getTotalLoadedClassCount();
    }

    /**
     * Find Loaded {@link Class} under specified inheritable {@link ClassLoader} and class names
     *
     * @param classLoader {@link ClassLoader}
     * @param classNames  class names set
     * @return {@link Class} if loaded , or <code>null</code>
     */
    public static Set<Class<?>> findLoadedClasses(ClassLoader classLoader, Set<String> classNames) {
        Set<Class<?>> loadedClasses = Sets.newLinkedHashSet();
        for (String className : classNames) {
            Class<?> cls = findLoadedClass(classLoader, className);
            if (cls != null) {
                loadedClasses.add(cls);
            }
        }
        return Collections.unmodifiableSet(loadedClasses);
    }

    /**
     * Check specified {@link Class} is loaded on specified inheritable {@link ClassLoader}
     *
     * @param classLoader {@link ClassLoader}
     * @param type        {@link Class}
     * @return If Loaded , return <code>true</code> , or <code>false</code>
     */
    public static boolean isLoadedClass(ClassLoader classLoader, Class<?> type) {
        return isLoadedClass(classLoader, type.getName());
    }

    /**
     * Check specified {@link Class#getName() class name}  is loaded on specified inheritable {@link ClassLoader}
     *
     * @param classLoader {@link ClassLoader}
     * @param className   {@link Class#getName() class name}
     * @return If Loaded , return <code>true</code> , or <code>false</code>
     */
    public static boolean isLoadedClass(ClassLoader classLoader, String className) {
        return findLoadedClass(classLoader, className) != null;
    }

    /**
     * Find Loaded {@link Class} under specified inheritable {@link ClassLoader}
     *
     * @param classLoader {@link ClassLoader}
     * @param className   class name
     * @return {@link Class} if loaded , or <code>null</code>
     */
    public static Class<?> findLoadedClass(ClassLoader classLoader, String className) {
        Class<?> loadedClass = null;
        Set<ClassLoader> classLoaders = getInheritableClassLoaders(classLoader);
        try {
            for (ClassLoader loader : classLoaders) {
                loadedClass = (Class<?>) FIND_LOADED_CLASS_METHOD.invoke(loader, className);
                if (loadedClass != null) {
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        return loadedClass;
    }

    /**
     * Loaded specified class name under {@link ClassLoader}
     *
     * @param classLoader {@link ClassLoader}
     * @param className   the name of {@link Class}
     * @return {@link Class} if can be loaded
     */
    @Nullable
    public static Class<?> loadClass(@Nonnull ClassLoader classLoader, @Nonnull String className) {
        try {
            return classLoader.loadClass(className);
        } catch (Throwable ignored) {
        }
        return null;
    }

    /**
     * Get the resource URLs Set under specified resource name and type
     *
     * @param classLoader  ClassLoader
     * @param resourceType {@link ResourceType} Enum
     * @param resourceName resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *                     <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException If any argument is <code>null</code>
     * @throws IOException          NullPointerException || IOException
     */
    public static Set<URL> getResources(ClassLoader classLoader, ResourceType resourceType, String resourceName) throws NullPointerException, IOException {
        String normalizedResourceName = resourceType.resolve(resourceName);
        Enumeration<URL> resources = classLoader.getResources(normalizedResourceName);
        return resources != null && resources.hasMoreElements() ? Sets.newLinkedHashSet(EnumerationUtils.toList(resources)) : Collections.emptySet();
    }

    /**
     * Get the resource URLs list under specified resource name
     *
     * @param classLoader  ClassLoader
     * @param resourceName resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *                     <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException If any argument is <code>null</code>
     * @throws IOException NullPointerException || IOException
     */
    public static Set<URL> getResources(ClassLoader classLoader, String resourceName) throws NullPointerException, IOException {
        Set<URL> resourceUrls = Collections.emptySet();
        for (ResourceType resourceType : ResourceType.values()) {
            resourceUrls = getResources(classLoader, resourceType, resourceName);
            if (CollectionUtils.isNotEmpty(resourceUrls)) {
                break;
            }
        }
        return resourceUrls;
    }

    /**
     * Get the resource URL under specified resource name
     *
     * @param classLoader  ClassLoader
     * @param resourceName resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *                     <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException If any argument is <code>null</code>
     */
    public static URL getResource(ClassLoader classLoader, String resourceName) throws NullPointerException {
        URL resourceUrl = null;
        for (ResourceType resourceType : ResourceType.values()) {
            resourceUrl = getResource(classLoader, resourceType, resourceName);
            if (resourceUrl != null) {
                break;
            }
        }
        return resourceUrl;
    }

    /**
     * Get the resource URL under specified resource name and type
     *
     * @param classLoader  ClassLoader
     * @param resourceType {@link ResourceType} Enum
     * @param resourceName resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *                     <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException If any argument is <code>null</code>
     */
    public static URL getResource(ClassLoader classLoader, ResourceType resourceType, String resourceName) throws NullPointerException {
        String normalizedResourceName = resourceType.resolve(resourceName);
        return classLoader.getResource(normalizedResourceName);
    }


    /**
     * Get the {@link Class} resource URL under specified {@link Class#getName() Class name}
     *
     * @param classLoader ClassLoader
     * @param className   class name
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException If any argument is <code>null</code>
     */
    public static URL getClassResource(ClassLoader classLoader, String className) {
        final String resourceName = className + FileSuffixConstants.CLASS;
        return getResource(classLoader, ResourceType.CLASS, resourceName);
    }

    /**
     * Get the {@link Class} resource URL under specified {@link Class}
     *
     * @param classLoader ClassLoader
     * @param type        {@link Class type}
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException If any argument is <code>null</code>
     */
    public static URL getClassResource(ClassLoader classLoader, Class<?> type) {
        String resourceName = type.getName();
        return getClassResource(classLoader, resourceName);
    }

    /**
     * Get all Inheritable {@link ClassLoader ClassLoaders} {@link Set} (including {@link ClassLoader} argument)
     *
     * @param classLoader {@link ClassLoader}
     * @return Read-only {@link Set}
     * @throws NullPointerException If <code>classLoader</code> argument is <code>null</code>
     */
    @Nonnull
    public static Set<ClassLoader> getInheritableClassLoaders(ClassLoader classLoader) throws NullPointerException {
        Set<ClassLoader> classLoadersSet = Sets.newLinkedHashSet();
        classLoadersSet.add(classLoader);
        ClassLoader parentClassLoader = classLoader.getParent();
        while (parentClassLoader != null) {
            classLoadersSet.add(parentClassLoader);
            parentClassLoader = parentClassLoader.getParent();
        }
        return Collections.unmodifiableSet(classLoadersSet);
    }

    /**
     * Get all loaded classes {@link Map} under specified inheritable {@link ClassLoader} , {@link ClassLoader} as key ,
     * its loaded classes {@link Set} as value.
     *
     * @param classLoader {@link ClassLoader}
     * @return Read-only Map
     * @throws UnsupportedOperationException UnsupportedOperationException
     * @throws NullPointerException          If <code>classLoader</code> argument is <code>null</code>
     */
    @Nonnull
    public static Map<ClassLoader, Set<Class<?>>> getAllLoadedClassesMap(ClassLoader classLoader) throws UnsupportedOperationException {
        Map<ClassLoader, Set<Class<?>>> allLoadedClassesMap = Maps.newLinkedHashMap();
        Set<ClassLoader> classLoadersSet = getInheritableClassLoaders(classLoader);
        for (ClassLoader loader : classLoadersSet) {
            allLoadedClassesMap.put(loader, getLoadedClasses(loader));
        }
        return Collections.unmodifiableMap(allLoadedClassesMap);
    }

    /**
     * Get all loaded classes {@link Set} under specified inheritable {@link ClassLoader}
     *
     * @param classLoader {@link ClassLoader}
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException If JVM does not support
     * @throws NullPointerException          If <code>classLoader</code> argument is <code>null</code>
     */
    @Nonnull
    public static Set<Class<?>> getAllLoadedClasses(ClassLoader classLoader) throws UnsupportedOperationException {
        Set<Class<?>> allLoadedClassesSet = Sets.newLinkedHashSet();
        Map<ClassLoader, Set<Class<?>>> allLoadedClassesMap = getAllLoadedClassesMap(classLoader);
        for (Set<Class<?>> loadedClassesSet : allLoadedClassesMap.values()) {
            allLoadedClassesSet.addAll(loadedClassesSet);
        }
        return Collections.unmodifiableSet(allLoadedClassesSet);
    }

    /**
     * Get loaded classes {@link Set} under specified {@link ClassLoader}( not all inheritable {@link ClassLoader
     * ClassLoaders})
     *
     * @param classLoader {@link ClassLoader}
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException If JVM does not support
     * @throws NullPointerException          If <code>classLoader</code> argument is <code>null</code>
     * @see #getAllLoadedClasses(ClassLoader)
     */
    @Nonnull
    public static Set<Class<?>> getLoadedClasses(ClassLoader classLoader) throws UnsupportedOperationException {
        final Set<Class<?>> classesSet;
        try {
            @SuppressWarnings("unchecked")
            List<Class<?>> classes = (List<Class<?>>) FieldUtils.readField(classLoader, "classes", true);
            classesSet = Sets.newLinkedHashSet(classes);
        } catch (IllegalAccessException e) {
            throw jvmUnsupportedOperationException(e);
        }
        return Collections.unmodifiableSet(Sets.newLinkedHashSet(classesSet));
    }

    /**
     * Find loaded classes {@link Set} in class path
     *
     * @param classLoader {@link ClassLoader}
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException If JVM does not support
     */
    public static Set<Class<?>> findLoadedClassesInClassPath(ClassLoader classLoader) throws UnsupportedOperationException {
        Set<String> classNames = ClassUtils.getAllClassNamesInClassPaths();
        return findLoadedClasses(classLoader, classNames);
    }

    /**
     * Find loaded classes {@link Set} in class paths {@link Set}
     *
     * @param classLoader {@link ClassLoader}
     * @param classPaths  the class paths for the {@link Set} of {@link JarFile} or classes directory
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException If JVM does not support
     * @see #findLoadedClass(ClassLoader, String)
     */
    public static Set<Class<?>> findLoadedClassesInClassPaths(ClassLoader classLoader, Set<String> classPaths) throws UnsupportedOperationException {
        Set<Class<?>> loadedClasses = Sets.newLinkedHashSet();
        for (String classPath : classPaths) {
            loadedClasses.addAll(findLoadedClassesInClassPath(classLoader, classPath));
        }
        return loadedClasses;
    }

    /**
     * Find loaded classes {@link Set} in class path
     *
     * @param classLoader {@link ClassLoader}
     * @param classPath   the class path for one {@link JarFile} or classes directory
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException If JVM does not support
     * @see #findLoadedClass(ClassLoader, String)
     */
    public static Set<Class<?>> findLoadedClassesInClassPath(ClassLoader classLoader, String classPath) throws UnsupportedOperationException {
        Set<String> classNames = ClassUtils.getClassNamesInClassPath(classPath, true);
        return findLoadedClasses(classLoader, classNames);
    }


    /**
     * Resource Type
     */
    public enum ResourceType {

        DEFAULT {
            @Override
            boolean supported(String name) {
                return true;
            }

            @Override
            public String normalize(String name) {
                return name;
            }


        }, CLASS {
            @Override
            boolean supported(String name) {
                return StringUtils.endsWith(name, FileSuffixConstants.CLASS);
            }

            @Override
            public String normalize(String name) {
                String className = StringUtils.replace(name, FileSuffixConstants.CLASS, StringUtils.EMPTY);
                return StringUtils.replace(className, Symbol.DOT, PathConstants.SLASH) + FileSuffixConstants.CLASS;
            }


        }, PACKAGE {
            @Override
            boolean supported(String name) {
                //TODO: use regexp to match more precise
                return !CLASS.supported(name) && !StringUtils.contains(name, PathConstants.SLASH) && !StringUtils.contains(name, PathConstants.BACK_SLASH);
            }

            @Override
            String normalize(String name) {
                return StringUtils.replace(name, Symbol.DOT, PathConstants.SLASH) + PathConstants.SLASH;
            }


        };

        /**
         * resolves resource name
         *
         * @param name resource name
         * @return a newly resolved resource name
         */
        public String resolve(String name) {
            String normalizedName = supported(name) ? normalize(name) : null;
            if (normalizedName == null) {
                return null;
            }

            normalizedName = UrlUtils.resolvePath(normalizedName);

            // 除去开头的"/"
            while (normalizedName.startsWith("/")) {
                normalizedName = normalizedName.substring(1);
            }

            return normalizedName;
        }

        /**
         * Is supported specified resource name in current resource type
         *
         * @param name resource name
         * @return If supported , return <code>true</code> , or return <code>false</code>
         */
        abstract boolean supported(String name);

        /**
         * Normalizes resource name
         *
         * @param name resource name
         * @return normalized resource name
         */
        abstract String normalize(String name);
    }

}
