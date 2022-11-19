package geek.object;

import lombok.extern.slf4j.Slf4j;
import util.constants.Constants;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * VM: {@code -XX:+PrintStringTableStatistics} 打印出字符串常量表的统计信息
 *
 * @author zqw
 * @date 2022/8/1
 */
@Slf4j
public class Equal {
    public static void main(String[] args) {

        List<String> list;
        long begin = System.currentTimeMillis();
        list = IntStream.rangeClosed(1, Constants.NUM_10000000)
                .mapToObj(i -> String.valueOf(i).intern())
                .collect(Collectors.toList());
        // 字符串常量池是一个大小固定的 Map, 一千万个字符串intern操作耗时41.386s;
        // 字符串太多,桶的数量默认只有60013,哈希冲突严重,导致每一个桶中的字符串数量很多,搜索速度很慢
        // solved: 使用 VM 参数:-XX:StringTableSize=10000000 指定更多数量的桶即可,耗时3128ms
        log.info("time: {}ms", System.currentTimeMillis() - begin);
        System.out.println(list.size());
    }
}
