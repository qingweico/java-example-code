package jcip;

import java.util.concurrent.*;

/**
 * @author:qiming
 * @date: 2021/3/31
 */
public class ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor();


    public static class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        public String call() throws Exception {
            // Here's where we would actually read the file
            return fileName;
        }
    }

    public class RenderPageTask implements Callable<String> {

        @Override
        public String call() throws ExecutionException, InterruptedException {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            // Here's where we would actually render the page
            return "";
        }
    }
}
