package thread;

import util.constants.Symbol;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * {@code MXBean} {@link ThreadGroup} 获取当前程序的线程数
 *
 * @author zqw
 * @date 2022/7/8
 */
class GetThreadCount {
    public static void main(String[] args) {
        System.out.println("============= MXBean =============");
        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMxBean.getAllThreadIds();
        ThreadInfo[] threadInfos = threadMxBean.getThreadInfo(threadIds);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId() + Symbol.COLON + Symbol.WHITE_SPACE + threadInfo.getThreadName());
        }
        System.out.println("============= ThreadGroup =============");
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        if(topGroup == null) {
            System.err.println("topGroup is null");
            return;
        }
        int nowThreads = topGroup.activeCount();
        Thread[] lstThreads = new Thread[nowThreads];
        topGroup.enumerate(lstThreads);
        for (int i = 0; i < nowThreads; i++) {
            System.out.println(i + ": " + lstThreads[i].getName());
        }
    }
}
