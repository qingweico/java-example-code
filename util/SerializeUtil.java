package util;

import object.entity.User;
import util.constants.Constants;

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

    private static final String DEFAULT_FILE_NAME = Constants.DEFAULT_FILE_PATH_MAME;

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
        ByteArrayOutputStream byteArrayOutput = null;
        byte[] bytes = null;
        try {
            byteArrayOutput = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(byteArrayOutput);
            oos.writeObject(object);
            bytes = byteArrayOutput.toByteArray();
        } catch (Exception e) {
            Print.err(e.getMessage());
        } finally {
            close(oos);
            close(byteArrayOutput);
        }
        return bytes;
    }

    public static void serializeToFile(Object object) {
        serializeToFile(object, DEFAULT_FILE_NAME);
    }

    /**
     * 序列化数据到文件中
     * @param path The file path of serialized data
     */
    public static void serializeToFile(Object object, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        }catch (IOException e) {
            // xxx
        }

    }
    /**
     * 从文件中反序列化中对象
     * @param path The file path of being serialized data
     */
    public static Object deserialize(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        }catch (ClassNotFoundException | IOException e) {
            Print.err(e.getMessage());
        }
        return null;
    }
    public static Object deserialize() {
        return deserialize(DEFAULT_FILE_NAME);
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
        ByteArrayInputStream byteArrayInput = null;
        ObjectInputStream ois = null;
        try {
            byteArrayInput = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(byteArrayInput);
            return ois.readObject();
        } catch (Exception e) {
            Print.err(e.getMessage());
        } finally {
            close(byteArrayInput);
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
        ByteArrayOutputStream byteArrayOutput = null;
        byte[] bytes = null;
        try {
            byteArrayOutput = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(byteArrayOutput);
            for (Object obj : list) {
                oos.writeObject(obj);
            }
            bytes = byteArrayOutput.toByteArray();
        } catch (Exception e) {
            Print.err(e.getMessage());
        } finally {
            close(oos);
            close(byteArrayOutput);
        }
        return bytes;
    }

    /**
     * 反序列化 list 集合
     *
     * @param bytes 字节数组
     * @return List<T>
     */
    public static <T> List<T> deserializeList(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        List<T> list = new ArrayList<>();
        ByteArrayInputStream byteArrayInput = null;
        ObjectInputStream ois = null;
        try {
            byteArrayInput = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(byteArrayInput);
            while (byteArrayInput.available() > 0) {
                @SuppressWarnings("unchecked")
                T obj = (T) ois.readObject();
                if (obj == null) {
                    break;
                }
                list.add(obj);
            }
        } catch (Exception e) {
            Print.err(e.getMessage());
        } finally {
            close(byteArrayInput);
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
                Print.err(e.getMessage());
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
        List<User> users = deserializeList(bytes);
        users.forEach(System.out::println);
    }
}
