package geek.serialize;

import java.util.ArrayList;

/**
 * transient Object[] elementData;
 * ----------------------  关于elementData属性被transient关键字修饰  ----------------------
 * transient关键字修饰的字段表示该属性不会被序列化;但由于 ArrayList 的数组是基于动态扩增的,所以并不是所有
 * 被分配的内存空间都储存了数据;如果采用外部序列化法实现数组的序列化,会序列化整个数组;ArrayList为了避免这些
 * 没有储存数据的内存空间被序列化,内部实现了两个私有方法 writeObject 以及 readObject 来完成自我序列化与
 * 反序列化,从而在序列化与反序列化数组时节省了时间和空间
 * {@link java.util.LinkedList} 也是自行实现了 writeObject 以及 readObject 进行序列化与反序列化
 *
 * @author zqw
 * @date 2022/7/3
 */
class ArrayListSerialize {
    public static void main(String[] args) {
        System.out.println(new ArrayList<>());
    }
}
