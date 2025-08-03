package coretech2.stream;

import org.junit.Test;
import cn.qingweico.io.Print;
import cn.qingweico.supplier.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * filter, map, flatMap
 *
 * @author zqw
 * @date 2022/8/14
 */
public class MapOperationStreamTest {
    /*可以处理需要两个char值来表示的 Unicode 字符*/

    public static Stream<String> codePoints(String s) {
        var result = new ArrayList<String>();
        int i = 0;
        while (i < s.length()) {
            int j = s.offsetByCodePoints(i, 1);
            result.add(s.substring(i, j));
            i = j;
        }
        return result.stream();
    }

    @Test
    public void flatStream() {
        List<String> words = Tools.genString(4, 10, true);
        Stream<Stream<String>> multiLayerStream = words.stream().map(MapOperationStreamTest::codePoints);
        multiLayerStream.forEach(stream -> stream.forEach(Print::prints));
        System.out.println();
        // flatStream
        Stream<String> flatStream = words.stream().flatMap(MapOperationStreamTest::codePoints);
        flatStream.forEach(Print::prints);
    }
}
