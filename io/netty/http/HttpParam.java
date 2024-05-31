package io.netty.http;

import lombok.Data;

import java.util.Date;

/**
 * @author zqw
 * @date 2024/5/28
 */
@Data
public class HttpParam {
    private String method;
    private Date date;
}
