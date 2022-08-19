package geek.design.decorator;

import lombok.Data;

/**
 * 促销类型
 *
 * @author zqw
 * @date 2022/7/9
 */
@Data
public class SupportPromotions implements Cloneable {

    /**
     * 该商品促销的ID
     */
    private int id;
    /**
     * 促销类型 1: 优惠券 2: 红包
     */
    private PromotionType promotionType;
    /**
     * 优先级;越大则表示抵扣支付金额优先度越靠前
     */
    private int priority;
    /**
     * 用户领取该商品的优惠券
     */
    private UserCoupon userCoupon;
    /**
     * 用户领取该商品的红包
     */
    private UserRedPacket userRedPacket;

    @Override
    public SupportPromotions clone() {
        SupportPromotions supportPromotions = null;
        try {
            supportPromotions = (SupportPromotions) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return supportPromotions;
    }
}