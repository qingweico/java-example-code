package io.tomcat;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zqw
 * @date 2022/7/5
 */
 class ServletMappingConfig {
    private final Map<String, String> servletMaps = new HashMap<>(8);

    public void addServlet(String servletUrl, String servletClass) {
        servletMaps.put(servletUrl, servletClass);
    }

    public String getServlet(String servletUrl) {
        return servletMaps.get(servletUrl);
    }
}
