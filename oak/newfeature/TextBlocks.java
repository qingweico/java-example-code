package oak.newfeature;

/**
 * 文本块
 * @author zqw
 * @date 2022/12/19
 * @since JDK15 (preview JDK13)
 */
class TextBlocks {
    public static void main(String[] args) {

        // 开始分隔符必须单独成行 所以,一个文字块至少有两行代码

        // 文本块对于缩进空格的处理
        // 文字块的内容并没有计入缩进空格
        // 像传统的字符串一样,文字块是字符串的一种常量表达式
        // 不同于传统字符串的是,在编译,文字块要顺序通过如下三个不同的编译步骤
        // 1:为了降低不同平台间换行符的表达差异,编译器把文字内容里的换行符统一转换成 LF(\\u000A)
        // 2:为了能够处理 Java 源代码里的缩进空格,要删除所有文字内容行和结束分隔符共享的前导空格,以及所有文字内容行的尾部空格
        // 3:最后处理转义字符,这样开发人员编写的转义序列就不会在第一步和第二步被修改或删除
        String textBlock = """
                <!DOCTYPE html>
                <html>
                    <body>
                        <h1>"This is Text Blocks!"</h1>
                    </body>
                </html>
                """;


        String stringBlock =
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <body>\n" +
                "        <h1>\"This is Text Blocks!\"</h1>\n" +
                "    </body>\n" +
                "</html>\n";

        // true
        // 文字块是在编译期处理的,并且在编译期被转换成了常量字符串,然后就被当作常规的字符串了
        // 所以,如果文字块代表的内容,和传统字符串代表的内容一样,那么这两个常量字符串变量就指向同一内存地址,代表同一个对象

        // 文字块就是字符串
        System.out.println(textBlock == stringBlock);
        // true
        System.out.println(textBlock.equals(stringBlock) );
    }
}
