package oak;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String username;
    private boolean isVip;

    public User(Integer id) {
        this.id = id;
    }

    public User() {
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
        return id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("username", username).add("isVip", isVip).toString();
    }
}
