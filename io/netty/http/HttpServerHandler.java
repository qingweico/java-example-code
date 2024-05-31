package io.netty.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.apache.http.entity.ContentType;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author zqw
 * @date 2024/5/28
 */
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final String FAVICON_ICO = "/favicon.ico";
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        HttpParam hp = new HttpParam();
        hp.setDate(new Date());
        if (msg instanceof HttpRequest request) {
            HttpHeaders headers = request.headers();
            String uri = request.uri();
            if (uri.equals(FAVICON_ICO)) {
                return;
            }
            HttpMethod method = request.method();
            log.info("{} Url : {}", method, uri);
            if (method.equals(HttpMethod.GET)) {
                QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
                hp.setMethod(HttpMethod.GET.name());
                logReqParam(queryDecoder);

            } else if (method.equals(HttpMethod.POST)) {
                FullHttpRequest fullRequest = (FullHttpRequest) msg;
                hp.setMethod(HttpMethod.POST.name());
                String contentType = headers.get("Content-Type");
                if (contentType.equals(ContentType.APPLICATION_JSON.getMimeType())) {
                    String jsonStr = fullRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
                    JSONObject obj = JSON.parseObject(jsonStr);
                    log.info("{}, {}", contentType, obj);

                } else if (contentType.equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
                    String jsonStr = fullRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
                    QueryStringDecoder queryDecoder = new QueryStringDecoder(jsonStr, false);
                    logReqParam(queryDecoder);
                }
            }
            byte[] content = JSON.toJSONString(hp).getBytes();
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
            response.headers().set(CONTENT_TYPE, ContentType.TEXT_PLAIN.getMimeType());
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private void logReqParam(QueryStringDecoder qsd) {
        Map<String, List<String>> uriAttributes = qsd.parameters();
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
            for (String attrVal : attr.getValue()) {
                log.info("{}={}", attr.getKey(), attrVal);
            }
        }
    }
}
