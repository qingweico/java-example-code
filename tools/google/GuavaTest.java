package tools.google;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import util.Print;
import util.RandomDataUtil;
import util.Tools;
import util.constants.Constants;
import util.constants.Symbol;

import java.util.*;

/**
 * Guava 工具包测试
 * @author zqw
 * @date 2023/2/17
 */
public class GuavaTest {
    @Test
    public void immutableColt() {

        // 不可变集合
        ImmutableList<String> list = ImmutableList.of("A", "B", "C");
        ImmutableMap<Integer, String> map = ImmutableMap.of(1, "A", 2, "B", 3, "C");
        Print.toPrint(list);
        Print.toPrint(map);
    }

    @Test
    public void multiMap() {

        // 多值Map
        Multimap<String, String> map = ArrayListMultimap.create();
        map.put("ReCall", "127.0.0.1");
        map.put("ReCall", "192.168.1.23");
        map.put("DataSource", "master");
        map.put("DataSource", "slave");
        System.out.println(map);
        System.out.println(map.get("ReCall"));


        // Spring
        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
        List<String> adds = new ArrayList<>();
        int loops = 4;
        for (int i = 0; i < loops; i++) {
            adds.add(RandomDataUtil.address(true));
        }
        mvm.put("Address", adds);
        Print.toPrint(mvm);

        // Apache
        org.apache.commons.collections.map.MultiValueMap multi = new org.apache.commons.collections.map.MultiValueMap();
        multi.put("Entry", "first");
        multi.put("Entry", "second");
        multi.put("Entry", "third");
        Print.toPrint(multi);
    }

    @Test
    public void multiValue() {
        // 多个键值对一个值
        HashBasedTable<Object, Object, Object> bt = HashBasedTable.create();
        bt.put("x1", "y1", "Pointer1");
        bt.put("x2", "y2", "Pointer2");
        System.out.println(bt);
        System.out.println(bt.get("x1", "y1"));
    }

    @Test
    public void filterMap() {
        // Map 过滤
        Map<String, Integer> map = new HashMap<>(3);
        map.put("T1", 10);
        map.put("T2", 20);
        map.put("T3", 21);
        Map<String, Integer> filtedmap = Maps.filterValues(map, val -> val > 20);
        Print.toPrint(map);
        Print.toPrint(filtedmap);
    }

    @Test
    public void joiner() {
        // Joiner连接
        Joiner joiner = Joiner.on(",");
        // String 连接
        String str = joiner.skipNulls().join("Hello", null, "World");
        System.out.println(str);
        // 键值对 连接
        Map<String, String> map = new HashMap<>(3);
        map.put("K1", "V1");
        map.put("K2", "V2");
        str = Joiner.on(",").withKeyValueSeparator(" : ").join(map);
        System.out.println(str);
    }

    @Test
    public void splitter() {
        String str = "Hello,World";
        // 按字符分割
        for (String s : Splitter.on(Symbol.DOT).split(str)) {
            Print.prints(s);
        }
        Print.println();
        // 按固定长度分割
        for (String d : Splitter.fixedLength(Constants.TWO).split(str)) {
            Print.prints(d);
        }
    }

    @Test
    public void primitives() {
        int[] array = Tools.genIntArray(10);
        System.out.println(Ints.max(array));
    }
}
