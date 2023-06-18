package geek.io.nio;

import cn.hutool.core.date.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import util.constants.Constants;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 文件IO: 实现高效正确的文件读写并非易事
 *
 * @author zqw
 * @date 2022/8/3
 */
@Slf4j
public class FileIoResolvingTest {
    String fileName = Constants.DEFAULT_FILE_PATH_MAME;
    /*文件读写需要确保字符编码一致*/

    @Test
    public void ensureFileEncodingConsistency() throws IOException {
        // 处理文件读写的时候 如果在字节层面进行操作 则不会涉及到字符编码问题
        // 而如果需要在字符层面进行读写的话 就需要明确字符的编码方式也就是字符集

        String content = "十年生死两茫茫";
        Files.deleteIfExists(Paths.get(fileName));
        Files.writeString(Paths.get(fileName), content, Charset.forName("GBK"));
        // 使用 GBK 编码 每个汉字占用2个字节 7个汉字 14个字节 28个16进制位
        log.info("bytes: {}", Hex.encodeHexString(Files.readAllBytes(Paths.get(fileName))).toUpperCase());

        char[] chars = new char[10];
        StringBuilder result = new StringBuilder();
        try (FileReader fr = new FileReader(fileName)) {
            int read;
            while ((read = fr.read(chars)) != -1) {
                result.append(new String(chars, 0, read));
            }
        }
        // 乱码
        // 因为FileReader是以当前机器默认的字符集来读取文件的;指定字符集需要直接使用FileInputStream
        // 或者InputStreamReader
        log.info("result: {}", result);

        // 输出当前机器默认的字符集
        log.info("charset: {}", Charset.defaultCharset());
        Files.write(Paths.get(fileName), content.getBytes(Charsets.UTF_8));
        // 使用 UTF8 编码 每个汉字占用3个字节 7个汉字 21字节 42个16进制位
        log.info("bytes:{}", Hex.encodeHexString(Files.readAllBytes(Paths.get(fileName))).toUpperCase());


        // JDK7
        log.info("Files.readAllLines: {}", Files.readAllLines(
                Paths.get(fileName),
                Charset.forName("GBK")).stream().findFirst().orElse(""));
    }
    /*使用 Files 类静态方法进行文件操作注意释放文件句柄*/

    @Test
    public void releaseFileHandle() throws IOException {

        Files.write(Paths.get(fileName),
                IntStream.rangeClosed(1, 10)
                        .mapToObj(i -> UUID.randomUUID().toString())
                        .collect(Collectors.toList()));

        LongAdder la = new LongAdder();
        IntStream.rangeClosed(1, 10000000).forEach(i -> {

            try {
                // 不会释放文件句柄
                Stream<String> stream = Files.lines(Paths.get(fileName));
                stream.forEach(line -> la.increment());
                stream.close();
                // 使用try-catch-resources
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("total: {}", la.longValue());

    }

    /*读写文件要考虑设置缓冲区*/

    @Test
    public void setFileBuffer() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(FileSystems.getDefault());
    }
}
