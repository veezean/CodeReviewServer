/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.11 : Database - code_review2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`code_review2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comments` text COLLATE utf8mb4_general_ci,
  `confirm_date` datetime(6) DEFAULT NULL,
  `confirm_notes` text COLLATE utf8mb4_general_ci,
  `confirm_result` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `confirmer` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `content` text COLLATE utf8mb4_general_ci,
  `create_time` datetime(6) DEFAULT NULL,
  `file_path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `identifier` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `line_range` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `review_date` datetime(6) DEFAULT NULL,
  `reviewer` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `cas_version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKchqbli5knfkn4aygo7rnkcxai` (`project_id`),
  CONSTRAINT `FKchqbli5knfkn4aygo7rnkcxai` FOREIGN KEY (`project_id`) REFERENCES `t_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_comment` */

/*Table structure for table `t_comment_column` */

DROP TABLE IF EXISTS `t_comment_column`;

CREATE TABLE `t_comment_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `column_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `editable_in_add_page` bit(1) NOT NULL,
  `editable_in_edit_page` bit(1) NOT NULL,
  `editable_in_confirm_page` bit(1) NOT NULL,
  `excel_column_width` int(11) NOT NULL,
  `input_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `required` bit(1) NOT NULL,
  `show_in_add_page` bit(1) NOT NULL,
  `show_in_edit_page` bit(1) NOT NULL,
  `show_in_confirm_page` bit(1) NOT NULL,
  `show_in_idea_table` bit(1) NOT NULL,
  `show_in_web_table` bit(1) NOT NULL,
  `web_table_column_width` int(11) NOT NULL,
  `show_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sort_index` int(11) NOT NULL,
  `support_in_excel` bit(1) NOT NULL,
  `system_initialization` bit(1) NOT NULL,
  `dict_collection_code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK40nj1kkbgbmyghy2jd8xaap5x` (`dict_collection_code`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_comment_column` */

insert  into `t_comment_column`(`id`,`column_code`,`editable_in_add_page`,`editable_in_edit_page`,`editable_in_confirm_page`,`excel_column_width`,`input_type`,`required`,`show_in_add_page`,`show_in_edit_page`,`show_in_confirm_page`,`show_in_idea_table`,`show_in_web_table`,`web_table_column_width`,`show_name`,`sort_index`,`support_in_excel`,`system_initialization`,`dict_collection_code`) values 
(10,'identifier','\0','\0','\0',50,'TEXT','','\0','','','','',200,'ID',0,'','',NULL),
(11,'filePath','','','\0',50,'TEXT','','','','','','',100,'文件路径',100,'','',NULL),
(12,'content','','','\0',50,'TEXTAREA','','','','\0','','',400,'代码片段',300,'','',NULL),
(13,'lineRange','','','\0',50,'TEXT','','','','','','',100,'代码行号',200,'','',NULL),
(14,'comment','','\0','',50,'TEXTAREA','','','\0','','','',300,'检视意见',600,'','',NULL),
(15,'type','','','',50,'COMBO_BOX','\0','','','','','',100,'意见类型',400,'','\0','CommentType'),
(16,'projectId','','','',50,'COMBO_BOX','\0','','','','','',100,'项目信息',1,'','','ProjectList'),
(17,'reviewDate','\0','\0','\0',50,'TEXT','','\0','\0','','','',110,'检视时间',500,'','',NULL),
(18,'realConfirmer','\0','\0','\0',50,'COMBO_BOX','','\0','\0','\0','','',100,'实际确认人员',900,'','','UserList'),
(19,'confirmResult','','\0','',50,'COMBO_BOX','\0','\0','\0','','','',100,'确认结果',1000,'','','ConfirmResult'),
(20,'assignConfirmer','','','\0',50,'COMBO_BOX','\0','','','\0','','',100,'指定确认人员',700,'','','UserList'),
(22,'reviewer','\0','','\0',50,'COMBO_BOX','','\0','','','','',100,'检视人员',450,'','','UserList'),
(23,'confirmNotes','\0','\0','',50,'TEXTAREA','\0','\0','\0','','','',300,'确认说明',1100,'','\0',NULL),
(24,'confirmDate','\0','\0','\0',50,'TEXT','\0','\0','\0','\0','','',110,'确认时间',1200,'','\0',NULL);

/*Table structure for table `t_department` */

DROP TABLE IF EXISTS `t_department`;

CREATE TABLE `t_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjoxpd0y26uhuy0j085jvqmlo8` (`parent_id`),
  CONSTRAINT `FKjoxpd0y26uhuy0j085jvqmlo8` FOREIGN KEY (`parent_id`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_department` */

insert  into `t_department`(`id`,`name`,`parent_id`) values 
(3,'dev',NULL),
(4,'dev222',3),
(5,'dev3333',3),
(6,'ddddd',NULL),
(8,'市场部',NULL),
(9,'市场部一组',8),
(10,'市场部二组322',8);

/*Table structure for table `t_dict_collection` */

DROP TABLE IF EXISTS `t_dict_collection`;

CREATE TABLE `t_dict_collection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dict_desc` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0:枚举列表，1:动态数据',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_dict_collection` */

insert  into `t_dict_collection`(`id`,`code`,`dict_desc`,`name`,`type`) values 
(18,'CommentType','','意见类型',0),
(19,'ConfirmResult','','确认结果',0),
(21,'ProjectList','系统预置动态列表，会拉取系统内所有项目名称与ID，作为下拉框选项','项目列表',1),
(22,'UserList','系统预置动态列表，会拉取系统内所有用户姓名与账号，作为下拉框选项','用户列表',1);

/*Table structure for table `t_dict_item` */

DROP TABLE IF EXISTS `t_dict_item`;

CREATE TABLE `t_dict_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `show_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sort` int(11) NOT NULL,
  `collection_id` bigint(20) DEFAULT NULL,
  `item_desc` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `value` (`value`,`collection_id`),
  KEY `FKfw17rrevpp6oe72lom3fb0otk` (`collection_id`),
  CONSTRAINT `FKfw17rrevpp6oe72lom3fb0otk` FOREIGN KEY (`collection_id`) REFERENCES `t_dict_collection` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_dict_item` */

insert  into `t_dict_item`(`id`,`show_name`,`value`,`sort`,`collection_id`,`item_desc`) values 
(12,'问题','1',0,18,NULL),
(13,'建议','2',10,18,NULL),
(14,'疑问','3',20,18,NULL),
(15,'未确认','unconfirmed',0,19,'注意这个unconfirmed值要固定，不要修改'),
(16,'已修改','2',10,19,NULL),
(17,'待修改','3',20,19,NULL),
(18,'拒绝','4',30,19,NULL);

/*Table structure for table `t_project` */

DROP TABLE IF EXISTS `t_project`;

CREATE TABLE `t_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_desc` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `project_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ja6dem8nnpdb5chengrxi6aj` (`department_id`),
  CONSTRAINT `FK6ja6dem8nnpdb5chengrxi6aj` FOREIGN KEY (`department_id`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_project` */

insert  into `t_project`(`id`,`project_desc`,`project_name`,`department_id`) values 
(10,'222','222',3),
(11,'222','111',8);

/*Table structure for table `t_review_comment` */

DROP TABLE IF EXISTS `t_review_comment`;

CREATE TABLE `t_review_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `real_confirm_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `confirm_result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `json_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_modified_user` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `data_version` bigint(20) NOT NULL,
  `assign_confirm_user` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `confirm_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `identifier` (`identifier`),
  KEY `confirm_result` (`confirm_result`),
  KEY `real_confirm_user` (`real_confirm_user`),
  KEY `create_user` (`create_user`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_review_comment` */

insert  into `t_review_comment`(`id`,`identifier`,`project_id`,`create_user`,`create_time`,`real_confirm_user`,`confirm_result`,`json_data`,`last_modified_user`,`last_modified_time`,`data_version`,`assign_confirm_user`,`confirm_time`) values 
(21,'4hxwxacm6n1l2i9k0t5x',7,'test','2023-06-06 22:14:12','test','2','[{\"code\":\"identifier\",\"editable\":false,\"inputType\":\"TEXT\",\"required\":true,\"show\":true,\"showName\":\"ID\",\"value\":\"4hxwxacm6n1l2i9k0t5x\"},{\"code\":\"projectId\",\"editable\":true,\"enumValues\":[{\"showName\":\"sdfsdfsd4444444\",\"value\":\"5\"},{\"showName\":\"3333333333333333333333333333\",\"value\":\"6\"},{\"showName\":\"dsfsdfs\",\"value\":\"7\"},{\"showName\":\"werwe\",\"value\":\"8\"}],\"inputType\":\"COMBO_BOX\",\"required\":false,\"show\":true,\"showName\":\"项目信息\",\"value\":\"7\"},{\"code\":\"filePath\",\"editable\":false,\"inputType\":\"TEXT\",\"required\":true,\"show\":true,\"showName\":\"文件路径\",\"value\":\"11\"},{\"code\":\"lineRange\",\"editable\":false,\"inputType\":\"TEXT\",\"required\":true,\"show\":true,\"showName\":\"代码行号\",\"value\":\"11\"},{\"code\":\"content\",\"editable\":false,\"inputType\":\"TEXTAREA\",\"required\":true,\"show\":false,\"showName\":\"代码片段\",\"value\":\"1111\"},{\"code\":\"type\",\"editable\":true,\"enumValues\":[{\"showName\":\"问题\",\"value\":\"1\"},{\"showName\":\"建议\",\"value\":\"2\"},{\"showName\":\"疑问\",\"value\":\"3\"}],\"inputType\":\"COMBO_BOX\",\"required\":false,\"show\":true,\"showName\":\"意见类型\",\"value\":\"2\"},{\"code\":\"reviewer\",\"editable\":false,\"enumValues\":[{\"showName\":\"测试用户833\",\"value\":\"test\"},{\"showName\":\"test7\",\"value\":\"test7\"},{\"showName\":\"test8\",\"value\":\"test8\"},{\"showName\":\"eee\",\"value\":\"eee\"},{\"showName\":\"444\",\"value\":\"444\"}],\"inputType\":\"COMBO_BOX\",\"required\":true,\"show\":true,\"showName\":\"检视人员\",\"value\":\"test\"},{\"code\":\"reviewDate\",\"editable\":false,\"inputType\":\"TEXT\",\"required\":true,\"show\":true,\"showName\":\"检视时间\",\"value\":\"2023-06-06 22:14:12\"},{\"code\":\"comment\",\"editable\":true,\"inputType\":\"TEXTAREA\",\"required\":true,\"show\":true,\"showName\":\"检视意见\",\"value\":\"111\"},{\"code\":\"assignConfirmer\",\"editable\":false,\"enumValues\":[{\"showName\":\"测试用户833\",\"value\":\"test\"},{\"showName\":\"test7\",\"value\":\"test7\"},{\"showName\":\"test8\",\"value\":\"test8\"},{\"showName\":\"eee\",\"value\":\"eee\"},{\"showName\":\"444\",\"value\":\"444\"}],\"inputType\":\"COMBO_BOX\",\"required\":false,\"show\":false,\"showName\":\"指定确认人员\",\"value\":\"test\"},{\"code\":\"realConfirmer\",\"editable\":false,\"enumValues\":[{\"showName\":\"测试用户833\",\"value\":\"test\"},{\"showName\":\"test7\",\"value\":\"test7\"},{\"showName\":\"test8\",\"value\":\"test8\"},{\"showName\":\"eee\",\"value\":\"eee\"},{\"showName\":\"444\",\"value\":\"444\"}],\"inputType\":\"COMBO_BOX\",\"required\":true,\"show\":false,\"showName\":\"实际确认人员\",\"value\":\"test\"},{\"code\":\"confirmResult\",\"editable\":true,\"enumValues\":[{\"showName\":\"未确认\",\"value\":\"unconfirmed\"},{\"showName\":\"已修改\",\"value\":\"2\"},{\"showName\":\"待修改\",\"value\":\"3\"},{\"showName\":\"拒绝\",\"value\":\"4\"}],\"inputType\":\"COMBO_BOX\",\"required\":false,\"show\":true,\"showName\":\"确认结果\",\"value\":\"2\"},{\"code\":\"confirmNotes\",\"editable\":true,\"inputType\":\"TEXTAREA\",\"required\":false,\"show\":true,\"showName\":\"确认说明\",\"value\":\"fffffff\"},{\"code\":\"confirmDate\",\"editable\":false,\"inputType\":\"TEXT\",\"required\":false,\"show\":false,\"showName\":\"确认时间\",\"value\":\"2023-06-06 22:14:12\"}]','test','2023-06-06 22:37:28',2,'test','2023-06-06 22:14:12');

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_desc` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`role_desc`,`role_name`) values 
(1,'111','admin'),
(2,'222','test'),
(3,'333','333'),
(4,'444','444'),
(5,'555','555'),
(6,'666','666'),
(7,'777','77777');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs1rnm14ev9cb4bbxcspqmcto3` (`department_id`),
  CONSTRAINT `FKs1rnm14ev9cb4bbxcspqmcto3` FOREIGN KEY (`department_id`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`account`,`name`,`password`,`phone_number`,`department_id`) values 
(1,'test','测试用户833','e10adc3949ba59abbe56e057f20f883e','3333377',5),
(6,'test7','test7','test7','test7',4),
(14,'eee','eee','e10adc3949ba59abbe56e057f20f883e','333333',3),
(15,'444','444','e10adc3949ba59abbe56e057f20f883e','444',3),
(16,'wwr','王某某','123456','11111',6),
(17,'codereview','管理员','e10adc3949ba59abbe56e057f20f883e','',3);

/*Table structure for table `t_user_login_token` */

DROP TABLE IF EXISTS `t_user_login_token`;

CREATE TABLE `t_user_login_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expire_at` bigint(20) NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_raqqcu6ei2s8meuhatddppu76` (`token`),
  KEY `FKthkicyakc0w402iniqyjd0ghw` (`user_id`),
  CONSTRAINT `FKthkicyakc0w402iniqyjd0ghw` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_user_login_token` */

insert  into `t_user_login_token`(`id`,`expire_at`,`token`,`user_id`) values 
(22,1690032452274,'x7623dbx51tkult7egokkctpafnmpuoi',1),
(23,1690032545192,'s7cbvnjvhzwtijdg3rbso8iuf4p3xd96',17);

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa9c8iiy6ut0gnx491fqx4pxam` (`role_id`),
  KEY `FKq5un6x7ecoef5w1n39cop66kl` (`user_id`),
  CONSTRAINT `FKa9c8iiy6ut0gnx491fqx4pxam` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FKq5un6x7ecoef5w1n39cop66kl` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`id`,`role_id`,`user_id`) values 
(38,2,15),
(39,1,15),
(40,1,16),
(41,1,1),
(42,2,1),
(43,3,1),
(44,1,17);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
