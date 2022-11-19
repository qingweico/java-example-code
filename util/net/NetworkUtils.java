package util.net;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author zqw
 * @date 2022/6/25
 */
@Slf4j
public class NetworkUtils {
    public static String getLocalMac(InetAddress ia) throws SocketException {
        byte[] macAddress = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        log.info("Mac Array: [{}], Mac Byte Array Length: {}", Arrays.toString(macAddress), macAddress.length);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < macAddress.length; i++) {
            if (i != 0) {
                result.append("-");
            }
            int tmp = macAddress[i] & 0xff;
            String str = Integer.toHexString(tmp);
            log.info("每8位: {}", str);
            if (str.length() == 1) {
                result.append("0").append(str);
            } else {
                result.append(str);
            }
        }
        return result.toString();
    }

    /**
     * 使用 Apache Http Client
     *
     * @param url 请求url
     * @param param url参数
     * @return 请求返回的内容
     */
    public static String doGet(String url, HashMap<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 返回结果
        String resultString = "";
        // 执行url之后的响应
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);

            // 将参数封装到uri里面
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建HTTP GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            log.info("rev - {}", resultString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
    /**
     * 使用 Java.net.URLConnection
     *
     * @param url         发送请求的 URL
     * @param param       请求参数, 请求参数应该是 name1=value1&name2=value2 的形式
     * @param contentType 编码类型
     */
    public static void sendGet(String url, String param, String contentType) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("rev - {}", result);
        } catch (Exception e) {
            log.error("sendGet error, url={}, param={}: {}", url, param, e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("sendGet close, url={}, param={}: {}", url, param, ex.getMessage());
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException, SocketException {
        log.info("Mac Address: {}", getLocalMac(Inet4Address.getLocalHost()));
    }
}
