/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.11 : Database - code_review
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`code_review` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `code_review`;

/*Table structure for table `e_dict` */

DROP TABLE IF EXISTS `e_dict`;

CREATE TABLE `e_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编码',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKf5wwh5osfukkeebw7h2yb4kmp` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据字典';

/*Data for the table `e_dict` */

insert  into `e_dict`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`code`,`name`,`remark`) values 
(2,'erupt','2023-03-07 22:33:46.644000','erupt','2023-03-07 22:33:46.644000','SystemNotice','系统通知',NULL),
(4,'erupt','2023-03-12 16:30:36.262000','erupt','2023-03-12 16:30:36.262000','CommentType','意见类型',NULL),
(5,'erupt','2023-03-12 16:31:37.631000','erupt','2023-03-12 16:31:37.631000','CommentLevel','严重级别',NULL),
(6,'erupt','2023-03-12 16:32:17.807000','erupt','2023-03-12 16:32:17.807000','BelongingTo','问题归类',NULL),
(7,'erupt','2023-03-12 16:33:31.182000','erupt','2023-03-12 16:33:31.182000','ConfirmResult','确认结果',NULL),
(8,'erupt','2023-03-12 16:58:35.390000','erupt','2023-03-12 16:58:35.390000','ServerDynamic_UserList','用户列表','以ServerDynamic_开头的表示会调用接口进行查询，无需手动指定枚举值。需要代码中实现相关处理接口。详见帮助文档。');

/*Table structure for table `e_dict_item` */

DROP TABLE IF EXISTS `e_dict_item`;

CREATE TABLE `e_dict_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编码',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `sort` int(11) DEFAULT NULL COMMENT '显示顺序',
  `erupt_dict_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKl0kiq8otpn3fvtlvarebt8xkh` (`code`,`erupt_dict_id`),
  KEY `FKrrbi2dt94rjd8sjt830m3w0a` (`erupt_dict_id`),
  CONSTRAINT `FKrrbi2dt94rjd8sjt830m3w0a` FOREIGN KEY (`erupt_dict_id`) REFERENCES `e_dict` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典项';

/*Data for the table `e_dict_item` */

insert  into `e_dict_item`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`code`,`name`,`remark`,`sort`,`erupt_dict_id`) values 
(3,'erupt','2023-03-12 16:30:50.326000','erupt','2023-03-12 16:30:50.326000','问题','问题',NULL,NULL,4),
(4,'erupt','2023-03-12 16:30:59.858000','erupt','2023-03-12 16:30:59.858000','建议','建议',NULL,NULL,4),
(5,'erupt','2023-03-12 16:31:06.639000','erupt','2023-03-12 16:31:06.639000','疑问','疑问',NULL,NULL,4),
(6,'erupt','2023-03-12 16:31:46.256000','erupt','2023-03-12 16:31:46.256000','提示','提示',NULL,NULL,5),
(7,'erupt','2023-03-12 16:31:51.176000','erupt','2023-03-12 16:31:51.176000','一般','一般',NULL,NULL,5),
(8,'erupt','2023-03-12 16:31:57.153000','erupt','2023-03-12 16:31:57.153000','严重','严重',NULL,NULL,5),
(9,'erupt','2023-03-12 16:32:42.848000','erupt','2023-03-12 16:32:42.848000','编码基础类','编码基础类',NULL,NULL,6),
(10,'erupt','2023-03-12 16:32:46.010000','erupt','2023-03-12 16:32:46.010000','业务功能类','业务功能类',NULL,NULL,6),
(11,'erupt','2023-03-12 16:32:54.566000','erupt','2023-03-12 16:32:54.566000','安全可靠类','安全可靠类',NULL,NULL,6),
(12,'erupt','2023-03-12 16:33:01.903000','erupt','2023-03-12 16:33:01.903000','其它','其它',NULL,NULL,6),
(13,'erupt','2023-03-12 16:34:14.791000','erupt','2023-03-12 16:34:14.791000','未确认','未确认','此项不可修改或者删除，系统中默认值，用于判定评审意见是否有被处理',NULL,7),
(14,'erupt','2023-03-12 16:34:23.015000','erupt','2023-03-12 16:34:23.015000','已修改','已修改',NULL,NULL,7),
(15,'erupt','2023-03-12 16:34:30.041000','erupt','2023-03-12 16:34:30.041000','待修改','待修改',NULL,NULL,7),
(16,'erupt','2023-03-12 16:34:39.215000','erupt','2023-03-12 16:34:39.215000','拒绝','拒绝',NULL,NULL,7);

/*Table structure for table `e_upms_login_log` */

DROP TABLE IF EXISTS `e_upms_login_log`;

CREATE TABLE `e_upms_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `browser` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '浏览器',
  `device_type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备类型',
  `ip` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP地址',
  `login_time` datetime(6) DEFAULT NULL COMMENT '登录时间',
  `region` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP来源',
  `system_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作系统',
  `token` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='登录日志';

