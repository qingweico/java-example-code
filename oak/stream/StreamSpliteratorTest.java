package oak.stream;

import cn.qingweico.constants.Symbol;
import cn.qingweico.io.Print;
import cn.qingweico.supplier.Tools;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * {@link Spliterator} 为并行执行而设计的, 是 Java 函数式编程和并行流的核心基石
 * 1: 遍历元素, 类似 {@link Iterator}
 * 2: 分割数据 {@link Spliterator#trySplit()}
 * @author zqw
 * @date 2025/8/31
 */
public class StreamSpliteratorTest {

    @Test
    public  void trySplit() {
        List<String> list = Tools.genString(20, 1);
        System.out.println("\toriginal");
        System.out.println(list);
        Spliterator<String> original = list.spliterator();
        Spliterator<String> firstHalf = original.trySplit();
        Spliterator<String> firstQuarter = firstHalf.trySplit();
        System.out.println("\tfirstQuarter");
        firstQuarter.forEachRemaining(Print::prints);
        System.out.println();
        System.out.println("\tfirstHalf");
        firstHalf.forEachRemaining(Print::prints);
        System.out.println();
        System.out.println("\tremaining");
        original.forEachRemaining(Print::prints);
    }
    @Test
    public void spliteratorImpl() {
        String str = String.join(Symbol.COMMA, Tools.genString(1, 100));
        System.out.println(str);
        StringSpliterator spliterator = new StringSpliterator(str, 0, 2);
        StreamSupport.stream(spliterator, true)
                .forEach(System.out::print);
    }
   /*自定义的 Spliterator*/
    static class StringSpliterator implements Spliterator<String> {
        private final String string;
        private int currentIndex = 0;
        private final int groupSize;
        public StringSpliterator(String string,
                                 int startIndex,
                                 int groupSize) {
            this.string = string;
            this.currentIndex = startIndex;
            this.groupSize = groupSize;
        }

        @Override
        public boolean tryAdvance(Consumer<? super String> action) {
            if (currentIndex >= string.length()) {
                return false;
            }
            int endIndex = Math.min(currentIndex + groupSize, string.length());
            String group = string.substring(currentIndex, endIndex);
            if (endIndex < string.length() || group.length() == groupSize) {
                group += Symbol.POUND_SIGN;
            }
            action.accept(group);
            currentIndex = endIndex;
            return true;
        }

        @Override
        public Spliterator<String> trySplit() {
            int remainingSize = string.length() - currentIndex;
            if (remainingSize <= groupSize * 4) {
                return null;
            }
            int splitPos = currentIndex + (remainingSize / 2);
            splitPos = splitPos - (splitPos % groupSize);
            if (splitPos <= currentIndex || splitPos >= string.length()) {
                return null;
            }
            this.currentIndex = splitPos;
            return new StringSpliterator(string, splitPos, groupSize);
        }

        @Override
        public long estimateSize() {
            int remainingChars = string.length() - currentIndex;
            return (long) Math.ceil((double) remainingChars / groupSize);
        }

        @Override
        public int characteristics() {
            return ORDERED | SIZED | NONNULL | IMMUTABLE;
        }
    }
}
