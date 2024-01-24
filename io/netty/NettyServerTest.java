package io.netty;

import com.github.javafaker.Faker;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.util.CharsetUtil;
import org.junit.Test;
import util.Print;
import util.constants.Constants;
import util.DateUtil;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2022/1/28
 */
public class NettyServerTest {
    private static final int PORT = Constants.DEFAULT_COMMON_PORT;
    private static final String HOST = Constants.LOOP_BACK;

    @Test
    public void server() throws InterruptedException {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap().group(boosGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline channel = socketChannel.pipeline();
                            channel.addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            // Future-Listener
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("bind port(" + PORT + ") success!");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Test
    public void client() throws InterruptedException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap().group(eventExecutors);
            bootstrap.channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline channel = socketChannel.pipeline();
                            channel.addLast(new NettyClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    @Test
    public void testProtoBufServer() {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap().group(boosGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline channel = socketChannel.pipeline();
                            StudentObject.Student instance = StudentObject.Student.getDefaultInstance();
                            channel.addLast("decoder", new ProtobufDecoder(instance));
                            channel.addLast(new ProtoBufServerHandler());
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

    @Test
    public void testProtoBufClient() {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap().group(eventExecutors);
            bootstrap.channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline channel = socketChannel.pipeline();
                            channel.addLast("encoder", new ProtobufEncoder());
                            channel.addLast(new ProtoBufClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    @Test
    public void testCodecServer() {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap().group(boosGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline channel = socketChannel.pipeline();
                            // 入栈的handler进行解码
                            channel.addLast(new ByteToMessageDecoder() {
                                @Override
                                public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
                                    System.out.println("server: ByteToMessageDecoder");
                                    if (in.readableBytes() >= Constants.BYTE) {
                                        out.add(in.readLong());
                                    }
                                }
                            });
                            // 出站的handler进行编码
                            channel.addLast(new MessageToByteEncoder<Long>() {
                                @Override
                                protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) {
                                    System.out.println("server: MessageToByteEncoder");
                                    out.writeLong(msg);
                                }
                            });
                            channel.addLast(new ServerCodecHandler());
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

    @Test
    public void testCodecClient() {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap().group(eventExecutors);
            bootstrap.channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline channel = socketChannel.pipeline();
                            // 出站的handler进行编码
                            channel.addLast(new MessageToByteEncoder<Long>() {
                                @Override
                                protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) {
                                    System.out.println("client: MessageToByteEncoder");
                                    out.writeLong(msg);
                                }
                            });
                            // 入栈的handler进行解码
                            channel.addLast(new ReplayingDecoder<Void>() {
                                @Override
                                protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
                                    System.out.println("client: ReplayingDecoder");
                                    out.add(in.readLong());
                                }
                            });
                            channel.addLast(new ClientCodecHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    /**
     * 读要解码; 写要编码
     * Inbound(decoder): server -> client(read)
     * Outbound(encoder): client -> server(write)
     */
    static class NettyServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            // 提交任务到taskQueue中; 异步执行
            ctx.channel().eventLoop().execute(() ->
                    System.out.printf("[%s %s] [%s]%n", DateUtil.format(),
                            Thread.currentThread().getName(), "Async Task"));
            // 定时任务
            ctx.channel().eventLoop().schedule(() -> System.out.printf("[%s %s] [%s]%n", DateUtil.format(),
                    Thread.currentThread().getName(), "Scheduled Task"), 2, TimeUnit.SECONDS);
            ByteBuf buf = (ByteBuf) msg;
            System.out.printf("[%s %s] [rcv: %s, from client %s]%n", DateUtil.format(),
                    Thread.currentThread().getName(),
                    buf.toString(CharsetUtil.UTF_8), ctx.channel().remoteAddress());
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.err.println("server error");
            ctx.close();
        }
    }

    static class ProtoBufServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            // 接受从客户端发送的 StudentObject.Student 类型的数据
            StudentObject.Student student = (StudentObject.Student) msg;
            System.out.printf("[%s %s] [rcv: %s, from client %s]%n", DateUtil.format(),
                    Thread.currentThread().getName(),
                    student.getName(), ctx.channel().remoteAddress());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.err.println("server error");
            ctx.close();
        }
    }

    static class ProtoBufClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            StudentObject.Student student = StudentObject.Student
                    .newBuilder()
                    .setName(new Faker().name().fullName())
                    .build();
            ctx.writeAndFlush(student);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.err.println("server error");
            ctx.close();
        }
    }

    static class ServerCodecHandler extends SimpleChannelInboundHandler<Long> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Long msg) {
            System.out.printf("[%s] [rcv: %s, from client %s]%n", DateUtil.format(), msg
                    , ctx.channel().remoteAddress());
            System.out.println("ServerCodecHandler");
            ctx.writeAndFlush(msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.err.println("server error");
            ctx.close();
        }
    }

    static class ClientCodecHandler extends SimpleChannelInboundHandler<Long> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Long msg) {
            System.out.printf("[%s] [rcv: %s, from server %s]%n", DateUtil.format(), msg
                    , ctx.channel().remoteAddress());
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            System.out.println("ClientCodecHandler");
            ctx.channel().writeAndFlush(123456L);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.err.println("server error");
            ctx.close();
        }
    }

    static class NettyClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8));
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf buf = (ByteBuf) msg;
            System.out.printf("[%s %s] [rcv: %s, from server %s]%n", DateUtil.format(),
                    Thread.currentThread().getName(),
                    buf.toString(CharsetUtil.UTF_8), ctx.channel().remoteAddress());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.err.println("server error");
            ctx.close();
        }
    }
}
