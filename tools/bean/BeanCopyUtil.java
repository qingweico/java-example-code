package tools.bean;


import junit.framework.Assert;
import object.entity.User;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import util.ObjectFactory;

/**
 * 各种 Bean 属性拷贝工具
 * @author zqw
 * @date 2023/2/10
 * @see org.springframework.beans.BeanUtils
 * @see cn.hutool.core.bean.BeanUtil
 * @see org.apache.commons.beanutils.BeanUtils
 * @see org.apache.commons.beanutils.PropertyUtils
 * @see org.springframework.cglib.beans.BeanCopier
 */
public class BeanCopyUtil {
    public static void main(String[] args) {
        User user = ObjectFactory.create(User.class, true);


        UserDto userDto = new UserDto();

        // Spring
        BeanUtils.copyProperties(user, userDto);
        System.out.println(userDto);

        // CGLIB BeanCopier
        // VM options : --add-opens java.base/java.lang=ALL-UNNAMED
        // 使用转换器可以实现不同类型的拷贝
        // BeanCopier只拷贝名称和类型都相同的属性
        BeanCopier copier = BeanCopier.create(User.class, UserDto.class, false);
        userDto = new UserDto();
        copier.copy(user, userDto, null);

        // TODO

        // mapstruct
    }
}
