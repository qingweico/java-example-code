package geek.design.decorator;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zqw
 * @date 2022/7/9
 */
@Data
public class Order {
    /**
     * 订单id
     */
    private long id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 总支付金额
     */
    private BigDecimal totalPayMoney;
    /**
     * 详细订单列表
     */
    private List<OrderDetail> list;
}
