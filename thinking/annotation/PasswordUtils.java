package thinking.annotation;

import java.util.List;

/**
 * @author zqw
 * @date 2021/4/8
 */
class PasswordUtils {

    @UseCase(id=47, description = "Password must contain at least one numeric")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }

    @UseCase(id=48)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id=49, description = "New password can't equal previously used ones")
    public boolean checkNewPassword(List<String> prevPasswords, String password) {
       return !prevPasswords.contains(password);
    }

}
