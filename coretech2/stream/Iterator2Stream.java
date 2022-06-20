package coretech2.stream;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author zqw
 * @date 2022/6/16
 * @see Collection#stream()
 * @see Collection#parallelStream()
 */
public class Iterator2Stream {

    private static String contents;

    static {
        try {
            contents = Files.readString(Paths.get("algorithm/set/a-tale-of-two-cities.txt"));
        } catch (IOException e) {
            contents = "";
        }
    }


    public static void main(String[] args) throws IOException {

        List<String> words = List.of(contents.split("\\PL+"));
        long counts = 0;
        for (String w : words) {
            if (w.length() > 12) {
                counts++;
            }
        }
        System.out.println(counts);
        counts = words.stream().filter(w -> w.length() > 12).count();
        System.out.println(counts);
        counts = words.parallelStream().filter(w -> w.length() > 12).count();
        System.out.println(counts);
    }

    @Test
    public void createStream() {
        // 可变长参数
        Stream.of(contents.split("\\PL+")).close();
        int[] arr = {1, 2, 3, 4, 5};
        // [)
        Arrays.stream(arr, 2, 3).forEach(System.out::println);
        // 创建不包含任何元素的流
        Stream<String> silence = Stream.empty();
        silence.forEach(System.out::println);
    }
}

