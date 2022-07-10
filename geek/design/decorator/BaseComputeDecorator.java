package geek.design.decorator;

import java.math.BigDecimal;

/**
 * 计算支付金额的抽象类
 *
 * @author zqw
 * @date 2022/7/9
 */

public abstract class BaseComputeDecorator implements IBaseCompute {

    private final IBaseCompute compute;

    public BaseComputeDecorator(IBaseCompute compute) {
        this.compute = compute;
    }

    @Override
    public BigDecimal computePayMoney(OrderDetail orderDetail) {
        BigDecimal payTotalMoney = new BigDecimal(0);
        if (compute != null) {
            payTotalMoney = compute.computePayMoney(orderDetail);
        }
        return payTotalMoney;
    }
}