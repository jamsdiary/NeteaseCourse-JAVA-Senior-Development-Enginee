package com.study.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

/**
 * @Auther: allen
 * @Date: 2019/2/17 16:05
 */
public class ProducerNew {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public ProducerNew(String topic, String[] args) {
        Properties props = new Properties();
        // Kafka服务端的主机名和端口号
        props.put("bootstrap.servers", "192.168.100.246:9092");
        // 等待所有副本节点的应答
        props.put("acks", "all");
        // 一批消息处理大小
        props.put("batch.size", 16384);//16M
        // 请求延时
        props.put("linger.ms", 10);
        // 发送缓存区内存大小
        props.put("buffer.memory", 33554432);//32M

        // 使用自定义分区器，如果自定义则适用默认的 DefaultPartitioner
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.study.kafka.partition.MySamplePartitioner");

        // key和value的序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    public void producerMsg() throws InterruptedException {
        String data = "Apache Storm is a free and open source distributed realtime computation system Storm makes it easy to reliably process unbounded streams of data doing for realtime processing what Hadoop did for batch processing. Storm is simple, can be used with any programming language, and is a lot of fun to use!\n" +
                "Storm has many use cases: realtime analytics, online machine learning, continuous computation, distributed RPC, ETL, and more. Storm is fast: a benchmark clocked it at over a million tuples processed per second per node. It is scalable, fault-tolerant, guarantees your data will be processed, and is easy to set up and operate.\n" +
                "Storm integrates with the queueing and database technologies you already use. A Storm topology consumes streams of data and processes those streams in arbitrarily complex ways, repartitioning the streams between each stage of the computation however needed. Read more in the tutorial.";
        data = data.replaceAll("[\\pP‘’“”]", "");
        String[] words = data.split(" ");
        Random _rand = new Random();

        Random rnd = new Random();
        int events = 10;
        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = System.currentTimeMillis();
            int lastIPnum = rnd.nextInt(255);
            String ip = "192.168.100." + lastIPnum;
            String msg = words[_rand.nextInt(words.length)];
            try {
                producer.send(new ProducerRecord<>(topic, ip, msg));
                System.out.println("Sent message: (" + ip + ", " + msg + ")");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ProducerNew producer = new ProducerNew("test", args);
        producer.producerMsg();
        Thread.sleep(20);
    }
}
