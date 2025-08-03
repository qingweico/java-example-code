package frame.mq.active;

import org.apache.activemq.ActiveMQConnectionFactory;
import cn.qingweico.io.Print;

import javax.jms.*;
import java.io.IOException;

/**
 * @author zqw
 * @date 2023/10/12
 *                                                               --> (Create) MessageConsumer --> (Receive from) Destination
 * ConnectionFactory ->  (Create)Connection --> (Create) Session --> (Create) Message
 *                                                               --> (Create) MessageProducer --> (Send To) Destination
 * JMS (Java Message Service) JSR 914
 * JCP (Java Community Process)
 * JSR (Java Specification Requests) Java规范提案 : 向 JCP提出新增一个标准化技术规范的正式请求
 */
public class JmsConsumer {
    private static final String ACTIVEMQ_URL = "tcp://106.12.136.221:61616";
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(destination);
        // 同步阻塞
        while (true) {
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            if (textMessage != null) {
                Print.grace("消费者接受消息", textMessage.getText());
            } else {
                break;
            }
        }
        // 异步非阻塞
        messageConsumer.setMessageListener(message -> {
            if (message instanceof TextMessage textMessage) {
                try {
                    Print.grace("queue MessageListener", textMessage.getText());
                } catch (JMSException e) {
                    Print.err(e.getMessage());
                }
            }
        });
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
