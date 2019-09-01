package com.study.mq.a5_vm;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.URI;

// 嵌入式的，或者自身系统中实现队列
// http://activemq.apache.org/run-broker.html
public class VMDemo {
    public static void main(String[] args) throws Exception {
        // 内嵌activemq服务
        new Thread(() -> {
            try {
                BrokerService broker = new BrokerService();
                broker.setBrokerName("vm-activemq"); // 设置名称
                broker.addConnector("tcp://localhost:61616"); // 增加connectorbroker.addConnector("vm://vm-activemq");
                broker.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // http://activemq.apache.org/broker-configuration-uri.html
        // BrokerFactory.createBroker(new URI("xbean:com/test/activemq-配置文件解析.xml"));
//        BrokerService broker = BrokerFactory.createBroker(new URI("broker:tcp://localhost:61616?"));
//        broker.start();

    }
}
