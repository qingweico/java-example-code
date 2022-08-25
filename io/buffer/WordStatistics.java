package io.buffer;


import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Word frequency statistics
 *
 * @author zqw
 * @date 2021/9/27
 */
public class WordStatistics {

    final ForkJoinPool pool = ForkJoinPool.commonPool();

    static class CountTask implements Callable<Map<String, Integer>> {

        private final long start;
        private final long end;
        private final String filename;

        public CountTask(String filename, long start, long end) {
            this.start = start;
            this.end = end;
            this.filename = filename;
        }

        @Override
        public Map<String, Integer> call() throws Exception {
            try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
                var channel = raf.getChannel();
                var mapBuf = channel.map(
                        FileChannel.MapMode.READ_ONLY,
                        start,
                        end - start);

                var str = StandardCharsets.US_ASCII.decode(mapBuf).toString();
                return countByString(str);
            }
        }
    }

    public void run(String filename, long chunkSize) throws ExecutionException, InterruptedException {
        var file = new File(filename);
        var fileSize = file.length();
        var position = 0L;
        var tasks = new ArrayList<Future<Map<String, Integer>>>();
        var start = System.currentTimeMillis();
        while (position < fileSize) {
            var next = Math.min(position + chunkSize, fileSize);
            var task = new CountTask(filename, position, next);
            position = next;
            var future = pool.submit(task);
            tasks.add(future);
        }
        System.out.format("split to %d tasks\n", tasks.size());

        var total = new HashMap<String, Integer>(16);
        for (var future : tasks) {
            var map = future.get();
            for (var entry : map.entrySet()) {
                incKey(entry.getKey(), total, entry.getValue());
            }
        }
        System.out.format("%dms\n", (System.currentTimeMillis() - start));
        System.out.println(total.get("adbbb"));
        System.out.println(total.size());
    }

    public static Map<String, Integer> countByString(String str) {

        var map = new HashMap<String, Integer>(16);
        StringTokenizer tokenizer = new StringTokenizer(str);
        while (tokenizer.hasMoreTokens()) {
            var word = tokenizer.nextToken();
            incKey(word, map, 1);
        }
        return map;
    }

    public static void incKey(String key, Map<String, Integer> map, Integer n) {
        map.put(key, map.getOrDefault(key, 0) + n);
    }

    /**
     * Single Thread
     *
     * @throws IOException @throw IOException
     */
    @Test
    public void single() throws IOException {
        var is = new BufferedInputStream(new FileInputStream("word"));
        var len = 0;
        var start = System.currentTimeMillis();
        var total = new HashMap<String, Integer>(16);
        var buffer = new byte[1024 * 4];
        while ((len = is.read(buffer)) != -1) {
            var bytes = Arrays.copyOfRange(buffer, 0, len);
            var str = new String(bytes);
            var hashMap = countByString(str);
            for (var entry : hashMap.entrySet()) {
                var key = entry.getKey();
                incKey(key, total, entry.getValue());
            }
        }
        System.out.format("%dms\n", (System.currentTimeMillis() - start));
        System.out.println(total.get("adbbb"));
        System.out.println(total.size());
    }

    /**
     * ForkJoin
     *
     * @throws ExecutionException   @throw ExecutionException
     * @throws InterruptedException @throw InterruptedException
     */
    @Test
    public void count() throws ExecutionException, InterruptedException {
        var count = new WordStatistics();
        count.run("word", 1024 * 1024);

    }
}
