package io.tomcat;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * @author zqw
 * @date 2022/7/5
 */
@Slf4j
public class Start {
    private final int port;
    private ServletMappingConfig servletConfig;

    public Start(int port) {
        this.port = port;
    }

    /**
     * 加载解析web.xml,初始化Servlet
     */
    private void loadServlet() {
        servletConfig = new ServletMappingConfig();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            // 获取根元素节点
            Element root = document.getRootElement();
            // 获取根节点的子标签
            List<Node> selectNodes = root.selectNodes("//servlet");
            for (Node element : selectNodes) {
                // servlet-name
                Node servletNameNode = element.selectSingleNode("servlet-name");
                String servletName = servletNameNode.getStringValue();
                // servlet-class
                Node servletClassNode = element.selectSingleNode("servlet-class");
                String servletClass = servletClassNode.getStringValue();

                // 根据 servlet-name 找到 servlet-mapping 中的 url
                Node servletMapping = root.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                servletConfig.addServlet(urlPattern, servletClass);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        loadServlet();
        ServerSocket server;
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000 * 60);
            log.info("\n\t----------------------------------------------------------\n\t" +
                    "Local: \t\thttp://localhost:" + port + "/\n\t" +
                    "----------------------------------------------------------");
            while (true) {
                Socket client;
                try {
                    client = server.accept();
                    // TODO 使用线程池
                    dispatch(new Request(client.getInputStream()), new Response(client.getOutputStream()));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    break;
                }
                client.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void dispatch(Request request, Response response) {
        String className = servletConfig.getServlet(request.getUrl());
        if (className != null) {
            try {
                Class<?> cls = Class.forName(className);
                Servlet servlet = (Servlet) cls.getConstructor().newInstance();
                servlet.service(request, response);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                log.error(e.getMessage());
            } catch (InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {
        Start tomcat = new Start(8080);
        tomcat.start();
    }
}
