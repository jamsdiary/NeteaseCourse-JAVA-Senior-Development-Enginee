package com.study.kafka.jmx;

import com.google.common.collect.Lists;

/**
 * @Auther: allen
 * @Date: 2019/2/20 17:07
 */
public class KafkaMetricMain {

    public static void main(String[] args) throws Exception {
        KafkaJmxConnection jmxConn = new KafkaJmxConnection("192.168.100.249:9999");
        jmxConn.init();

        while(true) {
            String topicName = "TPC_WALLET_UNFREEZE_DEDUCT_COMPENSATE";
            // 与topic无关的metric
            Object o1 = jmxConn.getValue(
                    "kafka.server:type=ReplicaManager,name=PartitionCount",
                    Lists.newArrayList("Value"));
            System.out.println(o1);
            // 与topic有关的metric
            Object o2 = jmxConn.getValue(topicName,
                    "kafka.server:type=BrokerTopicMetrics,name=BytesInPerSec",
                    Lists.newArrayList("Count", "OneMinuteRate", "FiveMinuteRate"));
            System.out.println(o2);
            Thread.sleep(5000);
        }
    }
}