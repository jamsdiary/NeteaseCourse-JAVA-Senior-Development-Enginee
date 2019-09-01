package com.study.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @Auther: allen
 * @Date: 2019/2/17 16:06
 */
public class ConsumerNew {
    private final KafkaConsumer<Integer, String> consumer;
    private final String topic;

    public ConsumerNew(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.100.246:9092");
        // 记住 consumer 是需要依赖zk的
        props.put("zookeeper.connect", "192.168.100.246:2181");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-test");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // latest,earliest,none latest:读取最新的，earliest:从头开始
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
    }

    public void consumerMsg(){
        try {
            consumer.subscribe(Collections.singletonList(this.topic));
            //System.out.println(consumer.listTopics());
            while(true){
                ConsumerRecords<Integer, String> records = consumer.poll(2000);
                for (ConsumerRecord<Integer, String> record : records) {
                    System.out.println("*******************Received message: (" + record.key() + ", " + record.value() + ") at partition "+record.partition()+" offset " + record.offset());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsumerNew Consumer = new ConsumerNew("test");
        Consumer.consumerMsg();
    }

}