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
CREATE DATABASE /*!32312 IF NOT EXISTS*/`code_review` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

use code_review;

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
  UNIQUE KEY `idx_column_code` (`column_code`),
  KEY `FK40nj1kkbgbmyghy2jd8xaap5x` (`dict_collection_code`) 
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_comment_column` */

INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('identifier', false, false, false, 30, 'TEXT', true, false, true, true, true, true, 100, 'ID', 0, true, true, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('filePath', true, true, false, 50, 'TEXT', true, true, true, true, true, true, 150, '文件路径', 100, true, true, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('content', true, true, false, 100, 'TEXTAREA', true, true, true, false, true, true, 400, '代码片段', 300, true, true, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('lineRange', true, true, false, 50, 'TEXT', true, true, true, true, true, true, 100, '代码行号', 200, true, true, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('comment', true, false, true, 100, 'TEXTAREA', true, true, false, true, true, true, 300, '检视意见', 600, true, true, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('type', true, true, true, 50, 'COMBO_BOX', false, true, true, true, true, true, 100, '意见类型', 400, true, false, 'CommentType');
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('projectId', true, true, true, 30, 'COMBO_BOX', false, true, true, true, true, true, 200, '项目信息', 1, true, true, 'ProjectList');
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('reviewDate', false, false, false, 50, 'DATE', true, false, false, true, true, true, 110, '检视时间', 500, true, true, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('realConfirmer', false, false, false, 50, 'COMBO_BOX', true, false, false, false, true, true, 100, '实际确认人员', 900, true, true, 'UserList');
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('confirmResult', true, false, true, 50, 'COMBO_BOX', false, false, false, true, true, true, 100, '确认结果', 1000, true, true, 'ConfirmResult');
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('assignConfirmer', true, true, false, 50, 'COMBO_BOX', false, true, true, false, true, true, 100, '指定确认人员', 700, true, true, 'UserList');
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('reviewer', false, true, false, 50, 'COMBO_BOX', true, false, true, true, true, true, 100, '检视人员', 450, true, true, 'UserList');
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('confirmNotes', false, false, true, 50, 'TEXTAREA', false, false, false, true, true, true, 300, '确认说明', 1100, true, false, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('confirmDate', false, false, false, 50, 'DATE', false, false, false, false, true, true, 110, '确认时间', 1200, true, false, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('gitBranchName', false, false, false, 50, 'TEXT', false, true, true, true, true, true, 200, 'gitBranchName', 3, true, false, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('gitRepositoryName', false, false, false, 50, 'TEXT', false, true, true, true, true, true, 200, 'gitRepositoryName', 2, true, false, null);

/*Table structure for table `t_department` */

DROP TABLE IF EXISTS `t_department`;

CREATE TABLE `t_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjoxpd0y26uhuy0j085jvqmlo8` (`parent_id`),
  CONSTRAINT `FKjoxpd0y26uhuy0j085jvqmlo8` FOREIGN KEY (`parent_id`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_department` */

insert  into `t_department`(`id`,`name`,`parent_id`) values 
(11,'研发中心',NULL),
(12,'业务开发部',11),
(13,'基础开发部',11),
(14,'系统架构部',11),
(15,'业务开发一组',12),
(16,'业务开发二组',12),
(17,'中间件开发组',13),
(18,'大数据算法团队',13),
(19,'系统架构一组',14),
(20,'系统架构二组',14);

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_project` */

insert  into `t_project`(`id`,`project_desc`,`project_name`,`department_id`) values 
(12,'业务开发一组测试项目1','业务开发一组测试项目1',15),
(13,'业务开发一组测试项目2','业务开发一组测试项目2',15),
(14,'业务开发二组测试项目1','业务开发二组测试项目1',16);

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

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_desc` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `can_access_page` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role_code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`role_desc`,`role_name`,`can_access_page`,`role_code`) values 
(8,'系统管理员角色，拥有系统所有权限。','系统管理员','reviewcomments,mytodo,mycommitted,myconfirmed,all,servMgt,commentFields,enums,systemConfig,users,roles,depts,projs,reports,dashboard','admin'),
(9,'普通用户，有权限进行与自己有关的评审数据的处理，比如我的待办、我提交的、我确认的','普通用户','reviewcomments,mytodo,mycommitted,myconfirmed','normal');

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
(1,'test','测试用户','e10adc3949ba59abbe56e057f20f883e','3333377',15),
(17,'codereview','管理员','e10adc3949ba59abbe56e057f20f883e','',11);

/*Table structure for table `t_user_login_token` */

DROP TABLE IF EXISTS `t_user_login_token`;

CREATE TABLE `t_user_login_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expire_at` bigint(20) NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_raqqcu6ei2s8meuhatddppu76` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_user_login_token` */

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
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`id`,`role_id`,`user_id`) values 
(55,8,17),
(57,9,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
