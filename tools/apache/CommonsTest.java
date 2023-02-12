package tools.apache;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;
import util.Print;

import java.util.ArrayList;
import java.util.List;

/**
 * Apache commons 工具包测试
 *
 * @author zqw
 * @date 2023/2/10
 */

public class CommonsTest {
    @Test
    public void strCheck() {
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(null));
        System.out.println(StringUtils.isEmpty(" "));

        System.out.println(StringUtils.isAnyBlank(" hhh ", null));
        System.out.println(StringUtils.isAnyBlank(""));
        System.out.println(StringUtils.isAnyBlank(new String[]{}));


        System.out.println(StringUtils.isBlank(null));
        System.out.println(StringUtils.isBlank(""));
        System.out.println(StringUtils.isBlank("   "));
    }



    /*判断是否为数字*/

    @Test
    public void isNumeric() {
        // isNumeric : Only Unicode digits, A decimal point is not a Unicode digit
        System.out.println(StringUtils.isNumeric("1"));
        System.out.println(StringUtils.isNumeric("1 2"));
        System.out.println(StringUtils.isNumeric("123abc"));
        System.out.println(StringUtils.isNumeric("123.9"));
        System.out.println(NumberUtils.isCreatable("123."));
    }

    /*随机字符串生成*/

    @Test
    public void randomStr() {
        // 十位数字随机数
        System.out.println(RandomStringUtils.randomNumeric(10));
        // 十位字母随机数
        System.out.println(RandomStringUtils.randomAlphabetic(10));
        // 十位ASCII随机数
        System.out.println(RandomStringUtils.randomAscii(10));
        // 十位数字和字母混合
        System.out.println(RandomStringUtils.random(10,true,true));
        // or
        System.out.println(RandomStringUtils.randomAlphanumeric(10));

    }


    /*LazyList*/


    @Test
    @SuppressWarnings("unchecked, rawtypes")
    public void lazyList() {
        List lazy = LazyList.decorate(new ArrayList<>(), () -> "Hello");
        String str = (String) lazy.get(2);
        System.out.println(str);
        lazy.add("World");
        System.out.println(lazy.size());
    }

    /*双向Map : 具有唯一的key*/

    @Test
    public void bidiMap() {
        BidiMap map = new TreeBidiMap();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        Print.toPrint(map);
        System.out.println(map.get(2));
        System.out.println(map.getKey("a"));
        System.out.println(map.removeValue("c"));
        Print.toPrint(map);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void strOpe() {
        // 合并两个数组
        String[] s1 = new String[] { "1", "2", "3" };
        String[] s2 = new String[] { "a", "b", "c" };
        String[] s = ArrayUtils.addAll(s1, s2);
        Print.printArray(s);
        // 字符串截取
        String str = ArrayUtils.toString(s);
        System.out.println(str);
        str = str.substring(1, str.length() - 1);
        System.out.println(str);
        System.out.println(StringUtils.substringAfter("SELECT * FROM PERSON ", "FROM"));
        str = "The best time to plant a tree is ten years ago, followed by now";
        // 首字符大写
        System.out.println(WordUtils.capitalize(str));
        // 取得类名
        System.out.println(ClassUtils.getShortClassName(CommonsTest.class));
        // 获取包名
        System.out.println(ClassUtils.getPackageName(CommonsTest.class));

        // HTML 标签转义
        System.out.println(StringEscapeUtils.escapeHtml("<html>"));

        System.out.println(StringUtils.join(s1, ","));
        System.out.println(StringUtils.rightPad("abc", 6, '0'));
        // 首字母大写
        System.out.println(StringUtils.capitalize("abc"));
        // 去掉空格
        System.out.println(StringUtils.deleteWhitespace("   ab  c  "));
        // 是否包含该字符
        System.out.println(StringUtils.contains("abc", "ba"));
        // 左边的字符
        System.out.println(StringUtils.left("abc", 2));
    }
}
