package frame.db.gen;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
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
        var str = new String(text.getBytes(), StandardCharsets.UTF_8);
        var doc = Jsoup.parse(str);
        var title = Objects.requireNonNull(doc.select("b").first()).text();
        var content = Objects.requireNonNull(doc.select("pre").first()).text();
        return new Chapter(title, content);
    }
}
