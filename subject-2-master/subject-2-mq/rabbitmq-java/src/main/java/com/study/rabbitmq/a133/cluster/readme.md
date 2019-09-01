# **示例说明**


此示例演示Virtual Hosts和权限的使用，及客户端链接集群的用法。

## **Virtual Hosts**

每一个 RabbitMQ 服务器都能创建虚拟的消息服务器，我们称之为虚拟主机 (virtual host) ,简称为 vhost。

每一个 vhost 本质上是一个独立的小型 RabbitMQ 服务器，拥有自己独立的队列、交换器及绑定关系等，井且它拥有自己独立的权限。

vhost 就像是虚拟机与物理服务器一样，它们在各个实例间提供逻辑上的分离，为不同程序安全保密地运行数据，它既能将同一个RabbitMQ 中的众多客户区分开，又可以避免队列和交换器等命名冲突。

vhost 之间是绝对隔离的，无法将 vhostl 中的交换器与 vhost2 中的队列进行绑定，这样既保证了安全性，又可以确保可移植性。

如果在使用 RabbitMQ 达到一定规模的时候，建议用户对业务功能、场景进行归类区分，并为之分配独立的 vhost。


### **Virtual Hosts 的功能说明**
vhost可以限制最大连接数和最大队列数，并且可以设置vhost下的用户资源权限和Topic权限，具体权限见下方说明。

