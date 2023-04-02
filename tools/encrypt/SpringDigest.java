package tools.encrypt;

import org.springframework.util.DigestUtils;

/**
 * Spring MD5 加密工具类
 *
 * @author zqw
 * @date 2023/3/31
 */
class SpringDigest {
    public static void main(String[] args) {
        String userprofile = System.getenv("USERPROFILE");
        System.out.println(DigestUtils.md5DigestAsHex(userprofile.getBytes()));
    }
}
