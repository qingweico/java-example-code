package io.nio.mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import util.constants.Constants;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 *
 * Apache MINA(Multipurpose Infrastructure for Network Applications)
 * MINA可以用来快速开发高性能, 高扩展性的网络通信应用; Mina 提供了事件驱动, 异步(Mina 的异步IO 默认使用的是JAVA NIO 作为底层支持)操作的编程模型
 *
 * @author zqw
 * @date 2023/2/18
 */
@Slf4j
class MinaServer {
    private static final int PORT = Constants.DEFAULT_COMMON_PORT;
    private static final int BUFFER_SIZE = Constants.KB * 12;
    public static void main(String[] args) {
        IoAcceptor ia;
        try {
            // 创建一个非堵塞的server端Socket
            ia = new NioSocketAcceptor();
            // 创建协议编码解码过滤器ProtocolCodecFilter
            ProtocolCodecFilter pf = new ProtocolCodecFilter(new TextLineCodecFactory(StandardCharsets.UTF_8, LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue()));
            // 设置端口
            InetSocketAddress pt = new InetSocketAddress(PORT);
            // 设置过滤器(使用Mina提供的文本换行符编解码器)
            ia.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
            // 设置读取数据的缓存区大小
            ia.getSessionConfig().setReadBufferSize(BUFFER_SIZE);
            // 读写通道10秒内无操作进入空闲状态
            ia.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            // 绑定逻辑处理器
            ia.setHandler(new MinaServerHandler());
            // 绑定端口
            ia.bind(pt);
            log.info("服务端启动成功..., 端口号为 : {}", PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
