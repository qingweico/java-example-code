package geek.design.decorator;

import java.math.BigDecimal;

/**
 * 支付基本类
 *
 * @author zqw
 * @date 2022/7/9
 */
public class BaseCompute implements IBaseCompute {


    @Override
    public BigDecimal computePayMoney(OrderDetail orderDetail) {
        orderDetail.setPayMoney(orderDetail.getMerchandise().getPrice());
        System.out.println("商品原单价金额为: " + orderDetail.getPayMoney());
        return orderDetail.getPayMoney();
    }
}