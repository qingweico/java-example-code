package thinking.container.map.hashcode;


import cn.qingweico.io.Print;

import java.util.ArrayList;
import java.util.*;
import java.util.List;

/**
 * Creating a good hashcode
 *
 * @author zqw
 * @date 2021/4/3
 */
class CountedString {
    private static final List<String> CREATED = new ArrayList<>();
    private final String s;
    private int id = 0;

    public CountedString(String str) {
        s = str;
        CREATED.add(s);

        // id is the total number of instances of
        // this string in use by CountedString:
        for (String s0 : CREATED) {
            if (s0.equals(s)) {
                id++;
            }
        }
    }

    @Override
    public String toString() {
        return "String: " + s + " id: " + id + " hashCode(): " + hashCode();
    }

    @Override
    public int hashCode() {
        // The very simple approach:
        // return s.hashCode() * id;
        // Using Joshua Bloch's recipe:
        int result = 17;
        result = 37 * result + s.hashCode();
        result = result * 37 + id;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CountedString &&
                s.equals(((CountedString) o).s) &&
                id == ((CountedString) o).id;
    }

    public static void main(String[] args) {
        CountedString[] cs = new CountedString[5];
        Map<CountedString, Integer> map = new HashMap<>(cs.length);
        for (int i = 0; i < cs.length; i++) {
            cs[i] = new CountedString("hi");
            map.put(cs[i], i);
        }
        Print.println(map);
        for (CountedString cString : cs) {
            Print.println("Looking up " + cString);
            Print.println(map.get(cString));
        }
    }
}
