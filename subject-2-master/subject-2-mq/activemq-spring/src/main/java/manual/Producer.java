package manual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

@SpringBootApplication
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplatePublish;

    @Autowired
    private JmsTemplate jmsTemplateQueue;

    @PostConstruct
    public void send() {
        // 队列模式发送
        jmsTemplatePublish.convertAndSend("topic1", "Hello Spring topic 1");

        // 发布订阅模式发送
        jmsTemplateQueue.send("queue1", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("msg", "Hello Spring xxxx");
                return message;
            }
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(Producer.class, args);
    }
}
