CREATE TABLE `tb_distributed_message` (
  `unique_id` varchar(255) DEFAULT NULL COMMENT '唯一ID',
  `msg_content` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `msg_status` int(11) DEFAULT '0' COMMENT '是否发送到MQ：0:未发送；1:已发送',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '消息创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单-分布式事务-本地消息表';


CREATE TABLE `table_order` (
  `order_id` varchar(255) NOT NULL COMMENT '订单号',
  `user_id` varchar(255) NOT NULL COMMENT '用户编号',
  `order_content` varchar(255) NOT NULL COMMENT '订单内容(买了哪些东西，送货地址)',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';



CREATE TABLE `table_dispatch` (
  `dispatch_seq` varchar(255) NOT NULL COMMENT '调度流水号',
  `order_id` varchar(255) NOT NULL COMMENT '订单编号',
  `dispatch_status` varchar(255) DEFAULT NULL COMMENT '调度状态',
  `dispatch_content` varchar(255) DEFAULT NULL COMMENT '调度内容(送餐员，路线)',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调度信息表';

