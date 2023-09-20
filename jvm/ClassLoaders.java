package jvm;


import util.Print;

import java.security.Provider;

/**
 * --------------- 类加载器 ---------------
 *
 * @author zqw
 * @date 2021/1/6
 */
class ClassLoaders {
    public static void main(String[] args) {

        // Gets the system class loader
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Print.println(systemClassLoader);

        // Gets the parent class of the system class loader
        ClassLoader parent = systemClassLoader.getParent();
        Print.println(parent);

        // Gets the bootstrap class loader
        ClassLoader bootstrapClassLoader = parent.getParent();
        Print.println(bootstrapClassLoader);

        // User-defined classes are loaded by default using the system class loader
        ClassLoader userDefinedClassLoader = ClassLoaders.class.getClassLoader();
        Print.println(userDefinedClassLoader);

        // Java's core class libraries are loaded using bootstrap classLoaders
        ClassLoader coreLibraryClassLoader = String.class.getClassLoader();
        Print.println(coreLibraryClassLoader);
        ClassLoader classLoader = Provider.class.getClassLoader();
        Print.println(classLoader);
    }
}
