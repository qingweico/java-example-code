package io.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import util.Print;
import util.constants.Constants;
import util.DateUtil;

/**
 * @author zqw
 * @date 2022/1/29
 */
public class NettyChatServer {
    private static final int PORT = Constants.DEFAULT_COMMON_PORT;

    public void start() {
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap().group(boosGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ChatServerHandler());

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

    static class ChatServerHandler extends SimpleChannelInboundHandler<String> {
        static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) {
            Channel channel = ctx.channel();
            channelGroup.forEach(ch -> {
                if (channel != ch) {
                    ch.writeAndFlush(String.format("[%s] %s: %s%n", DateUtil.format(),
                            channel.remoteAddress().toString().substring(1), msg));
                } else {
                    ch.writeAndFlush(String.format("[%s] [*]%s",
                            DateUtil.format(), msg));
                }
            });
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            Channel channel = ctx.channel();
            channelGroup.writeAndFlush(String.format("[%s] %s 加入聊天%n", DateUtil.format(),
                    channel.remoteAddress().toString().substring(1)));
            channelGroup.add(channel);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            Channel channel = ctx.channel();
            channelGroup.writeAndFlush(String.format("client [%s] %s 退出聊天%n", DateUtil.format(),
                    channel.remoteAddress().toString().substring(1)));
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            System.out.printf("[%s] %s 上线了%n", DateUtil.format(),
                    ctx.channel().remoteAddress().toString().substring(1));
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            System.out.printf("[%s] %s 下线了%n", DateUtil.format(),
                    ctx.channel().remoteAddress().toString().substring(1));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
        }
    }

    public static void main(String[] args) {
        NettyChatServer nettyChatServer = new NettyChatServer();
        nettyChatServer.start();
    }
}
