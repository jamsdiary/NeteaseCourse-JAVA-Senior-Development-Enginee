package com.study.kafka.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定义Kafka分区器
 *
 * @Auther: allen
 * @Date: 2019/2/22 14:33
 */
public class MySamplePartitioner implements Partitioner {
    private final AtomicInteger counter = new AtomicInteger(new Random().nextInt());
    private Random random = new Random();

    //我的分区器定义
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitioners = cluster.partitionsForTopic(topic);
        int numPartitions = partitioners.size();

        /**
         * 由于我们按key分区，在这里我们规定：key值不允许为null。
         * 在实际项目中，key为null的消息*，可以发送到同一个分区,或者随机分区。
         */
        int res = 1;
        if (keyBytes == null) {
            System.out.println("value is null");
            res = random.nextInt(numPartitions);
        } else {
//            System.out.println("value is " + value + "\n hashcode is " + value.hashCode());
            res = Math.abs(key.hashCode()) % numPartitions;
        }
        System.out.println("data partitions is " + res);
        return res;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
