package geek.design.decorator;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 商品
 *
 * @author zqw
 * @date 2022/7/9
 */
@Data
public class Merchandise {

    /**
     * 商品SKU
     */
    private String sku;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 支持促销类型
     */
    private Map<PromotionType, SupportPromotions> supportPromotions;
}