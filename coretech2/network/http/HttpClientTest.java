package coretech2.network.http;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

/**
 * @author zqw
 * @date 2022/8/7
 */
public class HttpClientTest {
    @Test
    public void simpleClient() throws URISyntaxException {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        // 构建一个 GET 请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://qingweico.cn"))
                .GET()
                .build();
        System.out.println(request);
    }
}
