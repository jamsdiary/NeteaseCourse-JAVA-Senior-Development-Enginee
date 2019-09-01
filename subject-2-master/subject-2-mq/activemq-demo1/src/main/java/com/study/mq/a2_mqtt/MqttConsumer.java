package com.study.mq.a2_mqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttConsumer {
    private static int qos = 2;
    private static String broker = "tcp://activemq.tony.com:1883";
    private static String userName = "admin";
    private static String passWord = "admin";

    private static MqttClient connect(String clientId) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(false);
        connOpts.setUserName(userName);
        connOpts.setPassword(passWord.toCharArray());
        connOpts.setConnectionTimeout(10);
        connOpts.setKeepAliveInterval(20);
        MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
        mqttClient.connect(connOpts);
        return mqttClient;

    }

    public static void sub(MqttClient mqttClient, String topic) throws MqttException {
        int[] Qos = {qos};
        String[] topics = {topic};
        mqttClient.subscribe(topics, Qos, new IMqttMessageListener[]{(s, mqttMessage) -> {
            System.out.println("收到新消息" + s + " > " + mqttMessage.toString());
        }});
    }

    private static void runsub(String clientId, String topic) throws MqttException {
        MqttClient mqttClient = connect(clientId);
        if (mqttClient != null) {
            sub(mqttClient, topic);
        }
    }

    public static void main(String[] args) throws MqttException {
        runsub("consumer-client-id-1", "x/y/z");
    }

}
