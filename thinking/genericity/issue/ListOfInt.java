package thinking.genericity.issue;

import java.util.ArrayList;
import java.util.List;

/**
 * Autoboxing compensates for the inability to use primitive in generics
 *
 * @author:周庆伟
 * @date: 2021/1/20
 */
public class ListOfInt {
    public static void main(String[] args) {
        List<Integer> li = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            li.add(i);
        }
        for (int i: li) {
            System.out.print(i + " ");

        }
    }
}
