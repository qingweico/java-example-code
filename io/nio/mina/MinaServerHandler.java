package io.nio.mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;
import java.util.Date;

/**
 * @author zqw
 * @date 2023/2/18
 */
@Slf4j
class MinaServerHandler extends IoHandlerAdapter {
    @Override
    public void sessionCreated(IoSession iosession) {
        InetSocketAddress sa = (InetSocketAddress) iosession.getRemoteAddress();
        String address = sa.getAddress().getHostAddress();
        log.info("服务端与客户端创建连接..., ip : {}", address);
    }


    @Override
    public void sessionOpened(IoSession iosession) {
        log.info("服务端与客户端连接打开...");
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        String msg = message.toString();
        log.info("服务端收到的数据为 : {}", msg);
        // 服务端断开的条件
        if ("bye".equals(msg)) {
            session.closeNow();
        }
        Date date = new Date();
        // 返回给服务端数据
        session.write(date);
    }

    @Override
    public void messageSent(IoSession session, Object message) {
        log.info("服务端发送信息成功...");
    }

    @Override
    public void sessionClosed(IoSession session) {
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        log.info("服务端进入空闲状态...");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        log.error("服务端发送异常...", cause);
    }
}
