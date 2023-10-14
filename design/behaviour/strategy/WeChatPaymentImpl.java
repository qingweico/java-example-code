package design.behaviour.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import util.ApiResponse;

/**
 * @author zqw
 * @date 2023/10/12
 */
@Slf4j
@Service
public class WeChatPaymentImpl implements PaymentService, InitializingBean {
    @Override
    public void afterPropertiesSet() {
        PaymentFactory.register(PaymentMethod.WeChat.getVal(), this);
    }

    @Override
    public ApiResponse<PayParam> doPay(PayParam payParam) {
        log.info(">>> 微信支付接口调用 >>> : [{}]", payParam);
        return new ApiResponse<>(payParam);
    }
}
