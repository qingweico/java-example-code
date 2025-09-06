package oak.test;

import org.junit.Test;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * {@link RandomGeneratorFactory }Java 17 中引入的随机数生成器工厂类(基于 SPI 机制),
 * 目的是提供一种统一、灵活的方式来发现、选择和实例化随机数生成器(RNG)算法, 替代旧的 {@link Math#random()}
 * 和 {@link java.util.Random}, 适合使用在并行流和高性能应用中
 *
 * @author zqw
 * @date 2025/8/31
 */
public class RandomGeneratorFactoryTest {
    @Test
    public void getAll() {
        var allAlgorithms = RandomGeneratorFactory.all()
                .map(f -> f.name() + " (" + f.group() + ")")
                .sorted()
                .toList();
        allAlgorithms.forEach(System.out::println);
    }

    @Test
    public void createSpecAlgor() {
        // 根据某个算法的名称创建 RandomGenerator
        RandomGenerator generator = RandomGenerator.of("L64X256MixRandom");
        System.out.println(generator.nextInt(100));
    }

    @Test
    public void getDefaultAlgo() {
        RandomGenerator generator = RandomGenerator.getDefault();
        System.out.println(generator.getClass().getSimpleName());
    }

    @Test
    public void createAlgoByFactory() {
        RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.of("L128X128MixRandom");
        // 获取算法的元数据
        boolean arbitrarilyJumpable = factory.isArbitrarilyJumpable();
        boolean hardware = factory.isHardware();
        boolean jumpable = factory.isJumpable();
        System.out.println(arbitrarilyJumpable);
        System.out.println(hardware);
        System.out.println(jumpable);
        RandomGenerator generator = factory.create();
        System.out.println(generator.nextFloat(10));
    }

    @Test
    public void seed() {
        // 需要可重复的随机数序列, 可以通过创建带有特定种子的生成器(必须在创建时就传入种子,而非在创建后再设置)
        long seed = 1L;
        RandomGeneratorFactory<RandomGenerator> rgf1 =
                RandomGeneratorFactory.of("L64X128MixRandom");
        RandomGeneratorFactory<RandomGenerator> rgf2 =
                RandomGeneratorFactory.of("L64X128MixRandom");

        System.out.println(rgf1.create(seed).nextInt(100));
        System.out.println(rgf2.create(seed).nextInt(100));
    }
}
