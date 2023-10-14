package design.behaviour.strategy;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zqw
 * @date 2023/10/12
 */
@Data
public class PayParam implements Serializable {


    /**
     * 支付方式 1 支付宝 2 微信 3 现金 {@link PaymentMethod}
     */
    private Integer payMethod;

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 用户id
     */
    private String userId;
}
