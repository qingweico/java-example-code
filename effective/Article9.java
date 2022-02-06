package effective;

import util.Constants;

import java.io.*;

/**
 * try-with-resource优先于try-finally
 *
 * @author zqw
 * @date 2021/4/2
 */
class Article9 {

    private static final int BUFFER_SIZE = Constants.KB;

    /**
     * try-finally - No longer the best way to close resources!
     * try-with-resources - the best way to close resources!
     *
     * @param path the resource path
     * @return The first line of file
     * @throws IOException An {@code IOException} may be thrown
     */
    static String firstLineOfFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

    /**
     * try-finally is ugly when used with more than one resource!
     * try-with-resources on multiple resources - short and sweet
     *
     * @param src the resource path
     * @param dst the target path
     * @throws IOException An {@code IOException} may be thrown
     */
    static void copy(String src, String dst) throws IOException {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) >= 0) {
                out.write(buf, 0, n);
            }
        }
    }

    /**
     * try-with-resources with a catch clause
     *
     * @param path       the resource path
     * @param defaultVal the default value when exception
     * @return The first line of file
     * @throws IOException An {@code IOException} may be thrown
     */
    static String firstLineOfFile(String path, String defaultVal) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            return defaultVal;
        }
    }


    // Always give priority to try-with-resource over try-finally
    // when dealing with resources that must be closed.
}
