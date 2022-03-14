package design.proxy;

import java.net.URL;

/**
 * @author zqw
 * @date 2022/3/10
 */
public class HighResolutionImage implements Image {
    private URL imageUrl;
    private long startTime;
    private long width;
    private long height;

    public long getWidth() {
        return width;
    }
    public long getHeight() {
        return height;
    }

    public HighResolutionImage(URL imageUrl) {
        this.imageUrl = imageUrl;
        startTime = System.currentTimeMillis();
        this.width = 600;
        this.height = 600;
    }

    public boolean isLoads() {
        long endTime = System.currentTimeMillis();
        return endTime - startTime > 3000;
    }

    @Override
    public void showImage() {
        System.out.println("Real image: " + this.imageUrl);
    }
}
