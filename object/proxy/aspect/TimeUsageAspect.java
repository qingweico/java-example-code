package object.proxy.aspect;

import cn.qingweico.io.Print;

/**
 * @author zqw
 * @date 2021/9/28
 */
public class TimeUsageAspect implements IAspect {

    long start;

    @Override
    public void before() {
        start = System.currentTimeMillis();
    }

    @Override
    public void after() {
        var usage = System.currentTimeMillis() - start;
        Print.time("time usage", usage);
    }
}
