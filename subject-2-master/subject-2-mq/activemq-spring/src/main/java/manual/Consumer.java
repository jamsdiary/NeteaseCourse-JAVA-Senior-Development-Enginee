package manual;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@SpringBootApplication
public class Consumer {

    @JmsListener(destination = "queue1", containerFactory = "jmsListenerContainerFactoryQueue")
    public void receiveQueue(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            System.out.println("收到文本消息：" + ((TextMessage) message).getText());
        } else if (message instanceof ActiveMQMapMessage) {
            System.out.println("收到Map消息：" + ((ActiveMQMapMessage) message).getContentMap());
        } else {
            System.out.println(message);
        }
    }

    @JmsListener(destination = "topic1", containerFactory = "jmsListenerContainerFactoryTopic")
    public void receiveTopic(String message) {
        System.out.println("收到订阅消息：" + message);
    }

    public static void main(String[] args) {
        SpringApplication.run(Consumer.class, args);
    }
}
