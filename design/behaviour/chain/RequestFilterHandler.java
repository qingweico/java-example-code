package design.behaviour.chain;

import cn.hutool.core.collection.CollUtil;
import design.behaviour.chain.impl.IpRequestFilter;
import design.behaviour.chain.impl.MethodRequestFilter;
import design.behaviour.chain.impl.TokenRequestFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

/**
 * @author zqw
 * @date 2023/10/16
 */
@Slf4j
public class RequestFilterHandler {
    private List<RequestFilterChain> chains;

    @SuppressWarnings("unused")
    private RequestFilterChain getLastChain() {
        if (CollUtil.isNotEmpty(chains) && chains.size() > 0) {
            return chains.get(chains.size() - 1);
        }
        if (CollUtil.isNotEmpty(chains) && chains.size() > 0) {
            return chains.get(chains.size() - 1);
        }
        init();
        return null;
    }

    private RequestFilterChain getFirstChain() {

        if (chains != null) {
            if (chains.size() > 0) {
                return chains.get(0);
            }
        }
        init();
        if (chains != null) {
            if (chains.size() > 0) {
                return chains.get(0);
            }
        }
        return null;

    }

    private void init() {
        synchronized (RequestFilterHandler.class) {
            if (chains != null) {
                return;
            }
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(IpRequestFilter.class, MethodRequestFilter.class, TokenRequestFilter.class);
            context.refresh();
            List<RequestFilter> beans = Arrays.asList(context.getBean(IpRequestFilter.class), context.getBean(MethodRequestFilter.class), context.getBean(TokenRequestFilter.class));
            if (CollUtil.isNotEmpty(beans)) {
                List<RequestFilter> filters = beans.stream().sorted(Comparator.comparing(RequestFilter::sort)).toList();
                int index = filters.size() - 1;
                List<RequestFilterChain> list = new ArrayList<>();
                for (int i = index; i >= 0; i--) {
                    RequestFilter requestFilter = filters.get(i);
                    RequestFilterChain chain = new RequestFilterChain();
                    chain.setRequestFilter(requestFilter);
                    if (list.size() > 0) {
                        chain.setNextFilterChain(list.get(0));
                    }
                    // Filter --> Filter --> 最后一个Filter
                    list.add(0, chain);
                }
                chains = list;
            } else {
                chains = Collections.emptyList();
            }
            context.close();
        }
    }

    public void doFilter(Request request, Response response) {

        RequestFilterChain firstChain = getFirstChain();
        if (firstChain != null && firstChain.getRequestFilter() != null) {
            firstChain.doFilter(request, response);
        }

        run();

    }

    public void run() {
        log.info(" >>>>>> Run Something >>>>>> ");
    }
}
