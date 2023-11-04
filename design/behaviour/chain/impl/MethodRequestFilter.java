package design.behaviour.chain.impl;

import design.behaviour.chain.Request;
import design.behaviour.chain.RequestFilter;
import design.behaviour.chain.RequestFilterChain;
import design.behaviour.chain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zqw
 * @date 2023/11/4
 */
@Slf4j@Component
public class MethodRequestFilter implements RequestFilter {

    private final static String POST = "POST";
    @Override
    public void doFilter(Request request, Response response, RequestFilterChain chain) {
        log.info("{}, sort : {}, request : {}", this.getClass().getSimpleName(), sort(), request );
        if (POST.equals(request.getRequestMethod())) {
            chain.doFilter(request, response);
        } else {
            log.info("Bad request, {}", request.getRequestMethod());
        }
    }
}
