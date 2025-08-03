package oak.newfeature;

import cn.hutool.core.date.StopWatch;
import jdk.incubator.vector.FloatVector;
import cn.qingweico.supplier.Tools;

/**
 * -------------------- 矢量运算 --------------------
 * Java 的矢量运算就是使用单个指令并行处理多个数据的一个尝试
 * (单指令多数据,Single Instruction Multiple Data)在现
 * 代的微处理器（CPU）中,一个控制器可以控制多个平行的处理单元;
 * 在现代的图形处理器(GPU)中更是拥有强大的并发处理能力和可编程
 * 流水线;这些处理器层面的技术为软件层面的单指令多数据处理提供
 * 了物理支持;Java 矢量运算的设计和实现,也是希望能够借助现代
 * 处理器的这种能力提高运算的性能
 *
 * @author zqw
 * @date 2022/12/30
 * Compile Args: --add-modules jdk.incubator.vector
 * VM (Runtime) Args: --add-modules jdk.incubator.vector
 */
class VectorCalculation {

    // 计算 线性方程 y = a0x0 + a1x1 + a2x2 + a3x3 + ...;


    private static final float[] A = new float[]{0.6F, 0.7F, 0.8F, 0.9F};
    private static final FloatVector VA =
            FloatVector.fromArray(FloatVector.SPECIES_128, A, 0);

    private static final float[] X = new float[]{1.0F, 2.0F, 3.0F, 4.0F};
    private static final FloatVector VX =
            FloatVector.fromArray(FloatVector.SPECIES_128, X, 0);

    private static final float[] HUGE_A = Tools.genFloatArray(1000000, 10);
    private static final FloatVector HUGE_VA =
            FloatVector.fromArray(FloatVector.SPECIES_128, HUGE_A, 0);
    private static final float[] HUGE_X = Tools.genFloatArray(1000000, 10);
    private static final FloatVector HUGE_VX =
            FloatVector.fromArray(FloatVector.SPECIES_128, HUGE_X, 0);

    public static void main(String[] args) {
        StopWatch sw = new StopWatch("calculate linear equation");
        sw.start("simple_scalar");
        System.out.println(sumInScalar(A, X));
        sw.stop();
        sw.start("simple_vector");
        System.out.println(sumInVector(VA, VX));
        sw.stop();
        sw.start("huge_scalar");
        System.out.println(sumInScalar(HUGE_A, HUGE_X));
        sw.stop();
        sw.start("huge_vector");
        System.out.println(sumInVector(HUGE_VA, HUGE_VX));
        sw.stop();
        System.out.println(sw.prettyPrint());
    }

    // 使用标量计算

    private static Returned sumInScalar(float[] a, float[] x) {
        if (a == null || x == null || (a.length ^ x.length) == 0) {
            return new Returned.ErrorCode(-1);
        }
        float[] y = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            y[i] = a[i] * x[i];
        }
        float r = 0F;
        for (float v : y) {
            r += v;
        }
        return new Returned.ReturnValue(r);
    }


    // 使用矢量计算

    private static Returned sumInVector(FloatVector va, FloatVector vx) {
        if (va == null || vx == null || va.length() != vx.length()) {
            return new Returned.ErrorCode(-1);
        }
        float[] y = va.mul(vx).toArray();

        float r = 0F;
        for (float v : y) {
            r += v;
        }
        return new Returned.ReturnValue(r);
    }
}
