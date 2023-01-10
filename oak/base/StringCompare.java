package oak.base;

/**
 * @author zqw
 * @date 2022/12/24
 */
class StringCompare {
    public static void main(String[] args) {
        String a = "hello";
        String b = "hello";
        System.out.println(a == b);
        // true 直接赋值字符串时会把字符串储存在字符串常量池中,
        // 当再次赋值时会到常量池中寻找,找到会指向堆中的地址,
        // 找不到会在堆中重新开辟一块空间
        // true
        System.out.println(a.equals(b));
        String c = new String("hello");
        String d = new String("hello");
        // false  使用new关键字会直接在堆空间中开辟一块新的空间
        System.out.println(c == d);
        // true
        System.out.println(c.equals(d));

        String s = "hello";
        String s1 = "he";
        String s2 = "llo";
        // "he" 和"llo"都为字符串常量,在预编译时期“+”被优化
        String s3 = "he" + "llo";
        // true 相当于直接把两个字符串常量自动合成为一个字符串常量
        System.out.println(s == s3);
        // false
        System.out.println(s1 == (s2 + s1));
        /*因为字符串+操作就是在程序运行时new了StringBuilder对象
        然后调用append()方法,拼接完成后再调用toString()方法返回一个String对象*/
    }
}
// - 字符串和字符串常量是两个不同的概念

// - 字符串常量是储存在本地方法区,而字符串则储存在堆里(heap)