/*Data for the table `e_upms_login_log` */

/*Table structure for table `e_upms_menu` */

DROP TABLE IF EXISTS `e_upms_menu`;

CREATE TABLE `e_upms_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编码',
  `icon` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `param` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '自定义参数',
  `sort` int(11) DEFAULT NULL COMMENT '顺序',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单类型',
  `value` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型值',
  `parent_menu_id` bigint(20) DEFAULT NULL COMMENT '上级菜单',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK95xpkppt33d2bka0g2d7rgwqt` (`code`),
  KEY `FK5mkgea183mm02v7ic1pdwxy5s` (`parent_menu_id`),
  CONSTRAINT `FK5mkgea183mm02v7ic1pdwxy5s` FOREIGN KEY (`parent_menu_id`) REFERENCES `e_upms_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单管理';

/*Data for the table `e_upms_menu` */

insert  into `e_upms_menu`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`code`,`icon`,`name`,`param`,`sort`,`status`,`type`,`value`,`parent_menu_id`) values 
(1,NULL,'2023-02-26 09:24:55.569000','erupt','2023-02-26 09:26:23.272000','$manager','fa fa-cogs','系统管理',NULL,1000,1,NULL,NULL,NULL),
(2,NULL,'2023-02-26 09:24:55.659000',NULL,NULL,'EruptMenu','','菜单管理',NULL,0,1,'tree','EruptMenu',1),
(3,NULL,'2023-02-26 09:24:55.662000',NULL,NULL,'EruptMenu@ADD',NULL,'新增',NULL,10,1,'button','EruptMenu@ADD',2),
(4,NULL,'2023-02-26 09:24:55.664000',NULL,NULL,'EruptMenu@EDIT',NULL,'修改',NULL,20,1,'button','EruptMenu@EDIT',2),
(5,NULL,'2023-02-26 09:24:55.666000',NULL,NULL,'EruptMenu@DELETE',NULL,'删除',NULL,30,1,'button','EruptMenu@DELETE',2),
(6,NULL,'2023-02-26 09:24:55.668000',NULL,NULL,'EruptMenu@VIEW_DETAIL',NULL,'详情',NULL,40,1,'button','EruptMenu@VIEW_DETAIL',2),
(7,NULL,'2023-02-26 09:24:55.670000','erupt','2023-02-26 09:28:04.679000','EruptRole',NULL,'角色管理',NULL,10,1,'table','EruptRole',44),
(8,NULL,'2023-02-26 09:24:55.672000',NULL,NULL,'EruptRole@ADD',NULL,'新增',NULL,10,1,'button','EruptRole@ADD',7),
(9,NULL,'2023-02-26 09:24:55.674000',NULL,NULL,'EruptRole@EDIT',NULL,'修改',NULL,20,1,'button','EruptRole@EDIT',7),
(10,NULL,'2023-02-26 09:24:55.676000',NULL,NULL,'EruptRole@DELETE',NULL,'删除',NULL,30,1,'button','EruptRole@DELETE',7),
(11,NULL,'2023-02-26 09:24:55.679000',NULL,NULL,'EruptRole@VIEW_DETAIL',NULL,'详情',NULL,40,1,'button','EruptRole@VIEW_DETAIL',7),
(12,NULL,'2023-02-26 09:24:55.681000','erupt','2023-02-26 09:28:13.640000','EruptOrg',NULL,'组织维护',NULL,20,1,'tree','EruptOrg',44),
(13,NULL,'2023-02-26 09:24:55.684000',NULL,NULL,'EruptOrg@ADD',NULL,'新增',NULL,10,1,'button','EruptOrg@ADD',12),
(14,NULL,'2023-02-26 09:24:55.687000',NULL,NULL,'EruptOrg@EDIT',NULL,'修改',NULL,20,1,'button','EruptOrg@EDIT',12),
(15,NULL,'2023-02-26 09:24:55.689000',NULL,NULL,'EruptOrg@DELETE',NULL,'删除',NULL,30,1,'button','EruptOrg@DELETE',12),
(16,NULL,'2023-02-26 09:24:55.692000',NULL,NULL,'EruptOrg@VIEW_DETAIL',NULL,'详情',NULL,40,1,'button','EruptOrg@VIEW_DETAIL',12),
(17,NULL,'2023-02-26 09:24:55.695000',NULL,NULL,'EruptPost','','岗位维护',NULL,30,1,'tree','EruptPost',1),
(18,NULL,'2023-02-26 09:24:55.697000',NULL,NULL,'EruptPost@ADD',NULL,'新增',NULL,10,1,'button','EruptPost@ADD',17),
(19,NULL,'2023-02-26 09:24:55.700000',NULL,NULL,'EruptPost@EDIT',NULL,'修改',NULL,20,1,'button','EruptPost@EDIT',17),
(20,NULL,'2023-02-26 09:24:55.703000',NULL,NULL,'EruptPost@DELETE',NULL,'删除',NULL,30,1,'button','EruptPost@DELETE',17),
(21,NULL,'2023-02-26 09:24:55.707000',NULL,NULL,'EruptPost@VIEW_DETAIL',NULL,'详情',NULL,40,1,'button','EruptPost@VIEW_DETAIL',17),
(22,NULL,'2023-02-26 09:24:55.710000','erupt','2023-02-26 09:28:26.420000','EruptUser',NULL,'用户配置',NULL,40,1,'table','EruptUser',44),
(23,NULL,'2023-02-26 09:24:55.712000',NULL,NULL,'EruptUser@ADD',NULL,'新增',NULL,10,1,'button','EruptUser@ADD',22),
(24,NULL,'2023-02-26 09:24:55.715000',NULL,NULL,'EruptUser@EDIT',NULL,'修改',NULL,20,1,'button','EruptUser@EDIT',22),
(25,NULL,'2023-02-26 09:24:55.718000',NULL,NULL,'EruptUser@DELETE',NULL,'删除',NULL,30,1,'button','EruptUser@DELETE',22),
(26,NULL,'2023-02-26 09:24:55.720000',NULL,NULL,'EruptUser@VIEW_DETAIL',NULL,'详情',NULL,40,1,'button','EruptUser@VIEW_DETAIL',22),
(27,NULL,'2023-02-26 09:24:55.723000',NULL,NULL,'EruptDict','','数据字典',NULL,50,1,'table','EruptDict',1),
(28,NULL,'2023-02-26 09:24:55.725000',NULL,NULL,'EruptDict@ADD',NULL,'新增',NULL,10,1,'button','EruptDict@ADD',27),
(29,NULL,'2023-02-26 09:24:55.727000',NULL,NULL,'EruptDict@EDIT',NULL,'修改',NULL,20,1,'button','EruptDict@EDIT',27),
(30,NULL,'2023-02-26 09:24:55.729000',NULL,NULL,'EruptDict@DELETE',NULL,'删除',NULL,30,1,'button','EruptDict@DELETE',27),
(31,NULL,'2023-02-26 09:24:55.732000',NULL,NULL,'EruptDict@EXPORT',NULL,'导出',NULL,40,1,'button','EruptDict@EXPORT',27),
(32,NULL,'2023-02-26 09:24:55.735000',NULL,NULL,'EruptDict@VIEW_DETAIL',NULL,'详情',NULL,50,1,'button','EruptDict@VIEW_DETAIL',27),
(33,NULL,'2023-02-26 09:24:55.737000',NULL,NULL,'EruptDictItem','','字典项',NULL,60,2,'table','EruptDictItem',1),
(34,NULL,'2023-02-26 09:24:55.740000',NULL,NULL,'EruptDictItem@ADD',NULL,'新增',NULL,10,1,'button','EruptDictItem@ADD',33),
(35,NULL,'2023-02-26 09:24:55.742000',NULL,NULL,'EruptDictItem@EDIT',NULL,'修改',NULL,20,1,'button','EruptDictItem@EDIT',33),
(36,NULL,'2023-02-26 09:24:55.743000',NULL,NULL,'EruptDictItem@DELETE',NULL,'删除',NULL,30,1,'button','EruptDictItem@DELETE',33),
(37,NULL,'2023-02-26 09:24:55.745000',NULL,NULL,'EruptDictItem@EXPORT',NULL,'导出',NULL,40,1,'button','EruptDictItem@EXPORT',33),
(38,NULL,'2023-02-26 09:24:55.747000',NULL,NULL,'EruptDictItem@VIEW_DETAIL',NULL,'详情',NULL,50,1,'button','EruptDictItem@VIEW_DETAIL',33),
(39,NULL,'2023-02-26 09:24:55.749000',NULL,NULL,'EruptOnline','','在线用户',NULL,65,1,'table','EruptOnline',1),
(40,NULL,'2023-02-26 09:24:55.751000',NULL,NULL,'EruptOnline@EXPORT',NULL,'导出',NULL,10,1,'button','EruptOnline@EXPORT',39),
(41,NULL,'2023-02-26 09:24:55.753000',NULL,NULL,'EruptLoginLog','','登录日志',NULL,70,1,'table','EruptLoginLog',1),
(42,NULL,'2023-02-26 09:24:55.754000',NULL,NULL,'EruptLoginLog@EXPORT',NULL,'导出',NULL,10,1,'button','EruptLoginLog@EXPORT',41),
(43,NULL,'2023-02-26 09:24:55.756000',NULL,NULL,'EruptOperateLog','','操作日志',NULL,80,1,'table','EruptOperateLog',1),
(44,'erupt','2023-02-26 09:27:49.134000','erupt','2023-02-26 09:27:49.134000','aV10NMEv',NULL,'用户管理',NULL,500,1,NULL,NULL,NULL),
(45,'erupt','2023-02-26 09:29:01.705000','erupt','2023-02-26 09:29:01.705000','8Aegh6fU',NULL,'评审管理',NULL,100,1,NULL,NULL,NULL),
(46,'erupt','2023-02-26 09:32:45.046000','erupt','2023-02-26 09:32:45.046000','xGQw0jDv',NULL,'项目列表',NULL,1010,1,'table','ProjectEntity',45),
(47,NULL,'2023-02-26 09:32:45.054000',NULL,NULL,'uZFq4YqD',NULL,'新增',NULL,10,1,'button','ProjectEntity@ADD',46),
(48,NULL,'2023-02-26 09:32:45.055000',NULL,NULL,'CFsTA4kI',NULL,'修改',NULL,20,1,'button','ProjectEntity@EDIT',46),
(49,NULL,'2023-02-26 09:32:45.057000',NULL,NULL,'9hYnlml5',NULL,'删除',NULL,30,1,'button','ProjectEntity@DELETE',46),
(50,NULL,'2023-02-26 09:32:45.058000',NULL,NULL,'FYVjNypj',NULL,'导出',NULL,40,1,'button','ProjectEntity@EXPORT',46),
(51,NULL,'2023-02-26 09:32:45.059000',NULL,NULL,'mm7QleVd',NULL,'详情',NULL,50,1,'button','ProjectEntity@VIEW_DETAIL',46),
(52,'erupt','2023-02-26 09:36:30.229000','erupt','2023-02-26 09:36:30.229000','YDHUylIf',NULL,'评审意见',NULL,1020,1,'table','CommentEntity',45),
(53,NULL,'2023-02-26 09:36:30.233000',NULL,NULL,'mXLQDwEA',NULL,'新增',NULL,10,1,'button','CommentEntity@ADD',52),
(54,NULL,'2023-02-26 09:36:30.234000',NULL,NULL,'bEWTSQoP',NULL,'修改',NULL,20,1,'button','CommentEntity@EDIT',52),
(55,NULL,'2023-02-26 09:36:30.235000',NULL,NULL,'Ui6Hi76u',NULL,'删除',NULL,30,1,'button','CommentEntity@DELETE',52),
(56,NULL,'2023-02-26 09:36:30.236000',NULL,NULL,'qATcNtML',NULL,'导出',NULL,40,1,'button','CommentEntity@EXPORT',52),
(57,NULL,'2023-02-26 09:36:30.236000',NULL,NULL,'VD133lC4',NULL,'详情',NULL,50,1,'button','CommentEntity@VIEW_DETAIL',52),
(58,'erupt','2023-03-05 17:44:48.049000','erupt','2023-03-12 22:27:23.835000','Lmdql8VZ',NULL,'评审字段配置',NULL,1030,1,'table','ColumnDefineEntity',45),
(59,NULL,'2023-03-05 17:44:48.060000',NULL,NULL,'SToP1GIQ',NULL,'新增',NULL,10,1,'button','ColumnDefineEntity@ADD',58),
(60,NULL,'2023-03-05 17:44:48.062000',NULL,NULL,'W6sCWxLt',NULL,'修改',NULL,20,1,'button','ColumnDefineEntity@EDIT',58),
(61,NULL,'2023-03-05 17:44:48.064000',NULL,NULL,'W7slJSgX',NULL,'删除',NULL,30,1,'button','ColumnDefineEntity@DELETE',58),
(62,NULL,'2023-03-05 17:44:48.066000',NULL,NULL,'v4is4XJ3',NULL,'详情',NULL,40,1,'button','ColumnDefineEntity@VIEW_DETAIL',58);
(63, NULL, '2023-04-23 13:38:13.018068', NULL, NULL, 'monitor', 'fa fa-bullseye', '系统监控', NULL, 10, 1, NULL, NULL, NULL);
(64, NULL, '2023-04-23 13:38:13.107143', NULL, NULL, 'server.html', NULL, '服务监控', NULL, 10, 1, 'tpl', 'server.html', 63);
(65, NULL, '2023-04-23 13:38:13.113304', NULL, NULL, 'redis.html', NULL, '缓存监控', NULL, 20, 1, 'tpl', 'redis.html', 63);

/*Table structure for table `e_upms_operate_log` */

DROP TABLE IF EXISTS `e_upms_operate_log`;

CREATE TABLE `e_upms_operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `api_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '功能名称',
  `create_time` datetime(6) DEFAULT NULL COMMENT '记录时间',
  `error_info` varchar(4000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '错误信息',
  `ip` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP地址',
  `operate_user` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作人',
  `region` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP来源',
  `req_addr` varchar(4000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求地址',
  `req_method` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求方法',
  `req_param` longtext COLLATE utf8mb4_general_ci COMMENT '请求参数',
  `status` bit(1) DEFAULT NULL COMMENT '是否成功',
  `total_time` bigint(20) DEFAULT NULL COMMENT '请求耗时',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志';

DROP TABLE IF EXISTS `e_upms_org`;

CREATE TABLE `e_upms_org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织编码',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织名称',
  `sort` int(11) DEFAULT NULL COMMENT '显示顺序',
  `parent_org_id` bigint(20) DEFAULT NULL COMMENT '上级组织',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKc2wj35ujq2m84uw59dx6wy3gj` (`code`),
  KEY `FKtj7222kjnkt7pv9kfn9g8ck4h` (`parent_org_id`),
  CONSTRAINT `FKtj7222kjnkt7pv9kfn9g8ck4h` FOREIGN KEY (`parent_org_id`) REFERENCES `e_upms_org` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组织维护';

/*Data for the table `e_upms_org` */

insert  into `e_upms_org`(`id`,`code`,`name`,`sort`,`parent_org_id`) values 
(1,'yfzx','研发中心',NULL,NULL),
(2,'yfb','研发部',NULL,1),
(3,'jgb','架构部',NULL,1),
(4,'jsz','技术组',NULL,2),
(5,'csz','测试组',NULL,2);

/*Table structure for table `e_upms_post` */

DROP TABLE IF EXISTS `e_upms_post`;

CREATE TABLE `e_upms_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '岗位编码',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '岗位名称',
  `weight` int(11) DEFAULT NULL COMMENT '岗位权重',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKltq5h3n5cyyk5nxtjhg9lhidg` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='岗位维护';

/*Data for the table `e_upms_post` */

/*Table structure for table `e_upms_role` */

DROP TABLE IF EXISTS `e_upms_role`;

CREATE TABLE `e_upms_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编码',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `sort` int(11) DEFAULT NULL COMMENT '展示顺序',
  `status` bit(1) DEFAULT NULL COMMENT '状态',
  `create_user_id` bigint(20) DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjgxkp7mr4183tcwosrbqpsl3a` (`code`),
  KEY `FKad39xpgtpmhq0fp5newnabv1g` (`create_user_id`),
  KEY `FKbghup2p4f1x9eokeygyg8p658` (`update_user_id`),
  CONSTRAINT `FKad39xpgtpmhq0fp5newnabv1g` FOREIGN KEY (`create_user_id`) REFERENCES `e_upms_user` (`id`),
  CONSTRAINT `FKbghup2p4f1x9eokeygyg8p658` FOREIGN KEY (`update_user_id`) REFERENCES `e_upms_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色管理';

