package frame.redis;

import frame.db.JdbcConfig;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import thread.pool.ThreadPoolBuilder;
import util.Print;
import util.constants.Symbol;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2023/2/18
 */
class GetSeqNum {
    private static final String SELECT_SQL = "SELECT SEQ FROM TB_SEQ_NUM WHERE MARK = ?";
    private static final String INSERT_SQL = "INSERT INTO TB_SEQ_NUM (MARK, SEQ, COMMENT) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE TB_SEQ_NUM SET SEQ = ? WHERE MARK = ?";
    private static final int START_INDEX = 1;
    private static final String LOCK_KEY = "SEQ_LOCK" + Symbol.COLON;

    public static Integer nextSeq(String mark, String comment) {
        RedissonConfig config = new RedissonConfig();
        final RedissonClient client = config.redisson();
        RLock lock = null;
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        try {
            lock = client.getLock(LOCK_KEY + mark);
            lock.lock(10, TimeUnit.SECONDS);
            Integer seq = jdbcTemplate.queryForObject(SELECT_SQL, Integer.class, mark);
            if (seq == null) {
                int update = jdbcTemplate.update(INSERT_SQL, mark, START_INDEX, comment);
                if (update != 1) {
                    throw new RuntimeException();
                }
                return START_INDEX;
            }
            seq++;
            int update = jdbcTemplate.update(UPDATE_SQL, seq, mark);
            if (update != 1) {
                throw new RuntimeException();
            }
            return seq;
        } catch (Exception e) {
            Print.err(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int tc = 10;
        ExecutorService pool = ThreadPoolBuilder.builder().corePoolSize(tc).maxPoolSize(tc).preStartAllCore(true).build();
        for (int i = 0; i < tc; i++) {
            pool.execute(() -> Print.prints(nextSeq("Seq-Num", "序列号")));
        }
        pool.shutdown();
    }
}
