package design.behaviour.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 支付策略工厂
 * @author zqw
 * @date 2023/10/12
 */

public class PaymentFactory {

    private static Map<Integer, PaymentService> map = new ConcurrentHashMap<>();

    public static PaymentService getPaymentService(Integer payMethod) {
        return map.get(payMethod);
    }

    public static void register(Integer payMethod, PaymentService paymentService) {
        map.put(payMethod, paymentService);
    }
}
