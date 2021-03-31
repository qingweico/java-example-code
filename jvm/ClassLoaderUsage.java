package jvm;


import java.security.Provider;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/1/6
 */
public class ClassLoaderUsage {
    public static void main(String[] args) {
        // Gets the system class loader
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        print(systemClassLoader);

        // Gets the parent class of the system class loader
        ClassLoader parent = systemClassLoader.getParent();
        print(parent);

        // Gets the bootstrap class loader
        ClassLoader bootstrapClassLoader = parent.getParent();
        print(bootstrapClassLoader);

        // User-defined classes are loaded by default using the system class loader
        ClassLoader userDefinedClassLoader = ClassLoaderUsage.class.getClassLoader();
        print(userDefinedClassLoader);

        // Java's core class libraries are loaded using bootstrap classLoaders
        ClassLoader coreLibraryClassLoader = String.class.getClassLoader();
        print(coreLibraryClassLoader);
        ClassLoader classLoader = Provider.class.getClassLoader();
        print(classLoader);


    }
}
