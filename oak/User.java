package oak;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zqw
 * @date 2021/2/23
 */
@Getter
@Setter
@Accessors(chain = true)
public class User implements Serializable {
    private Long id;
    private String username;
    private boolean isVip;

    private Integer instantiation = 10;

    public User(Long id) {
        this.id = id;
    }

    public User() {
    }

    public User(Long id, String username, boolean isVip) {
        this.id = id;
        this.username = username;
        this.isVip = isVip;
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
}
