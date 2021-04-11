package jcip;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static jcip.LaunderThrowable.launderThrowable;

/**
 * @author:qiming
 * @date: 2021/4/7
 */
public abstract class FutureRenderer {
    private final ExecutorService exec = Executors.newFixedThreadPool(10);
    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task =
                () -> {
                    List<ImageData> result = new ArrayList<>();
                    for (ImageInfo imageInfo : imageInfos) {
                        result.add(imageInfo.downloadImage());
                    }
                    return result;
                };

        Future<List<ImageData>> future = exec.submit(task);
        renderText(source);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            // Re-assert the thread's interrupted status
            Thread.currentThread().interrupt();
            // We don't need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);

}
