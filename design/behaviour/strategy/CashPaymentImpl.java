package design.behaviour.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import cn.qingweico.model.ApiResponse;

/**
 * @author zqw
 * @date 2023/10/12
 */
@Slf4j
@Service
public class CashPaymentImpl implements PaymentService, InitializingBean {
    @Override
    public void afterPropertiesSet() {
        PaymentFactory.register(PaymentMethod.Cash.getVal(), this);
    }

    @Override
    public ApiResponse<PayParam> doPay(PayParam payParam) {
        log.info(">>> 现金支付接口调用 >>> : [{}]", payParam);
        return new ApiResponse<>(payParam);
    }
}
