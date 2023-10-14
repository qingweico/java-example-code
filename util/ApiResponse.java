package util;

import lombok.Builder;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

/**
 * API 响应 JSON 格式数据
 *
 * @author zqw
 * @date 2023/10/12
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ApiResponse<T> {
    private @Builder.Default
    String code = "200";
    private @Builder.Default
    String msg = "OK";
    private @Builder.Default
    Long now = Instant.now().getEpochSecond();
    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }
}
