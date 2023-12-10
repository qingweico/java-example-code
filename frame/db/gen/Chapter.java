package frame.db.gen;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import util.constants.Symbol;

import java.io.Serializable;

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
        return this.title + Symbol.LINE_BREAK + this.content;
    }


    public static Chapter fromBodyText(String text) {
        var str = new String(text.getBytes());
        var doc = Jsoup.parse(str);
        var title = doc.select("b").text();
        var content = doc.select("pre").text();
        return new Chapter(title, content);
    }
}
