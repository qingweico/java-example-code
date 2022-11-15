package frame.db.gen;

import lombok.extern.slf4j.Slf4j;
import thread.pool.ThreadPoolBuilder;
import util.constants.Symbol;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 从 <a href="http://www.purepen.com/hlm"></> 加载 书籍章节资源
 * (http://www.purepen.com/hlm/%03d.htm, x) 超时, 更换源
 * 从 <a href="https://www.guichuideng.cc">鬼吹灯加载资源</>
 *
 * @author zqw
 */
@Slf4j
public class BookResourceLoader {


    /*《红楼梦》 〖清〗曹雪芹 高鹗 著 共120章节*/

    /* 从 https://www.guichuideng.cc/huangpizifen/ [鬼吹灯之黄皮子坟] 加载资源*/

    /*[第0章] https://www.guichuideng.cc/huangpizifen/180.html*/
    /*[第54章] https://www.guichuideng.cc/huangpizifen/259.html*/

    private final static Integer CHAPTER_NUMBER = 1;
    private final static String PATH = "/novel/";

    static {
        File file = new File(PATH);
        if (!file.exists() && file.mkdirs()) {}
    }

    @SuppressWarnings("rawtypes")
    public void parallelLoad() throws URISyntaxException, IOException, InterruptedException, ExecutionException {

        LinkedBlockingQueue<Resource<Chapter>> queue = IntStream.range(1, CHAPTER_NUMBER + 1)
                .mapToObj(this::getChapterResource)
                .collect(LinkedBlockingQueue::new, AbstractQueue::add, AbstractQueue::addAll);


        var client = ThreadLocal.withInitial(() ->
                HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .build()
        );
        var pool = ThreadPoolBuilder.custom().isEnableMonitor(true).preStartAllCore(true).builder();
        AtomicInteger counter = new AtomicInteger(0);
        var parallelTaskCount = 10;
        var joins = new Future[parallelTaskCount];
        for (int i = 0; i < parallelTaskCount; i++) {
            var future = pool.submit(() -> {
                while (true) {
                    var resource = queue.poll();
                    if (resource == null) {
                        break;
                    }
                    try {
                        var req = createRequest(resource);
                        var resp = client.get().send(
                                req,
                                HttpResponse.BodyHandlers.ofString(Charset.forName("gbk")));
                        resource.setData(Chapter.fromBodyText(resp.body()));
                        // 保存数据到本地磁盘
                        resource.save(PATH);
                        System.out.format("finished %d/%d\n", counter.incrementAndGet(), CHAPTER_NUMBER);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (HttpTimeoutException e) {
                        System.out.println("timeout retry.");
                        queue.add(resource);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            joins[i] = future;
        }
        pool.shutdown();
        for (Future join : joins) {
            join.get();
        }
    }

    public void load() throws URISyntaxException, IOException, InterruptedException {

        LinkedList<Resource<Chapter>> queue = IntStream.range(1, CHAPTER_NUMBER + 1)
                .mapToObj(this::getChapterResource)
                .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

        var client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(2000))
                .build();

        while (queue.size() > 0) {
            var resource = queue.pop();
            var req = createRequest(resource);
            var resp = client.send(
                    req,
                    HttpResponse.BodyHandlers.ofString(Charset.forName("gbk")));
            resource.setData(Chapter.fromBodyText(resp.body()));
            resource.save(PATH);
        }
    }


    private HttpRequest createRequest(Resource<Chapter> r) {
        URI url = r.getUri();
        if (url == null) {
            throw new IllegalStateException("url is null");
        }
        return HttpRequest
                .newBuilder()
                .timeout(Duration.ofMillis(3000))
                .uri(url)
                .GET()
                .build();
    }


    private Resource<Chapter> getChapterResource(int x) {
        return new Resource<>(
                String.format("https://www.guichuideng.cc/huangpizifen/%d.html", x + 179), x);
    }

    public static class Sentence {
        String text;
        int chapterId;


        public String getText() {
            return text;
        }

        public int getChapterId() {
            return chapterId;
        }

        @Override
        public String toString() {
            return "Sentence{" +
                    "text='" + text + '\'' +
                    ", chapterId=" + chapterId +
                    '}';
        }
    }


    public ArrayList<Sentence> sentences() throws IOException, ClassNotFoundException {
        var arr = new ArrayList<Sentence>();
        for (int i = 1; i <= CHAPTER_NUMBER; i++) {
            var file = new File(String.format("%s%d.txt", PATH, i));
            var fin = new ObjectInputStream(new FileInputStream(file));
            var chapter = (Chapter) fin.readObject();
            var sens = chapter.content.split(Symbol.FULL_STOP);

            for (String sen : sens) {
                var sentence = new Sentence();
                sentence.chapterId = i;
                sentence.text = sen.replaceAll("\n", "");
                arr.add(sentence);
            }
        }
        return arr;
    }

}
