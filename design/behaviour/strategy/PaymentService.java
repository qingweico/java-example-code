package design.behaviour.strategy;

import util.ApiResponse;

/**
 * @author zqw
 * @date 2023/10/12
 */
public interface PaymentService {

    /**
     * 发起支付
     * @param payParam PayParam
     * @return ApiResponse
     */
    default ApiResponse<?> doPay(PayParam payParam) {
        return ApiResponse.builder()
                .code("100")
                .msg("未实现支付业务, 请稍后重试")
                .data(null)
                .build();

    }
}
