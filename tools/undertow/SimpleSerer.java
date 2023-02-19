package tools.undertow;

import io.undertow.Undertow;
import io.undertow.util.Headers;

/**
 * Undertow是Red Hat公司的开源产品, 它完全采用Java语言开发, 是一款灵活的高性能Web服务器 ,支持阻塞IO和非阻塞IO
 * 可以输出html, 也可以是输出REST方式的json文本; 支持HTTP(s)协议
 * /////////////////// XNIO  ///////////////////
 * XNIO是JBoss的一个IO框架, undertow 网络部分使用的就是XNIO
 *
 * @author zqw
 * @date 2023/2/18
 */
class SimpleSerer {
    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                // 设置HttpHandler回调方法
                .setHandler(exchange -> {
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("Simple Undertow Server!");
                }).build();
        server.start();
    }
}
