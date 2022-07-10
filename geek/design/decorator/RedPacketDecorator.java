package geek.design.decorator;

import java.math.BigDecimal;

/**
 * 计算使用红包后的金额
 *
 * @author zqw
 * @date 2022/7/9
 */


public class RedPacketDecorator extends BaseComputeDecorator {

    public RedPacketDecorator(IBaseCompute compute) {
        super(compute);
    }

    public BigDecimal computePayMoney(OrderDetail orderDetail) {
        BigDecimal payTotalMoney = new BigDecimal(0);
        payTotalMoney = super.computePayMoney(orderDetail);
        payTotalMoney = computeCouponPayMoney(orderDetail);
        return payTotalMoney;
    }

    private BigDecimal computeCouponPayMoney(OrderDetail orderDetail) {

        BigDecimal redPacket = orderDetail
                .getMerchandise()
                .getSupportPromotions()
                .get(PromotionType.RED_PACKET)
                .getUserRedPacket().getRedPacket();
        System.out.println("红包优惠金额: " + redPacket);
        orderDetail.setPayMoney(orderDetail.getPayMoney().subtract(redPacket));
        return orderDetail.getPayMoney();
    }
}