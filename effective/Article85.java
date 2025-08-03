package effective;


import cn.qingweico.io.Print;
import cn.qingweico.serialize.SerializeUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 其他方法优先于Java序列化
 * @author zqw
 * @date 2021/11/12
 */
class Article85 {
    public static void main(String[] args) {
        byte[] bombed = bomb();
        Print.grace("byte stream length", bombed.length);
        // 反序列化时间花费的时间很长, hashCode方法被调用超过2^100次
        SerializeUtil.deserialize(bombed);
    }

    /**Deserialization bomb - deserializing this stream takes forever*/
    static byte[] bomb() {
        Set<Object> root = new HashSet<>();
        Set<Object> s1 = root;
        Set<Object> s2 = new HashSet<>();
        for(int i = 0;i < 100;i++) {
            Set<Object> t1 = new HashSet<>();
            Set<Object> t2 = new HashSet<>();
            // Make t1 unequal to t2
            t1.add("foo");
            s1.add(t1);
            s1.add(t2);
            s2.add(t1);
            s2.add(t2);
            s1 = t1;
            s2 = t2;
        }
        return SerializeUtil.serialize(root);
    }
    // 避免序列化攻击的最佳方式是永远不要反序列化任何东西
    // 使用JDK9 引入的 ObjectInputFilter 类
    // 使用[跨平台的结构化数据表示法]代替序列化(JSON or protobuf)


    // JSON 是 Douglas Crockford 为浏览器 - 服务器之间的通信设计的(起初是为 JavaScript 开发的)
    // Protobuf Buffers 是 Google 为了在服务器之间保存和交换结构化数据设计的(起初是为 C++ 开发的)
}
