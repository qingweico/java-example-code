package oak.test;

import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * <a href="https://url.spec.whatwg.org/"></a>
 * <a href="https://github.com/web-platform-tests/wpt/tree/master/url"></a>
 * {@code @SuppressWarnings({"SameParameterValue", "BooleanMethodIsAlwaysInverted"})}
 * {@code @SuppressWarnings("resource")}
 * jsf 技术
 * {@code @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)}
 * @see UriComponentsBuilder
 * @author zqw
 * @date 2025/8/20
 */
public class UriComponentsBuilderTest {

    @Test
    public void urlEncode() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost")
                .path("/api/a/b")
                .queryParam("query", "关键字");
        System.out.println(builder.toUriString());
        // encoded = true 表示传入的参数已经是经过 URL 编码的, 而不是决定是否要编码, 过去式
        System.out.println(builder.build(false).toUriString());
    }
}
