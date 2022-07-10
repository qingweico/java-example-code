package io.tomcat;

import lombok.Getter;

/**
 * @author zqw
 * @date 2022/7/5
 */
@Getter
public class ServletMapping {
    public ServletMapping(String servletName, String url, String clazz) {
        this.servletName = servletName;
        this.url = url;
        this.clazz = clazz;
    }

    private final String url;
    private final String servletName;
    private final String clazz;
}
