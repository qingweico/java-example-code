package coretech2.network.url;

import org.junit.Test;
import util.Print;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.util.Scanner;

/**
 * @author zqw
 * @date 2022/8/7
 * @see HttpURLConnection @since JDK1.1
 * @see HttpClient @since JDK11 替换了仅有阻塞模式的 {@link HttpURLConnection}
 */
public class UrlConnectionTest {
    @Test
    public void api() throws IOException {
        URL url = new URL("https://qingweico.cn");
        URLConnection conn = url.openConnection();
        // 在服务器建立连接之前设置请求头[有关 HTTP 请求头的格式 参见 RFC 2616]
        conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
        // 产生写操作的输出流(默认只产生从服务器读取信息的输入流)
        conn.setDoOutput(true);
        conn.connect();
        // getHeaderFieldKey 获取响应头的第n个键 若n=0或者大于消息头字段总数则返回null
        System.out.println(conn.getHeaderFieldKey(1));
        // getHeaderFields 返回一个包含了消息头中所有字段的标准Map对象
        Print.printMap(conn.getHeaderFields());
        // 使用 getHeaderField(0) 或者 getHeaderField(null) 来获取响应状态行
        System.out.println(conn.getHeaderField(0));
        System.out.println(conn.getHeaderField(null));
        // 获取一个输入流用于读取信息 与 url.openStream() 相同
        conn.getInputStream();
        System.out.println("############## 查询消息头的标准字段 ##############");
        System.out.println("ContentType: " + conn.getContentType());
        System.out.println("ContentLength: " + conn.getContentLength());
        System.out.println("ContentEncoding: " + conn.getContentEncoding());
        System.out.println("Data: " + conn.getDate());
        System.out.println("Expiration: " + conn.getExpiration());
        System.out.println("LastModified: " + conn.getLastModified());
        // sun.net.www.protocol.http.HttpURLConnection 处理 也可以注册自定义的处理器
        System.out.println("Content: " + conn.getContent());
        System.out.println("############## Response Content ##############");
        // simple print response content
        String encoding = conn.getContentEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        try (var in = new Scanner(conn.getInputStream(), encoding)) {
            int printLines = 10;
            for (int n = 1; in.hasNextLine() && n <= printLines; n++) {
                System.out.println(in.nextLine());
                if (in.hasNextLine()) {
                    System.out.println("...");
                }
            }
        }
    }
}
