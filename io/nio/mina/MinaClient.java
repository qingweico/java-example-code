package io.nio.mina;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import util.constants.Constants;

import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * @author zqw
 * @date 2023/2/18
 */
@Slf4j
@Data
class MinaClient {
    private static final String HOST = Constants.LOOP_BACK;
    private static final int PORT = Constants.DEFAULT_COMMON_PORT;
    private static IoConnector connector = new NioSocketConnector();
    private static IoSession session;

    public static void main(String[] args) {
        // 添加过滤器 : 可序列化的对象
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        // 添加业务逻辑处理器类
        connector.setHandler(new MinaClientHandler());
        ConnectFuture future = connector.connect(new InetSocketAddress(HOST, PORT));
        future.awaitUninterruptibly();
        session = future.getSession();
        HashMap<String, String> sy = new HashMap<>(12);
        // some data;
        session.write(sy);
    }
}
