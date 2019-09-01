# 示例说明

本示例是RabbitMQ 的Publish/Subscribe示例，使用`fanout`型交换器实现发布订阅模式。

启动Consumer 类会开启两个消费者，Producer 类运行后，两个消费者都能接收到消息