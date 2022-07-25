package tools.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import oak.User;
import org.junit.Test;
import util.ObjectFactory;

import java.util.Date;

/**
 * --------------- FastJson ---------------
 *
 * @author zqw
 * @date 2022/7/18
 */
public class FastJsonTest {
    @Test
    public void toJsonString() {
        User user = ObjectFactory.create(User.class, true);
        String jsonString = JSON.toJSONString(user);
        System.out.println(jsonString);
    }

    @Test
    public void toPojo() {
        String s = "{\"id\":6954685528382922753,\"username\":\"Dr. Tad Fritsch\",\"vip\":false}";
        User user = JSON.parseObject(s, User.class);
        System.out.println(user);
    }
    @Test
    public void handleDataTime() {
        Date date = new Date();
        System.out.println(JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd HH:mm:ss.SSS"));
        // 不同的日期格式
        System.out.println(JSON.toJSONString(date,  SerializerFeature.UseISO8601DateFormat));
    }
}
