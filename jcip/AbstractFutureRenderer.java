package jcip;

import thread.pool.ThreadPoolBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static jcip.LaunderThrowable.launderThrowable;

/**
 * @author zqw
 * @date 2021/4/7
 */
abstract class AbstractFutureRenderer {
    private final ExecutorService exec = ThreadPoolBuilder.custom().builder();
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
        /**
         * !downloadImage
         * @return  ///
         */
        ImageData downloadImage();
    }

    /**
     * renderText
     * @param s ///
     */
    abstract void renderText(CharSequence s);

    /**
     * scanForImageInfo
     * @param s CharSequence
     * @return ImageInfo List
     */
    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    /**
     * renderImage
     * @param i ImageData
     */
    abstract void renderImage(ImageData i);

}
