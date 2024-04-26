package design.observer;

import java.util.concurrent.Flow;

/**
 * @author zqw
 * @date 2022/3/2
 * java.util.Observer deprecated {@link Flow}
 * @see java.util.EventListener
 * @see javax.servlet.http.HttpSessionBindingListener
 */
public interface Observer {
    /**
     * update
     *
     * @param temp     温度
     * @param humidity 湿度
     * @param pressure 气压
     */
    void update(float temp, float humidity, float pressure);

    /**
     * cancel
     *
     * @param s Subject instance
     */
    void cancel(Subject s);
}
