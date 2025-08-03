package object.entity;

import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.NoArgsConstructor;
import cn.qingweico.supplier.ObjectFactory;
import cn.qingweico.io.Print;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author zqw
 * @date 2021/10/18
 */
@Data
@NoArgsConstructor
public class Customer implements Comparable<Object> {

    private String name;
    private Integer age;

    public Customer(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(age, customer.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("age", age)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull Object o) {
        Customer anObject = (Customer) o;
        // a.name > b.name => 交换ab
        if (this.name.compareTo(anObject.name) > 0) {
            return 1;
        }
        // a.name < b.name nothing to do
        if (this.name.compareTo(anObject.name) < 0) {
            return -1;
        }
        // name 一样的话 按照年龄升序排列
        return this.age.compareTo(anObject.getAge());
    }

    public static void main(String[] args) {
        TreeSet<Customer> customers = new TreeSet<>();
        customers.add(ObjectFactory.create(Customer.class, true));
        customers.add(ObjectFactory.create(Customer.class, true));
        customers.add(ObjectFactory.create(Customer.class, true));
        Print.printColl(customers);

        // 构建比较器: 按多个字段排序 默认升序, reversed() 降序
        Comparator<Customer> comparator = Comparator.comparing(Customer::getAge).thenComparing(Customer::getName).reversed();
        List<Customer> list = new ArrayList<>(Arrays.asList(
                ObjectFactory.create(Customer.class, true),
                ObjectFactory.create(Customer.class, true))
        );
        list.sort(comparator);
        Print.printColl(list);
    }
}
