package mqtt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.function.Consumer;

@SpringBootApplication
public class MqttApplication {
    private static final Log LOGGER = LogFactory.getLog(MqttApplication.class);

    public static void main(final String... args) {
        // https://spring.io/projects/spring-integration
        // https://github.com/spring-projects/spring-integration-samples/
        SpringApplication.run(MqttApplication.class, args);
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://activemq.tony.com:1883"});
        options.setUserName("admin");
        options.setPassword("admin".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    // publisher
    @Bean
    public IntegrationFlow mqttOutFlow() {
        // IntegrationFlows.from 数据来源，可以设定为每秒去取数据
        return IntegrationFlows.from(() -> "hello mqtt", new Consumer<SourcePollingChannelAdapterSpec>() {
            @Override
            public void accept(SourcePollingChannelAdapterSpec sourcePollingChannelAdapterSpec) {
                sourcePollingChannelAdapterSpec.poller(Pollers.fixedDelay(1000));
            }
        })
                .transform(p -> p + " sent to MQTT")
                .handle(mqttOutbound())
                .get();
    }

    @Bean
    public MessageHandler mqttOutbound() {
        // 创建handller
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("client-si-producer-0", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("x/y/z");
        return messageHandler;
    }

    // consumer
    @Bean
    public IntegrationFlow mqttInFlow() {
        return IntegrationFlows.from(mqttInbound())
                .transform(p -> p + ", received from MQTT")
                .handle(printHandler())
                .get();
    }

    private MessageHandler printHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println(message.getPayload().toString());
            }
        };
    }

    @Bean
    public MessageProducerSupport mqttInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("client-si-consumer-1",
                mqttClientFactory(), "x/y/z");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        return adapter;
    }
}