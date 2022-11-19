package tools.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import util.net.NetworkUtils;

/**
 * @author zqw
 * @date 2022/11/17
 */
public class JsoupTest {
    @Test
    public void randomShortArticle() {
        Document doc = Jsoup.parse(NetworkUtils.doGet("https://meiriyiwen.com/random", null));
        String title = doc.select("h1").text();
        String author = doc.getElementsByClass("article_author").text();
        String content = doc.getElementsByClass("article_text").text();
        System.out.println(title);
        System.out.println(author);
        System.out.println(content);
    }
}
