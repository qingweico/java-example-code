package design.structural.proxy;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author zqw
 * @date 2022/3/10
 * @see java.lang.reflect.Proxy
 * {@code RMI}
 */
public class ImageViewer {
    public static void main(String[] args) throws MalformedURLException {
        String image = "https://image.jpg";
        URL url = new URL(image);
        HighResolutionImage highResolutionImage = new HighResolutionImage(url);
        ImageProxy imageProxy = new ImageProxy(highResolutionImage);
        imageProxy.showImage();
    }

}
