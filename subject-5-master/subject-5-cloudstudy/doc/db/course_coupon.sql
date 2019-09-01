SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_coupon
-- ----------------------------
DROP TABLE IF EXISTS `tb_coupon`;
CREATE TABLE `tb_coupon` (
  `coupon_id` varchar(64) DEFAULT NULL COMMENT '优惠券编号',
  `coupon_type` varchar(255) DEFAULT NULL COMMENT '1-无门槛优惠券 2-满减优惠券 3-新人优惠 4-...',
  `coupon_content` varchar(255) DEFAULT NULL COMMENT '优惠券的内容（无门槛立减，满多少减多少...）',
  `start_time` datetime DEFAULT NULL COMMENT '适用时限开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '适用时限结束时间',
  `coupon_sum` int(11) DEFAULT NULL COMMENT '发放数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- ----------------------------
-- Table structure for tb_coupon_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_coupon_detail`;
CREATE TABLE `tb_coupon_detail` (
  `coupon_detail_id` varchar(64) NOT NULL COMMENT '优惠券明细ID',
  `coupon_id` varchar(64) DEFAULT NULL,
  `coupon_detail_status` varchar(255) DEFAULT NULL COMMENT '1-未使用 2-已使用 3-已过期 4-锁定  99-异常',
  `user_id` int(11) DEFAULT NULL COMMENT '领用人ID',
  `order_id` varchar(50) DEFAULT NULL COMMENT '订单ID',
  `start_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `end_time` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`coupon_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
