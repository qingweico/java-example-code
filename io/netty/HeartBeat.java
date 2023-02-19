package io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import util.constants.Constants;

/**
 * @author zqw
 * @date 2022/1/30
 */
public class HeartBeat {
    private static final int PORT = Constants.DEFAULT_COMMON_PORT;

    public void listen() {
        EventLoopGroup boosGroup = new NioEventLoopGroup(Constants.ONE);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap().group(boosGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new IdleStateHandler(1, 2, 3));
                    pipeline.addLast(new HeartBeatEventHandler());
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

    static class HeartBeatEventHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                String eventType = null;
                switch (idleStateEvent.state()) {
                    case READER_IDLE:
                        eventType = "read idle";
                        break;
                    case WRITER_IDLE:
                        eventType = "write idle";
                        break;
                    case ALL_IDLE:
                        eventType = "read_write idle";
                        break;
                    default:
                        System.err.println("unknown event");
                }
                System.out.println(ctx.channel().remoteAddress() + ": " + eventType);
            }
        }
    }

    public static void main(String[] args) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.listen();
    }
}
