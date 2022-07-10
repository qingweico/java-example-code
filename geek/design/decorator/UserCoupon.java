package geek.design.decorator;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 优惠券
 *
 * @author zqw
 * @date 2022/7/9
 */
@Data
public class UserCoupon {

    /**
     * 优惠券id
     */
    private long id;
    /**
     * 领取优惠券用户id
     */
    private long userId;
    /**
     * 商品SKU
     */
    private String sku;
    /**
     * 优惠金额
     */
    private BigDecimal coupon;
}