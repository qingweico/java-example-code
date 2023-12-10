package frame.db.gen;

import org.apache.commons.lang3.tuple.Pair;
import thread.pool.ThreadPoolBuilder;
import util.DatabaseHelper;
import util.constants.Symbol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * @author zqw
 * @date 2022/8/14
 */
public class SqlExecutor {

    static ThreadLocal<Connection> tlc;

    public SqlExecutor() {
        tlc = ThreadLocal.withInitial(SqlExecutor::getConnection);
    }


    public static Connection getConnection() {
        return DatabaseHelper.getConnection();
    }

    private void createTable() throws IOException {
        var file = new File("data/sql/post.sql");
        try (FileInputStream fis = new FileInputStream(file)) {
            var content = new String(fis.readAllBytes(), StandardCharsets.UTF_8);
            Arrays.stream(
                            content.split(Symbol.SEMICOLON))
                    .filter(x -> x.length() > 0)
                    .forEach(x -> {
                        try {
                            execute(x);
                        } catch (SQLException t) {
                            t.printStackTrace();
                        }
                    });
        }

    }


    private static void execute(String sql) throws SQLException {
        try (final Statement statement = tlc.get().createStatement()) {
            statement.execute(sql);
        }
    }


    static class Worker implements Callable<Worker> {
        static AtomicInteger counter = new AtomicInteger(0);
        private final int id;
        private final int num;
        private final int bucket;

        public Worker(int id, int num, int bucket) {
            this.id = id;
            this.num = num;
            this.bucket = bucket;
        }

        @Override
        public Worker call() throws IOException, ClassNotFoundException {

            System.out.format("run worker %d\n", id);

            // 10 threads
            var buckets = this.num / this.bucket;

            // 1 user -> 10 post
            var totalUser = this.num / 10;
            var userBuckets = buckets / 10;
            var batchSize = 10;

            for (int j = 0; j < userBuckets; j++) {
                RowGen rowGen = new RowGen();
                try {
                    // create users
                    var sql = rowGen.genUserBatch(this.bucket);
                    // create posts
                    // 1000 users -> 100,000 posts
                    var userStart = this.id * totalUser + j * 1000;
                    var userEnd = userStart + 1000;
                    for (int i = 0; i < batchSize; i++) {
                        var postSql = rowGen.getBatchPost(1000, userStart, userEnd);
                        execute(postSql);
                    }
                    execute(sql);
                    System.out.format("finished %d/%d\n", counter.incrementAndGet(), buckets);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


    public static Stream<Pair<Integer, Integer>> batch(int start, int end, int batch) {
        return IntStream.iterate(0, x -> x + 1)
                // tips: 整数之间的除法结果使用浮点数接收时会造成精度丢失 使用(*1.0) 提高精度即可
                .limit((long) (Math.ceil((end - start) / (batch * 1.0)) + 1))
                .mapToObj(i -> Pair.of(start + i * batch, Math.min(start + (i + 1) * batch, end)));
    }


    public void run(int num, int bucket) throws IOException, ClassNotFoundException, SQLException, ExecutionException, InterruptedException {
        this.createTable();
        var pool = ThreadPoolBuilder.builder().preStartAllCore(true).build();
        Stream.iterate(0, x -> x + 1)
                .limit(10)
                .map(i -> pool.submit(new Worker(i, num / 10, bucket)))
                .toList()
                .forEach(future -> {
                    System.out.println(future);
                    try {
                        future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
        tlc.remove();
        pool.shutdown();
    }

    public static void main(String[] args) throws Exception {
        var starter = new SqlExecutor();
        starter.run(1000, 10);
    }
}
