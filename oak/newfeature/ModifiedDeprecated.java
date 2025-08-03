package oak.newfeature;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import cn.qingweico.io.Print;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * -------------------- 改进的废弃 : 怎么避免使用废弃的特性 --------------------
 * 在 JDK 中,公开接口的废弃需要使用两种不同的机制 :
 * 1 : Deprecated注解
 * 2 : Deprecated文档标记
 * Deprecated 的注解会编译到类文件里,并且可以在运行时查验
 * Deprecated 文档标记用于描述废弃接口的文档中;除了标记接口的废弃状态之外,一般情况下,我们还要描述废弃的原因和替代的方案
 * 公开接口的废弃机制,是在 JDK 1.5 的时候发布的,在 JDK 9 中接口废弃机制里有了重大的改进
 * 1 : 添加了一个新的工具,jdeprscan;有了这个工具,就可以扫描编译好的 Java 类或者包,看看有没有使用废弃的接口了
 * 即使代码使用了 SuppressWarnings 注解, jdeprscan 的结果也不受影响,如果我们使用第三方的类库,或者已经编译好的
 * 类库,发现对废弃接口的依赖关系很重要;如果将来废弃接口被删除,使用废弃接口的类库将不能正常运行;而 jdeprscan
 * 允许我们在使用一个类库之前进行废弃依赖关系检查,提前做好风险的评估
 * 2 : 给 Deprecated 注解增加了一个forRemoval的属性;如果这个属性设置为true,那就表示这个废弃接口的删除已
 * 经提上日程了;两到三个版本之后,这个废弃的接口就会被删除
 * 3 : 给 Deprecated 注解增加了一个since的属性;这个属性会说明这个接口是在哪一个版本废弃的
 *
 * @author zqw
 * @date 2023/1/1
 */
class ModifiedDeprecated {

    public static void main(String[] args) {
        try {
            System.out.println(Arrays.toString(DigestImpl.of("SHA-256").digest("Hello SHA-256!".getBytes())));
            Digest0 digest = new DigestImpl();
            digest.digest("Hello SHA-256!".getBytes(), "&dASd(Sd98!@!".getBytes());
        } catch (NoSuchAlgorithmException ex) {
            // ignore
        }
    }


}

sealed abstract class Digest0 permits DigestImpl {

    /**
     * @deprecated This method is not performance friendly. Use
     * {@link #digest(byte[], byte[]) instead.
     */
    @Deprecated(since = "1.4", forRemoval = true)
    public abstract byte[] digest(byte[] message) throws NoSuchAlgorithmException;


    public void digest(byte[] message, byte[] digestValue) {
        throw new UnsupportedOperationException();

    }
}

@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
final class DigestImpl extends Digest0 {
    private String algo;


    // 当 forRemoval = true 时@SuppressWarnings("deprecation") 就会失去效果,
    // 要想消除掉编译警告请使用@SuppressWarnings("removal")
    @SuppressWarnings("removal")
    @Override
    public byte[] digest(byte[] message) throws NoSuchAlgorithmException {
        return message;
    }

    @Override
    public void digest(byte[] message, byte[] digestValue) {
        // simple handle
        Print.grace(new String(message), new String(digestValue));
    }
}
