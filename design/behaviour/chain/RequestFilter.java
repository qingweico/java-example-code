package design.behaviour.chain;

/**
 * @author zqw
 * @date 2023/10/16
 */
public interface RequestFilter {

    /**
     * doFilter
     * @param request Request
     * @param response Response
     * @param chain RequestFilterChain
     */
    void doFilter(Request request, Response response, RequestFilterChain chain);

    /**
     * RequestFilterChain Sort order
     * @return Sort order
     */
    default int sort() {
        return 1;
    }
}
