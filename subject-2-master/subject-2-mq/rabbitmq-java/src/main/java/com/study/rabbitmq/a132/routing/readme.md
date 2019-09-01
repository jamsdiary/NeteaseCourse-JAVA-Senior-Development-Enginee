# 示例说明

本示例是RabbitMQ 的topic示例，启动Consumer 类会开启三个消费者。

第一个消费者接收topic_test交换器上所有消息。

第二个消费者接收topic_test交换器上routingKey以`a.`开头的所有消息。

第三个消费者接收topic_test交换器上routingKey以`b.`开头的所有消息。

Producer 会发送三条不同的消息，三条消息的routingKey分别为：`a.c1`、`a.c2`、`b.c1`。
