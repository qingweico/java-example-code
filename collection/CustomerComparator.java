package collection;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author zqw
 * @date 2021/10/18
 */
class CustomerComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer o1, Customer o2) {

        // Descending order by name(Swapping when return 1)
        return Integer.compare(0, o1.getName().compareTo(o2.getName()));
    }

    public static void main(String[] args) {
        Set<Customer> set = new TreeSet<>(new CustomerComparator());
        set.add(new Customer("Tom", 28));
        set.add(new Customer("Jerry", 20));
        set.add(new Customer("Rose", 24));
        System.out.println(set);
     }
}
