package eight;

import com.google.common.base.MoreObjects;

/**
 * @author zqw
 * @date 2021/2/23
 */
public class User {
    private String username;
    private boolean isVIP;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("isVIP", isVIP)
                .toString();
    }
}
