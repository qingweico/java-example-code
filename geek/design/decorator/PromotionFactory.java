package geek.design.decorator;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 计算促销后的支付价格
 *
 * @author zqw
 * @date 2022/7/9
 */
public class PromotionFactory {

    public static BigDecimal getPayMoney(OrderDetail orderDetail) {

        // 获取给商品设定的促销类型
        Map<PromotionType, SupportPromotions> supportPromotionsList = orderDetail.getMerchandise().getSupportPromotions();

        // 初始化计算类
        IBaseCompute baseCompute = new BaseCompute();
        if (supportPromotionsList != null && supportPromotionsList.size() > 0) {
            // 遍历设置的促销类型,通过装饰器组合促销类型
            for (PromotionType promotionType : supportPromotionsList.keySet()) {
                baseCompute = promotion(supportPromotionsList.get(promotionType), baseCompute);
            }
        }
        return baseCompute.computePayMoney(orderDetail);
    }

    /**
     * 组合促销类型
     *
     * @param supportPromotions SupportPromotions
     * @param baseCompute       IBaseCompute
     * @return IBaseCompute
     */
    private static IBaseCompute promotion(SupportPromotions supportPromotions, IBaseCompute baseCompute) {
        if (PromotionType.COUPON.equals(supportPromotions.getPromotionType())) {
            baseCompute = new CouponDecorator(baseCompute);
        } else if (PromotionType.RED_PACKET.equals(supportPromotions.getPromotionType())) {
            baseCompute = new RedPacketDecorator(baseCompute);
        }
        return baseCompute;
    }
}