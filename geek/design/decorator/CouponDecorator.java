package geek.design.decorator;

import java.math.BigDecimal;

/**
 * 计算使用优惠券后的金额
 *
 * @author zqw
 * @date 2022/7/9
 */
public class CouponDecorator extends BaseComputeDecorator {

    public CouponDecorator(IBaseCompute compute) {
        super(compute);
    }

    public BigDecimal computePayMoney(OrderDetail orderDetail) {
        BigDecimal payTotalMoney = new BigDecimal(0);
        payTotalMoney = super.computePayMoney(orderDetail);
        payTotalMoney = countCouponPayMoney(orderDetail);
        return payTotalMoney;
    }

    private BigDecimal countCouponPayMoney(OrderDetail orderDetail) {

        BigDecimal coupon = orderDetail.getMerchandise().getSupportPromotions().get(PromotionType.COUPON).getUserCoupon().getCoupon();
        System.out.println("优惠券金额: " + coupon);

        orderDetail.setPayMoney(orderDetail.getPayMoney().subtract(coupon));
        return orderDetail.getPayMoney();
    }
}