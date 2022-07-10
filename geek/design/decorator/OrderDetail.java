package geek.design.decorator;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 详细订单
 *
 * @author zqw
 * @date 2022/7/9
 */
@Data
public class OrderDetail {
    /**
     * 详细订单id
     */
    private long id;
    /**
     * 主订单ID
     */
    private long orderId;
    /**
     * 商品详情
     */
    private Merchandise merchandise;
    /**
     * 支付单价
     */
    private BigDecimal payMoney;
}