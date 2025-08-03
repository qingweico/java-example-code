package design.behaviour.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import cn.qingweico.model.ApiResponse;

/**
 * @author zqw
 * @date 2023/10/12
 */
@Slf4j@Service
public class AliPaymentImpl implements PaymentService, InitializingBean {
    @Override
    public void afterPropertiesSet() {
        PaymentFactory.register(PaymentMethod.Ali.getVal(), this);
    }

    @Override
    public ApiResponse<PayParam> doPay(PayParam payParam) {
        log.info(">>> 支付宝接口调用 >>> : [{}]", payParam);
        return new ApiResponse<>(payParam);
    }
}
