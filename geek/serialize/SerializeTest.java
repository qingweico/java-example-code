package geek.serialize;

import lombok.extern.slf4j.Slf4j;
import oak.User;
import org.testng.annotations.Test;
import util.RandomDataGenerator;

import java.io.*;

@Slf4j
public class SerializeTest {
    private static final String path = "geek/serialize/";
    private static final String fileName = "user.bin";

    @Test
    public void deserializationWhiteList() {
        ObjectInputStreamImpl ois;
        try {
            ois = new ObjectInputStreamImpl(new FileInputStream(path + fileName));
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
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + fileName));
        User user = new User();
        user.setUsername(RandomDataGenerator.randomName());
        oos.writeObject(user);
        oos.flush();
        oos.close();
    }
}
