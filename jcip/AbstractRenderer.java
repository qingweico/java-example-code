package jcip;

import java.util.List;
import java.util.concurrent.*;

import static jcip.LaunderThrowable.launderThrowable;

/**
 * ExecutorCompletionService implements CompletionService
 * QueueingFuture<V> extends FutureTask<V>
 *
 * @author zqw
 * @date 2021/4/7
 */
abstract class AbstractRenderer {
    private final ExecutorService executor;

    AbstractRenderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        final List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService =
                new ExecutorCompletionService<>(executor);
        for (final ImageInfo imageInfo : info) {
            completionService.submit(imageInfo::downloadImage);
        }

        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    interface ImageData {
    }

    interface ImageInfo {
        /**
         * downloadImage
         * @return ImageData
         */
        ImageData downloadImage();
    }

    /**
     * renderText
     * @param s CharSequence
     */
    abstract void renderText(CharSequence s);

    /**
     * scanForImageInfo
     * @param s CharSequence
     * @return List<ImageInfo>
     */
    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    /**
     * renderImage
     * @param i ImageData
     */
    abstract void renderImage(ImageData i);
}
