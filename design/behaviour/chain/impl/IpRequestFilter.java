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
@Slf4j
@Component
public class IpRequestFilter implements RequestFilter {

    private static final String IP = "ip";

    @Override
    public void doFilter(Request request, Response response, RequestFilterChain chain) {
        log.info("{}, sort : {}, request : {}", this.getClass().getSimpleName(), sort(), request );
        if (request.getHeaders().get(IP) != null) {
            chain.doFilter(request, response);
        } else {
            log.info("ip is null");
        }
    }

    @Override
    public int sort() {
        return 2;
    }
}
