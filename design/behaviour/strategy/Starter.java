package design.behaviour.strategy;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import cn.qingweico.model.ApiResponse;
import cn.qingweico.supplier.SnowflakeIdWorker;

/**
 * 策略模式(Strategy Pattern)是一种面向对象设计模式, 用于定义一组算法, 将每个算法封装在独立的类中, 并使它们可以互相替换
 * @author zqw
 * @date 2023/10/12
 */
public class Starter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AliPaymentImpl.class, WeChatPaymentImpl.class, CashPaymentImpl.class);
        context.refresh();
        Long userId = SnowflakeIdWorker.nextId();
        Integer payMethod = PaymentMethod.Ali.getVal();
        ApiResponse<?> response;
        if (!PaymentMethod.isValidPaymentMethod(payMethod)) {
            response = ApiResponse.builder()
                    .code("101")
                    .msg("不支持的支付方式")
                    .data(null)
                    .build();
        } else {
            String orderNo = RandomStringUtils.random(12, false, true);
            String requestBody = """
                    {
                    "payMethod": ${payMethod},
                    "orderNo": ${orderNo},
                    "userId": ${userId}
                    }
                    """;

            requestBody = requestBody.replace("${orderNo}", orderNo).replace("${userId}", String.valueOf(userId)).replace("${payMethod}", String.valueOf(payMethod));
            PayParam payParam = JSONUtil.toBean(requestBody, PayParam.class);
            response = PaymentFactory.getPaymentService(payParam.getPayMethod()).doPay(payParam);
        }
        System.out.println(JSONUtil.toJsonStr(response));
        context.close();
    }
}
