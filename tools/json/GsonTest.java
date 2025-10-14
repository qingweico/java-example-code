package tools.json;

import cn.qingweico.collection.CollectionData;
import cn.qingweico.io.Print;
import cn.qingweico.supplier.ObjectFactory;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import misc.tree.MenuTreeNode;
import object.entity.User;
import org.junit.Test;
import thinking.genericity.BasicGenerator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * --------------- Gson ---------------
 * @author zqw
 * @date 2022/7/14
 */
public class GsonTest {
    @Test
    public void toJson() {
        Gson gson = new Gson();
        User user = ObjectFactory.create(User.class, true);
        // 将对象或者list转换为 Json
        CollectionData<User> collectionData = new CollectionData<>(BasicGenerator.create(User.class), 3);
        for (User u : collectionData) {
            ObjectFactory.populate(u, User.class);
        }
        System.out.println(gson.toJson(collectionData));
        System.out.println(gson.toJson(user));
    }

    @Test
    public void toPojo() {
        String json = "{\"id\":6953332975908970496,\"username\":\"Dr. Gregory Wiegand\",\"isVip\":false}";
        Gson gson = new Gson();
        System.out.println(gson.fromJson(json, User.class));
    }

    @Test
    @SuppressWarnings("all")
    public void toList() {
        String json = "[{\"id\":6953337173878792192,\"username\":\"Wai Sipes\",\"isVip\":false},{\"id\":6953337174029787136,\"username\":\"Erich Prohaska\",\"isVip\":true},{\"id\":6953337174088507392,\"username\":\"Edward Sanford\",\"isVip\":true}]";
        Gson gson = new Gson();
        // 将 Json 转换为 List(带有泛型)(或者Map)
        List<User> users = gson.fromJson(json, new TypeToken<List<User>>() {
        }.getType());
        for (User u : users) {
            System.out.println(u);
        }
    }
    @Test
    public void toJsonByReader() throws FileNotFoundException {
        Reader reader = new FileReader("misc/tree/MenuTree.json");
        Gson gson = new Gson();
        JsonReader jsonReader = gson.newJsonReader(reader);
        // Type 用来处理泛型
        Type type = new TypeToken<Map<String, List<MenuTreeNode>>>() {}.getType();
        Map<String, List<MenuTreeNode>> root = gson.fromJson(jsonReader, type);
        Print.printColl(root.get("root"));
    }
}
