package thread.pool;

import lombok.extern.slf4j.Slf4j;
import util.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ----------------------- Task --------------------------
 * 通过测试IO密集型和计算密集型任务来验证线程池中线程数量与CPU核心的关系
 *
 * @author zqw
 * @date 2022/7/2
 */
@Slf4j
class CPUCoreThreadPoolCount {

    static final double pi = Math.acos(-1.0);
    static final ThreadLocalRandom tlr = ThreadLocalRandom.current();
    static int cpuCore = Runtime.getRuntime().availableProcessors();
    static int threadCount = cpuCore * 2;
    static CountDownLatch latch = new CountDownLatch(threadCount * 2);

    static class CPUType extends AbstractTask {


        @Override
        @SuppressWarnings("unused")
        public void task() {
            double result = 0.0;
            for (int i = 0; i < Constants.NUM_1000000; i++) {
                result += 1.0 * i / pi * tlr.nextDouble(100);
            }
        }

        CPUType(List<Long> runTimeList, List<Long> wholeTimeList) {
            super(runTimeList, wholeTimeList);
        }
    }

    static class IOType extends AbstractTask {

        IOType(List<Long> runTimeList, List<Long> wholeTimeList) {
            super(runTimeList, wholeTimeList);
        }


        @Override
        public void task() {
            File sourceFile = new File("data");
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile))) {
                byte[] buf = new byte[Constants.KB];
                for (; ; ) {
                    int read = bis.read(buf);
                    if (read == -1) {
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    static abstract class AbstractTask implements Runnable {
        List<Long> wholeTimeList;
        List<Long> runTimeList;
        long startTime;

        public AbstractTask(List<Long> runTimeList, List<Long> wholeTimeList) {
            this.runTimeList = runTimeList;
            this.wholeTimeList = wholeTimeList;
            this.startTime = System.currentTimeMillis();
        }

        abstract protected void task();

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            task();
            long end = System.currentTimeMillis();
            // 程序整体执行的时间 包含在队列中等待的时间
            long wholeTime = end - startTime;
            //  程序真正运行的时间
            long runTime = end - start;
            wholeTimeList.add(wholeTime);
            runTimeList.add(runTime);
            log.info("[{}] 单个线程花费时间: [{}]ms", this.getClass().getSimpleName(), end - start);
            latch.countDown();
        }
    }

    public static void main(String[] args) {

        ExecutorService pool = CustomThreadPool.newFixedThreadPool(threadCount);
        List<Long> crtl = Collections.synchronizedList(new ArrayList<>());
        List<Long> cwtl = Collections.synchronizedList(new ArrayList<>());
        AbstractTask cpuType = new CPUType(crtl, cwtl);
        for (int i = 0; i < threadCount; i++) {
            pool.execute(cpuType);
        }
        List<Long> irtl = Collections.synchronizedList(new ArrayList<>());
        List<Long> iwtl = Collections.synchronizedList(new ArrayList<>());
        AbstractTask ioType = new IOType(irtl, iwtl);
        for (int i = 0; i < threadCount; i++) {
            pool.execute(ioType);
        }
        pool.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            // ignore
        }
        int cwt = 0;
        int crt = 0;
        int iwt = 0;
        int irt = 0;
        for (long time : cwtl) {
            cwt += time;
        }
        for (long time : crtl) {
            crt += time;
        }
        for (long time : iwtl) {
            iwt += time;
        }
        for (long time : irtl) {
            irt += time;
        }
        log.info("cpu core: [{}], thread count: [{}]", cpuCore, threadCount);
        log.info("CPU Type: wholeTime: [{}]ms, runTime: [{}]ms", cwt, crt);
        log.info("IO Type: wholeTime: [{}]ms, runTime: [{}]ms", iwt, irt);
    }
}
