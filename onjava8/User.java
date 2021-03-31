package onjava8;

/**
 * @author:qiming
 * @date: 2021/2/23
 */
public class User {
    private String Username;
    private String password;
    private boolean isVIP;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }
}
