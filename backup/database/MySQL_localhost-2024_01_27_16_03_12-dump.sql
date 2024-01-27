-- MySQL dump 10.13  Distrib 5.7.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hfb
-- ------------------------------------------------------
-- Server version	5.7.33-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED='257a497e-3211-11ee-a7c9-6c240813fb00:1-169625';

--
-- Current Database: `hfb`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `hfb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `hfb`;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_code` varchar(50) NOT NULL DEFAULT '0' COMMENT '用户code',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '帐户可用余额',
  `freeze_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '投资中冻结金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户账号表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (15,'9ad90c27ba464181bb0715273ca1849d',0.00,0.00,'2024-01-25 04:27:51','2024-01-25 04:27:51',0),(16,'f08a322965f24e3bb4a7afc781b63363',0.00,0.00,'2024-01-25 04:47:26','2024-01-25 04:47:26',0),(17,'6266516709fa4eb38d808ec39399567a',74849.26,0.00,'2024-01-25 04:49:47','2024-01-25 04:49:47',0),(18,'f2c18a112f8948e98adff24bd86c3c05',116368.31,0.00,'2024-01-26 08:20:02','2024-01-26 08:20:02',0);
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_bind`
--

DROP TABLE IF EXISTS `user_bind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_bind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `agent_id` int(11) NOT NULL DEFAULT '0' COMMENT '商户id',
  `agent_user_id` varchar(50) NOT NULL DEFAULT '0' COMMENT 'P2P商户的用户id',
  `personal_name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `id_card` varchar(20) NOT NULL DEFAULT '' COMMENT '身份证号',
  `bank_no` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `return_url` varchar(255) DEFAULT NULL,
  `notify_url` varchar(255) DEFAULT NULL,
  `timestamp` bigint(1) DEFAULT NULL,
  `bind_code` varchar(50) DEFAULT NULL COMMENT '绑定的汇付宝id',
  `pay_passwd` varchar(50) DEFAULT NULL COMMENT '支付密码',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户绑定表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_bind`
--

LOCK TABLES `user_bind` WRITE;
/*!40000 ALTER TABLE `user_bind` DISABLE KEYS */;
INSERT INTO `user_bind` VALUES (15,999888,'2','Crystal Boyle','15500816515','BindAgreeUserV2 KC','KC773944AEJJ20Q8AF',NULL,'http://localhost:3000/user','http://localhost/api/core/userBind/notify',1706154846307,'9ad90c27ba464181bb0715273ca1849d','123123',0,'2024-01-25 04:27:51','2024-01-25 04:27:51',0),(16,999888,'2','Joey Kemmer','14708531601','897637200807275159','142878202108169100',NULL,'http://localhost:3000/user','http://localhost/api/core/userBind/notify',1706158035111,'f08a322965f24e3bb4a7afc781b63363','123123',0,'2024-01-25 04:47:26','2024-01-25 04:47:26',0),(17,999888,'2','Casey Schowalter','18506365596','182008199709242074','693542201007283493',NULL,'http://localhost:3000/user','http://localhost/api/core/userBind/notify',1706158181787,'6266516709fa4eb38d808ec39399567a','123123',0,'2024-01-25 04:49:47','2024-01-25 04:49:47',0),(18,999888,'1','Mr. Geraldine Sc','14766949886','708218201503218137','726958201912118659',NULL,'http://localhost:3000/user','http://localhost/api/core/userBind/notify',1706257193474,'f2c18a112f8948e98adff24bd86c3c05','123123',0,'2024-01-26 08:20:02','2024-01-26 08:20:02',0);
/*!40000 ALTER TABLE `user_bind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_invest`
--

DROP TABLE IF EXISTS `user_invest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_invest` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `vote_bind_code` varchar(50) NOT NULL DEFAULT '0' COMMENT '投资人绑定协议号',
  `benefit_bind_code` varchar(50) DEFAULT '0' COMMENT '借款人绑定协议号',
  `agent_project_code` varchar(50) DEFAULT '' COMMENT '项目编号',
  `agent_project_name` varchar(100) DEFAULT '' COMMENT '项目名称',
  `agent_bill_no` varchar(50) DEFAULT '' COMMENT '商户订单号',
  `vote_amt` decimal(10,2) DEFAULT NULL COMMENT '投资金额',
  `vote_prize_amt` decimal(10,2) DEFAULT NULL COMMENT '投资奖励金额',
  `vote_fee_amt` decimal(10,2) DEFAULT '0.00' COMMENT 'P2P商户手续费',
  `project_amt` decimal(10,2) DEFAULT NULL COMMENT '项目总金额',
  `note` varchar(200) DEFAULT NULL COMMENT '投资备注',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态（0：默认 1：已放款 -1：已撤标）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户投资表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_invest`
--

