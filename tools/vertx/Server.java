package tools.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

/**
 * @author zqw
 * @date 2022/8/9
 */
public class Server extends AbstractVerticle {

    @Override
    public void start() {
        Vertx vertx = Vertx.vertx();
        vertx.createHttpServer().requestHandler(req -> req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!")).listen(8080);
    }

    public static void main(String[] args) {
        new Server().start();
    }
}