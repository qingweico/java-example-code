package oak.newfeature;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * lombok中setter是一个可变操作,可以通过其它方式来替代需要setter的场景
 * 使用 of() 方法构建新对象,withXX() 方法修改单个参数,toBuilder()
 * 方法修改多个参数,修改后会返回新的对象,保证了数据的不可变性
 * @author zqw
 * @date 2022/12/30
 */
@With
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Model {
    String id;
    String name;
    String type;

    public static void main(String[] args) {
        // 构建Model
        var model01 = Model.of("1", "model01", "model");
        // 构建空Model
        var model02 = Model.of();
        // 构建指定参数的Model
        var model03 = model02.toBuilder().id("3").name("model03").build();
        System.out.println(model03);
        // 修改Model的一个值,通过@With来生成一个全新的model
        var model04 = model01.withName("model04");
        System.out.println(model04);
        // 修改多个值,通过@Builder来生成一个全新的model
        var model05 = model01.toBuilder().name("model05").type("new_model").build();
        System.out.println(model05);
    }
}
