package oak.serialize;

import cn.qingweico.serialize.SerializeUtil;
import object.entity.User;

/**
 * 实体类中序列化id的作用
 * serialVersionUID
 *
 * @author zqw
 * @date 2023/6/14
 */
class SerialVersionId {
    public static void main(String[] args) {
        User user = new User();
        SerializeUtil.serializeToFile(user);
        user = (User) SerializeUtil.deserialize();
        // 在实体类增加属性时 反序列化之前对象序列化后的文件时会异常(JDK的一种安全机制)
        //  local class incompatible: stream classdesc serialVersionUID = xxx, local class serialVersionUID = xxx
        // 解决 : 在实体类中增加serialVersionUID字段
        System.out.println(user.getUsername());
    }
}
