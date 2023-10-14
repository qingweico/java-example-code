package frame.mq.active;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zqw
 * @date 2023/10/12
 */
public class JmsProducerOnTopic {
    private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    private static final String TOPIC_NAME = "topic";
    private static final Integer MESSAGE_NUMBER = 6;

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer = session.createProducer(topic);
        for (int i = 0; i < MESSAGE_NUMBER; i++) {
            TextMessage textMessage = session.createTextMessage("topic message " + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
    }
}
