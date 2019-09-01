/*
Navicat MySQL Data Transfer

Source Server         : 192.168.100.201
Source Server Version : 50726
Source Host           : 192.168.100.201:3306
Source Database       : cloud_auth

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-06-25 17:01:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth_client
-- ----------------------------
DROP TABLE IF EXISTS `auth_client`;
CREATE TABLE `auth_client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '服务编码',
  `secret` varchar(255) DEFAULT NULL COMMENT '服务密钥',
  `name` varchar(255) DEFAULT NULL COMMENT '服务名',
  `locked` char(1) DEFAULT NULL COMMENT '是否锁定',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `crt_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `crt_host` varchar(255) DEFAULT NULL COMMENT '创建主机',
  `upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  `upd_user` varchar(255) DEFAULT NULL COMMENT '更新人',
  `upd_name` varchar(255) DEFAULT NULL COMMENT '更新姓名',
  `upd_host` varchar(255) DEFAULT NULL COMMENT '更新主机',
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of auth_client
-- ----------------------------
INSERT INTO `auth_client` VALUES ('1', 'cloud-gate', '123456', 'cloud-gate', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `auth_client` VALUES ('3', 'cloud-admin', '123456', 'cloud-admin', '0', '', null, null, null, null, '2017-07-06 21:42:17', '1', '管理员', '0:0:0:0:0:0:0:1', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client` VALUES ('6', 'cloud-auth', '123456', 'cloud-auth', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `auth_client` VALUES ('11', 'cloud-config', 'fXHsssa2', 'cloud-config', '0', 'config', null, null, null, null, '2019-06-11 07:13:51', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client` VALUES ('13', 'cloud-template', 'bZf8yvj8', 'cloud-template', '0', 'template', null, null, null, null, '2019-06-11 07:14:57', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client` VALUES ('14', 'cloud-trace', 'wKTl6GGE', 'cloud-trace', '0', 'trace', null, null, null, null, '2019-06-11 07:15:14', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client` VALUES ('15', 'cloud-monitor', 'eEQBUcnW', 'cloud-monitor', '0', 'monitor', null, null, null, null, '2019-06-11 07:15:31', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client` VALUES ('16', 'cloud-classroom-course', '123456', 'cloud-classroom-course', '0', '课程微服务', '2019-05-31 04:01:17', '1', 'admin', '192.168.1.160', '2019-05-31 04:01:17', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client` VALUES ('17', 'cloud-classroom-order', '123456', 'cloud-classroom-order', '0', '课程订单', '2019-05-31 08:41:36', '1', 'admin', '192.168.1.160', '2019-05-31 08:41:36', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client` VALUES ('18', 'cloud-classroom-essearch', '123456', 'cloud-classroom-essearch', '0', 'es搜索提供相关接口', '2019-06-02 01:15:57', '1', 'admin', '192.168.1.160', '2019-06-02 01:16:34', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for auth_client_service
-- ----------------------------
DROP TABLE IF EXISTS `auth_client_service`;
CREATE TABLE `auth_client_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of auth_client_service
-- ----------------------------
INSERT INTO `auth_client_service` VALUES ('21', '4', '5', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `auth_client_service` VALUES ('23', '3', '6', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `auth_client_service` VALUES ('47', '3', '1', null, '2019-06-02 01:18:38', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client_service` VALUES ('48', '6', '1', null, '2019-06-02 01:18:38', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client_service` VALUES ('49', '16', '1', null, '2019-06-02 01:18:38', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client_service` VALUES ('50', '18', '1', null, '2019-06-02 01:18:38', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client_service` VALUES ('51', '17', '1', null, '2019-06-02 01:18:38', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `auth_client_service` VALUES ('52', '16', '17', null, '2019-06-03 07:16:55', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
