package geek.serialize;

import java.io.*;


/**
 * Java序列化的缺陷 无法跨语言
 * 针对序列化 通常建议
 * 1: 敏感信息不要被序列化,建议使用 {@code transient} 关键字保护起来
 * 2: 反序列化中,建议在 readObject 中实现与对象构建过程相同的安全检查和数据检查
 *
 * @author zqw
 * @date 2022/6/29
 * @since JDK9 {@link ObjectInputFilter} Java 引入了过滤器机制
 */
class AvoidUsingJavaSerialize {
    public static void main(String[] args) throws IOException {
    }
}

