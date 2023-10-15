package design.behaviour.command;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zqw
 * @date 2023/10/15
 */
@Slf4j
class Receiver {
    public void action() {
        log.info("接受者执行命令");
    }
}
