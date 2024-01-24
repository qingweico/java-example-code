package frame.mq.active;

import org.apache.activemq.ActiveMQConnectionFactory;
import util.Print;

import javax.jms.*;

/**
 * @author zqw
 * @date 2023/10/12
 */
public class JmsConsumerOnTopic {
    private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    private static final String TOPIC_NAME = "topic";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer messageConsumer = session.createConsumer(topic);
        messageConsumer.setMessageListener(message -> {
            if (message instanceof TextMessage textMessage) {
                try {
                    System.out.println("topic MessageListener " + textMessage.getText());
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
