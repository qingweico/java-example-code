package collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author:qiming
 * @date: 2021/10/18
 */
public class CustomerComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer o1, Customer o2) {

        // descending order by name
        if(o1.getName().compareTo(o2.getName()) > 0) {return -1;}
        // swap
        if(o1.getName().compareTo(o2.getName()) < 0) {return 1;}
        return 0;
    }

    public static void main(String[] args) {
        Set<Customer> set = new TreeSet<>(new CustomerComparator());
        set.add(new Customer("Tom", 28));
        set.add(new Customer("Jerry", 20));
        set.add(new Customer("Rose", 24));
        System.out.println(set);
     }
     @Test
     public void bitComparable() {
         Integer[] a = new Integer[]{1, 8, 2 ,0};
         Arrays.sort(a, (o1, o2) -> Integer.bitCount(o1) > Integer.bitCount(o2) ? o1 - o2 : o2 - o1);
         System.out.println(Arrays.toString(a));
     }
}
