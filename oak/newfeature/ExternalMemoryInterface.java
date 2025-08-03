package oak.newfeature;

import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;

import java.nio.ByteBuffer;

/**
 * -------------------- 外部内存接口 --------------------
 * ########## 本地内存出现的背景 ##########
 * 对性能有着偏执的追求的类库,为了避免 Java 垃圾收集器不可预测的行为以及额外的性能开销,
 * 这些产品一般倾向于使用 JVM 之外的内存来存储和管理数据,这样的数据,就是我们常说的堆外数据
 * 使用堆外存储最常用的办法,就是使用 ByteBuffer 这个类来分配直接存储空间(direct buffer)
 * JVM 虚拟机会尽最大努力直接在直接存储空间上执行 IO 操作,避免数据在本地和 JVM 之间的拷贝
 * 由于频繁的内存拷贝是性能的主要障碍之一 所以为了极致的性能,应用程序通常也会尽量避免内存的拷贝
 * 理想的状况下,一份数据只需要一份内存空间,这就是我们常说的零拷贝
 * {@link ByteBuffer} 是异步编程和非阻塞编程的核心类,几乎所有的 Java 异步模式或者非阻塞模式
 * 的代码,都要直接或者间接地使用 {@code ByteBuffer} 来管理数据
 * 非阻塞和异步编程模式的出现,起始于对于阻塞式文件描述符(File descriptor)(包括网络套接字)
 * 读取性能的不满,而诞生于 2002 年的 ByteBuffer,最初的设想也主要是用来解决当时文件描述符的读写性能的
 * ########## ByteBuffer 类主要有两个缺陷 ##########
 * 1 : 没有资源释放的接口;一旦一个 ByteBuffer 实例化,它占用了内存的释放,就会完全依赖 JVM 的垃圾回收机制
 * 2 : 存储空间尺寸的限制;ByteBuffer 的存储空间的大小,是使用 Java 的整数来表示的,所以它的存储空间最多只有 2G
 * ########## 外部内存接口出现的原因 ##########
 * 对 ByteBuffer 类的缺陷进行一个合理的改进,就是重新建造一个轮子,这个新的轮子就是外部内存接口
 *
 * @author zqw
 * @date 2022/12/30
 * VM (Runtime) Args: --add-modules jdk.incubator.foreign
 * @see MemorySegment 定义和模拟了一段连续的内存区域
 * @see MemoryAccess 定义了可以对 MemorySegment 执行读写操作
 * 在 ByteBuffer 的设计里,内存的表达和操作是在 ByteBuffer 这一个类里完成的;在外部内存接口的设计里
 * 把对象表达和对象的操作拆分成了两个类,这两类的寻址数据类型使用的是长整形(long),这样长整形的
 * 寻址类型就解决了 ByteBuffer 的第二个缺陷
 */
class ExternalMemoryInterface {
    public static void main(String[] args) {

        int sz = Constants.BYTE;
        // 实现了 AutoCloseable 的接口 就可以使用 try-with-resource 这样的语句,,及时地释放掉它管理的内存了
        // 解决了 ByteBuffer 的第一个缺陷
        try (ResourceScope scope = ResourceScope.newConfinedScope()) {
            MemorySegment segment = MemorySegment.allocateNative(sz, scope);
            for (int i = 0; i < sz; i++) {
                MemoryAccess.setByteAtOffset(segment, i, (byte) ('a' + i));
                Print.prints(MemoryAccess.getByteAtOffset(segment, i));
            }
        }
    }
}
