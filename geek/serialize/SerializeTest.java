package geek.serialize;

import lombok.extern.slf4j.Slf4j;
import oak.User;
import object.Student;
import org.testng.annotations.Test;
import util.RandomDataGenerator;

import java.io.*;

/**
 * @author zqw
 * @date 2022/6/29
 */
@Slf4j
public class SerializeTest {
    private final String path = "geek/serialize/";
    private final String studentFileName = "student.bin";

    @Test
    public void deserializationWhiteList() {
        ObjectInputStreamImpl ois;
        try {
            FileInputStream fis = new FileInputStream(path + studentFileName);
            ois = new ObjectInputStreamImpl(fis);
            // Exception: Unauthorized deserialization attempt; object.Student
            User user = (User) ois.readObject();
            log.info("user: [{}]", user);
        } catch (IOException ex) {
            log.error("{}", ex.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void serializeUserObject() throws IOException {
        String userFileName = "user.bin";
        FileOutputStream fis = new FileOutputStream(path + userFileName);
        ObjectOutputStream oos = new ObjectOutputStream(fis);
        User user = new User();
        user.setUsername(RandomDataGenerator.randomName());
        oos.writeObject(user);
        oos.flush();
        oos.close();
        System.out.println("serialize user success!");
    }

    @Test
    public void serializeStudentObject() throws IOException {
        FileOutputStream fis = new FileOutputStream(path + studentFileName);
        ObjectOutputStream oos = new ObjectOutputStream(fis);
        Student user = new Student();
        user.setName(RandomDataGenerator.randomName());
        oos.writeObject(user);
        oos.flush();
        oos.close();
        System.out.println("serialize student success!");
    }
}
