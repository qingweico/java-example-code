package object.entity;

import cn.qingweico.annotation.Ignore;
import com.google.common.base.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author zqw
 * @date 2021/2/23
 */
@Getter
@Setter
@Accessors(chain = true)
@Slf4j
public class User implements Serializable, Cloneable {
    @Serial
    @Ignore
    private static final long serialVersionUID = 1L;
    @EqualsAndHashCode.Exclude
    private Long id;
    private String username;
    @Ignore
    private String password;
    @EqualsAndHashCode.Exclude
    private boolean isVip;
    @Ignore
    private transient Integer instantiation = 10;

    public User(Long id) {
        this.id = id;
    }

    public User() {
    }

    public User(Long id, String username, boolean isVip) {
        this.id = id;
        this.username = username;
        this.isVip = isVip;
        log.info("User Constructor...");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof User;

    }

    @Override
    public int hashCode() {
        return (int) (id >>> 16);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("username", username).add("isVip", isVip).toString();
    }

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        ObjectOutputStream.PutField fields = out.putFields();
        log.info("序列化前的 id : {}",  this.id);
        log.info("序列化前的 username : {}",  this.username);
        fields.put("id", this.id);
        fields.put("username", "序列化后用户名");
        out.writeFields();
    }

    @Serial
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField fields = in.readFields();
        log.info("序列化后的 id : {}",  fields.get("id", Long.class));
        log.info("序列化后的 username : {}",  fields.get("username", String.class));
        this.username = "反序列化后的用户名";
    }

    @Serial
    private void readObjectNoData() throws ObjectStreamException {
        // 该方法用于处理序列化前后对象版本兼容性问题
        throw new InvalidObjectException("Stream data required");
    }

}
