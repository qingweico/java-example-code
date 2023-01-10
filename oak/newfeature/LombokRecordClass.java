package oak.newfeature;

import lombok.Builder;
import lombok.With;

/**
 * lombok 结合 Record 类使用
 * Lombok 在 1.18.20 版本就对 Record 进行了支持
 * 1.18.22 版本开始全面支持 JDK17 的其他新特性
 *
 * @author zqw
 * @date 2022/12/30
 */
class LombokRecordClass {
    public static void main(String[] args) {
        var m = new Model0("1", "xxx", "type");
        System.out.println(m);
        var newModel = m.withId("2");
        System.out.println(newModel);
    }
}

// @NoArgsConstructor,@RequiredArgsConstructor,@AllArgsConstructor @ToString 只支持class和enum,不支持record
// Record 类暂不支持默认.of() 的对象构造方法
@With
@Builder(toBuilder = true)
record Model0(String id, String name, String type) {
}
