# 示例说明



此示例的生产者和消费者都只是通过队列来发送和接收消息，并未用到Exchange，那是因为手动通过channel.queueDeclare创建队列时，后台会自动将这个队列绑定到一个名称为空的 Direct Exchange 上，绑定 RoutingKey 与队列名称相同。

所以在调用channel.basicPublish方法时，exchange传的是空字符串，而routingKey传的是队列名。
有了这个默认的交换机和绑定，使我们只关心队列这一层即可，这个比较适合做一些简单的应用。

在RabbitMQ的管理界面的Exchange中可以看到这个默认交换器：(AMQP default)