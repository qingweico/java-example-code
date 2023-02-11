package oak.newfeature;

import geek.exception.OutOfBoundsBench;
import org.apache.commons.lang3.StringUtils;

/**
 * //////////////// 异常处理 ////////////////
 * 使用启用 JFR(Java Flight Recorder)来收集诊断和分析数据
 * 异常代码性能消耗 {@link OutOfBoundsBench}, 解决 : 使用错误码来代替异常 {@link Returned}
 * 错误码的缺点 : 虽然性能提高了,但是需要更多的逻辑代码,降低了代码的可维护性和可读性,丢弃了调试信息
 * @author zqw
 * @date 2023/1/18
 */
class ExceptionHandler {
    public static void main(String[] args) {
        System.out.println(checkString("ExceptionHandler"));
        System.out.println(checkString(""));
    }

    public static Returned<String> checkString(String v) {
        if(StringUtils.isNotEmpty(v)) {
            return new Returned.ReturnValue<>(v);
        }else {
            return new Returned.ErrorCode<>(400);
        }
    }
}
