package io.nio.mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * @author zqw
 * @date 2023/2/18
 */
@Slf4j
class MinaClientHandler extends IoHandlerAdapter {
    public void messageReceived(IoSession session, Object message) {
        String msg = message.toString();
        log.info("client rev data : {}", msg);

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        log.error("error occur..., {}", cause.getMessage());
    }
}
