package design.behaviour.chain;

import lombok.Data;

/**
 * @author zqw
 * @date 2023/10/16
 */
@Data
public class RequestFilterChain {

    private RequestFilterHandler requestFilterHandler;

    private RequestFilter requestFilter;

    private RequestFilterChain nextFilterChain;


    public void doFilter(Request request, Response response) {
        if (requestFilter != null) {
            if (nextFilterChain == null) {
                nextFilterChain = new RequestFilterChain();
            }
            requestFilter.doFilter(request, response, nextFilterChain);
        }
    }
}
