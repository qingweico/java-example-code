package thinking.container.map.hashcode;


import java.util.ArrayList;
import java.util.*;
import java.util.List;

import static util.Print.print;

/**
 * Creating a good hashcode
 *
 * @author:qiming
 * @date: 2021/4/3
 */
public class CountedString {
    private static final List<String> created = new ArrayList<>();
    private final String s;
    private int id = 0;
    public CountedString(String str) {
        s = str;
        created.add(s);

        // id is the total number of instances of
        // this string in use by CountedString:
        for(String s0 : created) {
            if(s0.equals(s)) {
                id++;
            }
        }
    }
    public String toString() {
        return "String: " + s + " id: " + id  + " hashCode(): " + hashCode();
    }
    public int hashCode() {
        // Thee very simple approach:
        // return s.hashCode() * id;
        // Using Joshua Bloch's recipe:
        int result = 17;
        result = 37 * result + s.hashCode();
        result = result * 37 + id;
        return result;
    }
    public boolean equals(Object o) {
        return o instanceof CountedString &&
                s.equals(((CountedString)o).s) &&
                id == ((CountedString)o).id;
    }

    public static void main(String[] args) {
        Map<CountedString, Integer> map = new HashMap<>();
        CountedString[] cs = new CountedString[5];
        for(int i = 0; i< cs.length;i++) {
            cs[i] = new CountedString("hi");
            map.put(cs[i],i);
        }
        print(map);
        for(CountedString cString : cs) {
            print("Looking up " + cString);
            print(map.get(cString));
        }
    }
}
