package oak.regx;

/**
 * @author zqw
 * @date 2023/7/27
 */
class RegxMatch {
    public static void main(String[] args) {
        String s = "https://www.tensorflow.org/";
        String rex = "[a-z]+://[a-z.]+/";
        String rex2 = "[htps]+://www.tensorflow.org/";
        String rex3 = "[a-zA-Z.:/]+";

        // true
        System.out.println(s.matches(rex));
        // true
        System.out.println(s.matches(rex2));
        // true
        System.out.println(s.matches(rex3));
    }
}
