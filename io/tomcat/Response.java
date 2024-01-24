package io.tomcat;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zqw
 * @date 2022/7/5
 */
public class Response {
    public Response(OutputStream os) {
        this.os = os;
    }

    public void write(String content) throws IOException {
        String tailPad = "\r\n";
        os.write(("HTTP/1.1 200 " + tailPad + "Content-Type:text/html" + tailPad + tailPad +
                "<html><body>" + content + "</body></html>").getBytes());
    }

    public void stop() throws IOException {
        os.close();
    }

    private final OutputStream os;
}
