package design.structural.proxy;

import lombok.Getter;

import java.net.URL;

/**
 * @author zqw
 * @date 2022/3/10
 */
public class HighResolutionImage implements Image {
    private final URL imageUrl;
    private final long startTime;
    @Getter
    private final long width;
    @Getter
    private final long height;

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
