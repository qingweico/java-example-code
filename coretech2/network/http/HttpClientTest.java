package coretech2.network.http;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * JDK HTTP Client
 *
 * @author zqw
 * @date 2022/8/7
 */
public class HttpClientTest {
    @Test
    public void simpleClient() throws URISyntaxException, IOException, InterruptedException {

        HttpClient.newHttpClient();
        // 配置客户端,使用构建器
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        // 构建一个 GET 请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://qingweico.cn"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        System.out.println(response.statusCode());
        System.out.println(response.headers().toString());
    }
}
