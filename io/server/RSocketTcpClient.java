package io.server;

import io.netty.channel.ChannelOption;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.ByteBufPayload;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;
import reactor.util.retry.Retry;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;

import java.time.Duration;

/**
 * RSocket 是一种基于 Reactive Streams 规范的二进制协议, 用于分布式应用程序之间的通信
 * {@link TcpClientTransport} 是 RSocket 提供的基于 TCP 的客户端传输实现
 *
 * @author zqw
 * @date 2025/8/2
 */
class RSocketTcpClient {
    public static void main(String[] args) throws InterruptedException {
        TcpClientTransport transport = TcpClientTransport.create(
                TcpClient.create()
                        .host(Constants.LOOP_BACK)
                        .port(Constants.DEFAULT_COMMON_PORT)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        );
        Mono<RSocket> socketMono = RSocketConnector.create()
                .reconnect(Retry.fixedDelay(3, Duration.ofSeconds(1)))
                .connect(transport);

        socketMono.subscribe(socket -> {
            socket.requestResponse(ByteBufPayload.create("Hello", "Metadata"))
                    .subscribe(payload -> Print.grace("Received response", payload.getDataUtf8()));
        });
    }
}
