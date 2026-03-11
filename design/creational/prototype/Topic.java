package design.creational.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author zqw
 * @date 2026/1/15
 */
@AllArgsConstructor
@Data
class Topic {
    /**选项和问题*/
    private Map<String, String> option;
    /**答案*/
    private String key;
}
