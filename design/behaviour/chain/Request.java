package design.behaviour.chain;

import lombok.Data;

import java.util.Map;

/**
 * @author zqw
 * @date 2023/10/16
 */
@Data
public class Request {

    private Map<String, Object> headers;

    private String requestMethod;
}
