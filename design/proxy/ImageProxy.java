package design.proxy;

import thread.cas.UnsafeSupport;

/**
 * @author zqw
 * @date 2022/3/10
 */
public class ImageProxy implements Image {
    private final HighResolutionImage highResolutionImage;

    public ImageProxy(HighResolutionImage highResolutionIma) {
        this.highResolutionImage = highResolutionIma;
    }

    @Override
    public void showImage() {
        while (!highResolutionImage.isLoads()) {
            System.out.println("Temp image: " + highResolutionImage.getWidth() + " " +
                    highResolutionImage.getHeight());
            UnsafeSupport.shortWait(100);
        }
        highResolutionImage.showImage();
    }
}
