package geek.collection;

import util.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * List.subList 进行切片会导致 OOM
 *
 * @author zqw
 * @date 2022/8/2
 */
class SubList {

    static List<List<Integer>> data = new ArrayList<>();

    private static void oom() {
        for (int i = 0; i < Constants.NUM_1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(rawList.subList(0, 1));
        }
    }

    public static void main(String[] args) {
        // TODO https://time.geekbang.org/column/article/216778?screen=full
        oom();
    }
}
