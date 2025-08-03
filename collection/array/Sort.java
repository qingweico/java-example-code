package collection.array;

import cn.qingweico.supplier.Tools;
import object.entity.Customer;
import thinking.genericity.BasicGenerator;
import cn.qingweico.collection.CollectionData;
import cn.qingweico.supplier.ObjectFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author zqw
 * @date 2022/7/16
 */
class Sort {
    public static void main(String[] args) {
        List<Integer> list = Tools.genList(20, 100);
        System.out.println(list);
        // 对于原始数据类型使用 Dual-pivot QuickSort
        int[] toArray = list.stream().mapToInt(x -> x).toArray();
        Arrays.sort(toArray);
        System.out.println(Arrays.toString(toArray));

        // 对于对象数据类型 使用 TimSort (并归和二分插入排序结合的优化排序算法)
        CollectionData<Customer> cd = new CollectionData<>(BasicGenerator.create(Customer.class), 3);
        for (Customer c : cd) {
            ObjectFactory.populate(c, Customer.class);
        }
        System.out.println(cd);
        Arrays.sort(cd.toArray());
        System.out.println(cd);
    }
}
