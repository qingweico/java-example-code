package oak.newfeature;

import lombok.extern.slf4j.Slf4j;

/**
 * -------------------- 模块化应用程序 --------------------
 *
 * @author zqw
 * @date 2023/1/7
 * -----模块关键字-----
 * {@code module} 要定义的模块的名字
 * {@code exports} 说明了这个模块允许外部访问的 API(这个模块的公开接口)
 * {@code uses} 说明这个模块直接使用了 xxx 定义的服务接口
 * {@code requires} 这个模块需要使用 xxx 这个模块
 * {@code provides} 说明这个模块实现了 xxx 定义的服务接口
 */
@Slf4j
class ModularApplication {
    public static void main(String[] args) {

    }
}
