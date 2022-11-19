package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
/**
 * @author zqw
 * @date 2020/02/05
 */
class Url {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.taobao.com");

        /* Byte stream */
        InputStream is = url.openStream();

        /* Character stream */
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);

        /* Provide caching function */
        BufferedReader br = new BufferedReader(isr);

        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }
}
