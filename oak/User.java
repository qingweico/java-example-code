package oak;

import com.google.common.base.MoreObjects;

/**
 * @author zqw
 * @date 2021/2/23
 */
public class User {
    private String username;
    private boolean isVip;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("isVIP", isVip)
                .toString();
    }
}
