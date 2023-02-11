package thread.tl;

import thinking.genericity.Holder;
import thread.pool.ThreadPoolBuilder;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * 同步 {@link ThreadLocal} 中的数据
 *
 * @author zqw
 * @date 2023/1/15
 */
class SyncTlData {

    static HashSet<Holder<Integer>> set = new HashSet<>();

    synchronized static void addSet(Holder<Integer> v) {
        set.add(v);

    }

    static ThreadLocal<Holder<Integer>> tl = ThreadLocal.withInitial(() -> {
        Holder<Integer> holder = new Holder<>();
        holder.set(0);
        addSet(holder);
        return holder;
    });

    static void add() {
        Holder<Integer> holder = tl.get();
        holder.set(holder.get() + 1);
    }

    static Integer stat() {
        Optional<Integer> optional = set.stream().map(Holder::get).reduce(Integer::sum);
        return optional.orElse(0);
    }

    public static void main(String[] args) throws InterruptedException {
        int tc = 200;
        ExecutorService pool = ThreadPoolBuilder.builder(tc)
                .corePoolSize(tc)
                .maxPoolSize(tc)
                .preStartAllCore(true)
                .build();
        for (int i = 0; i < tc; i++) {
            pool.execute(SyncTlData::add);
        }
        pool.shutdown();
        if (pool.awaitTermination(1, TimeUnit.MINUTES)) {
            System.out.println(stat());
            System.out.println("over!");
        }
    }
}