- 在 `Admin -> Limits` 页面可以设置vhost的最大连接数和最大队列数，达到限制后，继续创建，将会报错。
- 用户资源权限是指RabbitMQ 用户在客户端执行AMQP操作命令时，拥有对资源的操作和使用权限。权限分为三个部分：`configure、write、read`，见下方表格说明。参考：http://www.rabbitmq.com/access-control.html#permissions

    | AMQP 0-9-1 Operation |                                                | configure | write                  | read              |
    | :------------------- | :--------------------------------------------- | :-------- | :--------------------- | :---------------- |
    | exchange.declare     | (passive=false)                                | exchange  |
    | exchange.declare     | (passive=true)                                 |
    | exchange.declare     | (with [AE](http://www.rabbitmq.com/ae.html))   | exchange  | exchange (AE)          | exchange          |
    | exchange.delete      |                                                | exchange  |
    | queue.declare        | (passive=false)                                | queue     |
    | queue.declare        | (passive=true)                                 |
    | queue.declare        | (with [DLX](http://www.rabbitmq.com/dlx.html)) | queue     | exchange (DLX)         | queue             |
    | queue.delete         |                                                | queue     |
    | exchange.bind        |                                                |           | exchange (destination) | exchange (source) |
    | exchange.unbind      |                                                |           | exchange (destination) | exchange (source) |
    | queue.bind           |                                                |           | queue                  | exchange          |
    | queue.unbind         |                                                |           | queue                  | exchange          |
    | basic.publish        |                                                |           | exchange               |
    | basic.get            |                                                |           |                        | queue             |
    | basic.consume        |                                                |           |                        | queue             |
    | queue.purge          |                                                |           |                        | queue             |

    **举例说明：**
    - 比如创建队列时，会调用`queue.declare`方法，此时会使用到`configure`权限，会校验队列名是否与`configure`的表达式匹配。
    - 比如队列绑定交换器时，会调用`queue.bind`方法，此时会用到`write` 和 `read`权限，会检验队列名是否与`write`的表达式匹配，交换器名是否与`read`的表达式匹配。

- Topic权限，参考：http://www.rabbitmq.com/access-control.html#topic-authorisation
  - Topic权限是RabbitMQ 针对STOMP和MQTT等协议实现的一种权限。由于这类协议都是基于Topic消费的，而AMQP是基于Queue消费，所以AMQP的标准资源权限不适合用在这类协议中，而Topic权限也不适用于AMQP协议。所以，我们一般不会去使用它，只用在使用了MQTT这类的协议时才可能会用到。
  
#### **vhost使用示例**
1. 使用管理员用户登录Web管理界面。
2. 在 `Admin -> Virtual Hosts` 页面添加一个名为 `v1` 的Virtual Hosts。
   - 此时还需要为此vhost分配用户，添加一个新用户
3. 在 `Admin -> Users` 页面添加一个名为 `order-user` 的用户，并设置为 `management` 角色。
4. 从 `Admin` 进入 `order-user` 的用户设置界面,在 `Permissions` 中，为用户分配vhost为/v1，并为每种权限设置需要匹配的目标名称的正则表达式。

    |           字段名 | 值     | 说明                                                                                                                         |
    | ---------------: | :----- | :--------------------------------------------------------------------------------------------------------------------------- |
    |     Virtual Host | /v1    | 指定用户的vhost，以下权限都只限于 `/v1` vhost中                                                                              |
    | Configure regexp | eq-.*  | 只能操作名称以eq-开头的exchange或queue；为空则不能操作任何exchange和queue                                                    |
    |     Write regexp | .*     | 能够发送消息到任意名称的exchange，并且能绑定到任意名称的队列和任意名称的目标交换器（指交换器绑定到交换器），为空表示没有权限 |
    |      Read regexp | ^test$ | 只能消费名为test队列上的消息，并且只能绑定到名为test的交换器                                                                 |
5. 执行示例代码`VirtualHostsDemo`。


## **集群连接恢复**

- **参考**：https://www.rabbitmq.com/api-guide.html#connection-recovery

- 通过`factory.setAutomaticRecoveryEnabled(true);`可以设置连接自动恢复的开关，默认已开启

- 通过`factory.setNetworkRecoveryInterval(10000);`可以设置间隔多长时间尝试恢复一次，默认是5秒：`com.rabbitmq.client.ConnectionFactory.DEFAULT_NETWORK_RECOVERY_INTERVAL`

- 什么时候会触发连接恢复？https://www.rabbitmq.com/api-guide.html#recovery-triggers

  - 如果启用了自动连接恢复，将由以下事件触发：
    - 连接的I/O循环中抛出IOExceiption
    - 读取Socket套接字超时
    - 检测不到服务器心跳
    - 在连接的I/O循环中引发任何其他异常
  
  - 如果客户端第一次连接失败，不会自动恢复连接。需要我们自己负责重试连接、记录失败的尝试、实现重试次数的限制等等。
        ```java
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接配置
    
        try {
            Connection conn = factory.newConnection();
        } catch (java.net.ConnectException e) {
            Thread.sleep(5000);
            // 重新连接
        }
        ```
    
  - 如果程序中调用了`Connection.Close`，也不会自动恢复连接。
  - 如果是`Channel-level`的异常，也不会自动恢复连接，因为这些异常通常是应用程序中存在语义问题(例如试图从不存在的队列消费)。

- 在Connection和Channel上，可以设置重新连接的监听器，开始重连和重连成功时，会触发监听器。添加和移除监听，需要将Connection或Channel强制转换成Recoverable接口。
  ```java
  ((Recoverable) connection).addRecoveryListener()
  ((Recoverable) connection).removeRecoveryListener()
  ```
  
## **重连测试方式**
 - 测试前，先按[集群部署方式](http://code.study.com/MQ/rabbitmq/rabbitmq#%E5%8D%95%E6%9C%BA%E5%A4%9A%E8%8A%82%E7%82%B9%E9%83%A8%E7%BD%B2)搭建好集群。
 - 开启集群节点后，启动`Consumer`和`Producer`。
 - 使用 `rabbitmqctl -n [node_name] stop_app` 命令关闭一个节点，例如：`rabbitmqctl -n rabbit2 stop_app`;
 - 查看Consumer和Producer控制台是否有重连的信息。
 - 使用 `rabbitmqctl -n [node_name] start_app` 可开启关闭的节点。 
 

## **镜像队列测试**

- 测试方式
  - 生产者连接10.10.1.41:5672发送消息后，停止rabbit1节点

    | 队列持久化 | 消息持久化 | 镜像队列 | 结果                                                                         |
    | :--------: | :--------: | :------: | :--------------------------------------------------------------------------- |
    |     否     |     否     |    否    | rabbit1重启后，队列和消息丢失                                                |
    |     是     |     否     |    否    | rabbit1重启后，队列存在但消息丢失；rabbit1不启动消费者连接其它节点也无法启动 |
    |     是     |     是     |    否    | rabbit1重启后，队列存在，消息丢失；rabbit1不启动消费者连接其它节点也无法启动 |
    |     否     |     否     |    是    | 对列和消息都还存在，并且消费者能够正常消费                                   |
    |     是     |     否     |    是    | 同上                                                                         |
    |     是     |     是     |    是    | 同上                                                                         |

