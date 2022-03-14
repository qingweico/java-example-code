package effective;

/**
 * 对可恢复的情况使用受检异常, 对编程错误使用运行时异常
 * @author zqw
 * @date 2021/11/13
 */
public class Article70 {

    // For recoverable situation, a checked exception must be thrown.
    // For program errors, a runtime exception should be thrown.
    // If you are not sure whether it is recoverable, throw an unchecked exception.
    // Do not define any throw type that is neither a checked exception nor a runtime exception.
    // Provide methods on the checked exception to assist in recover.
}
