package geek.design.decorator;

import util.SnowflakeIdWorker;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zqw
 * @date 2022/7/9
 */
public class Starter {
    static Map<PromotionType, SupportPromotions> supportPromotionsList = new HashMap<>(8);

    public static void main(String[] args) throws InterruptedException, IOException {
        Order order = new Order();
        init(order);

        for (Map.Entry<PromotionType, SupportPromotions> entry : supportPromotionsList.entrySet()) {
            PromotionType pt = entry.getKey();
            SupportPromotions sp = entry.getValue();
            System.out.println("\t ------------------" + pt.name() + "------------------");
            System.out.println("\t sp id: " + sp.getId());
            System.out.println("\t sp priority: " + sp.getPriority());
            System.out.println("\t sp user coupon: " + sp.getUserCoupon());
            System.out.println("\t sp red packet: " + sp.getUserRedPacket());
        }

        for (OrderDetail orderDetail : order.getList()) {
            BigDecimal payMoney = PromotionFactory.getPayMoney(orderDetail);
            orderDetail.setPayMoney(payMoney);
            System.out.println("\t ------------------ 订单详情 ------------------");
            System.out.println("订单详情id: " + orderDetail.getId());
            System.out.println("商品信息: " + orderDetail.getMerchandise());
            System.out.println("订单id: " + orderDetail.getOrderId());
            System.out.println("最终支付金额:" + orderDetail.getPayMoney());
        }
    }

    private static void init(Order order) {

        SupportPromotions spC = new SupportPromotions();
        // 优惠券类型
        spC.setPromotionType(PromotionType.COUPON);
        // 设置优惠券权值为1
        spC.setPriority(1);
        UserCoupon userCoupon = new UserCoupon();
        // 设置优惠券金额为3元
        userCoupon.setCoupon(new BigDecimal(3));
        userCoupon.setSku("sku-coupon");
        userCoupon.setId(SnowflakeIdWorker.nextId());
        userCoupon.setUserId(1);
        spC.setUserCoupon(userCoupon);
        supportPromotionsList.put(PromotionType.COUPON, spC);

        SupportPromotions spR = spC.clone();
        // 红包类型
        spR.setPromotionType(PromotionType.RED_PACKET);
        // 设置红包权值为1
        spR.setPriority(2);
        UserRedPacket userRedPacket = new UserRedPacket();
        userRedPacket.setId(SnowflakeIdWorker.nextId());
        // 设置红包金额为10元
        userRedPacket.setRedPacket(new BigDecimal(10));
        userRedPacket.setSku("sku-red-packet");
        userCoupon.setUserId(1);
        spR.setUserRedPacket(userRedPacket);

        supportPromotionsList.put(PromotionType.RED_PACKET, spR);
        Merchandise merchandise = new Merchandise();
        merchandise.setSku("sku-apple");
        merchandise.setName("苹果");
        merchandise.setPrice(new BigDecimal(20));
        merchandise.setSupportPromotions(supportPromotionsList);
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(SnowflakeIdWorker.nextId());
        orderDetail.setOrderId(SnowflakeIdWorker.nextId());
        orderDetail.setMerchandise(merchandise);
        orderDetails.add(orderDetail);
        order.setList(orderDetails);
    }
}
