package tools.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author zqw
 * @date 2022/7/16
 * @see HttpURLConnection
 */
public class HttpUrlConnection {
    public static void main(String[] args) throws IOException {
        URL url = new URL("");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
    }
}