/*Data for the table `e_upms_role` */

insert  into `e_upms_role`(`id`,`create_time`,`update_time`,`code`,`name`,`sort`,`status`,`create_user_id`,`update_user_id`) values 
(1,'2023-02-26 16:03:53.534000','2023-02-26 16:38:57.011000','admin','ADMIN',10,'',1,1);

/*Table structure for table `e_upms_role_menu` */

DROP TABLE IF EXISTS `e_upms_role_menu`;

CREATE TABLE `e_upms_role_menu` (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `FKr6bl403chgwjnb6jk0uqqd9x8` (`menu_id`),
  CONSTRAINT `FKgsdnakqsme4htxkiapwmf6tbi` FOREIGN KEY (`role_id`) REFERENCES `e_upms_role` (`id`),
  CONSTRAINT `FKr6bl403chgwjnb6jk0uqqd9x8` FOREIGN KEY (`menu_id`) REFERENCES `e_upms_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `e_upms_role_menu` */

insert  into `e_upms_role_menu`(`role_id`,`menu_id`) values 
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(1,8),
(1,9),
(1,10),
(1,11),
(1,12),
(1,13),
(1,14),
(1,15),
(1,16),
(1,17),
(1,18),
(1,19),
(1,20),
(1,21),
(1,22),
(1,23),
(1,24),
(1,25),
(1,26),
(1,27),
(1,28),
(1,29),
(1,30),
(1,31),
(1,32),
(1,33),
(1,34),
(1,35),
(1,36),
(1,37),
(1,38),
(1,39),
(1,40),
(1,41),
(1,42),
(1,43),
(1,44),
(1,45),
(1,46),
(1,47),
(1,48),
(1,49),
(1,50),
(1,51),
(1,52),
(1,53),
(1,54),
(1,55),
(1,56),
(1,57)
(1,63),
(1,64),
(1,65);

/*Table structure for table `e_upms_user` */

DROP TABLE IF EXISTS `e_upms_user`;

CREATE TABLE `e_upms_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `account` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `is_admin` bit(1) DEFAULT NULL COMMENT '超管用户',
  `status` bit(1) DEFAULT NULL COMMENT '账户状态',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(6) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `expire_date` datetime(6) DEFAULT NULL COMMENT '账号失效时间',
  `is_md5` bit(1) DEFAULT NULL COMMENT 'md5加密',
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号码',
  `remark` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `reset_pwd_time` datetime(6) DEFAULT NULL COMMENT '重置密码时间',
  `white_ip` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ip白名单',
  `erupt_org_id` bigint(20) DEFAULT NULL COMMENT '所属组织',
  `erupt_post_id` bigint(20) DEFAULT NULL COMMENT '岗位',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_user_id` bigint(20) DEFAULT NULL,
  `erupt_menu_id` bigint(20) DEFAULT NULL COMMENT '首页菜单',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK812t22yn0295dkkvx5gjgahax` (`account`),
  KEY `FK1re8jv3614mkk2wfxscvgvmnm` (`erupt_org_id`),
  KEY `FK53cice19aydjcuykpv847ocdv` (`erupt_post_id`),
  KEY `FKdvwfw4x66ahh1tavd69cnx4i0` (`create_user_id`),
  KEY `FKct3f9stm4eti10401f7rbh5ey` (`update_user_id`),
  KEY `FKga0jd7sahnn1tv14mq4dy5kba` (`erupt_menu_id`),
  CONSTRAINT `FK1re8jv3614mkk2wfxscvgvmnm` FOREIGN KEY (`erupt_org_id`) REFERENCES `e_upms_org` (`id`),
  CONSTRAINT `FK53cice19aydjcuykpv847ocdv` FOREIGN KEY (`erupt_post_id`) REFERENCES `e_upms_post` (`id`),
  CONSTRAINT `FKct3f9stm4eti10401f7rbh5ey` FOREIGN KEY (`update_user_id`) REFERENCES `e_upms_user` (`id`),
  CONSTRAINT `FKdvwfw4x66ahh1tavd69cnx4i0` FOREIGN KEY (`create_user_id`) REFERENCES `e_upms_user` (`id`),
  CONSTRAINT `FKga0jd7sahnn1tv14mq4dy5kba` FOREIGN KEY (`erupt_menu_id`) REFERENCES `e_upms_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户配置';

/*Data for the table `e_upms_user` */

insert  into `e_upms_user`(`id`,`name`,`account`,`is_admin`,`status`,`create_time`,`update_time`,`email`,`expire_date`,`is_md5`,`password`,`phone`,`remark`,`reset_pwd_time`,`white_ip`,`erupt_org_id`,`erupt_post_id`,`create_user_id`,`update_user_id`,`erupt_menu_id`) values 
(1,'codereview','codereview','','','2023-02-26 09:24:55.767000','2023-03-12 22:29:56.685000',NULL,NULL,'','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,NULL,NULL,4,NULL,NULL,1,NULL);

/*Table structure for table `e_upms_user_role` */

DROP TABLE IF EXISTS `e_upms_user_role`;

CREATE TABLE `e_upms_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FKes2ylim5w3ej690ss84sb956x` (`user_id`),
  CONSTRAINT `FK3h4lekfh26f5f8b7by3ejges6` FOREIGN KEY (`role_id`) REFERENCES `e_upms_role` (`id`),
  CONSTRAINT `FKes2ylim5w3ej690ss84sb956x` FOREIGN KEY (`user_id`) REFERENCES `e_upms_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `e_upms_user_role` */

insert  into `e_upms_user_role`(`user_id`,`role_id`) values 
(1,1);

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comments` text COLLATE utf8mb4_general_ci COMMENT '评审意见',
  `confirm_date` datetime(6) DEFAULT NULL COMMENT '意见确认时间',
  `confirm_notes` text COLLATE utf8mb4_general_ci COMMENT '确认说明',
  `confirm_result` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '确认结果',
  `confirmer` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '确认人员',
  `content` text COLLATE utf8mb4_general_ci COMMENT '代码片段',
  `create_time` datetime(6) DEFAULT NULL COMMENT '记录提交时间',
  `file_path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '代码路径',
  `identifier` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '唯一ID',
  `line_range` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '代码行号范围',
  `review_date` datetime(6) DEFAULT NULL COMMENT '评审时间',
  `reviewer` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评审人员',
  `update_time` datetime(6) DEFAULT NULL COMMENT '最后更新时间',
  `project_id` bigint(20) DEFAULT NULL COMMENT '归属项目',
  PRIMARY KEY (`id`),
  KEY `FKchqbli5knfkn4aygo7rnkcxai` (`project_id`),
  CONSTRAINT `FKchqbli5knfkn4aygo7rnkcxai` FOREIGN KEY (`project_id`) REFERENCES `t_project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='评审意见';

/*Data for the table `t_comment` */

/*Table structure for table `t_comment_column` */

DROP TABLE IF EXISTS `t_comment_column`;

CREATE TABLE `t_comment_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `column_code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '唯一编码',
  `confirm_prop` bit(1) NOT NULL COMMENT '确认界面显示',
  `editable` bit(1) NOT NULL COMMENT '是否可编辑',
  `editable_in_confirm_page` bit(1) NOT NULL COMMENT '确认界面是否允许修改',
  `excel_column_width` int(11) NOT NULL COMMENT 'excel占用列宽',
  `input_type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '输入类型',
  `required` bit(1) NOT NULL COMMENT '是否必填',
  `show_in_add_page` bit(1) NOT NULL COMMENT '是否显示在新增界面',
  `show_in_comfirm_page` bit(1) NOT NULL COMMENT '是否显示在确认界面',
  `show_in_table` bit(1) NOT NULL COMMENT '是否显示在表格中',
  `show_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对外显示名称',
  `sort_index` int(11) NOT NULL COMMENT '排序号',
  `support_in_excel` bit(1) NOT NULL COMMENT '是否支持导出到表格中',
  `system_initialization` bit(1) NOT NULL COMMENT '是否系统预置',
  `combo_box_dict_code_id` bigint(20) DEFAULT NULL COMMENT '下拉框类型的动态拉取Code',
  PRIMARY KEY (`id`),
  KEY `FK1h20r5nysqicpr15yiimkwean` (`combo_box_dict_code_id`),
  CONSTRAINT `FK1h20r5nysqicpr15yiimkwean` FOREIGN KEY (`combo_box_dict_code_id`) REFERENCES `e_dict` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='评审字段定义';

/*Data for the table `t_comment_column` */

insert  into `t_comment_column`(`id`,`column_code`,`confirm_prop`,`editable`,`editable_in_confirm_page`,`excel_column_width`,`input_type`,`required`,`show_in_add_page`,`show_in_comfirm_page`,`show_in_table`,`show_name`,`sort_index`,`support_in_excel`,`system_initialization`,`combo_box_dict_code_id`) values 
(22,'identifier','\0','\0','\0',15,'TEXT','','\0','\0','','ID',0,'','',NULL),
(23,'filePath','\0','\0','\0',50,'TEXT','','','','','文件路径',1,'','',NULL),
(24,'content','\0','\0','\0',50,'TEXTAREA','','','','','代码片段',2,'','',NULL),
(25,'lineRange','\0','\0','\0',15,'TEXT','','','','','代码行号',3,'','',NULL),
(26,'comment','\0','','\0',50,'TEXTAREA','','','','','检视意见',4,'','',NULL),
(27,'type','\0','','\0',15,'COMBO_BOX','','','','','意见类型',5,'','\0',4),
(28,'level','\0','','\0',15,'COMBO_BOX','','','','','严重级别',6,'','\0',5),
(29,'belongingTo','\0','','\0',20,'COMBO_BOX','','','','','问题归类',7,'','\0',6),
(30,'reviewDate','\0','\0','\0',20,'TEXT','','\0','\0','\0','检视时间',8,'','',NULL),
(31,'reviewer','\0','','\0',20,'COMBO_BOX','','','','','检视人员',9,'','\0',8),
(32,'confirmResult','','','',20,'COMBO_BOX','\0','\0','','','确认结果',10,'','\0',7),
(33,'confirmNotes','','','',50,'TEXTAREA','\0','\0','','','确认说明',11,'','\0',NULL),
(34,'projectVersion','\0','','',30,'TEXT','\0','','','','项目版本',12,'','\0',NULL),
(35,'blongingIssue','','','',30,'TEXT','\0','','','','问题归属',13,'','\0',NULL),
(36,'confirmer','','','',20,'COMBO_BOX','\0','\0','','','确认人员',14,'','\0',8);

/*Table structure for table `t_project` */

DROP TABLE IF EXISTS `t_project`;

CREATE TABLE `t_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_desc` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '项目描述信息',
  `project_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '项目名称',
  `department_id` bigint(20) DEFAULT NULL COMMENT '所属组织',
  PRIMARY KEY (`id`),
  KEY `FKir1vk2mhtdjyb0hlm7e8jqe15` (`department_id`),
  CONSTRAINT `FKir1vk2mhtdjyb0hlm7e8jqe15` FOREIGN KEY (`department_id`) REFERENCES `e_upms_org` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='项目信息';

/*Data for the table `t_project` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
