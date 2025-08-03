package io.netty.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.apache.http.entity.ContentType;
import cn.qingweico.constants.Symbol;

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
class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

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
            } else if (method.equals(HttpMethod.CONNECT)) {
                // 处理 HTTPS CONNECT 请求
                handleConnect(ctx, request);
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

    private void handleConnect(ChannelHandlerContext ctx, HttpRequest request) {
        // 解析目标地址 如 CONNECT domain:443 HTTP/1.1
        String[] parts = request.uri().split(Symbol.COLON);
        String host = parts[0];
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 443;

        Bootstrap bootstrap = new Bootstrap()
                .group(ctx.channel().eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new RelayHandler(ctx.channel()));
                    }
                });

        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                ctx.writeAndFlush(new DefaultHttpResponse(
                        HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK
                ));
            } else {
                ctx.close();
            }
        });
    }
    public static class RelayHandler extends ChannelInboundHandlerAdapter {
        private final Channel relayChannel;

        public RelayHandler(Channel relayChannel) {
            this.relayChannel = relayChannel;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            relayChannel.writeAndFlush(msg);
        }
    }
}
