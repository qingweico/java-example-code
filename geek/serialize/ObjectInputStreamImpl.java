package geek.serialize;

import object.entity.User;
import object.entity.Student;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author zqw
 * @date 2022/6/29
 */
class ObjectInputStreamImpl extends ObjectInputStream {

    static Student student;

    static {
        student = new Student();
        student.setName(UUID.randomUUID().toString());
        student.setScore(100);
    }

    public ObjectInputStreamImpl(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws ClassNotFoundException, IOException {
        // 通过校验反序列化对象的名称来控制反序列化对象
        if (!desc.getName().equals(User.class.getName())) {
            throw new InvalidClassException("Unauthorized deserialization attempt", desc.getName());
        }
        return super.resolveClass(desc);

    }

    public static void main(String[] args) {
        compareSerializeStreamSize();
    }

    public static void compareSerializeStreamSize() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // Java 序列化后的流会变大;影响系统的吞吐量
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(student);
        } catch (IOException ex) {
            try {
                baos.close();
            } catch (IOException e) {
                // ignore
            }
        }
        byte[] bytes = baos.toByteArray();
        System.out.println("ObjectOutputStream: 字节编码长度为: " + bytes.length);


        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);

        byte[] name = student.getName().getBytes();
        byte[] score = student.getScore().toString().getBytes();
        byteBuffer.putInt(name.length);
        byteBuffer.put(name);
        byteBuffer.putInt(score.length);
        byteBuffer.put(score);

        byteBuffer.flip();
        bytes = new byte[byteBuffer.remaining()];
        System.out.print("ByteBuffer 字节编码长度：" + bytes.length);
    }
}
