package design.observer;

/**
 * @author zqw
 * @date 2022/3/2
 * @see java.util.Observer
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
}
