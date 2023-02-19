package io.tomcat;

import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zqw
 * @date 2022/7/5
 */
@Getter
@ToString
class Request {
    private String url;
    private HttpRequestType method;
    private static final Integer KB = 8;

    private static final String FAVICON = "/favicon.ico";

    public Request(InputStream is) throws IOException {
        byte[] bytes = new byte[KB * 8];
        int read = is.read(bytes);
        if (read > 0) {
            String s = new String(bytes, 0, read);
            String[] ss = s.split("\\s");
            url = ss[1];
            if (FAVICON.equals(url)) {
                return;
            }
            switch (ss[0]) {
                case "GET": {
                    method = HttpRequestType.HTTP_REQUEST_GET;
                    break;
                }
                case "POST": {
                    method = HttpRequestType.HTTP_REQUEST_POST;
                    break;
                }
                default:break;
            }
            System.out.println(this);
        }
    }
}
