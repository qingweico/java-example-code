package design.creational.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 问答题 实体类
 * @author zqw
 * @date 2026/1/15
 */
@Data
@AllArgsConstructor
class AnswerQuestion {
    /**问题*/
    private String name;
    /**答案*/
    private String key;
}
