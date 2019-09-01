/*
Navicat MySQL Data Transfer

Source Server         : 192.168.100.201
Source Server Version : 50726
Source Host           : 192.168.100.201:3306
Source Database       : cloud_admin

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-06-25 17:01:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_element
-- ----------------------------
DROP TABLE IF EXISTS `base_element`;
CREATE TABLE `base_element` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '资源编码',
  `type` varchar(255) DEFAULT NULL COMMENT '资源类型',
  `name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `uri` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `menu_id` varchar(255) DEFAULT NULL COMMENT '资源关联菜单',
  `parent_id` varchar(255) DEFAULT NULL,
  `path` varchar(2000) DEFAULT NULL COMMENT '资源树状检索路径',
  `method` varchar(10) DEFAULT NULL COMMENT '资源请求类型',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
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
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of base_element
-- ----------------------------
INSERT INTO `base_element` VALUES ('3', 'userManager:btn_add', 'button', '新增', '/admin/user', '1', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('4', 'userManager:btn_edit', 'button', '编辑', '/admin/user/{*}', '1', null, null, 'PUT', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('5', 'userManager:btn_del', 'button', '删除', '/admin/user/{*}', '1', null, null, 'DELETE', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('9', 'menuManager:element', 'uri', '按钮页面', '/admin/element', '6', null, null, 'GET', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('10', 'menuManager:btn_add', 'button', '新增', '/admin/menu/{*}', '6', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('11', 'menuManager:btn_edit', 'button', '编辑', '/admin/menu/{*}', '6', '', '', 'PUT', '', '2017-06-24 00:00:00', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `base_element` VALUES ('12', 'menuManager:btn_del', 'button', '删除', '/admin/menu/{*}', '6', '', '', 'DELETE', '', '2017-06-24 00:00:00', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `base_element` VALUES ('13', 'menuManager:btn_element_add', 'button', '新增元素', '/admin/element', '6', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('14', 'menuManager:btn_element_edit', 'button', '编辑元素', '/admin/element/{*}', '6', null, null, 'PUT', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('15', 'menuManager:btn_element_del', 'button', '删除元素', '/admin/element/{*}', '6', null, null, 'DELETE', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('16', 'groupManager:btn_add', 'button', '新增', '/admin/group', '7', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('17', 'groupManager:btn_edit', 'button', '编辑', '/admin/group/{*}', '7', null, null, 'PUT', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('18', 'groupManager:btn_del', 'button', '删除', '/admin/group/{*}', '7', null, null, 'DELETE', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('19', 'groupManager:btn_userManager', 'button', '分配用户', '/admin/group/{*}/user', '7', null, null, 'PUT', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('20', 'groupManager:btn_resourceManager', 'button', '分配权限', '/admin/group/{*}/authority', '7', null, null, 'GET', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('21', 'groupManager:menu', 'uri', '分配菜单', '/admin/group/{*}/authority/menu', '7', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('22', 'groupManager:element', 'uri', '分配资源', '/admin/group/{*}/authority/element', '7', null, null, 'POST', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('23', 'userManager:view', 'uri', '查看', '/admin/user/{*}', '1', '', '', 'GET', '', '2017-06-26 00:00:00', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `base_element` VALUES ('24', 'menuManager:view', 'uri', '查看', '/admin/menu/{*}', '6', '', '', 'GET', '', '2017-06-26 00:00:00', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `base_element` VALUES ('27', 'menuManager:element_view', 'uri', '查看', '/admin/element/{*}', '6', null, null, 'GET', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('28', 'groupManager:view', 'uri', '查看', '/admin/group/{*}', '7', null, null, 'GET', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('32', 'groupTypeManager:view', 'uri', '查看', '/admin/groupType/{*}', '8', null, null, 'GET', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('33', 'groupTypeManager:btn_add', 'button', '新增', '/admin/groupType', '8', null, null, 'POST', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('34', 'groupTypeManager:btn_edit', 'button', '编辑', '/admin/groupType/{*}', '8', null, null, 'PUT', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('35', 'groupTypeManager:btn_del', 'button', '删除', '/admin/groupType/{*}', '8', null, null, 'DELETE', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('41', 'gateLogManager:view', 'button', '查看', '/admin/gateLog', '27', null, null, 'GET', '', '2017-07-01 00:00:00', '1', 'admin', '0:0:0:0:0:0:0:1', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('42', 'serviceManager:view', 'URI', '查看', '/auth/service/{*}', '30', null, null, 'GET', null, '2017-12-26 20:17:42', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('43', 'serviceManager:btn_add', 'button', '新增', '/auth/service', '30', null, null, 'POST', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('44', 'serviceManager:btn_edit', 'button', '编辑', '/auth/service/{*}', '30', null, null, 'PUT', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('45', 'serviceManager:btn_del', 'button', '删除', '/auth/service/{*}', '30', null, null, 'DELETE', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('46', 'serviceManager:btn_clientManager', 'button', '服务授权', '/auth/service/{*}/client', '30', null, null, 'POST', null, '2017-12-30 16:32:48', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('47', 'courseManager:btn_add', 'button', '新增', '/cloud/classroom/course', '36', null, null, 'POST', null, '2019-05-30 07:28:18', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('48', 'courseManager:btn_edit', 'button', '修改', '/cloud/classroom/course', '36', null, null, 'PUT', null, '2019-05-30 07:53:57', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('49', 'courseManager:btn_del', 'button', '删除', '/cloud/classroom/course', '36', null, null, 'DELETE', null, '2019-05-30 07:57:31', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('50', 'cloudClassroomCourseManager:view', 'uri', '查询', '/cloud/classroom/course/list', '36', null, null, 'GET', null, '2019-05-30 07:58:15', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('55', 'orderManager:view', 'uri', '查看', 'cloud/classroom/order/list', '37', null, null, 'GET', null, '2019-05-30 08:09:59', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('56', 'orderManager:btn_add', 'button', '新增', '/cloud/classroom/order', '37', null, null, 'POST', null, '2019-05-30 08:10:31', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('57', 'orderManager:btn_edit', 'button', '修改', '/cloud/classroom/order', '37', null, null, 'PUT', null, '2019-05-30 08:11:30', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `base_element` VALUES ('58', 'orderManager:btn_del', 'button', '删除', '/cloud/classroom/order', '37', null, null, 'DELETE', null, '2019-05-30 08:12:00', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for base_group
-- ----------------------------
DROP TABLE IF EXISTS `base_group`;
CREATE TABLE `base_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '角色编码',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `parent_id` int(11) NOT NULL COMMENT '上级节点',
  `path` varchar(2000) DEFAULT NULL COMMENT '树状关系',
  `type` char(1) DEFAULT NULL COMMENT '类型',
  `group_type` int(11) NOT NULL COMMENT '角色组类型',
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of base_group
-- ----------------------------
INSERT INTO `base_group` VALUES ('1', 'adminRole', '管理员', '-1', '/adminRole', null, '1', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group` VALUES ('3', 'testGroup', '体验组', '-1', '/testGroup', null, '1', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group` VALUES ('4', 'visitorRole', '游客', '3', '/testGroup/visitorRole', null, '1', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group` VALUES ('6', 'company', 'AG集团', '-1', '/company', null, '2', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group` VALUES ('7', 'financeDepart', '财务部', '6', '/company/financeDepart', null, '2', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group` VALUES ('8', 'hrDepart', '人力资源部', '6', '/company/hrDepart', null, '2', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group` VALUES ('9', 'blogAdmin', '博客管理员', '-1', '/blogAdmin', null, '1', '', '2017-07-16 16:59:30', '1', 'Mr.AG', '0:0:0:0:0:0:0:1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group` VALUES ('10', 'teacherRole', '教师', '1', '/adminRole/teacherRole', null, '1', null, '2019-05-30 04:57:43', '1', 'admin', '192.168.1.160', '2019-05-30 04:57:43', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for base_group_leader
-- ----------------------------
DROP TABLE IF EXISTS `base_group_leader`;
CREATE TABLE `base_group_leader` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of base_group_leader
-- ----------------------------
INSERT INTO `base_group_leader` VALUES ('6', '9', '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group_leader` VALUES ('13', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group_leader` VALUES ('14', '10', '5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for base_group_member
-- ----------------------------
DROP TABLE IF EXISTS `base_group_member`;
CREATE TABLE `base_group_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of base_group_member
-- ----------------------------
INSERT INTO `base_group_member` VALUES ('2', '4', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group_member` VALUES ('9', '9', '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_group_member` VALUES ('10', '1', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for base_group_type
-- ----------------------------
DROP TABLE IF EXISTS `base_group_type`;
CREATE TABLE `base_group_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(255) DEFAULT NULL COMMENT '创建人ID',
  `crt_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `crt_host` varchar(255) DEFAULT NULL COMMENT '创建主机',
  `upd_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `upd_user` varchar(255) DEFAULT NULL COMMENT '最后更新人ID',
  `upd_name` varchar(255) DEFAULT NULL COMMENT '最后更新人',
  `upd_host` varchar(255) DEFAULT NULL COMMENT '最后更新主机',
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of base_group_type
-- ----------------------------
INSERT INTO `base_group_type` VALUES ('1', 'role', '角色类型', 'role', null, null, null, null, '2017-08-25 17:52:37', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_group_type` VALUES ('2', 'depart', '部门类型', null, null, null, null, null, '2017-08-25 17:52:43', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_group_type` VALUES ('3', 'free', '自定义类型', 'sadf', null, null, null, null, '2017-08-26 08:22:25', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for base_menu
-- ----------------------------
DROP TABLE IF EXISTS `base_menu`;
CREATE TABLE `base_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '路径编码',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `parent_id` int(11) NOT NULL COMMENT '父级节点',
  `href` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `type` char(10) DEFAULT NULL,
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `path` varchar(500) DEFAULT NULL COMMENT '菜单上下级关系',
  `enabled` char(1) DEFAULT NULL COMMENT '启用禁用',
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of base_menu
-- ----------------------------
INSERT INTO `base_menu` VALUES ('1', 'userManager', '用户管理', '5', '/admin/user', 'fa-user', 'menu', '0', '', '/adminSys/baseManager/userManager', null, null, null, null, null, '2017-09-05 21:06:51', '1', 'Mr.AG', '127.0.0.1', '_import(\'admin/user/index\')', null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('5', 'baseManager', '基础配置管理', '13', '/admin', 'setting', 'dirt', '0', '', '/adminSys/baseManager', null, null, null, null, null, '2017-09-05 21:46:11', '1', 'Mr.AG', '127.0.0.1', 'Layout', null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('6', 'menuManager', '菜单管理', '5', '/admin/menu', 'category', 'menu', '0', '', '/adminSys/baseManager/menuManager', null, null, null, null, null, '2017-09-05 21:10:25', '1', 'Mr.AG', '127.0.0.1', '_import(\'admin/menu/index\')', null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('7', 'groupManager', '角色权限管理', '5', '/admin/group', 'group_fill', 'menu', '0', '', '/adminSys/baseManager/groupManager', null, null, null, null, null, '2017-09-05 21:11:34', '1', 'Mr.AG', '127.0.0.1', 'import(\'admin/group/index\')', null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('8', 'groupTypeManager', '角色类型管理', '5', '/admin/groupType', 'fa-users', 'menu', '0', '', '/adminSys/baseManager/groupTypeManager', null, null, null, null, null, '2017-09-05 21:12:28', '1', 'Mr.AG', '127.0.0.1', '_import(\'admin/groupType/index\')', null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('13', 'adminSys', '权限管理系统', '-1', '/base', 'setting', 'dirt', '0', '', '/adminSys', null, null, null, null, null, '2017-09-28 12:09:22', '1', 'Mr.AG', '127.0.0.1', 'Layout', null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('27', 'gateLogManager', '操作日志', '5', '/admin/gateLog', 'viewlist', 'menu', '0', '', '/adminSys/baseManager/gateLogManager', null, '2017-07-01 00:00:00', '1', 'admin', '0:0:0:0:0:0:0:1', '2017-09-05 22:32:55', '1', 'Mr.AG', '127.0.0.1', '_import(\'admin/gateLog/index\')', null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('29', 'authManager', '服务权限管理', '13', '/auth', 'service', 'dirt', '0', '服务权限管理', '/adminSys/authManager', null, '2017-12-26 19:54:45', '1', 'Mr.AG', '127.0.0.1', '2019-01-27 12:42:09', '1', 'admin', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('30', 'serviceManager', '服务管理', '29', '/auth/service', 'client', null, '0', '服务管理', '/adminSys/authManager/serviceManager', null, '2017-12-26 19:56:06', '1', 'Mr.AG', '127.0.0.1', '2017-12-26 19:56:06', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('31', 'monitorManager', '监控模块管理', '13', '/service', 'service', 'dirt', '0', null, '/adminSys/monitorManager', null, '2018-02-25 09:36:35', '1', 'Mr.AG', '127.0.0.1', '2019-01-27 12:42:13', '1', 'admin', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('32', 'serviceEurekaManager', '服务注册中心', '31', '', 'client', 'menu', '0', null, '/adminSys/monitorManager/serviceEurekaManager', null, '2018-02-25 09:37:04', '1', 'Mr.AG', '127.0.0.1', '2019-01-27 12:14:35', '1', 'admin', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('33', 'serviceMonitorManager', '服务状态监控', '31', '', 'client', 'menu', '0', null, '/adminSys/monitorManager/serviceEurekaManager', null, '2018-02-25 09:37:05', '1', 'Mr.AG', '127.0.0.1', '2019-01-27 12:07:14', '1', 'admin', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('34', 'serviceZipkinManager', '服务链路监控', '31', '', 'client', 'menu', '0', null, '/adminSys/monitorManager/serviceZipkinManager', null, '2018-02-25 09:38:05', '1', 'Mr.AG', '127.0.0.1', '2019-01-27 13:13:33', '1', 'admin', '127.0.0.1', '2121', null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('35', 'cloudClassroomManager', '云课堂管理', '13', '/cloud/classroom', null, 'dirt', '0', null, '/adminSys/cloudClassroomManager', null, '2019-05-30 07:11:12', '1', 'admin', '192.168.1.160', '2019-05-30 07:11:12', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('36', 'cloudClassroomCourseManager', '课程管理', '35', '/cloud/classroom/course', null, 'menu', '0', null, '/adminSys/cloudClassroomManager/cloudClassroomCourseManager', null, '2019-05-30 07:19:21', '1', 'admin', '192.168.1.160', '2019-05-30 07:19:21', '1', 'admin', '192.168.1.160', '_import(\'cloud/classroom/course/index\')', null, null, null, null, null, null, null);
INSERT INTO `base_menu` VALUES ('37', 'cloudClassroomOrderManager', '订单管理', '35', '/cloud/classroom/order', null, 'menu', '0', null, '/adminSys/cloudClassroomManager/cloudClassroomOrderManager', null, '2019-05-30 08:00:11', '1', 'admin', '192.168.1.160', '2019-06-02 01:32:09', '1', 'admin', '192.168.1.160', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for base_resource_authority
-- ----------------------------
DROP TABLE IF EXISTS `base_resource_authority`;
CREATE TABLE `base_resource_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authority_id` varchar(255) DEFAULT NULL COMMENT '角色ID',
  `authority_type` varchar(255) DEFAULT NULL COMMENT '角色类型',
  `resource_id` varchar(255) DEFAULT NULL COMMENT '资源ID',
  `resource_type` varchar(255) DEFAULT NULL COMMENT '资源类型',
  `parent_id` varchar(255) DEFAULT NULL,
  `path` varchar(2000) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=1121 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of base_resource_authority
-- ----------------------------
INSERT INTO `base_resource_authority` VALUES ('287', '1', 'group', '5', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('288', '1', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('289', '1', 'group', '10', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('290', '1', 'group', '11', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('291', '1', 'group', '12', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('294', '1', 'group', '5', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('295', '1', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('296', '1', 'group', '10', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('297', '1', 'group', '11', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('298', '1', 'group', '12', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('299', '1', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('300', '1', 'group', '12', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('301', '1', 'group', '10', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('302', '1', 'group', '11', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('303', '1', 'group', '13', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('304', '1', 'group', '14', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('305', '1', 'group', '15', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('306', '1', 'group', '10', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('307', '1', 'group', '11', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('308', '1', 'group', '12', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('309', '1', 'group', '13', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('310', '1', 'group', '14', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('311', '1', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('312', '1', 'group', '15', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('313', '1', 'group', '16', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('314', '1', 'group', '17', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('315', '1', 'group', '18', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('317', '1', 'group', '20', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('318', '1', 'group', '21', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('319', '1', 'group', '22', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('349', '4', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('371', '1', 'group', '23', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('372', '1', 'group', '24', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('373', '1', 'group', '27', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('374', '1', 'group', '28', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('375', '1', 'group', '23', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('378', '1', 'group', '5', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('379', '1', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('380', '1', 'group', '11', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('381', '1', 'group', '14', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('382', '1', 'group', '13', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('383', '1', 'group', '15', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('384', '1', 'group', '12', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('385', '1', 'group', '24', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('386', '1', 'group', '10', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('387', '1', 'group', '27', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('388', '1', 'group', '16', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('389', '1', 'group', '18', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('390', '1', 'group', '17', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('392', '1', 'group', '20', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('393', '1', 'group', '28', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('394', '1', 'group', '22', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('395', '1', 'group', '21', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('396', '4', 'group', '23', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('397', '4', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('398', '4', 'group', '27', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('399', '4', 'group', '24', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('400', '4', 'group', '28', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('401', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('402', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('403', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('421', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('422', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('423', '4', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('424', '4', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('436', '1', 'group', '32', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('437', '1', 'group', '33', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('438', '1', 'group', '34', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('439', '1', 'group', '35', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('440', '4', 'group', '32', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('464', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('465', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('466', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('467', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('468', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('469', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('470', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('471', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('472', '1', 'group', '40', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('492', '1', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('493', '1', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('494', '1', 'group', '40', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('516', '4', 'group', '41', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('517', '4', 'group', '30', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('518', '4', 'group', '31', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('519', '4', 'group', '40', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('611', '4', 'group', '42', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('612', '4', 'group', '36', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('628', '4', 'group', '13', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('629', '4', 'group', '5', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('630', '4', 'group', '1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('631', '4', 'group', '6', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('632', '4', 'group', '7', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('633', '4', 'group', '8', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('634', '4', 'group', '27', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('635', '4', 'group', '9', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('636', '4', 'group', '24', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('637', '4', 'group', '22', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('638', '4', 'group', '23', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('639', '4', 'group', '25', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('640', '4', 'group', '26', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('641', '4', 'group', '28', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('666', '1', 'group', '41', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('689', '1', 'group', '43', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('691', '1', 'group', '44', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('710', '9', 'group', '42', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('711', '9', 'group', '43', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('712', '9', 'group', '44', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('713', '9', 'group', '45', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('718', '9', 'group', '42', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('719', '9', 'group', '44', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('720', '9', 'group', '45', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('721', '9', 'group', '43', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('722', '1', 'group', '41', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('774', '1', 'group', '3', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('775', '1', 'group', '4', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('812', '1', 'group', '19', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('924', '1', 'group', '42', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('945', '1', 'group', '45', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('956', '1', 'group', '46', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('981', '9', 'group', '23', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('982', '9', 'group', '1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('983', '9', 'group', '13', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('984', '9', 'group', '-1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('985', '9', 'group', '5', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('987', '10', 'group', '9', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('988', '10', 'group', '10', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('989', '10', 'group', '11', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('990', '10', 'group', '12', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('991', '10', 'group', '13', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('992', '10', 'group', '14', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('993', '10', 'group', '15', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('994', '10', 'group', '27', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('995', '10', 'group', '24', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1001', '10', 'group', '48', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1003', '10', 'group', '49', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1014', '1', 'group', '47', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1032', '1', 'group', '48', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1033', '1', 'group', '50', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1034', '1', 'group', '49', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1052', '1', 'group', '58', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1053', '1', 'group', '56', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1054', '1', 'group', '57', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1055', '1', 'group', '55', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1056', '1', 'group', '33', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1057', '1', 'group', '34', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1058', '1', 'group', '13', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1059', '1', 'group', '35', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1060', '1', 'group', '36', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1061', '1', 'group', '37', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1062', '1', 'group', '-1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1063', '1', 'group', '27', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1064', '1', 'group', '29', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1065', '1', 'group', '1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1066', '1', 'group', '5', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1067', '1', 'group', '6', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1068', '1', 'group', '7', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1069', '1', 'group', '8', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1070', '1', 'group', '30', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1071', '1', 'group', '31', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1072', '1', 'group', '32', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1073', '10', 'group', '50', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1089', '10', 'group', '55', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1090', '10', 'group', '56', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1091', '10', 'group', '57', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1097', '10', 'group', '58', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1103', '10', 'group', '47', 'button', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1116', '10', 'group', '13', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1117', '10', 'group', '35', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1118', '10', 'group', '36', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1119', '10', 'group', '37', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `base_resource_authority` VALUES ('1120', '10', 'group', '-1', 'menu', '-1', null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `mobile_phone` varchar(255) DEFAULT NULL,
  `tel_phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_name` varchar(255) DEFAULT NULL,
  `crt_host` varchar(255) DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) DEFAULT NULL,
  `upd_name` varchar(255) DEFAULT NULL,
  `upd_host` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of base_user
-- ----------------------------
INSERT INTO `base_user` VALUES ('1', 'admin', '$2a$12$S/yLlj9kzi5Dgsz97H4rAekxrPlk/10eXp1lUJcAVAx.2M9tOpWie', 'Andy', '', null, '', null, '', '男', null, null, '', null, null, null, null, '2017-11-16 23:23:49', '1', 'Mr.AG', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_user` VALUES ('2', 'test', '$2a$12$zWe6knO6rGp15UVfdWTTxu.Ykt.k3QnD5FPoj6a1cnL63csHY2A1S', '测试账户', '', null, '', null, '', '男', null, null, '', null, null, null, null, '2017-07-15 19:18:07', '1', '管理员', '0:0:0:0:0:0:0:1', null, null, null, null, null, null, null, null);
INSERT INTO `base_user` VALUES ('4', 'blog', '$2a$12$S/yLlj9kzi5Dgsz97H4rAekxrPlk/10eXp1lUJcAVAx.2M9tOpWie', 'Andy(博主)', '', null, '', null, '', '男', null, null, '12', null, null, null, null, '2019-01-27 15:33:13', '1', 'admin', '127.0.0.1', null, null, null, null, null, null, null, null);
INSERT INTO `base_user` VALUES ('5', 'xiaoming', '$2a$12$R1SRHcyhAgwABdIN5AEbY.8.KCpbDxJk7HjxileFcqEJbyaYKmZgm', '小明', null, null, null, null, null, '男', null, null, '12', '2019-05-30 04:51:48', '1', 'admin', '192.168.1.160', '2019-06-05 08:08:50', '1', 'admin', '192.168.100.160', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for gate_log
-- ----------------------------
DROP TABLE IF EXISTS `gate_log`;
CREATE TABLE `gate_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `menu` varchar(255) DEFAULT NULL COMMENT '菜单',
  `opt` varchar(255) DEFAULT NULL COMMENT '操作',
  `uri` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `crt_time` datetime DEFAULT NULL COMMENT '操作时间',
  `crt_user` varchar(255) DEFAULT NULL COMMENT '操作人ID',
  `crt_name` varchar(255) DEFAULT NULL COMMENT '操作人',
  `crt_host` varchar(255) DEFAULT NULL COMMENT '操作主机',
  `body` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of gate_log
-- ----------------------------
INSERT INTO `gate_log` VALUES ('1', '菜单管理', '新增', '/admin/menu', '2018-02-25 09:36:35', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('2', '菜单管理', '新增', '/admin/menu', '2018-02-25 09:37:04', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('3', '菜单管理', '新增', '/admin/menu', '2018-02-25 09:37:04', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('4', '菜单管理', '编辑', '/admin/menu', '2018-02-25 09:37:20', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('5', '菜单管理', '编辑', '/admin/menu', '2018-02-25 09:37:32', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('6', '菜单管理', '编辑', '/admin/menu', '2018-02-25 09:37:35', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('7', '菜单管理', '编辑', '/admin/menu', '2018-02-25 09:37:40', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('8', '菜单管理', '新增', '/admin/menu', '2018-02-25 09:38:04', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('9', '角色权限管理', '新增', '/admin/group', '2018-02-25 09:38:21', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('10', '菜单管理', '编辑', '/admin/menu', '2018-02-25 09:38:55', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('11', '用户管理', '编辑', '/admin/user', '2018-03-06 11:26:28', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('12', '用户管理', '编辑', '/admin/user', '2018-03-06 11:26:34', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('13', '用户管理', '编辑', '/admin/user', '2018-03-07 11:21:10', '1', 'Mr.AG', '127.0.0.1', null);
INSERT INTO `gate_log` VALUES ('14', '用户管理', '编辑', '/admin/user/{*}', '2019-01-27 15:33:13', '1', 'Mr.AG', '/127.0.0.1:54809', '{\"id\":4,\"username\":\"blog\",\"name\":\"Mr.AG(博主)\",\"birthday\":\"\",\"mobilePhone\":\"\",\"email\":\"\",\"sex\":\"男\",\"description\":\"12\",\"updTime\":\"2019-01-27 15:30:14\",\"updUser\":\"1\",\"updName\":\"admin\",\"updHost\":\"127.0.0.1\"}');
INSERT INTO `gate_log` VALUES ('15', '用户管理', '新增', '/admin/user', '2019-05-30 04:51:48', '1', 'Mr.AG', '/127.0.0.1:62012', 'null');
INSERT INTO `gate_log` VALUES ('16', '角色权限管理', '新增', '/admin/group', '2019-05-30 04:57:43', '1', 'Mr.AG', '/127.0.0.1:63837', 'null');
INSERT INTO `gate_log` VALUES ('17', '角色权限管理', '分配用户', '/admin/group/{*}/user', '2019-05-30 06:52:59', '1', 'Mr.AG', '/127.0.0.1:60866', 'null');
INSERT INTO `gate_log` VALUES ('18', '菜单管理', '新增元素', '/admin/element', '2019-05-30 07:28:18', '1', 'Mr.AG', '/127.0.0.1:55488', 'null');
INSERT INTO `gate_log` VALUES ('19', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-05-30 07:41:39', '1', 'Mr.AG', '/127.0.0.1:59585', 'null');
INSERT INTO `gate_log` VALUES ('20', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-05-30 07:53:35', '1', 'Mr.AG', '/127.0.0.1:63177', 'null');
INSERT INTO `gate_log` VALUES ('21', '菜单管理', '新增元素', '/admin/element', '2019-05-30 07:53:57', '1', 'Mr.AG', '/127.0.0.1:63304', 'null');
INSERT INTO `gate_log` VALUES ('22', '菜单管理', '新增元素', '/admin/element', '2019-05-30 07:57:31', '1', 'Mr.AG', '/127.0.0.1:64355', 'null');
INSERT INTO `gate_log` VALUES ('23', '菜单管理', '新增元素', '/admin/element', '2019-05-30 07:58:15', '1', 'Mr.AG', '/127.0.0.1:64573', 'null');
INSERT INTO `gate_log` VALUES ('24', '菜单管理', '新增元素', '/admin/element', '2019-05-30 08:01:47', '1', 'Mr.AG', '/127.0.0.1:49285', 'null');
INSERT INTO `gate_log` VALUES ('25', '菜单管理', '新增元素', '/admin/element', '2019-05-30 08:03:05', '1', 'Mr.AG', '/127.0.0.1:49677', 'null');
INSERT INTO `gate_log` VALUES ('26', '菜单管理', '新增元素', '/admin/element', '2019-05-30 08:03:54', '1', 'Mr.AG', '/127.0.0.1:49909', 'null');
INSERT INTO `gate_log` VALUES ('27', '菜单管理', '新增元素', '/admin/element', '2019-05-30 08:04:56', '1', 'Mr.AG', '/127.0.0.1:50213', 'null');
INSERT INTO `gate_log` VALUES ('28', '菜单管理', '删除元素', '/admin/element/{*}', '2019-05-30 08:08:55', '1', 'Mr.AG', '/127.0.0.1:51724', 'null');
INSERT INTO `gate_log` VALUES ('29', '菜单管理', '删除元素', '/admin/element/{*}', '2019-05-30 08:08:56', '1', 'Mr.AG', '/127.0.0.1:51739', 'null');
INSERT INTO `gate_log` VALUES ('30', '菜单管理', '删除元素', '/admin/element/{*}', '2019-05-30 08:09:05', '1', 'Mr.AG', '/127.0.0.1:51777', 'null');
INSERT INTO `gate_log` VALUES ('31', '菜单管理', '删除元素', '/admin/element/{*}', '2019-05-30 08:09:15', '1', 'Mr.AG', '/127.0.0.1:51833', 'null');
INSERT INTO `gate_log` VALUES ('32', '菜单管理', '新增元素', '/admin/element', '2019-05-30 08:09:59', '1', 'Mr.AG', '/127.0.0.1:52055', 'null');
INSERT INTO `gate_log` VALUES ('33', '菜单管理', '新增元素', '/admin/element', '2019-05-30 08:10:31', '1', 'Mr.AG', '/127.0.0.1:52207', 'null');
INSERT INTO `gate_log` VALUES ('34', '菜单管理', '新增元素', '/admin/element', '2019-05-30 08:11:30', '1', 'Mr.AG', '/127.0.0.1:52526', 'null');
INSERT INTO `gate_log` VALUES ('35', '菜单管理', '新增元素', '/admin/element', '2019-05-30 08:12:00', '1', 'Mr.AG', '/127.0.0.1:52681', 'null');
INSERT INTO `gate_log` VALUES ('36', '服务管理', '新增', '/auth/service', '2019-05-31 04:01:17', '1', 'Mr.AG', '/127.0.0.1:59212', 'null');
INSERT INTO `gate_log` VALUES ('37', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-05-31 04:15:29', '1', 'Mr.AG', '/127.0.0.1:64815', 'null');
INSERT INTO `gate_log` VALUES ('38', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-05-31 04:15:42', '1', 'Mr.AG', '/127.0.0.1:64911', 'null');
INSERT INTO `gate_log` VALUES ('39', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-05-31 04:17:13', '1', 'Mr.AG', '/127.0.0.1:65493', 'null');
INSERT INTO `gate_log` VALUES ('40', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-05-31 04:17:35', '1', 'Mr.AG', '/127.0.0.1:49277', 'null');
INSERT INTO `gate_log` VALUES ('41', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-05-31 04:17:45', '1', 'Mr.AG', '/127.0.0.1:49354', 'null');
INSERT INTO `gate_log` VALUES ('42', '服务管理', '新增', '/auth/service', '2019-05-31 08:41:36', '1', 'Mr.AG', '/127.0.0.1:61645', 'null');
INSERT INTO `gate_log` VALUES ('43', '服务管理', '新增', '/auth/service', '2019-06-02 01:15:57', '1', 'Mr.AG', '/127.0.0.1:54593', 'null');
INSERT INTO `gate_log` VALUES ('44', '服务管理', '编辑', '/auth/service/{*}', '2019-06-02 01:16:34', '1', 'Mr.AG', '/127.0.0.1:54830', 'null');
INSERT INTO `gate_log` VALUES ('45', '菜单管理', '编辑', '/admin/menu/{*}', '2019-06-02 01:32:09', '1', 'Mr.AG', '/127.0.0.1:60985', 'null');
INSERT INTO `gate_log` VALUES ('46', '课程管理', '新增', '/cloud/classroom/course', '2019-06-03 09:48:55', '5', '小明', '/0:0:0:0:0:0:0:1:52533', 'null');
INSERT INTO `gate_log` VALUES ('47', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-04 23:59:22', '1', 'Mr.AG', '/127.0.0.1:54426', 'null');
INSERT INTO `gate_log` VALUES ('48', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-05 00:00:13', '1', 'Mr.AG', '/127.0.0.1:54621', 'null');
INSERT INTO `gate_log` VALUES ('49', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-05 00:04:10', '1', 'Mr.AG', '/127.0.0.1:55471', 'null');
INSERT INTO `gate_log` VALUES ('50', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-05 00:20:28', '1', 'Mr.AG', '/127.0.0.1:59181', 'null');
INSERT INTO `gate_log` VALUES ('51', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-05 00:20:35', '1', 'Mr.AG', '/127.0.0.1:59206', 'null');
INSERT INTO `gate_log` VALUES ('52', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-05 00:20:41', '1', 'Mr.AG', '/127.0.0.1:59232', 'null');
INSERT INTO `gate_log` VALUES ('53', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-05 00:20:47', '1', 'Mr.AG', '/127.0.0.1:59256', 'null');
INSERT INTO `gate_log` VALUES ('54', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-05 01:04:19', '1', 'Mr.AG', '/127.0.0.1:50661', 'null');
INSERT INTO `gate_log` VALUES ('55', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-05 01:04:30', '1', 'Mr.AG', '/127.0.0.1:50709', 'null');
INSERT INTO `gate_log` VALUES ('56', '菜单管理', '编辑元素', '/admin/element/{*}', '2019-06-05 01:04:37', '1', 'Mr.AG', '/127.0.0.1:50742', 'null');
INSERT INTO `gate_log` VALUES ('57', '课程管理', '新增', '/cloud/classroom/course', '2019-06-05 07:29:24', '1', 'Mr.AG', '/127.0.0.1:61654', 'null');
INSERT INTO `gate_log` VALUES ('58', '课程管理', '新增', '/cloud/classroom/course', '2019-06-05 07:34:51', '1', 'Mr.AG', '/127.0.0.1:62730', 'null');
INSERT INTO `gate_log` VALUES ('59', '课程管理', '新增', '/cloud/classroom/course', '2019-06-05 07:38:36', '1', 'Mr.AG', '/127.0.0.1:63482', 'null');
INSERT INTO `gate_log` VALUES ('60', '用户管理', '编辑', '/admin/user/{*}', '2019-06-05 08:08:33', '1', 'Mr.AG', '/127.0.0.1:53429', 'null');
INSERT INTO `gate_log` VALUES ('61', '用户管理', '编辑', '/admin/user/{*}', '2019-06-05 08:08:49', '1', 'Mr.AG', '/127.0.0.1:53491', 'null');
INSERT INTO `gate_log` VALUES ('62', '服务管理', '编辑', '/auth/service/{*}', '2019-06-11 07:13:51', '1', 'Mr.AG', '/127.0.0.1:64353', 'null');
INSERT INTO `gate_log` VALUES ('63', '服务管理', '编辑', '/auth/service/{*}', '2019-06-11 07:14:57', '1', 'Mr.AG', '/127.0.0.1:64750', 'null');
INSERT INTO `gate_log` VALUES ('64', '服务管理', '编辑', '/auth/service/{*}', '2019-06-11 07:15:14', '1', 'Mr.AG', '/127.0.0.1:64853', 'null');
INSERT INTO `gate_log` VALUES ('65', '服务管理', '编辑', '/auth/service/{*}', '2019-06-11 07:15:31', '1', 'Mr.AG', '/127.0.0.1:64944', 'null');
INSERT INTO `gate_log` VALUES ('66', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 08:01:51', '1', 'Mr.AG', '/192.168.100.160:53021', 'null');
INSERT INTO `gate_log` VALUES ('67', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 08:06:46', '1', 'Mr.AG', '/192.168.100.160:53835', 'null');
INSERT INTO `gate_log` VALUES ('68', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 09:05:31', '1', 'Mr.AG', '/192.168.100.160:64441', 'null');
INSERT INTO `gate_log` VALUES ('69', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 09:23:36', '1', 'Mr.AG', '/192.168.100.160:51329', 'null');
INSERT INTO `gate_log` VALUES ('70', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 09:23:55', '1', 'Mr.AG', '/192.168.100.160:51395', 'null');
INSERT INTO `gate_log` VALUES ('71', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 10:07:15', '1', 'Mr.AG', '/192.168.100.160:59247', 'null');
INSERT INTO `gate_log` VALUES ('72', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 10:10:31', '1', 'Mr.AG', '/192.168.100.160:59785', 'null');
INSERT INTO `gate_log` VALUES ('73', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 10:14:49', '1', 'Mr.AG', '/192.168.100.160:60509', 'null');
INSERT INTO `gate_log` VALUES ('74', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 10:23:24', '1', 'Mr.AG', '/192.168.100.160:61954', 'null');
INSERT INTO `gate_log` VALUES ('75', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 10:26:16', '1', 'Mr.AG', '/192.168.100.160:62433', 'null');
INSERT INTO `gate_log` VALUES ('76', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 10:29:11', '1', 'Mr.AG', '/192.168.100.160:62973', 'null');
INSERT INTO `gate_log` VALUES ('77', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 10:32:23', '1', 'Mr.AG', '/192.168.100.160:63537', 'null');
INSERT INTO `gate_log` VALUES ('78', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 10:39:33', '1', 'Mr.AG', '/192.168.100.160:64725', 'null');
INSERT INTO `gate_log` VALUES ('79', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 23:37:36', '1', 'Mr.AG', '/192.168.100.160:56709', 'null');
INSERT INTO `gate_log` VALUES ('80', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 23:45:34', '1', 'Mr.AG', '/192.168.100.160:58019', 'null');
INSERT INTO `gate_log` VALUES ('81', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 23:47:07', '1', 'Mr.AG', '/192.168.100.160:58288', 'null');
INSERT INTO `gate_log` VALUES ('82', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 23:56:28', '1', 'Mr.AG', '/192.168.100.160:59970', 'null');
INSERT INTO `gate_log` VALUES ('83', '课程管理', '新增', '/cloud/classroom/course', '2019-06-13 23:56:44', '1', 'Mr.AG', '/192.168.100.160:60020', 'null');
INSERT INTO `gate_log` VALUES ('84', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 00:05:14', '1', 'Mr.AG', '/192.168.100.160:61706', 'null');
INSERT INTO `gate_log` VALUES ('85', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 00:13:13', '1', 'Mr.AG', '/192.168.100.160:63044', 'null');
INSERT INTO `gate_log` VALUES ('86', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 03:47:39', '1', 'Mr.AG', '/192.168.100.160:57015', 'null');
INSERT INTO `gate_log` VALUES ('87', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 04:47:33', '1', 'Mr.AG', '/192.168.100.160:56454', 'null');
INSERT INTO `gate_log` VALUES ('88', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 04:47:35', '1', 'Mr.AG', '/192.168.100.160:56459', 'null');
INSERT INTO `gate_log` VALUES ('89', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 04:47:45', '1', 'Mr.AG', '/192.168.100.160:56489', 'null');
INSERT INTO `gate_log` VALUES ('90', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 10:06:20', '1', 'Mr.AG', '/192.168.100.160:53434', 'null');
INSERT INTO `gate_log` VALUES ('91', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 10:12:38', '1', 'Mr.AG', '/192.168.100.160:55412', 'null');
INSERT INTO `gate_log` VALUES ('92', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 10:13:31', '1', 'Mr.AG', '/192.168.100.160:55710', 'null');
INSERT INTO `gate_log` VALUES ('93', '课程管理', '新增', '/cloud/classroom/course', '2019-06-14 10:14:07', '1', 'Mr.AG', '/192.168.100.160:55905', 'null');
INSERT INTO `gate_log` VALUES ('94', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 01:12:56', '1', 'Andy', '/192.168.100.160:57687', 'null');
INSERT INTO `gate_log` VALUES ('95', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 01:27:23', '1', 'Andy', '/192.168.100.160:60341', 'null');
INSERT INTO `gate_log` VALUES ('96', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 01:29:06', '1', 'Andy', '/192.168.100.160:60632', 'null');
INSERT INTO `gate_log` VALUES ('97', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 01:29:28', '1', 'Andy', '/192.168.100.160:60685', 'null');
INSERT INTO `gate_log` VALUES ('98', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 01:41:17', '1', 'Andy', '/192.168.100.160:62596', 'null');
INSERT INTO `gate_log` VALUES ('99', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 01:41:39', '1', 'Andy', '/192.168.100.160:62638', 'null');
INSERT INTO `gate_log` VALUES ('100', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 01:58:32', '1', 'Andy', '/192.168.100.160:65475', 'null');
INSERT INTO `gate_log` VALUES ('101', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 01:59:37', '1', 'Andy', '/192.168.100.160:49278', 'null');
INSERT INTO `gate_log` VALUES ('102', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 02:11:51', '1', 'Andy', '/192.168.100.160:51433', 'null');
INSERT INTO `gate_log` VALUES ('103', '课程管理', '新增', '/cloud/classroom/course', '2019-06-16 02:27:27', '1', 'Andy', '/192.168.100.160:54469', 'null');
