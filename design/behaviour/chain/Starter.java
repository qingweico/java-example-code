package design.behaviour.chain;

import cn.qingweico.collection.CollUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 责任链模式
 *
 * @author zqw
 * @date 2023/10/16
 */
public class Starter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RequestFilterHandler.class);
        context.refresh();
        RequestFilterHandler requestFilterHandler = context.getBean(RequestFilterHandler.class);


        Request request = new Request();
        Map<String, Object> heads = new HashMap<>(CollUtils.mapSize(2));
        heads.put("ip", "127.0.0.1");
        heads.put("token", "token");
        request.setHeaders(heads);
        request.setRequestMethod("POST");

        requestFilterHandler.doFilter(request, new Response());

        context.close();
    }
}
