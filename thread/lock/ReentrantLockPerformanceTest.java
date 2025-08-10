package thread.lock;


import cn.hutool.core.io.FileUtil;
import cn.qingweico.io.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.util.FileCopyUtils;
import cn.qingweico.concurrent.pool.ThreadPoolBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * {@link ReentrantLock } 和 {@link  ReentrantReadWriteLock} 锁读写性能测试
 *
 * @author zqw
 * @date 2025/7/24
 */

@Slf4j
class ReentrantLockPerformanceTest {

    static String parentDir = "data/rw";
    static String[] filenames = new String[]{"r1.txt", "r2.txt", "r3.txt", "r4.txt", "r5.txt", "r6.txt",
            "w1.txt", "w2.txt", "w3.txt", "w4.txt", "w5.txt", "w6.txt", "w7.txt", "w8.txt", "w9.txt", "w10.txt"};

    static ExecutorService taskPool = ThreadPoolBuilder
            .builder(400)
            .threadPoolName("rw-lock")
            .corePoolSize(100)
            .maxPoolSize(100)
            .preStartAllCore(true)
            .build();

    static ExecutorService main = ThreadPoolBuilder.builder().build();
    static List<File> readFiles = FileUtil.loopFiles(parentDir, pathname -> StringUtils.startsWith(pathname.getName(), "r"));
    static List<File> writeFile = FileUtil.loopFiles(parentDir, pathname -> StringUtils.startsWith(pathname.getName(), "w"));

    private static final String OUTPUT_COPYWRITING = "[{}] 读写测试结果: {} ms";

    public static void main(String[] args) throws InterruptedException {
        FileUtils.fillTextToFile(parentDir, e -> StringUtils.startsWith(e, "r"), 1024 * 1024 * 50, filenames);
        CountDownLatch terminal = new CountDownLatch(2);
        main.execute(() -> {
            try {
                reentrantLock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                terminal.countDown();
            }
        });

        main.execute(() -> {
            try {
                reentrantReadWriteLock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                terminal.countDown();
            }
        });
        terminal.await();
        taskPool.shutdown();
        main.shutdown();
    }

    public static void reentrantLock() throws InterruptedException {
        executeWithLock(new ReentrantLock(), Lock::lock, Lock::unlock);
    }

    public static void reentrantReadWriteLock() throws InterruptedException {
        executeWithLock(new ReentrantReadWriteLock(),
                lock -> lock.readLock().lock(),
                lock -> lock.readLock().unlock(),
                lock -> lock.writeLock().lock(),
                lock -> lock.writeLock().unlock());
    }

    private static <T> void executeWithLock(T lock,
                                            Consumer<T> readLockAcquirer,
                                            Consumer<T> readLockReleaser,
                                            Consumer<T> writeLockAcquirer,
                                            Consumer<T> writeLockReleaser) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        CountDownLatch latch = new CountDownLatch(200);
        stopWatch.start();

        for (int i = 0; i < 100; i++) {
            taskPool.execute(createLockTask(lock, readLockAcquirer, readLockReleaser, latch,
                    () -> readFileToString(lock.getClass().getSimpleName())));

            taskPool.execute(createLockTask(lock, writeLockAcquirer, writeLockReleaser, latch,
                    () -> copyFileToFile(lock.getClass().getSimpleName())));
        }

        latch.await();
        stopWatch.stop();
        log.info(OUTPUT_COPYWRITING, lock.getClass().getSimpleName(), stopWatch.getTime());
    }

    private static void executeWithLock(Lock lock,
                                        Consumer<Lock> lockAcquirer,
                                        Consumer<Lock> lockReleaser) throws InterruptedException {
        executeWithLock(lock, lockAcquirer, lockReleaser, lockAcquirer, lockReleaser);
    }


    private static <T> Runnable createLockTask(T lock,
                                               Consumer<T> lockAcquirer,
                                               Consumer<T> lockReleaser,
                                               CountDownLatch latch,
                                               Runnable operation) {
        return () -> {
            try {
                lockAcquirer.accept(lock);
                operation.run();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                lockReleaser.accept(lock);
                latch.countDown();
            }
        };
    }


    public static void readFileToString(String className) {
        File file = readFiles.get(RandomUtils.nextInt(0, readFiles.size()));
        log.info("[{}] 读文件 [{}] 开始", className, file.getAbsolutePath());
        try (FileReader reader = new FileReader(file)) {
            FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        log.info("[{}] 读文件 [{}] 结束", className, file.getAbsolutePath());
    }


    private static void copyFileToFile(String className) {
        File rf = readFiles.get(RandomUtils.nextInt(0, readFiles.size()));
        File wf = writeFile.get(RandomUtils.nextInt(0, writeFile.size()));
        log.info("[{}] 写文件 [{}] => [{}] 开始", className, rf.getAbsolutePath(), wf.getAbsolutePath());
        try (FileReader reader = new FileReader(rf)) {
            try (FileWriter writer = new FileWriter(wf)) {
                FileCopyUtils.copy(reader, writer);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        log.info("[{}] 写文件 [{}] => [{}] 结束", className, rf.getAbsolutePath(), wf.getAbsolutePath());
    }
}
