package collection;

import cn.hutool.core.lang.UUID;
import cn.qingweico.collection.CollUtils;
import cn.qingweico.io.Print;
import cn.qingweico.supplier.RandomDataGenerator;
import org.springframework.core.CollectionFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 一些Collection通用的操作
 *
 * @author zqw
 * @date 2025/8/9
 * @see CollectionFactory
 * @see Collections
 */
class SpringCollectionFactory {
    public static void main(String[] args) throws IOException {
        Properties properties = CollectionFactory.createSortedProperties(false);
        FileOutputStream fos = new FileOutputStream("out.txt");
        CollUtils.fillMap(properties, 10, () -> UUID.fastUUID().toString(true), () -> RandomDataGenerator.address(true));
        properties.storeToXML(fos, "CollectionFactory.createSortedProperties");
        Map<Object, Object> map = CollectionFactory.createMap(TreeMap.class, String.class, 10);
        CollUtils.fillMap(map, 20);
        Print.printMap(map);
        Enumeration<Object> enumeration = Collections.enumeration(map.keySet());
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement());
        }
    }
}
