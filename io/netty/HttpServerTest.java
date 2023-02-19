package io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.junit.Test;
import util.constants.Constants;
import util.DateUtil;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author zqw
 * @date 2022/1/28
 */
public class HttpServerTest {
    private static final int PORT = Constants.DEFAULT_COMMON_PORT;
    private static final String FAVICON = "/favicon.ico";

    @Test
    public void server() {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap().group(boosGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("HttpServerCodec", new HttpServerCodec());
                            pipeline.addLast("HttpServerHandler", new HttpServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    static class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws URISyntaxException {
            if (msg instanceof HttpRequest) {
                HttpRequest request = (HttpRequest)msg;
                URI uri = new URI(request.uri());
                if(FAVICON.equals(uri.getPath())) {
                    return;
                }
                System.out.printf("[%s %s] [request from client %s]%n", DateUtil.format(),
                        Thread.currentThread().getName(), ctx.channel().remoteAddress());
                ByteBuf content = Unpooled.copiedBuffer("netty http server", CharsetUtil.UTF_8);
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
                        .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                ctx.writeAndFlush(response);
            }
        }
    }
}
