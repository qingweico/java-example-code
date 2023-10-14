package design.behaviour.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式
 *
 * @author zqw
 * @date 2023/10/12
 */
@AllArgsConstructor
@Getter
public enum PaymentMethod {

    /**
     * 支付宝
     */
    Ali(1, "支付宝"),
    /**
     * 微信
     */
    WeChat(2, "微信"),
    /**
     * 现金
     */
    Cash(3, "现金");

    private final Integer val;
    private final String desc;

    /**
     * 校验支付方式
     *
     * @param value 支付方式
     * @return boolean true or false
     */
    public static boolean isValidPaymentMethod(Integer value) {
        if (value != null) {
            return value.equals(Ali.getVal())
                    || value.equals(WeChat.getVal())
                    || value.equals(Cash.getVal());
        }
        return false;
    }
}
