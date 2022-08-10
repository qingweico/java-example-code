package effective;

import util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 了解和使用类库
 * <p>
 * 优秀的第三方类库比如Google优秀的开源Guava类库
 * 从经济的角度分析表明: 类库代码受到的关注远远超过大多数普通程序员在同样功能上所能给予的投入
 * 从java7开始, 就不应该再使用Random ThreadLocalRandom可以产生更高质量的随机数, 并且速度非常快
 *
 * @author zqw
 * @date 2020/11/23
 */
class ThreadLocalRandomTest {
    static ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    public static void main(String[] args) {
        System.out.println(threadLocalRandom.nextInt(100));
    }
}
class Article59 {
    /** Common but deeply flawed*/
    static Random rnd = new Random();

    static int random(int n) {
        return Math.abs(rnd.nextInt()) % n;
    }

    public static void main(String[] args) {
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < Constants.NUM_1000000; i++) {
            if (random(n) < n / 2) {
                low++;
            }
        }
        // 接近666666, 说明random方法产生的数字有三分之二落在了随机数取值的前半部分
        System.out.println(low);
    }
}

// 在java9中InputStream中增加了transferTo方法
// 打印出命令行指定一条URL(url需要加协议)的内容, Linux中curl命令作用大体如此

class InputStreamOfTransferTo {
    public static void main(String[] args) throws IOException {
        /* Programmer arguments: https://qingweico.cn*/
        try (InputStream in = new URL(args[0]).openStream()) {
            in.transferTo(System.out);
        }
    }
}
