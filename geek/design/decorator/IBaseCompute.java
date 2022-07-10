package geek.design.decorator;

import java.math.BigDecimal;

/**
 * 计算支付金额接口类
 *
 * @author zqw
 * @date 2022/7/9
 */


public interface IBaseCompute {

    /**
     * 计算金额
     *
     * @param orderDetail {@link OrderDetail}
     * @return 计算后的金额
     */
    BigDecimal computePayMoney(OrderDetail orderDetail);
}