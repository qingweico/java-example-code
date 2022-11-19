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
import util.constants.Symbol;
import util.net.NetworkUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 书籍章节实体类
 *
 * @author zqw
 */
@AllArgsConstructor
class Chapter implements Serializable {
    public String title;
    public String content;

    @Override
    public String toString() {
        return this.title + Symbol.COLON + this.content;
    }


    public static Chapter fromBodyText(String text) {
        var str = new String(text.getBytes(), StandardCharsets.UTF_8);
        var doc = Jsoup.parse(str);
        var title = Objects.requireNonNull(doc.select("span").first()).text();
        var content = Objects.requireNonNull(doc.select("p")).text();
        return new Chapter(title, content);
    }
}
