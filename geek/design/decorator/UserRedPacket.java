package geek.design.decorator;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 红包
 *
 * @author zqw
 * @date 2022/7/9
 */


@Data
public class UserRedPacket {

    /**
     * 红包id
     */
    private long id;
    /**
     * 领取用户id
     */
    private long userId;
    /**
     * 商品SKU
     */
    private String sku;
    /**
     * 领取红包金额
     */
    private BigDecimal redPacket;
}