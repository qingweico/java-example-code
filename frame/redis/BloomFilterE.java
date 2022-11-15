package frame.redis;


import annotation.Pass;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

/**
 * 布隆过滤器 解决缓存穿透问题
 *
 * @author zqw
 * @date 2022/8/27
 */
@Pass
@SuppressWarnings("all")
class BloomFilterE {

    private BloomFilter<Integer> filter;


    @PostConstruct
    public void init() {
        // 元素数量; 期望误判率
        this.filter = BloomFilter.create(Funnels.integerFunnel(), 10000, 0.01);
        IntStream.rangeClosed(1, 10000).forEach(filter::put);
    }

    public void api(Integer id) {
        if(filter.mightContain(id)) {
            // 走 redis 缓存

            // 如果缓存为空 走数据库查询 并写入缓存
        }
    }
}
