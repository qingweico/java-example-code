package coretech2.secure.classloader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 引导类加载器 负责加载包含在
 * java.base
 * java.datatransfer
 * java.desktop
 * java.instrument
 * java.logger
 * java.management
 * java.management.rmi
 * java.naming
 * java.prefs
 * java.rmi
 * java.security.sasl
 * java.xml
 * 以及大量的 JDK 内部模块中的平台类
 * {@link URLClassLoader}
 *
 * @author zqw
 * @date 2022/7/7
 */
class ClassLoading {
    public static void main(String[] args) {
        // 加载插件类
        URL url = null;
        try {
            url = new URL("file:///lib/InstrumentationAgent.jar");
        } catch (MalformedURLException e) {
            System.err.println("url error");
        }
        try (var loader = new URLClassLoader(new URL[]{url})) {
            // TODO JDK11 JDK14 重新编辑jar即可
            // UnsupportedClassVersionError: InstrumentationAgent has been compiled by
            // a more recent version of the Java Runtime (class file version 58.0), this
            // version of the Java Runtime only recognizes class file versions up to 55.0
            Class<?> cls = loader.loadClass("InstrumentationAgent");
            System.out.println(cls);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
