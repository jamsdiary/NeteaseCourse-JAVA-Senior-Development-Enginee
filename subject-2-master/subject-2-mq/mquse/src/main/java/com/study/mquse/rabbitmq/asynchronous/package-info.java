/**
 * @Auther: allen
 * @Date: 2019/2/27 10:50
 */
package com.study.mquse.rabbitmq.asynchronous;

/*
 * 异步调用：模拟用户注册业务
 *
 * 扩展：
 * 1.message acknowledgment（消息确认）：如果消费者在没有处理完一个消息就挂掉了，则这个消息就会遗失，所以必须在消费者代码中通知给RabbitMQ。默认是手动通知的，这样可以确保消息不会遗失。如果没有接收到确认，RabbitMQ会指派另外一个消费者处理任务。 channel.basicAck(envelope.getDeliveryTag(),false);和channel.basicConsume(QUEUE_NAME, false, consumer);都是必须的，否则会造成RabbitMQ无法释放已经处理过的消息和导致内存溢出。
 * 2.Message durability（消息持久化）：可修改channel.queueDeclare("task_queue", durable, false, false, null);及channel.basicPublish("", "task_queue", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());来使消息能持久化。需要注意的是，如果一个queue已经定义为非持久化，则不能再改为持久化，会出错，此时必须定义一个新的queue(换个名字）
 * 3.Fair dispatch（分配策略）：默认情况下，RabbitMQ会平均的分配消息给消费者，它不会管这个消费者目前手上有多少未完成的任务，这可能会造成有的消费者很忙，有的消费者很闲。通过channel.basicQos(1);可以指定消费者每次只接收一条消息，只有当这条消息已经处理完毕，并且确认以后，才接收下一条的消息。
 */