/**
 * @Auther: allen
 * @Date: 2019/2/19 11:06
 */
package com.study.kafka.partition;

/*
Kafka分区机制介绍与示例

Kafka中可以将Topic从物理上划分成一个或多个分区（Partition），
每个分区在物理上对应一个文件夹，以”topicName_partitionIndex”的命名方式命名，
该文件夹下存储这个分区的所有消息(.log)和索引文件(.index)，这使得Kafka的吞吐率可以水平扩展。

生产者在生产数据的时候，可以为每条消息指定Key，这样消息被发送到broker时，会根据分区规则选择被存储到哪一个分区中，
如果分区规则设置的合理，那么所有的消息将会被均匀的分布到不同的分区中，这样就实现了负载均衡和水平扩展。
另外，在消费者端，同一个消费组可以多线程并发的从多个分区中同时消费数据。

*******************************************************
* kafka---partitioner及自定义
*******************************************************
如果消息的 key 为 null，此时 producer 会使用默认的 partitioner 分区器将消息随机分布到 topic 的可用 partition 中。
如果 key 不为 null，并且使用了默认的分区器，kafka 会使用自己的 hash 算法对 key 取 hash 值，
使用 hash 值与 partition 数量取模，从而确定发送到哪个分区。
注意：此时 key 相同的消息会发送到相同的分区(只要 partition 的数量不变化)。

=== 默认的分区器的实现
1、DefaultPartitioner实现了Partitioner接口

2、分区算法的实现在这个方法中：
public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster){…………}

3、如果我们需要实现自己的分区器，那么可以有2种方法
    (1)新建一个包路径和DefaultPartitioner所在的路径一致，然后更改
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster){…………}
    方法体的内容，更改为我们自己的算法即可。
    (2)新建一个类，实现Partitioner接口
 */