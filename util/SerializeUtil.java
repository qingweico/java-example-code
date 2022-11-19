package util;

import object.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 序列化工具
 *
 * @author zqw
 * @date 2022/8/26
 */
public class SerializeUtil {

    /**
     * 序列化
     *
     * @param object 序列化对象
     * @return 序列化后的字节数组
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(oos);
            close(baos);
        }
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @return 反序列化后得到的对象
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(bais);
            close(ois);
        }
        return null;
    }

    /**
     * 序列化 list 集合
     *
     * @param list list 集合
     * @return 序列化后的字节数组
     */
    public static byte[] serializeList(List<?> list) {

        if (list == null || list.size() == 0) {
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            for (Object obj : list) {
                oos.writeObject(obj);
            }
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(oos);
            close(baos);
        }
        return bytes;
    }

    /**
     * 反序列化 list 集合
     *
     * @param bytes 字节数组
     * @return List<T>
     */
    public static <T> List<T> deserializeList(byte[] bytes, Class<T> t) {
        if (bytes == null) {
            return null;
        }

        List<T> list = new ArrayList<T>();
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            while (bais.available() > 0) {
                @SuppressWarnings("unchecked")
                T obj = (T) ois.readObject();
                if (obj == null) {
                    break;
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(bais);
            close(ois);
        }
        return list;
    }

    /**
     * 关闭io流对象
     *
     * @param closeable Closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Object
        System.out.println("Object---------------------------");
        User user = ObjectFactory.create(User.class, true);
        byte[] bytes = serialize(user);
        System.out.println(deserialize(bytes));

        System.out.println("List---------------------------");
        // List
        List<User> list = new ArrayList<>();
        int size = 5;
        for (int i = 0; i < size; i++) {
            list.add(ObjectFactory.create(User.class, true));
        }
        bytes = serializeList(list);
        List<User> users = deserializeList(bytes, User.class);
        users.forEach(System.out::println);
    }
}
