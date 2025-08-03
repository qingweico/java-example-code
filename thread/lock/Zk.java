package thread.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.commons.lang3.StringUtils;
import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author zqw
 * @date 2022/3/12
 */
class Zk implements Runnable {
    static int inventory = 1;
    static int THREAD_COUNT = Constants.TEN;
    static String SEPARATOR = "/";
    static String path = "";
    private static final String ZK_SERVER = "119.29.35.129:2181";
    private static final String ZK_PARENT_NODE = "/zk_parent_lock";
    private static String beforePath = "";
    private static final ZkClient ZK_CLIENT = new ZkClient(
            ZK_SERVER,
            5000,
            5000,
            new SerializableSerializer());

    static CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(10);

    @Override
    public void run() {
        new Zk().lock();
        try {
            latch.await();
            if (inventory > 0) {
                Thread.sleep(5);
                inventory--;
            }
            System.out.println(inventory);
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        } finally {
            new Zk().unlock();
        }
    }

    public static void main(String[] args) {
        if (!ZK_CLIENT.exists(ZK_PARENT_NODE)) {
            // 创建持久父节点
            ZK_CLIENT.createPersistent(ZK_PARENT_NODE);
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            pool.execute(new Zk());
        }
        pool.shutdown();
    }


    public void lock() {
        if (tryLock()) {
            System.out.println("获得锁");
        } else {
            waitForLock();
            lock();

        }
    }

    public void unlock() {

    }

    public synchronized boolean tryLock() {
        if (StringUtils.isBlank(path)) {
            // 创建临时顺序节点
            path = ZK_CLIENT.createEphemeralSequential(ZK_PARENT_NODE + SEPARATOR + Thread.currentThread().getId(), Thread.currentThread().getName());
        }
        List<String> children = ZK_CLIENT.getChildren(ZK_PARENT_NODE);
        Collections.sort(children);
        if (path.equals(ZK_PARENT_NODE + SEPARATOR + children.get(0))) {
            System.out.println("lock success");
            return true;
        } else {
            int i = Collections.binarySearch(children, path.substring(ZK_PARENT_NODE.length() + 1));
            beforePath = ZK_PARENT_NODE + SEPARATOR + children.get(i - 1);
        }
        return false;
    }

    public void waitForLock() {
        IZkDataListener listener = new IZkDataListener() {

            @Override
            public void handleDataChange(String s, Object o) {
            }

            @Override
            public void handleDataDeleted(String s) {
                System.out.println(Thread.currentThread().getName() + "监听到节点删除事件");
                latch.countDown();
            }
        };
        ZK_CLIENT.subscribeDataChanges(beforePath, listener);
        if (ZK_CLIENT.exists(beforePath)) {
            try {
                System.out.println("加锁失败, 等待");
                latch.await();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
        }
        ZK_CLIENT.unsubscribeDataChanges(beforePath, listener);
    }
}
