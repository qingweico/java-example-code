package frame.db.gen;

import lombok.AllArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.Objects;

/**
 * 书籍章节实体类
 *
 * @author zqw
 */
@AllArgsConstructor
public class Chapter implements Serializable {
    public String title;
    public String content;

    @Override
    public String toString() {
        return this.title + " : " + this.content.length();
    }


    public static Chapter fromBodyText(String text) {
        var doc = Jsoup.parse(text);
        var title = Objects.requireNonNull(doc.select("span").first()).text();
        var content = Objects.requireNonNull(doc.select("p")).text();
        return new Chapter(title, content);
    }

    public static void main(String[] args) {
        Document doc = Jsoup.parse(doGet("https://meiriyiwen.com/random"));
        String title = doc.select("h1").text();
        String author = doc.getElementsByClass("article_author").text();
        String content = doc.getElementsByClass("article_text").text();
        System.out.println(title);
        System.out.println(author);
        System.out.println(content);
    }
    public static String doGet(String url) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 返回结果
        String resultString = "";
        // 执行url之后的响应
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
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

}
