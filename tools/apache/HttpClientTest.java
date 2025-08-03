package tools.apache;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import cn.qingweico.network.NetworkUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Apache HttpClient 测试
 *
 * @author zqw
 * @date 2022/7/17
 */
@Slf4j
public class HttpClientTest {
    @Test
    public void simpleRequest() throws IOException {
        // 设置超时
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(2000)
                .setSocketTimeout(6000)
                .setConnectTimeout(2000)
                .build();
        // 建立client
        String contentJson;
        try (CloseableHttpClient client = HttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                .build()) {
            HttpGet getContentJson = new HttpGet("https://www.baidu.com");
            contentJson = EntityUtils.toString(client.execute(getContentJson).getEntity());
        }
        System.out.println(contentJson);
    }


    @Test
    public void sendGet() {
        NetworkUtils.sendGet("https://whois.pconline.com.cn/ipJson.jsp", "json=true", Charset.forName("gbk").toString());
    }

    @Test
    public void doGet() {
        HashMap<String, String> map = new HashMap<>(1);
        map.put("json", "true");
        NetworkUtils.doGet("https://whois.pconline.com.cn/ipJson.jsp", map);
    }
}
