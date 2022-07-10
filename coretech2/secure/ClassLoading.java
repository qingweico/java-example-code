package coretech2.secure;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * {@link URLClassLoader}
 *
 * @author zqw
 * @date 2022/7/7
 */
class ClassLoading {
    public static void main(String[] args) {
        URL url = null;
        try {
            url = new URL("");
        } catch (MalformedURLException e) {
            System.err.println("url error");
        }
        try (var loader = new URLClassLoader(new URL[]{url})) {
            Class<?> cls = loader.loadClass("");
            System.out.println(cls);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
