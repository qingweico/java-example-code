package frame.mq.active;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zqw
 * @date 2023/10/12
 */
public class JmsProducer {
    private static final String ACTIVEMQ_URL = "tcp://106.12.136.221:61616";
    private static final String QUEUE_NAME = "queue";
    private static final Integer MESSAGE_NUMBER = 6;

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        for (int i = 0; i < MESSAGE_NUMBER; i++) {
            TextMessage textMessage = session.createTextMessage("queue message " + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
    }
}
