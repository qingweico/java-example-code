package io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import util.Print;
import util.constants.Constants;
import util.DateUtil;

/**
 * @author zqw
 * @date 2022/1/30
 */
public class WebSocketServer {
    private static final int PORT = Constants.DEFAULT_COMMON_PORT;

    public void serve() {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap().group(boosGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpObjectAggregator(Constants.KB * 8));
                            // ws://127.0.0.1:8848/
                            pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                            pipeline.addLast(new TextWebSocketFrameHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    static class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
            System.out.printf("[%s] server rcv: %s%n", DateUtil.format(), msg.text());
            String reply = String.format("[%s] server reply: %s%n", DateUtil.format(), msg.text());
            ctx.channel().writeAndFlush(new TextWebSocketFrame(reply));
        }

        // Web 客户端连接后触发该方法
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            System.out.println("handlerAdded LongText: " + ctx.channel().id().asLongText());
            System.out.println("handlerAdded ShortText: " + ctx.channel().id().asShortText());
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            System.out.println("handlerRemoved LongText: " + ctx.channel().id().asLongText());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.out.println(cause.getMessage());
            ctx.close();
        }
    }

    public static void main(String[] args) {
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.serve();
    }
}