LOCK TABLES `user_invest` WRITE;
/*!40000 ALTER TABLE `user_invest` DISABLE KEYS */;
INSERT INTO `user_invest` VALUES (1,'f2c18a112f8948e98adff24bd86c3c05','6266516709fa4eb38d808ec39399567a','LEND20240126115032094','Test','INVEST20240126174858975',100.00,0.00,0.00,1000.00,'投资',1,'2024-01-26 09:49:01','2024-01-26 12:27:12',0),(2,'f2c18a112f8948e98adff24bd86c3c05','6266516709fa4eb38d808ec39399567a','LEND20240126115032094','Test','INVEST20240126191642100',100.00,0.00,0.00,1000.00,'投资',1,'2024-01-26 11:16:45','2024-01-26 12:27:12',0),(3,'f2c18a112f8948e98adff24bd86c3c05','6266516709fa4eb38d808ec39399567a','LEND20240126115032094','Test','INVEST20240126191839771',100.00,0.00,0.00,1000.00,'投资',1,'2024-01-26 11:18:42','2024-01-26 12:27:12',0),(4,'f2c18a112f8948e98adff24bd86c3c05','6266516709fa4eb38d808ec39399567a','LEND20240126223141893','发达歌','INVEST20240126224237980',100000.00,0.00,0.00,100000.00,'投资',1,'2024-01-26 14:42:51','2024-01-26 14:43:24',0);
/*!40000 ALTER TABLE `user_invest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_item_return`
--

DROP TABLE IF EXISTS `user_item_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_item_return` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_return_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '还款id',
  `agent_project_code` varchar(50) DEFAULT '0' COMMENT '还款项目编号',
  `vote_bill_no` varchar(50) DEFAULT '' COMMENT '投资单号',
  `to_bind_code` varchar(50) DEFAULT '0.00' COMMENT '收款人（投资人）',
  `transit_amt` decimal(10,2) DEFAULT NULL COMMENT '还款金额',
  `base_amt` decimal(10,2) DEFAULT NULL COMMENT '还款本金',
  `benifit_amt` decimal(10,2) DEFAULT NULL COMMENT '还款利息',
  `fee_amt` decimal(10,2) DEFAULT NULL COMMENT '商户手续费',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户还款明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_item_return`
--

LOCK TABLES `user_item_return` WRITE;
/*!40000 ALTER TABLE `user_item_return` DISABLE KEYS */;
INSERT INTO `user_item_return` VALUES (1,1,'LEND20240126223141893','INVEST20240126224237980','f2c18a112f8948e98adff24bd86c3c05',5166.66,4166.67,999.99,0.00,NULL,0,'2024-01-27 05:48:07','2024-01-27 05:48:07',0),(2,2,'LEND20240126223141893','INVEST20240126224237980','f2c18a112f8948e98adff24bd86c3c05',5166.66,4166.67,999.99,0.00,NULL,0,'2024-01-27 05:53:09','2024-01-27 05:53:09',0),(3,3,'LEND20240126223141893','INVEST20240126224237980','f2c18a112f8948e98adff24bd86c3c05',5124.99,4166.67,958.32,0.00,NULL,0,'2024-01-27 05:53:43','2024-01-27 05:53:43',0);
/*!40000 ALTER TABLE `user_item_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_return`
--

DROP TABLE IF EXISTS `user_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_return` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `agent_goods_name` varchar(200) NOT NULL DEFAULT '0' COMMENT '商户商品名称',
  `agent_batch_no` varchar(50) DEFAULT '0' COMMENT '批次号',
  `from_bind_code` varchar(50) DEFAULT '' COMMENT '还款人绑定协议号',
  `total_amt` decimal(10,2) DEFAULT '0.00' COMMENT '还款总额',
  `vote_fee_amt` decimal(10,2) DEFAULT NULL COMMENT 'P2P商户手续费',
  `data` json DEFAULT NULL COMMENT '还款明细数据',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户还款表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_return`
--

LOCK TABLES `user_return` WRITE;
/*!40000 ALTER TABLE `user_return` DISABLE KEYS */;
INSERT INTO `user_return` VALUES (1,'发达歌','RETURN20240126224324669','6266516709fa4eb38d808ec39399567a',5166.66,0.00,'[{\"feeAmt\": 0, \"baseAmt\": 4166.67, \"benifitAmt\": 999.99, \"toBindCode\": \"f2c18a112f8948e98adff24bd86c3c05\", \"transitAmt\": 5166.66, \"voteBillNo\": \"INVEST20240126224237980\", \"agentProjectCode\": \"LEND20240126223141893\"}]','用户2还款',0,'2024-01-27 05:48:07','2024-01-27 05:48:07',0),(2,'发达歌','RETURN20240126224324669','6266516709fa4eb38d808ec39399567a',5166.66,0.00,'[{\"feeAmt\": 0, \"baseAmt\": 4166.67, \"benifitAmt\": 999.99, \"toBindCode\": \"f2c18a112f8948e98adff24bd86c3c05\", \"transitAmt\": 5166.66, \"voteBillNo\": \"INVEST20240126224237980\", \"agentProjectCode\": \"LEND20240126223141893\"}]','用户2还款',0,'2024-01-27 05:53:09','2024-01-27 05:53:09',0),(3,'发达歌','RETURN20240126224324334','6266516709fa4eb38d808ec39399567a',5124.99,0.00,'[{\"feeAmt\": 0, \"baseAmt\": 4166.67, \"benifitAmt\": 958.32, \"toBindCode\": \"f2c18a112f8948e98adff24bd86c3c05\", \"transitAmt\": 5124.99, \"voteBillNo\": \"INVEST20240126224237980\", \"agentProjectCode\": \"LEND20240126223141893\"}]','用户2还款',0,'2024-01-27 05:53:43','2024-01-27 05:53:43',0);
/*!40000 ALTER TABLE `user_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `srb_core`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `srb_core` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `srb_core`;

--
-- Table structure for table `borrow_info`
--

DROP TABLE IF EXISTS `borrow_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrow_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '借款用户id',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '借款金额',
  `period` int(11) DEFAULT NULL COMMENT '借款期限',
  `borrow_year_rate` decimal(10,2) DEFAULT NULL COMMENT '年化利率',
  `return_method` tinyint(3) DEFAULT NULL COMMENT '还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本',
  `money_use` tinyint(3) DEFAULT NULL COMMENT '资金用途',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态（0：未提交，1：审核中， 2：审核通过， -1：审核不通过）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='借款信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow_info`
--

LOCK TABLES `borrow_info` WRITE;
/*!40000 ALTER TABLE `borrow_info` DISABLE KEYS */;
INSERT INTO `borrow_info` VALUES (2,2,100000.00,24,0.12,2,3,2,'2024-01-26 14:02:44','2024-01-26 14:02:44',0);
/*!40000 ALTER TABLE `borrow_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower`
--

DROP TABLE IF EXISTS `borrower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrower` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(20) NOT NULL DEFAULT '0' COMMENT '身份证号',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `sex` tinyint(3) DEFAULT NULL COMMENT '性别（1：男 0：女）',
  `age` tinyint(3) DEFAULT NULL COMMENT '年龄',
  `education` tinyint(3) DEFAULT NULL COMMENT '学历',
  `is_marry` tinyint(1) DEFAULT NULL COMMENT '是否结婚（1：是 0：否）',
  `industry` tinyint(3) DEFAULT NULL COMMENT '行业',
  `income` tinyint(3) DEFAULT NULL COMMENT '月收入',
  `return_source` tinyint(3) DEFAULT NULL COMMENT '还款来源',
  `contacts_name` varchar(30) DEFAULT NULL COMMENT '联系人名称',
  `contacts_mobile` varchar(11) DEFAULT NULL COMMENT '联系人手机',
  `contacts_relation` tinyint(3) DEFAULT NULL COMMENT '联系人关系',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态（0：未认证，1：认证中， 2：认证通过， -1：认证失败）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='借款人';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrower`
--

LOCK TABLES `borrower` WRITE;
/*!40000 ALTER TABLE `borrower` DISABLE KEYS */;
INSERT INTO `borrower` VALUES (3,2,'Casey Schowalter','182008199709242074','18913212404',1,18,4,1,1,4,1,'Nicole Emard Jr.','15316510284',1,2,'2024-01-26 14:21:03','2024-01-26 14:26:49',0);
/*!40000 ALTER TABLE `borrower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower_attach`
--

DROP TABLE IF EXISTS `borrower_attach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrower_attach` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `borrower_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '借款人id',
  `image_type` varchar(10) DEFAULT NULL COMMENT '图片类型（idCard1：身份证正面，idCard2：身份证反面，house：房产证，car：车）',
  `image_url` varchar(200) DEFAULT NULL COMMENT '图片路径',
  `image_name` varchar(50) DEFAULT NULL COMMENT '图片名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_borrower_id` (`borrower_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='借款人上传资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrower_attach`
--

LOCK TABLES `borrower_attach` WRITE;
/*!40000 ALTER TABLE `borrower_attach` DISABLE KEYS */;
INSERT INTO `borrower_attach` VALUES (9,3,'idCard1','https://srb-vectorx.oss-cn-shanghai.aliyuncs.com/oss/2024/01/26/90c27d60-a979-4267-af76-c04c69c7dce9.png','身份证反面.png','2024-01-26 14:21:03','2024-01-26 14:21:03',0),(10,3,'idCard2','https://srb-vectorx.oss-cn-shanghai.aliyuncs.com/oss/2024/01/26/efdb3e17-fdb2-4277-b8d5-36025a8e1ce7.png','身份证正面.png','2024-01-26 14:21:03','2024-01-26 14:21:03',0),(11,3,'house','https://srb-vectorx.oss-cn-shanghai.aliyuncs.com/oss/2024/01/26/4c89eade-a184-40a0-8b8a-ce8ec81c7f8c.jpg','房产证.jpg','2024-01-26 14:21:03','2024-01-26 14:21:03',0),(12,3,'car','https://srb-vectorx.oss-cn-shanghai.aliyuncs.com/oss/2024/01/26/d4910efe-9c3b-44e4-8437-d1dd821d9b7c.jpeg','驾驶证.jpeg','2024-01-26 14:21:03','2024-01-26 14:21:03',0);
/*!40000 ALTER TABLE `borrower_attach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict`
--

DROP TABLE IF EXISTS `dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
  `value` int(11) DEFAULT NULL COMMENT '值',
  `dict_code` varchar(20) DEFAULT NULL COMMENT '编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_parent_id_value` (`parent_id`,`value`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82008 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据字典';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict`
--

LOCK TABLES `dict` WRITE;
/*!40000 ALTER TABLE `dict` DISABLE KEYS */;
INSERT INTO `dict` VALUES (1,0,'全部分类',NULL,'ROOT','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(20000,1,'行业',NULL,'industry','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(20001,20000,'IT',1,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(20002,20000,'医生',2,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(20003,20000,'教师',3,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(20004,20000,'导游',4,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(20005,20000,'律师',5,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(20006,20000,'其他',6,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(30000,1,'学历',NULL,'education','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(30001,30000,'高中',1,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(30002,30000,'大专',2,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(30003,30000,'本科',3,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(30004,30000,'研究生',4,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(30005,30000,'其他',5,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(40000,1,'收入',NULL,'income','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(40001,40000,'0-3000',1,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(40002,40000,'3000-5000',2,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(40003,40000,'5000-10000',3,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(40004,40000,'10000以上',4,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(50000,1,'收入来源',NULL,'returnSource','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(50001,50000,'工资',1,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(50002,50000,'股票',2,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(50003,50000,'兼职',3,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(60000,1,'关系',NULL,'relation','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(60001,60000,'夫妻',1,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(60002,60000,'兄妹',2,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(60003,60000,'父母',3,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(60004,60000,'其他',4,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(70000,1,'还款方式',NULL,'returnMethod','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(70001,70000,'等额本息',1,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(70002,70000,'等额本金',2,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(70003,70000,'每月还息一次还本',3,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(70004,70000,'一次还本还息',4,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(80000,1,'资金用途',NULL,'moneyUse','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(80001,80000,'旅游',1,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(80002,80000,'买房',2,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(80003,80000,'装修',3,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(80004,80000,'医疗',4,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(80005,80000,'美容',5,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(80006,80000,'其他',6,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(81000,1,'借款状态',NULL,'borrowStatus','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(81001,81000,'待审核',0,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(81002,81000,'审批通过',1,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(81003,81000,'还款中',2,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(81004,81000,'结束',3,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(81005,81000,'审批不通过',-1,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(82000,1,'学校性质',NULL,'SchoolStatus','2024-01-23 14:02:01','2024-01-23 14:02:01',0),(82001,82000,'211/985',NULL,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(82002,82000,'一本',NULL,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(82003,82000,'二本',NULL,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(82004,82000,'三本',NULL,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(82005,82000,'高职高专',NULL,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(82006,82000,'中职中专',NULL,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0),(82007,82000,'高中及以下',NULL,NULL,'2024-01-23 14:02:01','2024-01-23 14:02:01',0);
/*!40000 ALTER TABLE `dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `integral_grade`
--

DROP TABLE IF EXISTS `integral_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `integral_grade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `integral_start` int(11) DEFAULT NULL COMMENT '积分区间开始',
  `integral_end` int(11) DEFAULT NULL COMMENT '积分区间结束',
  `borrow_amount` decimal(10,2) DEFAULT NULL COMMENT '借款额度',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='积分等级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `integral_grade`
--

LOCK TABLES `integral_grade` WRITE;
/*!40000 ALTER TABLE `integral_grade` DISABLE KEYS */;
INSERT INTO `integral_grade` VALUES (1,10,50,10000.00,'2020-12-08 09:02:29','2021-02-19 09:58:10',0),(2,51,100,30000.00,'2020-12-08 09:02:42','2021-02-19 10:00:25',0),(3,101,2000,100000.00,'2020-12-08 09:02:57','2021-02-23 13:03:03',0);
/*!40000 ALTER TABLE `integral_grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lend`
--

DROP TABLE IF EXISTS `lend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '借款用户id',
  `borrow_info_id` bigint(20) DEFAULT NULL COMMENT '借款信息id',
  `lend_no` varchar(30) DEFAULT NULL COMMENT '标的编号',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '标的金额',
  `period` int(11) DEFAULT NULL COMMENT '投资期数',
  `lend_year_rate` decimal(10,2) DEFAULT NULL COMMENT '年化利率',
  `service_rate` decimal(10,2) DEFAULT NULL COMMENT '平台服务费率',
  `return_method` tinyint(3) DEFAULT NULL COMMENT '还款方式',
  `lowest_amount` decimal(10,2) DEFAULT NULL COMMENT '最低投资金额',
  `invest_amount` decimal(10,2) DEFAULT NULL COMMENT '已投金额',
  `invest_num` int(11) DEFAULT NULL COMMENT '投资人数',
  `publish_date` datetime DEFAULT NULL COMMENT '发布日期',
  `lend_start_date` date DEFAULT NULL COMMENT '开始日期',
  `lend_end_date` date DEFAULT NULL COMMENT '结束日期',
  `lend_info` text COMMENT '说明',
  `expect_amount` decimal(10,2) DEFAULT NULL COMMENT '平台预期收益',
  `real_amount` decimal(10,2) DEFAULT NULL COMMENT '实际收益',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `check_admin_id` bigint(1) DEFAULT NULL COMMENT '审核用户id',
  `payment_time` datetime DEFAULT NULL COMMENT '放款时间',
  `payment_admin_id` datetime DEFAULT NULL COMMENT '放款人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_lend_no` (`lend_no`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_borrow_info_id` (`borrow_info_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标的准备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lend`
--

LOCK TABLES `lend` WRITE;
/*!40000 ALTER TABLE `lend` DISABLE KEYS */;
INSERT INTO `lend` VALUES (2,2,2,'LEND20240126223141893','发达歌',100000.00,24,0.12,0.05,2,100.00,100000.00,1,'2024-01-26 22:31:42','2024-01-26','2026-01-26','Provident non incidunt necessitatibus eum voluptatem labore veniam iusto. Tempore consequatur ut. Aut quia tempora culpa aut quas ut. Vitae voluptates necessitatibus beatae sit et quaerat. Deleniti aut dolorem perspiciatis doloremque sit quidem adipisci doloribus suscipit. Eum fugit rem illo aliquam amet facilis dolorem. Vitae corrupti perspiciatis corrupti. Exercitationem delectus quod soluta voluptatem incidunt enim. Aliquid quo sunt quia aut quod consequatur minima. Ipsa qui qui. Eveniet in dolore. Doloribus in aut. Molestiae inventore facere reprehenderit voluptate nobis.',9999.98,10000.01,2,'2024-01-26 22:31:42',1,'2024-01-26 22:43:25',NULL,'2024-01-26 14:31:41','2024-01-26 14:31:41',0);
/*!40000 ALTER TABLE `lend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lend_item`
--

DROP TABLE IF EXISTS `lend_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lend_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lend_item_no` varchar(50) DEFAULT NULL COMMENT '投资编号',
  `lend_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '标的id',
  `invest_user_id` bigint(20) DEFAULT NULL COMMENT '投资用户id',
  `invest_name` varchar(20) DEFAULT NULL COMMENT '投资人名称',
  `invest_amount` decimal(10,2) DEFAULT NULL COMMENT '投资金额',
  `lend_year_rate` decimal(10,2) DEFAULT NULL COMMENT '年化利率',
  `invest_time` datetime DEFAULT NULL COMMENT '投资时间',
  `lend_start_date` date DEFAULT NULL COMMENT '开始日期',
  `lend_end_date` date DEFAULT NULL COMMENT '结束日期',
  `expect_amount` decimal(10,2) DEFAULT NULL COMMENT '预期收益',
  `real_amount` decimal(10,2) DEFAULT NULL COMMENT '实际收益',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态（0：默认 1：已支付 2：已还款）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_lend_item_no` (`lend_item_no`) USING BTREE,
  KEY `idx_lend_id` (`lend_id`) USING BTREE,
  KEY `idx_invest_user_id` (`invest_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标的出借记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lend_item`
--

LOCK TABLES `lend_item` WRITE;
/*!40000 ALTER TABLE `lend_item` DISABLE KEYS */;
INSERT INTO `lend_item` VALUES (4,'INVEST20240126224237980',2,1,'19941918997',100000.00,0.12,'2024-01-26 22:42:37','2024-01-26','2026-01-26',12499.76,958.32,1,'2024-01-26 14:42:37','2024-01-26 14:42:37',0);
/*!40000 ALTER TABLE `lend_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lend_item_return`
--

DROP TABLE IF EXISTS `lend_item_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lend_item_return` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lend_return_id` bigint(20) DEFAULT NULL COMMENT '标的还款id',
  `lend_item_id` bigint(20) DEFAULT NULL COMMENT '标的项id',
  `lend_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '标的id',
  `invest_user_id` bigint(1) DEFAULT NULL COMMENT '出借用户id',
  `invest_amount` decimal(10,2) DEFAULT NULL COMMENT '出借金额',
  `current_period` int(11) DEFAULT NULL COMMENT '当前的期数',
  `lend_year_rate` decimal(10,2) DEFAULT NULL COMMENT '年化利率',
  `return_method` tinyint(3) DEFAULT NULL COMMENT '还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本',
  `principal` decimal(10,2) DEFAULT NULL COMMENT '本金',
  `interest` decimal(10,2) DEFAULT NULL COMMENT '利息',
  `total` decimal(10,2) DEFAULT NULL COMMENT '本息',
  `fee` decimal(10,2) DEFAULT '0.00' COMMENT '手续费',
  `return_date` date DEFAULT NULL COMMENT '还款时指定的还款日期',
  `real_return_time` datetime DEFAULT NULL COMMENT '实际发生的还款时间',
  `is_overdue` tinyint(1) DEFAULT NULL COMMENT '是否逾期',
  `overdue_total` decimal(10,2) DEFAULT NULL COMMENT '逾期金额',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态（0-未归还 1-已归还）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_lend_return_id` (`lend_return_id`) USING BTREE,
  KEY `idx_lend_item_id` (`lend_item_id`) USING BTREE,
  KEY `idx_lend_id` (`lend_id`) USING BTREE,
  KEY `idx_invest_user_id` (`invest_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标的出借回款记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lend_item_return`
--

LOCK TABLES `lend_item_return` WRITE;
/*!40000 ALTER TABLE `lend_item_return` DISABLE KEYS */;
INSERT INTO `lend_item_return` VALUES (1,1,4,2,1,100000.00,1,0.12,2,4166.67,999.99,5166.66,0.00,'2024-02-26','2024-01-27 13:53:10',0,NULL,1,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(2,2,4,2,1,100000.00,2,0.12,2,4166.67,958.32,5124.99,0.00,'2024-03-26','2024-01-27 13:53:44',0,NULL,1,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(3,3,4,2,1,100000.00,3,0.12,2,4166.67,916.66,5083.33,0.00,'2024-04-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(4,4,4,2,1,100000.00,4,0.12,2,4166.67,874.99,5041.66,0.00,'2024-05-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(5,5,4,2,1,100000.00,5,0.12,2,4166.67,833.32,4999.99,0.00,'2024-06-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(6,6,4,2,1,100000.00,6,0.12,2,4166.67,791.66,4958.33,0.00,'2024-07-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(7,7,4,2,1,100000.00,7,0.12,2,4166.67,749.99,4916.66,0.00,'2024-08-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(8,8,4,2,1,100000.00,8,0.12,2,4166.67,708.32,4874.99,0.00,'2024-09-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(9,9,4,2,1,100000.00,9,0.12,2,4166.67,666.66,4833.33,0.00,'2024-10-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(10,10,4,2,1,100000.00,10,0.12,2,4166.67,624.99,4791.66,0.00,'2024-11-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(11,11,4,2,1,100000.00,11,0.12,2,4166.67,583.32,4749.99,0.00,'2024-12-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(12,12,4,2,1,100000.00,12,0.12,2,4166.67,541.66,4708.33,0.00,'2025-01-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(13,13,4,2,1,100000.00,13,0.12,2,4166.67,499.99,4666.66,0.00,'2025-02-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(14,14,4,2,1,100000.00,14,0.12,2,4166.67,458.32,4624.99,0.00,'2025-03-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(15,15,4,2,1,100000.00,15,0.12,2,4166.67,416.66,4583.33,0.00,'2025-04-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(16,16,4,2,1,100000.00,16,0.12,2,4166.67,374.99,4541.66,0.00,'2025-05-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(17,17,4,2,1,100000.00,17,0.12,2,4166.67,333.32,4499.99,0.00,'2025-06-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(18,18,4,2,1,100000.00,18,0.12,2,4166.67,291.66,4458.33,0.00,'2025-07-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(19,19,4,2,1,100000.00,19,0.12,2,4166.67,249.99,4416.66,0.00,'2025-08-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(20,20,4,2,1,100000.00,20,0.12,2,4166.67,208.32,4374.99,0.00,'2025-09-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(21,21,4,2,1,100000.00,21,0.12,2,4166.67,166.66,4333.33,0.00,'2025-10-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(22,22,4,2,1,100000.00,22,0.12,2,4166.67,124.99,4291.66,0.00,'2025-11-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(23,23,4,2,1,100000.00,23,0.12,2,4166.67,83.32,4249.99,0.00,'2025-12-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(24,24,4,2,1,100000.00,24,0.12,2,4166.67,41.66,4208.33,0.00,'2026-01-26',NULL,0,NULL,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0);
/*!40000 ALTER TABLE `lend_item_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lend_return`
--

DROP TABLE IF EXISTS `lend_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lend_return` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lend_id` bigint(20) DEFAULT NULL COMMENT '标的id',
  `borrow_info_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '借款信息id',
  `return_no` varchar(30) DEFAULT NULL COMMENT '还款批次号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '借款人用户id',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '借款金额',
  `base_amount` decimal(10,2) DEFAULT NULL COMMENT '计息本金额',
  `current_period` int(11) DEFAULT NULL COMMENT '当前的期数',
  `lend_year_rate` decimal(10,2) DEFAULT NULL COMMENT '年化利率',
  `return_method` tinyint(3) DEFAULT NULL COMMENT '还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本',
  `principal` decimal(10,2) DEFAULT NULL COMMENT '本金',
  `interest` decimal(10,2) DEFAULT NULL COMMENT '利息',
  `total` decimal(10,2) DEFAULT NULL COMMENT '本息',
  `fee` decimal(10,2) DEFAULT '0.00' COMMENT '手续费',
  `return_date` date DEFAULT NULL COMMENT '还款时指定的还款日期',
  `real_return_time` datetime DEFAULT NULL COMMENT '实际发生的还款时间',
  `is_overdue` tinyint(1) DEFAULT NULL COMMENT '是否逾期',
  `overdue_total` decimal(10,2) DEFAULT NULL COMMENT '逾期金额',
  `is_last` tinyint(1) DEFAULT NULL COMMENT '是否最后一次还款',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态（0-未归还 1-已归还）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_return_no` (`return_no`) USING BTREE,
  KEY `idx_lend_id` (`lend_id`) USING BTREE,
  KEY `idx_borrow_info_id` (`borrow_info_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='还款记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lend_return`
--

LOCK TABLES `lend_return` WRITE;
/*!40000 ALTER TABLE `lend_return` DISABLE KEYS */;
INSERT INTO `lend_return` VALUES (1,2,2,'RETURN20240126224324669',2,100000.00,100000.00,1,0.12,2,4166.67,999.99,5166.66,0.00,'2024-01-26','2024-01-27 13:53:10',0,NULL,0,1,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(2,2,2,'RETURN20240126224324334',2,100000.00,100000.00,2,0.12,2,4166.67,958.32,5124.99,0.00,'2024-01-26','2024-01-27 13:53:44',0,NULL,0,1,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(3,2,2,'RETURN20240126224324470',2,100000.00,100000.00,3,0.12,2,4166.67,916.66,5083.33,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(4,2,2,'RETURN20240126224324547',2,100000.00,100000.00,4,0.12,2,4166.67,874.99,5041.66,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(5,2,2,'RETURN20240126224324394',2,100000.00,100000.00,5,0.12,2,4166.67,833.32,4999.99,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(6,2,2,'RETURN20240126224324731',2,100000.00,100000.00,6,0.12,2,4166.67,791.66,4958.33,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(7,2,2,'RETURN20240126224324578',2,100000.00,100000.00,7,0.12,2,4166.67,749.99,4916.66,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(8,2,2,'RETURN20240126224324848',2,100000.00,100000.00,8,0.12,2,4166.67,708.32,4874.99,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(9,2,2,'RETURN20240126224324856',2,100000.00,100000.00,9,0.12,2,4166.67,666.66,4833.33,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(10,2,2,'RETURN20240126224324076',2,100000.00,100000.00,10,0.12,2,4166.67,624.99,4791.66,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(11,2,2,'RETURN20240126224324480',2,100000.00,100000.00,11,0.12,2,4166.67,583.32,4749.99,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(12,2,2,'RETURN20240126224324314',2,100000.00,100000.00,12,0.12,2,4166.67,541.66,4708.33,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(13,2,2,'RETURN20240126224324055',2,100000.00,100000.00,13,0.12,2,4166.67,499.99,4666.66,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(14,2,2,'RETURN20240126224324718',2,100000.00,100000.00,14,0.12,2,4166.67,458.32,4624.99,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(15,2,2,'RETURN20240126224324938',2,100000.00,100000.00,15,0.12,2,4166.67,416.66,4583.33,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(16,2,2,'RETURN20240126224324086',2,100000.00,100000.00,16,0.12,2,4166.67,374.99,4541.66,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(17,2,2,'RETURN20240126224324252',2,100000.00,100000.00,17,0.12,2,4166.67,333.32,4499.99,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(18,2,2,'RETURN20240126224324738',2,100000.00,100000.00,18,0.12,2,4166.67,291.66,4458.33,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(19,2,2,'RETURN20240126224324501',2,100000.00,100000.00,19,0.12,2,4166.67,249.99,4416.66,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(20,2,2,'RETURN20240126224324609',2,100000.00,100000.00,20,0.12,2,4166.67,208.32,4374.99,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(21,2,2,'RETURN20240126224324572',2,100000.00,100000.00,21,0.12,2,4166.67,166.66,4333.33,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(22,2,2,'RETURN20240126224324810',2,100000.00,100000.00,22,0.12,2,4166.67,124.99,4291.66,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(23,2,2,'RETURN20240126224324505',2,100000.00,100000.00,23,0.12,2,4166.67,83.32,4249.99,0.00,'2024-01-26',NULL,0,NULL,0,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0),(24,2,2,'RETURN20240126224324925',2,100000.00,100000.00,24,0.12,2,4166.67,41.66,4208.33,0.00,'2024-01-26',NULL,0,NULL,1,0,'2024-01-26 14:43:24','2024-01-26 14:43:24',0);
/*!40000 ALTER TABLE `lend_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trans_flow`
--

DROP TABLE IF EXISTS `trans_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trans_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `trans_no` varchar(30) NOT NULL DEFAULT '' COMMENT '交易单号',
  `trans_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '交易类型（1：充值 2：提现 3：投标 4：投资回款 ...）',
  `trans_type_name` varchar(100) DEFAULT NULL COMMENT '交易类型名称',
  `trans_amount` decimal(10,2) DEFAULT NULL COMMENT '交易金额',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_trans_no` (`trans_no`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='交易流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trans_flow`
--

LOCK TABLES `trans_flow` WRITE;
/*!40000 ALTER TABLE `trans_flow` DISABLE KEYS */;
INSERT INTO `trans_flow` VALUES (8,1,'Mr. Geraldine Sc','20240126223231009',1,'充值',100000.00,'重置','2024-01-26 14:32:36','2024-01-26 14:32:36',0),(9,1,'Mr. Geraldine Sc','20240126224151530',1,'充值',100000.00,'重置','2024-01-26 14:41:55','2024-01-26 14:41:55',0),(10,1,'Mr. Geraldine Sc','INVEST20240126224237980',2,'投标锁定',100000.00,'投资项目编号：LEND20240126223141893，项目名称：发达歌','2024-01-26 14:42:52','2024-01-26 14:42:52',0),(11,2,'Casey Schowalter','LOAN20240126224324535',5,'放款到账',89999.99,'借款放款到账，编号：LEND20240126223141893','2024-01-26 14:43:24','2024-01-26 14:43:24',0),(12,1,'Mr. Geraldine Sc','TRANS20240126224324563',3,'放款解锁',100000.00,'冻结资金转出，出借放款，编号：LEND20240126223141893','2024-01-26 14:43:24','2024-01-26 14:43:24',0),(13,2,'Casey Schowalter','WITHDRAW20240127000528918',8,'提现',100.00,'提现','2024-01-26 16:08:02','2024-01-26 16:08:02',0),(14,2,'Casey Schowalter','WITHDRAW20240127134224929',8,'提现',99900.00,'提现','2024-01-27 05:42:28','2024-01-27 05:42:28',0),(15,2,'Casey Schowalter','20240127134755175',1,'充值',100000.00,'重置','2024-01-27 05:47:58','2024-01-27 05:47:58',0),(16,2,'Casey Schowalter','RETURN20240126224324669',6,'还款扣减',5166.66,'借款人还款扣减，项目编号：LEND20240126223141893，项目名称：发达歌','2024-01-27 05:53:10','2024-01-27 05:53:10',0),(17,1,'Mr. Geraldine Sc','RETURNITEM20240127135310549',7,'出借回款',5166.66,'还款到账，项目编号：LEND20240126223141893，项目名称：发达歌','2024-01-27 05:53:10','2024-01-27 05:53:10',0),(18,2,'Casey Schowalter','RETURN20240126224324334',6,'还款扣减',5124.99,'借款人还款扣减，项目编号：LEND20240126223141893，项目名称：发达歌','2024-01-27 05:53:44','2024-01-27 05:53:44',0),(19,1,'Mr. Geraldine Sc','RETURNITEM20240127135344893',7,'出借回款',5124.99,'还款到账，项目编号：LEND20240126223141893，项目名称：发达歌','2024-01-27 05:53:44','2024-01-27 05:53:44',0),(20,2,'Casey Schowalter','20240127155122622',1,'充值',1.00,'充值','2024-01-27 07:51:27','2024-01-27 07:51:27',0),(21,1,'Mr. Geraldine Sc','20240127155410121',1,'充值',1.00,'充值','2024-01-27 07:54:14','2024-01-27 07:54:14',0),(22,2,'Casey Schowalter','20240127155630306',1,'充值',1.00,'充值','2024-01-27 07:56:33','2024-01-27 07:56:33',0);
/*!40000 ALTER TABLE `trans_flow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '帐户可用余额',
  `freeze_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (2,1,110292.65,0.00,'2024-01-26 14:34:22','2024-01-27 07:54:14',0,0),(3,2,89710.35,0.00,'2024-01-26 16:05:24','2024-01-27 07:56:33',0,0);
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_bind`
--

DROP TABLE IF EXISTS `user_bind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_bind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `id_card` varchar(20) NOT NULL DEFAULT '' COMMENT '身份证号',
  `bank_no` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `bank_type` varchar(20) DEFAULT NULL COMMENT '银行类型',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `bind_code` varchar(50) DEFAULT NULL COMMENT '绑定账户协议号',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户绑定表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_bind`
--

LOCK TABLES `user_bind` WRITE;
/*!40000 ALTER TABLE `user_bind` DISABLE KEYS */;
INSERT INTO `user_bind` VALUES (1,2,'Casey Schowalter','182008199709242074','693542201007283493','商业银行','18506365596','6266516709fa4eb38d808ec39399567a',1,'2024-01-25 03:43:05','2024-01-25 04:46:21',0),(2,1,'Mr. Geraldine Sc','708218201503218137','726958201912118659','交通银行','14766949886','f2c18a112f8948e98adff24bd86c3c05',1,'2024-01-26 08:19:53','2024-01-26 08:19:53',0);
/*!40000 ALTER TABLE `user_bind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '1：出借人 2：借款人',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '用户密码',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户昵称',
  `name` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户姓名',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `openid` varchar(40) DEFAULT NULL COMMENT '微信用户标识openid',
  `head_img` varchar(200) DEFAULT NULL COMMENT '头像',
  `bind_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '绑定状态（0：未绑定，1：绑定成功 -1：绑定失败）',
  `borrow_auth_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '借款人认证状态（0：未认证 1：认证中 2：认证通过 -1：认证失败）',
  `bind_code` varchar(50) DEFAULT NULL COMMENT '绑定账户协议号',
  `integral` int(11) NOT NULL DEFAULT '0' COMMENT '用户积分',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态（0：锁定 1：正常）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `uk_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,1,'19941918997','123123','19941918997','Mr. Geraldine Sc',NULL,NULL,NULL,'https://srb-vectorx.oss-cn-shanghai.aliyuncs.com/avatar/1.gif',1,0,'f2c18a112f8948e98adff24bd86c3c05',0,1,'2024-01-24 14:12:25','2024-01-26 08:20:03',0),(2,2,'18913212404','123123','18913212404','Casey Schowalter',NULL,NULL,NULL,'https://srb-vectorx.oss-cn-shanghai.aliyuncs.com/avatar/1.gif',1,2,'6266516709fa4eb38d808ec39399567a',290,1,'2024-01-24 14:38:45','2024-01-26 14:28:00',0);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_integral`
--

DROP TABLE IF EXISTS `user_integral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_integral` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `integral` int(11) DEFAULT NULL COMMENT '积分',
  `content` varchar(100) DEFAULT NULL COMMENT '获取积分说明',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户积分记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_integral`
--

LOCK TABLES `user_integral` WRITE;
/*!40000 ALTER TABLE `user_integral` DISABLE KEYS */;
INSERT INTO `user_integral` VALUES (1,2,30,'借款人基本信息','2024-01-25 16:42:39','2024-01-25 16:42:39',0),(2,2,60,'借款人车辆信息','2024-01-25 16:42:39','2024-01-25 16:42:39',0),(3,2,30,'借款人基本信息','2024-01-25 16:43:30','2024-01-25 16:43:30',0),(4,2,60,'借款人车辆信息','2024-01-25 16:43:30','2024-01-25 16:43:30',0),(5,2,100,'借款人基本信息','2024-01-26 14:27:06','2024-01-26 14:27:06',0),(6,2,30,'借款人身份证信息','2024-01-26 14:27:06','2024-01-26 14:27:06',0),(7,2,60,'借款人车辆信息','2024-01-26 14:27:06','2024-01-26 14:27:06',0),(8,2,100,'借款人房产信息','2024-01-26 14:27:06','2024-01-26 14:27:06',0);
/*!40000 ALTER TABLE `user_integral` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_login_record`
--

DROP TABLE IF EXISTS `user_login_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_login_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户登录记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login_record`
--

LOCK TABLES `user_login_record` WRITE;
/*!40000 ALTER TABLE `user_login_record` DISABLE KEYS */;
INSERT INTO `user_login_record` VALUES (1,1,'0:0:0:0:0:0:0:1','2024-01-24 14:34:32','2024-01-24 14:34:32',0),(2,1,'127.0.0.1','2024-01-24 14:35:11','2024-01-24 14:35:11',0),(3,2,'0:0:0:0:0:0:0:1','2024-01-24 14:38:57','2024-01-24 14:38:57',0),(4,2,'0:0:0:0:0:0:0:1','2024-01-24 15:00:38','2024-01-24 15:00:38',0),(5,2,'192.168.170.200','2024-01-25 02:25:03','2024-01-25 02:25:03',0),(6,2,'192.168.170.200','2024-01-25 03:40:05','2024-01-25 03:40:05',0),(7,2,'192.168.170.200','2024-01-25 13:34:20','2024-01-25 13:34:20',0),(8,2,'192.168.170.200','2024-01-25 14:05:03','2024-01-25 14:05:03',0),(9,2,'192.168.170.200','2024-01-26 01:38:47','2024-01-26 01:38:47',0),(10,1,'192.168.170.200','2024-01-26 04:50:47','2024-01-26 04:50:47',0),(11,2,'192.168.170.200','2024-01-26 04:53:43','2024-01-26 04:53:43',0),(12,1,'192.168.170.200','2024-01-26 04:54:08','2024-01-26 04:54:08',0),(13,2,'192.168.170.200','2024-01-26 07:04:48','2024-01-26 07:04:48',0),(14,1,'192.168.170.200','2024-01-26 08:13:49','2024-01-26 08:13:49',0),(15,1,'192.168.170.200','2024-01-26 10:53:43','2024-01-26 10:53:43',0),(16,1,'192.168.170.200','2024-01-26 10:53:43','2024-01-26 10:53:43',0),(17,2,'192.168.170.200','2024-01-26 13:56:53','2024-01-26 13:56:53',0),(18,1,'192.168.170.200','2024-01-26 13:57:09','2024-01-26 13:57:09',0),(19,2,'192.168.170.200','2024-01-27 05:39:04','2024-01-27 05:39:04',0),(20,1,'192.168.170.200','2024-01-27 05:43:12','2024-01-27 05:43:12',0);
/*!40000 ALTER TABLE `user_login_record` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-27 16:03:12
