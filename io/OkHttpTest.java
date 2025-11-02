package io;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.qingweico.model.HttpRequestEntity;
import cn.qingweico.network.NetworkUtils;
import cn.qingweico.supplier.RandomDataGenerator;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Mime 常量 {@link ContentType} {@link MimeTypeUtils} {@link org.springframework.util.MimeType}
 *
 * @author zqw
 * @date 2025/7/23
 */
@Slf4j
public class OkHttpTest {

    private static final OkHttpClient HTTP_CLIENT;

    static {
        HTTP_CLIENT = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }



    public static String sendPost(HttpRequestEntity hre) {
        Response response = null;
        String responseBodyString = null;
        String url = hre.getRequestUrl();
        Map<String, String> requestBody = hre.getRequestBody();
        MediaType mediaType = MediaType.parse(ContentType.JSON.getValue());
        JSONObject jsonObject = new JSONObject();
        if (requestBody != null) {
            requestBody.forEach(jsonObject::put);
        }
        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .method(ServletUtil.METHOD_POST, body)
                    .addHeader(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue()).build();
            OkHttpClient.Builder builder = new OkHttpClient()
                    .newBuilder()
                    .readTimeout(hre.getReadTimeout(), TimeUnit.MILLISECONDS)
                    .connectTimeout(hre.getConnectTimeout(), TimeUnit.MILLISECONDS);
            if (StringUtils.isNotEmpty(hre.getProxyHost())) {
                builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hre.getProxyHost(), hre.getProxyPort())));
            }
            OkHttpClient httpClient = builder.build();
            log.info("POST 请求 URL ===> {}", url);
            log.info("请求体 ===>  {}", NetworkUtils.formBodyToString(body));
            response = httpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                responseBodyString = responseBody.string();
                LoggerFactory.getLogger(OkHttpTest.class).info("POST 请求成功, 返回的响应为 ===> {}", responseBodyString);
            }
        } catch (Exception e) {
            log.error("POST 请求失败 ===> {}, {}", url, e.getMessage(), e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBodyString;
    }

    @Test
    public void sendGet() {

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("jsonplaceholder.typicode.com")
                .addPathSegment("comments")
                .addQueryParameter("postId", "1")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        log.info("GET 请求 URL : {}", url);
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            log.info("响应状态码 : {}", response.code());
            ResponseBody body = response.body();
            if (body != null) {
                log.info("响应内容 : {}", body.string());
            }
        } catch (IOException e) {
            log.info("GET 请求失败 : {}", e.getMessage(), e);
        }
    }

    /**
     * POST 请求 - application/x-www-form-urlencoded 格式
     * 对应表单提交数据
     */
    @Test
    public void sendFormPostUrlencoded() {
        // 使用 FormBody(专门用于构建 x-www-form-urlencoded类型的数据)
        // 或者手动构建表单字符串
        // RequestBody.create("name=&age=&phone=&city=", MediaType.parse(ContentType.FORM_URLENCODED.getValue()));
        RequestBody formBody = new FormBody.Builder()
                .add("name", RandomDataGenerator.name())
                .add("age", String.valueOf(RandomDataGenerator.rndInt(100)))
                .add("phone", RandomDataGenerator.phone())
                .add("city", RandomDataGenerator.address())
                .build();

        Request request = new Request.Builder()
                .url("https://httpbin.org/post")
                .post(formBody)
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36")
                .addHeader(Header.CONTENT_TYPE.getValue(), ContentType.FORM_URLENCODED.getValue()).build();
        log.info("POST 请求 URL : {}", request.url());
        log.info("请求体 : {}", NetworkUtils.formBodyToString(formBody));
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            log.info("response.isSuccessful : {}", response.isSuccessful());
            if (response.body() != null) {
                log.info(response.body().string());
            }
        } catch (IOException e) {
            log.info("x-www-form-urlencoded POST 请求失败 : {}", e.getMessage(), e);
        }
    }



    public static void main(String[] args) {
        HttpRequestEntity httpRequestEntity = HttpRequestEntity.builder()
                .requestUrl("https://google.com.hk/search?q=YouTube")
                .proxyHost("127.0.0.1")
                .proxyPort(10808)
                .build();
        System.out.println(sendPost(httpRequestEntity));
    }
}
