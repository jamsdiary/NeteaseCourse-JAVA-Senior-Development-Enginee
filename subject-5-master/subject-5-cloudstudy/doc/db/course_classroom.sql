/*
Navicat MySQL Data Transfer

Source Server         : 192.168.100.201
Source Server Version : 50726
Source Host           : 192.168.100.201:3306
Source Database       : cloud_classroom

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-06-25 17:02:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '课程名',
  `image` varchar(255) DEFAULT NULL COMMENT '课程图片',
  `price` double(10,2) DEFAULT NULL COMMENT '价格',
  `synopsis` varchar(255) DEFAULT NULL COMMENT '简介',
  `description` varchar(255) DEFAULT NULL COMMENT '详细介绍',
  `classification` varchar(255) DEFAULT NULL COMMENT '分类',
  `teacher_id` varchar(255) DEFAULT NULL COMMENT '教师ID',
  `status` int(11) DEFAULT NULL COMMENT '课程状态',
  `start_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '开课时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程表';

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1122323c0-7717-11e9-9379-0242ac110003', 'java-设计模设AAA', 'http://dong-nao-andy-bucket.oss-cn-shenzhen.aliyuncs.com/images/2019/06/14/15605196626321660.jpg', '2346.00', 'java-设计模设323', 'java-设计模设234', 'java-设计模设344', '5', null, '2019-06-14 17:53:30', '2019-06-14 08:56:30');
INSERT INTO `course` VALUES ('11258bc0-7717-11e9-9379-0242ac110003', 'java-设计模设BBB', 'http://dong-nao-andy-bucket.oss-cn-shenzhen.aliyuncs.com/images/2019/06/16/15606715578186159.jpg', '66.00', 'java-设计模设', 'java-设计模设', 'java-设计模设', '5', null, '2019-05-24 22:15:54', '2019-06-16 02:52:38');
INSERT INTO `course` VALUES ('24640845-8e81-11e9-8f13-0242ac110002', '课程123', 'http://dong-nao-andy-bucket.oss-cn-shenzhen.aliyuncs.com/images/2019/06/14/15605189199339056.jpg', '2346.00', '55', '88', '', '1', null, '2019-06-14 17:53:30', '2019-06-14 08:28:40');
INSERT INTO `course` VALUES ('64963c6a-8e7f-11e9-8f13-0242ac110002', '测试课程test', 'http://dong-nao-andy-bucket.oss-cn-shenzhen.aliyuncs.com/images/2019/06/14/15605243999622835.jpg', '999.00', '33', '', '', '5', null, '2019-06-14 16:39:01', '2019-06-14 10:00:00');
INSERT INTO `course` VALUES ('722262d4-8156-11e9-8f13-0242ac110002', 'rabbitmqEEEE', null, '1400.00', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件（亦称面向消息的中间件）。RabbitMQ服务器是用Erlang语言编写的，而集群和故障转移是构建在开放电信平台框架上的。所有主要的编程语言均有与代理接口通讯的客户端库', null, '27', null, null, '2019-05-28 09:39:49');
INSERT INTO `course` VALUES ('73b780cb-755d-11e9-9379-0242ac110003', 'java架构师课程CCC', 'http://dong-nao-andy-bucket.oss-cn-shenzhen.aliyuncs.com/images/2019/06/16/15606885328014220.jpg', '120.00', 'java架构师课程', 'java架构师课程', 'java架构师课程', '5', null, '2019-05-24 22:16:20', '2019-06-16 07:35:34');
INSERT INTO `course` VALUES ('73b780cb-755d-2344-9279-0242ac110003', 'java架构师课程EEEE', null, '120.00', 'java架构师课程', 'java架构师课程', 'java架构师课程', '5', null, '2019-05-24 22:16:23', '2019-05-23 22:47:02');
INSERT INTO `course` VALUES ('73b780cb-755d-2344-9379-0242ac110003', 'java架构师课程DDD', 'http://dong-nao-andy-bucket.oss-cn-shenzhen.aliyuncs.com/images/2019/06/16/15606719174308328.jpg', '120.00', 'java架构师课程', 'java架构师课程', 'java架构师课程', '5', null, '2019-05-24 22:16:27', '2019-06-16 02:58:38');
INSERT INTO `course` VALUES ('a277835f-7bd1-11e9-9379-0242ac110003', 'rabbitmqBBB', null, '1300.00', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件（亦称面向消息的中间件）。RabbitMQ服务器是用Erlang语言编写的，而集群和故障转移是构建在开放电信平台框架上的。所有主要的编程语言均有与代理接口通讯的客户端库', null, '26', null, '2019-05-24 22:13:18', '2019-05-22 22:06:32');
INSERT INTO `course` VALUES ('b3df8d20-7e2f-11e9-8f13-0242ac110002', 'rabbitmqCCCC', null, '1400.00', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件（亦称面向消息的中间件）。RabbitMQ服务器是用Erlang语言编写的，而集群和故障转移是构建在开放电信平台框架上的。所有主要的编程语言均有与代理接口通讯的客户端库', null, '27', null, null, '2019-05-24 22:24:56');
INSERT INTO `course` VALUES ('b6c47a71-860e-11e9-8f13-0242ac110002', 'rabbitmqFFFF', null, '1400.00', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件（亦称面向消息的中间件）。RabbitMQ服务器是用Erlang语言编写的，而集群和故障转移是构建在开放电信平台框架上的。所有主要的编程语言均有与代理接口通讯的客户端库', null, '5', null, null, '2019-06-03 09:48:55');
INSERT INTO `course` VALUES ('c0cad825-7bcf-11e9-9379-0242ac110003', 'rabbitmq', null, '1200.00', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件（亦称面向消息的中间件）。RabbitMQ服务器是用Erlang语言编写的，而集群和故障转移是构建在开放电信平台框架上的。所有主要的编程语言均有与代理接口通讯的客户端库', null, '26', null, '2019-05-24 22:13:22', '2019-05-22 21:53:03');
INSERT INTO `course` VALUES ('cec3b8d8-7bd1-11e9-9379-0242ac110003', 'rabbitmqBBB', null, '1300.00', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件（亦称面向消息的中间件）。RabbitMQ服务器是用Erlang语言编写的，而集群和故障转移是构建在开放电信平台框架上的。所有主要的编程语言均有与代理接口通讯的客户端库', null, '26', null, '2019-05-24 22:13:26', '2019-05-22 22:07:46');
INSERT INTO `course` VALUES ('dd2274d1-8150-11e9-8f13-0242ac110002', 'rabbitmqDDDD', null, '1402.00', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件', 'RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件（亦称面向消息的中间件）。RabbitMQ服务器是用Erlang语言编写的，而集群和故障转移是构建在开放电信平台框架上的。所有主要的编程语言均有与代理接口通讯的客户端库', null, '29', null, null, '2019-05-28 08:59:52');

-- ----------------------------
-- Table structure for course_order
-- ----------------------------
DROP TABLE IF EXISTS `course_order`;
CREATE TABLE `course_order` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `order_time` datetime DEFAULT NULL COMMENT '订购时间',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `total` double DEFAULT NULL COMMENT '共计',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `course_id` varchar(50) DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程订单';

-- ----------------------------
-- Records of course_order
-- ----------------------------
INSERT INTO `course_order` VALUES ('129c051e-8157-11e9-8f13-0242ac110002', '2019-05-28 09:44:19', null, '1', '1400', '5', '722262d4-8156-11e9-8f13-0242ac110002');
INSERT INTO `course_order` VALUES ('1418d10b-7e30-11e9-8f13-0242ac110002', '2019-05-24 22:27:35', null, '1', '1400', '5', 'b3df8d20-7e2f-11e9-8f13-0242ac110002');
INSERT INTO `course_order` VALUES ('1963916b-7bd3-11e9-9379-0242ac110003', '2019-05-21 22:17:00', null, '0', '1300', '5', 'a277835f-7bd1-11e9-9379-0242ac110003');
INSERT INTO `course_order` VALUES ('25d798f1-7bc1-11e9-9379-0242ac110003', '2019-05-21 20:08:29', null, '1', '66', '5', '1122323c0-7717-11e9-9379-0242ac110003');
INSERT INTO `course_order` VALUES ('965cdaca-7af3-11e9-9379-0242ac110003', '2019-05-20 19:37:46', null, '1', '66', '5', '73b780cb-755d-2344-9279-0242ac110003');
INSERT INTO `course_order` VALUES ('a0be89eb-7af4-11e9-9379-0242ac110003', '2019-05-20 19:45:15', null, '0', '66', '5', '1122323c0-7717-11e9-9379-0242ac110003');
INSERT INTO `course_order` VALUES ('c33a0633-7af4-11e9-9379-0242ac110003', '2019-05-20 19:46:03', null, '0', '66', '5', '1122323c0-7717-11e9-9379-0242ac110003');
INSERT INTO `course_order` VALUES ('c36db830-7af5-11e9-9379-0242ac110003', '2019-05-20 19:52:54', null, '0', '66', '5', '1122323c0-7717-11e9-9379-0242ac110003');
INSERT INTO `course_order` VALUES ('cc5e8717-7ae4-11e9-9379-0242ac110003', '2019-05-20 17:51:55', null, '1', '66', '5', '73b780cb-755d-2344-9379-0242ac110003');
INSERT INTO `course_order` VALUES ('d7aa96db-7af9-11e9-9379-0242ac110003', '2019-05-20 20:21:44', null, '0', '66', '5', '1122323c0-7717-11e9-9379-0242ac110003');
INSERT INTO `course_order` VALUES ('e186d09c-860c-11e9-8f13-0242ac110002', '2019-06-03 09:35:48', null, '1', '66', '5', '1122323c0-7717-11e9-9379-0242ac110003');
INSERT INTO `course_order` VALUES ('e81193a6-7b07-11e9-9379-0242ac110003', '2019-05-20 22:02:24', null, '0', '66', '5', '1122323c0-7717-11e9-9379-0242ac110003');

-- ----------------------------
-- Table structure for user_study
-- ----------------------------
DROP TABLE IF EXISTS `user_study`;
CREATE TABLE `user_study` (
  `id` varchar(50) NOT NULL COMMENT '用户学习ID',
  `course_id` varchar(50) DEFAULT NULL COMMENT '课程id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `start_time` datetime DEFAULT NULL COMMENT '学员正式开始时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_study
-- ----------------------------
INSERT INTO `user_study` VALUES ('3c0fe04e-7bdc-11e9-9379-0242ac110003', '1122323c0-7717-11e9-9379-0242ac110003', '6', '2019-05-21 23:22:24');
INSERT INTO `user_study` VALUES ('65e96ff8-8157-11e9-8f13-0242ac110002', '722262d4-8156-11e9-8f13-0242ac110002', '6', '2019-05-28 09:46:38');
INSERT INTO `user_study` VALUES ('6c7c9f3b-7baf-11e9-9379-0242ac110003', '73b780cb-755d-2344-9279-0242ac110003', '6', '2019-05-21 18:02:26');
INSERT INTO `user_study` VALUES ('8821ad55-7e30-11e9-8f13-0242ac110002', 'b3df8d20-7e2f-11e9-8f13-0242ac110002', '27', '2019-05-24 22:30:52');
INSERT INTO `user_study` VALUES ('8d917727-860e-11e9-8f13-0242ac110002', '1122323c0-7717-11e9-9379-0242ac110003', '5', '2019-06-03 09:47:46');
INSERT INTO `user_study` VALUES ('a4ce07f0-7aff-11e9-9379-0242ac110003', '73b780cb-755d-2344-9279-0242ac110003', '6', '2019-05-20 21:03:16');
INSERT INTO `user_study` VALUES ('ddb30610-7bbd-11e9-9379-0242ac110003', '73b780cb-755d-2344-9279-0242ac110003', '6', '2019-05-21 19:45:48');
