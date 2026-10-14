-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: qm_sjk
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `param_key` varchar(50) NOT NULL COMMENT '配置键',
  `param_value` varchar(500) DEFAULT NULL COMMENT '配置值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`param_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES ('sys.account.registerUser','true','是否开启用户注册');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_file`
--

DROP TABLE IF EXISTS `sys_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_file` (
  `file_id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL COMMENT '原始文件名',
  `file_url` varchar(500) NOT NULL COMMENT '文件访问地址',
  `file_type` varchar(50) DEFAULT NULL COMMENT '文件类型(jpg/pdf)',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小(字节)',
  `uploader_id` bigint DEFAULT NULL COMMENT '上传者ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_file`
--

LOCK TABLES `sys_file` WRITE;
/*!40000 ALTER TABLE `sys_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL COMMENT '公告标题',
  `content` text NOT NULL COMMENT '公告内容(富文本)',
  `publisher_id` bigint NOT NULL COMMENT '发布人ID(宿管/管理员)',
  `target_type` tinyint DEFAULT '0' COMMENT '接收对象: 0-全员, 1-仅学生, 2-仅辅导员',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'停水通知','因管道维修，今晚停水',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(2,'停电通知','线路检修，明天停电',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(3,'卫生检查','下周一全校大检查',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(4,'放假通知','国庆放假安排',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(5,'防诈骗提醒','注意财产安全',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(6,'失物招领','捡到饭卡一张',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(7,'门禁时间调整','门禁改为23:00',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(8,'违规电器整治','严禁使用热得快',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(9,'宿舍文化节','欢迎报名参加',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(10,'新生入学指南','欢迎新同学',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(11,'空调清洗','请配合清洗空调',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(12,'网费充值优惠','充100送20',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(13,'考研加油','祝各位学子金榜题名',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(14,'毕业离校流程','请按时办理退宿',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(15,'冬季防火','注意用电安全',1,0,'2025-11-27 20:11:55','2025-11-27 20:11:55',0),(16,'test','9.3 环境配置表\n环境	目的	数据库	域名	特性\n开发环境	本地开发	本地MongoDB	localhost:3000	热重载、调试工具\n测试环境	功能测试	测试数据库	staging.app.com	模拟生产环境\n预生产环境	最终测试	生产数据副本	preprod.app.com	与生产环境一致\n生产环境	线上服务	生产数据库	app.com	监控、日志、备份\n维护与优化\n10.1 性能监控指标表\n监控指标	目标值	监控工具	报警阈值	处理措施\n页面加载时间	<3秒	Google Analytics	>5秒	优化资源加载\nAPI响应时间	<500ms	New Relic	>1秒	数据库优化\n错误率	<1%	Sentry	>5%	立即排查修复\n服务器CPU使用率	<70%	Datadog	>90%	扩容或优化\n内存使用率	<80%	Datadog	>95%	检查内存泄漏\n10.2 优化策略表\n优化领域	具体措施	预期效果	实施难度\n前端性能	代码分割、懒加载	减少初始加载时间30%	中等\n图片优化	WebP格式、CDN	减少图片大小50%	简单\n数据库	索引优化、查询缓存	提升查询速度60%	困难\n缓存策略	Redis缓存、浏览器缓存	减少服务器负载40%	中等\n打包优化	Tree shaking、压缩	减少bundle大小40%	简单\n10.3 安全防护措施\n安全威胁	防护措施	实施方法	检查频率\nXSS攻击	输入转义、CSP头	使用DOMPurify库	每次发布\nCSRF攻击	CSRF Token	同步Token模式	每次发布\nSQL注入	参数化查询	使用ORM/ODM	代码审查\n暴力破解	速率限制	express-rate-limit	实时监控\n数据泄露	加密存储	bcrypt加密密码	定期审计\n总结\n本教程详细介绍了Web应用从0到1的完整开发流程，通过表格和流程图的形式直观展示了各个阶段的关键信息。主要涵盖：\n\n系统规划 - 明确目标、分析需求、制定计划\n\n技术选型 - 对比不同技术方案的优缺点\n\n环境搭建 - 配置开发环境和工具链\n\n架构设计 - 设计可扩展的系统架构\n\n开发实现 - 前后端协同开发核心功能\n\n测试部署 - 确保质量并上线运行\n\n维护优化 - 持续改进和性能优化\n\n记住，成功的Web应用开发是一个持续迭代的过程。始终保持学习的心态，关注用户反馈，不断优化和改进你的应用。\n\n祝您开发顺利！',17,0,'2025-11-28 15:24:22','2025-11-28 15:24:22',0),(17,'关于2025年寒假期间宿舍封闭管理的通知','<p style=\"text-align: start;\">各位同学：</p><p style=\"text-align: start;\">寒假将至，为确保假期校园安全，经学校研究决定，将对学生公寓实行封闭管理。现将有关事项通知如下：</p><p style=\"text-align: start;\">一、 封楼与开楼时间</p><p style=\"text-align: start;\">封楼时间：2025年1月15日（周三）中午 12:00。开楼时间：2025年2月20日（周四）上午 08:00。封楼期间，除留校申请获批的同学外，其他同学不得滞留宿舍。</p><p style=\"text-align: start;\">二、 离校前注意事项请同学们在离校前务必做好以下工作：</p><p style=\"text-align: start;\">整理好个人物品，贵重物品（如电脑、相机、现金等）请随身携带，不要存放在宿舍。清理宿舍阳台及室内的易燃杂物，切断所有电源（拔掉插排），关闭水龙头。关好门窗，最后离开的同学请务必反锁房门，并贴好封条。</p><p style=\"text-align: start;\">三、 假期留校要求留校同学需严格遵守《假期留校住宿管理规定》，严禁违章用电，严禁留宿外人。宿管中心将不定期进行安全检查。</p><p style=\"text-align: start;\">四、 紧急联系方式假期期间，如遇紧急情况，请拨打保卫处24小时值班电话：&lt;span style=\"color: red; font-weight: bold;\"&gt;021-12345678&lt;/span&gt;。</p><p style=\"text-align: start;\">祝各位同学度过一个安全、愉快、充实的假期！</p><p style=\"text-align: start;\">&lt;p style=\"text-align: right;\"&gt;学生宿管中心&lt;/p&gt;<br>&lt;p style=\"text-align: right;\"&gt;2025年1月10日&lt;/p&gt;</p>',17,0,'2025-11-28 15:45:46','2025-11-28 15:45:46',0),(18,'关于2025年寒假期间宿舍封闭管理的通知','<p style=\"text-align: start;\">各位同学：</p><p style=\"text-align: start;\">寒假将至，为确保假期校园安全，经学校研究决定，将对学生公寓实行封闭管理。现将有关事项通知如下：</p><p style=\"text-align: start;\">一、 封楼与开楼时间</p><p style=\"text-align: start;\">封楼时间：2025年1月15日（周三）中午 12:00。开楼时间：2025年2月20日（周四）上午 08:00。封楼期间，除留校申请获批的同学外，其他同学不得滞留宿舍。</p><p style=\"text-align: start;\">二、 离校前注意事项请同学们在离校前务必做好以下工作：</p><p style=\"text-align: start;\">整理好个人物品，贵重物品（如电脑、相机、现金等）请随身携带，不要存放在宿舍。清理宿舍阳台及室内的易燃杂物，切断所有电源（拔掉插排），关闭水龙头。关好门窗，最后离开的同学请务必反锁房门，并贴好封条。</p><p style=\"text-align: start;\">三、 假期留校要求留校同学需严格遵守《假期留校住宿管理规定》，严禁违章用电，严禁留宿外人。宿管中心将不定期进行安全检查。</p><p style=\"text-align: start;\">四、 紧急联系方式假期期间，如遇紧急情况，请拨打保卫处24小时值班电话：&lt;span style=\"color: red; font-weight: bold;\"&gt;021-12345678&lt;/span&gt;。</p><p style=\"text-align: start;\">祝各位同学度过一个安全、愉快、充实的假期！</p>',17,0,'2025-11-28 15:53:04','2025-12-02 14:12:33',0);
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) NOT NULL COMMENT '登录账号(学号/工号)',
  `password` varchar(128) NOT NULL COMMENT '加密密码(BCrypt)',
  `user_type` tinyint NOT NULL COMMENT '身份类型: 0-学生, 1-辅导员, 2-宿管, 3-系统管理员',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称/显示名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号(找回密码用)',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱(找回密码用)',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1-正常, 0-冻结',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',3,'超级管理员',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(2,'tea01','$2a$10$C7x0M6LPgvRxeshJqBKsf.qu8l.ckXThyAT/GH5RDxuB7tcc0QPIy',1,'赵老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-12-01 13:32:10',0),(3,'tea02','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'钱老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:16:23',0),(4,'tea03','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'孙老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(5,'tea04','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'李老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(6,'tea05','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'周老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(7,'tea06','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'吴老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(8,'tea07','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'郑老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(9,'tea08','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'王老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(10,'tea09','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'冯老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(11,'tea10','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'陈老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(12,'tea11','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'褚老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(13,'tea12','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'卫老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(14,'tea13','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'蒋老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(15,'tea14','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'沈老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(16,'tea15','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',1,'韩老师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(17,'man01','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管张姨',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:14:18',0),(18,'man02','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管李叔',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(19,'man03','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管王姨',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(20,'man04','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管赵叔',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(21,'man05','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管刘姨',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(22,'man06','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管孙叔',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(23,'man07','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管周姨',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(24,'man08','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管吴叔',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(25,'man09','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管郑姨',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(26,'man10','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管冯叔',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(27,'man11','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管陈姨',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(28,'man12','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管楚叔',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(29,'man13','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管卫姨',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(30,'man14','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管蒋叔',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(31,'man15','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',2,'宿管沈姨',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(32,'stu01','$2a$10$Na71DR3mzqfvJ4uxYaPwY.T9kUGGNUangjSM5FFXvnKPmXNtOXkDW',0,'学生甲',NULL,'13458792536','1945894317@qq.com',1,NULL,NULL,'2025-11-27 20:11:55','2025-12-02 14:14:25',0),(33,'stu02','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生乙',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(34,'stu03','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生丙',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(35,'stu04','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生丁',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(36,'stu05','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生戊',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(37,'stu06','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生己',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(38,'stu07','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生庚',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(39,'stu08','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生辛',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(40,'stu09','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生壬',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(41,'stu10','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生癸',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(42,'stu11','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生子',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(43,'stu12','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生丑',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(44,'stu13','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生寅',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(45,'stu14','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生卯',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(46,'stu15','$2a$10$H9VInXRPpTFlzaHqqoSVyuCLLZkZnbLjI3VHt1WCQY6rSHFYjyGWq',0,'学生辰',NULL,NULL,NULL,1,NULL,NULL,'2025-11-27 20:11:55','2025-11-27 20:19:22',0),(101,'stu_test_01','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'张三丰',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(102,'stu_test_02','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'李寻欢',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-12-05 18:34:43',1),(103,'stu_test_03','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'郭靖',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(104,'stu_test_04','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'黄蓉',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(105,'stu_test_05','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'杨过',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(106,'stu_test_06','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'小龙女',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(107,'stu_test_07','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'令狐冲',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(108,'stu_test_08','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'任盈盈',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(109,'stu_test_09','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'乔峰',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(110,'stu_test_10','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'段誉',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(111,'stu_test_11','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'虚竹',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(112,'stu_test_12','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'王语嫣',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-12-03 19:09:28',0),(113,'stu_test_13','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'韦小宝',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(114,'stu_test_14','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'阿珂',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(115,'stu_test_15','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'周伯通',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(116,'stu_test_16','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'洪七公',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(117,'stu_test_17','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'欧阳锋',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(118,'stu_test_18','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'黄药师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(119,'stu_test_19','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'一灯大师',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0),(120,'stu_test_20','$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt8ae13.41s58am/.,a',0,'风清扬',NULL,NULL,NULL,1,NULL,NULL,'2025-11-28 16:46:07','2025-11-28 16:46:07',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_attendance`
--

DROP TABLE IF EXISTS `tb_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_attendance` (
  `attendance_id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint NOT NULL,
  `type` tinyint NOT NULL COMMENT '类型: 1-晚归, 2-未归',
  `occur_time` datetime NOT NULL COMMENT '发生时间',
  `is_handled` tinyint DEFAULT '0' COMMENT '是否已处理: 0-否, 1-是',
  `handle_result` varchar(255) DEFAULT NULL COMMENT '处理结果(警告/通报)',
  PRIMARY KEY (`attendance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='考勤/晚归记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_attendance`
--

LOCK TABLES `tb_attendance` WRITE;
/*!40000 ALTER TABLE `tb_attendance` DISABLE KEYS */;
INSERT INTO `tb_attendance` VALUES (1,32,1,'2025-11-27 20:11:56',0,NULL),(2,33,2,'2025-11-27 20:11:56',1,NULL),(3,34,1,'2025-11-27 20:11:56',0,NULL),(4,35,2,'2025-11-27 20:11:56',1,NULL),(5,36,1,'2025-11-27 20:11:56',0,NULL),(6,37,2,'2025-11-27 20:11:56',1,NULL),(7,38,1,'2025-11-27 20:11:56',0,NULL),(8,39,2,'2025-11-27 20:11:56',1,NULL),(9,40,1,'2025-11-27 20:11:56',0,NULL),(10,41,2,'2025-11-27 20:11:56',1,NULL),(11,42,1,'2025-11-27 20:11:56',0,NULL),(12,43,2,'2025-11-27 20:11:56',1,NULL),(13,44,1,'2025-11-27 20:11:56',0,NULL),(14,45,2,'2025-11-27 20:11:56',1,NULL),(15,46,1,'2025-11-27 20:11:56',0,NULL);
/*!40000 ALTER TABLE `tb_attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_borrow_record`
--

DROP TABLE IF EXISTS `tb_borrow_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_borrow_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint NOT NULL,
  `item_id` bigint NOT NULL,
  `borrow_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `return_time` datetime DEFAULT NULL,
  `status` tinyint DEFAULT '0' COMMENT '状态: 0-借用中, 1-已归还, 2-已逾期',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物品借用记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_borrow_record`
--

LOCK TABLES `tb_borrow_record` WRITE;
/*!40000 ALTER TABLE `tb_borrow_record` DISABLE KEYS */;
INSERT INTO `tb_borrow_record` VALUES (1,32,1,'2025-11-27 20:11:55',NULL,0,NULL),(2,33,2,'2025-11-27 20:11:55',NULL,1,NULL),(3,34,3,'2025-11-27 20:11:55',NULL,2,NULL),(4,35,4,'2025-11-27 20:11:55',NULL,0,NULL),(5,36,5,'2025-11-27 20:11:55',NULL,1,NULL),(6,37,6,'2025-11-27 20:11:55',NULL,2,NULL),(7,38,7,'2025-11-27 20:11:55',NULL,0,NULL),(8,39,8,'2025-11-27 20:11:55',NULL,1,NULL),(9,40,9,'2025-11-27 20:11:55',NULL,2,NULL),(10,41,10,'2025-11-27 20:11:55',NULL,0,NULL),(11,42,11,'2025-11-27 20:11:55',NULL,1,NULL),(12,43,12,'2025-11-27 20:11:55',NULL,2,NULL),(13,44,13,'2025-11-27 20:11:55',NULL,0,NULL),(14,45,14,'2025-11-27 20:11:55',NULL,1,NULL),(15,46,15,'2025-11-27 20:11:55',NULL,2,NULL);
/*!40000 ALTER TABLE `tb_borrow_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_building`
--

DROP TABLE IF EXISTS `tb_building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_building` (
  `building_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '楼栋名称(如:南苑1号楼)',
  `type` tinyint DEFAULT '1' COMMENT '类型: 1-男生宿, 2-女生宿, 3-混合',
  `total_floor` int DEFAULT '6' COMMENT '总层数',
  `location` varchar(128) DEFAULT NULL COMMENT '具体位置',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`building_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍楼栋表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_building`
--

LOCK TABLES `tb_building` WRITE;
/*!40000 ALTER TABLE `tb_building` DISABLE KEYS */;
INSERT INTO `tb_building` VALUES (1,'南苑1号楼',1,6,'南校区生活区','男生宿舍，靠近食堂'),(2,'南苑2号楼',1,6,'南校区生活区','男生宿舍，设施较新'),(3,'南苑3号楼',2,6,'南校区生活区','女生宿舍，环境安静'),(4,'南苑4号楼',2,6,'南校区生活区','女生宿舍，靠近图书馆'),(5,'北苑A座',1,7,'北校区','研究生男生宿舍'),(6,'北苑B座',2,7,'北校区','研究生女生宿舍'),(7,'东区1号楼',3,5,'东校区','混合宿舍，留学生专用'),(8,'西区1号楼',1,6,'西校区','本科新生宿舍'),(9,'西区2号楼',1,6,'西校区','本科新生宿舍'),(10,'馨园1栋',2,6,'中心花园旁','模范宿舍楼'),(11,'馨园2栋',2,6,'中心花园旁','模范宿舍楼'),(12,'专家楼A',3,4,'行政楼后','访问学者公寓'),(13,'教工宿舍1',3,6,'北门旁','青年教师公寓'),(14,'临时周转楼',3,3,'老校区','临时住宿'),(15,'创新创业楼',1,5,'科技园','创业团队住宿区');
/*!40000 ALTER TABLE `tb_building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_counselor`
--

DROP TABLE IF EXISTS `tb_counselor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_counselor` (
  `counselor_id` bigint NOT NULL COMMENT '关联sys_user.user_id',
  `work_no` varchar(32) NOT NULL COMMENT '工号',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `college_name` varchar(64) DEFAULT NULL COMMENT '负责院系',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`counselor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='辅导员信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_counselor`
--

LOCK TABLES `tb_counselor` WRITE;
/*!40000 ALTER TABLE `tb_counselor` DISABLE KEYS */;
INSERT INTO `tb_counselor` VALUES (2,'tea01','赵老师','计算机学院','13800000001','2025-11-27 20:11:55'),(3,'tea02','钱老师','机械学院','13800000002','2025-11-27 20:11:55'),(4,'tea03','孙老师','外语学院','13800000003','2025-11-27 20:11:55'),(5,'tea04','李老师','经管学院','13800000004','2025-11-27 20:11:55'),(6,'tea05','周老师','艺术学院','13800000005','2025-11-27 20:11:55'),(7,'tea06','吴老师','数学学院','13800000006','2025-11-27 20:11:55'),(8,'tea07','郑老师','物理学院','13800000007','2025-11-27 20:11:55'),(9,'tea08','王老师','化学学院','13800000008','2025-11-27 20:11:55'),(10,'tea09','冯老师','生科学院','13800000009','2025-11-27 20:11:55'),(11,'tea10','陈老师','法学院','13800000010','2025-11-27 20:11:55'),(12,'tea11','褚老师','医学院','13800000011','2025-11-27 20:11:55'),(13,'tea12','卫老师','建筑学院','13800000012','2025-11-27 20:11:55'),(14,'tea13','蒋老师','土木学院','13800000013','2025-11-27 20:11:55'),(15,'tea14','沈老师','能动学院','13800000014','2025-11-27 20:11:55'),(16,'tea15','韩老师','信息学院','13800000015','2025-11-27 20:11:55');
/*!40000 ALTER TABLE `tb_counselor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_dorm_application`
--

DROP TABLE IF EXISTS `tb_dorm_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_dorm_application` (
  `application_id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint NOT NULL,
  `type` tinyint NOT NULL COMMENT '类型: 1-入住, 2-退宿, 3-调宿',
  `current_room_id` bigint DEFAULT NULL COMMENT '当前宿舍(调宿/退宿用)',
  `target_room_id` bigint DEFAULT NULL COMMENT '目标宿舍(调宿用)',
  `reason` varchar(500) NOT NULL,
  `proof_url` varchar(500) DEFAULT NULL COMMENT '证明材料',
  `status` tinyint DEFAULT '0' COMMENT '状态: 0-待审核, 1-辅导员通过, 2-宿管通过(完成), 3-驳回',
  `audit_remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`application_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍变动申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_dorm_application`
--

LOCK TABLES `tb_dorm_application` WRITE;
/*!40000 ALTER TABLE `tb_dorm_application` DISABLE KEYS */;
INSERT INTO `tb_dorm_application` VALUES (1,32,1,NULL,NULL,'新生入学',NULL,0,NULL,'2025-11-27 20:11:56'),(2,33,2,NULL,NULL,'毕业退宿',NULL,1,NULL,'2025-11-27 20:11:56'),(3,34,3,NULL,NULL,'宿舍太吵',NULL,2,NULL,'2025-11-27 20:11:56'),(4,35,1,NULL,NULL,'复学入住',NULL,3,NULL,'2025-11-27 20:11:56'),(5,36,2,NULL,NULL,'实习退宿',NULL,0,NULL,'2025-11-27 20:11:56'),(6,37,3,NULL,NULL,'性格不合',NULL,1,NULL,'2025-11-27 20:11:56'),(7,38,1,NULL,NULL,'申请入住',NULL,2,NULL,'2025-11-27 20:11:56'),(8,39,2,NULL,NULL,'出国退宿',NULL,3,NULL,'2025-11-27 20:11:56'),(9,40,3,NULL,NULL,'换个环境',NULL,0,NULL,'2025-11-27 20:11:56'),(10,41,1,NULL,NULL,'入住',NULL,1,NULL,'2025-11-27 20:11:56'),(11,42,2,NULL,NULL,'退宿',NULL,2,NULL,'2025-11-27 20:11:56'),(12,43,3,NULL,NULL,'调宿',NULL,3,NULL,'2025-11-27 20:11:56'),(13,44,1,NULL,NULL,'入住',NULL,0,NULL,'2025-11-27 20:11:56'),(14,45,2,NULL,NULL,'退宿',NULL,1,NULL,'2025-11-27 20:11:56'),(15,46,3,NULL,NULL,'调宿',NULL,2,NULL,'2025-11-27 20:11:56');
/*!40000 ALTER TABLE `tb_dorm_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_dorm_manager`
--

DROP TABLE IF EXISTS `tb_dorm_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_dorm_manager` (
  `manager_id` bigint NOT NULL COMMENT '关联sys_user.user_id',
  `work_no` varchar(32) NOT NULL COMMENT '工号',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `manage_building_id` bigint DEFAULT NULL COMMENT '负责管理的楼栋ID',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿管信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_dorm_manager`
--

LOCK TABLES `tb_dorm_manager` WRITE;
/*!40000 ALTER TABLE `tb_dorm_manager` DISABLE KEYS */;
INSERT INTO `tb_dorm_manager` VALUES (17,'man01','宿管张姨',1,'13900000001'),(18,'man02','宿管李叔',2,'13900000002'),(19,'man03','宿管王姨',3,'13900000003'),(20,'man04','宿管赵叔',4,'13900000004'),(21,'man05','宿管刘姨',5,'13900000005'),(22,'man06','宿管孙叔',6,'13900000006'),(23,'man07','宿管周姨',7,'13900000007'),(24,'man08','宿管吴叔',8,'13900000008'),(25,'man09','宿管郑姨',9,'13900000009'),(26,'man10','宿管冯叔',10,'13900000010'),(27,'man11','宿管陈姨',11,'13900000011'),(28,'man12','宿管楚叔',12,'13900000012'),(29,'man13','宿管卫姨',13,'13900000013'),(30,'man14','宿管蒋叔',14,'13900000014'),(31,'man15','宿管沈姨',15,'13900000015');
/*!40000 ALTER TABLE `tb_dorm_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_electricity_log`
--

DROP TABLE IF EXISTS `tb_electricity_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_electricity_log` (
  `log_id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint NOT NULL,
  `amount` decimal(10,2) NOT NULL COMMENT '充值金额',
  `balance_after` decimal(10,2) NOT NULL COMMENT '充值后余额',
  `operator_name` varchar(64) NOT NULL COMMENT '充值人姓名',
  `payment_method` varchar(32) DEFAULT 'WeChat' COMMENT '支付方式',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='电费充值记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_electricity_log`
--

LOCK TABLES `tb_electricity_log` WRITE;
/*!40000 ALTER TABLE `tb_electricity_log` DISABLE KEYS */;
INSERT INTO `tb_electricity_log` VALUES (1,1,100.00,250.00,'学生甲','WeChat','2025-11-27 20:11:56'),(2,2,50.00,138.50,'学生乙','WeChat','2025-11-27 20:11:56'),(3,3,200.00,400.00,'学生丙','WeChat','2025-11-27 20:11:56'),(4,4,30.00,75.00,'学生丁','WeChat','2025-11-27 20:11:56'),(5,5,100.00,220.00,'学生戊','WeChat','2025-11-27 20:11:56'),(6,6,50.00,80.50,'学生己','WeChat','2025-11-27 20:11:56'),(7,7,500.00,1000.00,'学生庚','WeChat','2025-11-27 20:11:56'),(8,8,10.00,20.00,'学生辛','WeChat','2025-11-27 20:11:56'),(9,9,66.00,132.66,'学生壬','WeChat','2025-11-27 20:11:56'),(10,10,100.00,199.99,'学生癸','WeChat','2025-11-27 20:11:56'),(11,11,20.00,35.00,'学生子','WeChat','2025-11-27 20:11:56'),(12,12,300.00,600.00,'学生丑','WeChat','2025-11-27 20:11:56'),(13,13,100.00,280.00,'学生寅','WeChat','2025-11-27 20:11:56'),(14,14,20.00,40.00,'学生卯','WeChat','2025-11-27 20:11:56'),(15,15,100.00,210.00,'学生辰','WeChat','2025-11-27 20:11:56'),(16,1,60.00,210.00,'学生甲','微信支付','2025-11-27 21:24:11'),(17,1,50.00,260.00,'学生甲','微信支付','2025-11-28 10:07:34'),(18,1,50.00,310.00,'学生甲','微信支付','2025-11-28 10:07:53');
/*!40000 ALTER TABLE `tb_electricity_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_hygiene_check`
--

DROP TABLE IF EXISTS `tb_hygiene_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_hygiene_check` (
  `check_id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint NOT NULL,
  `score` int NOT NULL COMMENT '得分',
  `deduct_reason` varchar(255) DEFAULT NULL COMMENT '扣分原因',
  `check_date` date NOT NULL COMMENT '检查日期',
  `checker_name` varchar(64) DEFAULT NULL COMMENT '检查人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`check_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='卫生检查记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_hygiene_check`
--

LOCK TABLES `tb_hygiene_check` WRITE;
/*!40000 ALTER TABLE `tb_hygiene_check` DISABLE KEYS */;
INSERT INTO `tb_hygiene_check` VALUES (1,1,90,NULL,'2025-11-27','李阿姨','2025-11-27 20:11:56'),(2,2,85,'垃圾未倒','2025-11-27','李阿姨','2025-11-27 20:11:56'),(3,3,95,NULL,'2025-11-27','李阿姨','2025-11-27 20:11:56'),(4,4,70,'地面脏','2025-11-27','李阿姨','2025-11-27 20:11:56'),(5,5,88,NULL,'2025-11-27','李阿姨','2025-11-27 20:11:56'),(6,6,60,'违规电器','2025-11-27','李阿姨','2025-11-27 20:11:56'),(7,7,92,NULL,'2025-11-27','李阿姨','2025-11-27 20:11:56'),(8,8,80,'物品乱放','2025-11-27','李阿姨','2025-11-27 20:11:56'),(9,9,98,NULL,'2025-11-27','李阿姨','2025-11-27 20:11:56'),(10,10,75,'有异味','2025-11-27','李阿姨','2025-11-27 20:11:56'),(11,11,85,NULL,'2025-11-27','李阿姨','2025-11-27 20:11:56'),(12,12,90,NULL,'2025-11-27','李阿姨','2025-11-27 20:11:56'),(13,13,65,'被子未叠','2025-11-27','李阿姨','2025-11-27 20:11:56'),(14,14,93,NULL,'2025-11-27','李阿姨','2025-11-27 20:11:56'),(15,15,82,'桌面乱','2025-11-27','李阿姨','2025-11-27 20:11:56');
/*!40000 ALTER TABLE `tb_hygiene_check` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_item`
--

DROP TABLE IF EXISTS `tb_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_item` (
  `item_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '物品名称(如:手推车)',
  `total_count` int DEFAULT '0' COMMENT '总库存',
  `available_count` int DEFAULT '0' COMMENT '当前可用库存',
  `location` varchar(128) DEFAULT NULL COMMENT '存放位置',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公用物品库存表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_item`
--

LOCK TABLES `tb_item` WRITE;
/*!40000 ALTER TABLE `tb_item` DISABLE KEYS */;
INSERT INTO `tb_item` VALUES (1,'手推车',10,8,'1号楼'),(2,'梯子',5,5,'2号楼'),(3,'扳手',20,18,'值班室'),(4,'螺丝刀',20,20,'值班室'),(5,'打气筒',10,9,'3号楼'),(6,'扫把',50,45,'仓库'),(7,'拖把',50,40,'仓库'),(8,'吸尘器',2,2,'宿管办'),(9,'急救箱',5,5,'前台'),(10,'插线板',30,25,'物资柜'),(11,'吹风机',10,10,'1楼'),(12,'微波炉',3,3,'公共区'),(13,'挂烫机',2,1,'公共区'),(14,'五金箱',5,5,'值班室'),(15,'雨伞',20,15,'前台');
/*!40000 ALTER TABLE `tb_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_leave_apply`
--

DROP TABLE IF EXISTS `tb_leave_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_leave_apply` (
  `apply_id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint NOT NULL COMMENT '申请学生ID',
  `counselor_id` bigint NOT NULL COMMENT '审批辅导员ID',
  `type` varchar(32) NOT NULL COMMENT '类型: 事假/病假/其他',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `reason` varchar(500) NOT NULL COMMENT '请假原因',
  `proof_image` varchar(500) DEFAULT NULL COMMENT '证明材料图片',
  `status` tinyint DEFAULT '0' COMMENT '状态: 0-待审批, 1-已批准, 2-已驳回, 3-已销假, 4-已撤销',
  `audit_remark` varchar(255) DEFAULT NULL COMMENT '审批意见/驳回理由',
  `audit_time` datetime DEFAULT NULL COMMENT '审批时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`apply_id`),
  KEY `idx_stu_counselor` (`student_id`,`counselor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生请假申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_leave_apply`
--

LOCK TABLES `tb_leave_apply` WRITE;
/*!40000 ALTER TABLE `tb_leave_apply` DISABLE KEYS */;
INSERT INTO `tb_leave_apply` VALUES (1,32,2,'病假','2025-11-27 20:11:55','2025-11-28 20:11:55','感冒',NULL,0,NULL,NULL,'2025-11-27 20:11:55'),(2,33,3,'事假','2025-11-27 20:11:55','2025-11-29 20:11:55','回家',NULL,1,NULL,NULL,'2025-11-27 20:11:55'),(3,34,4,'病假','2025-11-27 20:11:55','2025-11-30 20:11:55','发烧',NULL,2,NULL,NULL,'2025-11-27 20:11:55'),(4,35,5,'事假','2025-11-27 20:11:55','2025-11-28 20:11:55','考驾照',NULL,0,NULL,NULL,'2025-11-27 20:11:55'),(5,36,6,'病假','2025-11-27 20:11:55','2025-11-28 20:11:55','胃疼',NULL,1,NULL,NULL,'2025-11-27 20:11:55'),(6,37,7,'事假','2025-11-27 20:11:55','2025-12-02 20:11:55','家里有事',NULL,2,NULL,NULL,'2025-11-27 20:11:55'),(7,38,8,'病假','2025-11-27 20:11:55','2025-11-28 20:11:55','头疼',NULL,0,NULL,NULL,'2025-11-27 20:11:55'),(8,39,9,'事假','2025-11-27 20:11:55','2025-11-29 20:11:55','参加婚礼',NULL,1,NULL,NULL,'2025-11-27 20:11:55'),(9,40,10,'病假','2025-11-27 20:11:55','2025-11-28 20:11:55','扭伤',NULL,2,NULL,NULL,'2025-11-27 20:11:55'),(10,41,11,'事假','2025-11-27 20:11:55','2025-11-28 20:11:55','面试',NULL,0,NULL,NULL,'2025-11-27 20:11:55'),(11,42,12,'病假','2025-11-27 20:11:55','2025-11-28 20:11:55','过敏',NULL,1,NULL,NULL,'2025-11-27 20:11:55'),(12,43,13,'事假','2025-11-27 20:11:55','2025-11-30 20:11:55','探亲',NULL,2,NULL,NULL,'2025-11-27 20:11:55'),(13,44,14,'病假','2025-11-27 20:11:55','2025-11-28 20:11:55','牙疼',NULL,0,NULL,NULL,'2025-11-27 20:11:55'),(14,45,15,'事假','2025-11-27 20:11:55','2025-11-29 20:11:55','旅游',NULL,1,NULL,NULL,'2025-11-27 20:11:55'),(15,46,16,'病假','2025-11-27 20:11:55','2025-11-28 20:11:55','腹泻',NULL,2,NULL,NULL,'2025-11-27 20:11:55'),(16,32,2,'事假','2025-11-08 00:00:00','2025-11-22 00:00:00','test',NULL,1,'同意','2025-11-28 10:09:45','2025-11-28 10:08:48');
/*!40000 ALTER TABLE `tb_leave_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_repair`
--

DROP TABLE IF EXISTS `tb_repair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_repair` (
  `repair_id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint NOT NULL COMMENT '报修人ID',
  `building_id` bigint NOT NULL,
  `room_id` bigint NOT NULL,
  `repair_type` varchar(32) NOT NULL COMMENT '类型: 水电/家具/网络/其他',
  `description` varchar(500) NOT NULL COMMENT '故障描述',
  `image_urls` text COMMENT '故障图片(逗号分隔)',
  `status` tinyint DEFAULT '0' COMMENT '状态: 0-待审核, 1-已派单, 2-维修中, 3-已完成',
  `repairer_name` varchar(64) DEFAULT NULL COMMENT '维修工姓名',
  `repairer_phone` varchar(20) DEFAULT NULL COMMENT '维修工电话',
  `submit_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `rate_score` int DEFAULT NULL COMMENT '评价分数(1-5)',
  `rate_comment` varchar(255) DEFAULT NULL COMMENT '评价内容',
  PRIMARY KEY (`repair_id`),
  KEY `idx_student` (`student_id`),
  KEY `idx_room` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='维修报修单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_repair`
--

LOCK TABLES `tb_repair` WRITE;
/*!40000 ALTER TABLE `tb_repair` DISABLE KEYS */;
INSERT INTO `tb_repair` VALUES (1,32,1,1,'水电','水龙头漏水',NULL,0,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(2,33,2,2,'家具','椅子腿断了',NULL,1,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(3,34,3,3,'网络','网口不通',NULL,0,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(4,35,4,4,'门窗','门锁坏了',NULL,1,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(5,36,5,5,'水电','灯泡不亮',NULL,0,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(6,37,6,6,'家具','柜门掉了',NULL,1,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(7,38,7,7,'网络','WiFi信号差',NULL,0,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(8,39,8,8,'门窗','窗户关不上',NULL,1,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(9,40,9,9,'水电','马桶堵了',NULL,0,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(10,41,10,10,'家具','床板裂了',NULL,1,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(11,42,11,11,'网络','无法连接校园网',NULL,0,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(12,43,12,12,'门窗','玻璃碎了',NULL,1,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(13,44,13,13,'水电','停水了',NULL,0,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(14,45,14,14,'家具','桌子晃动',NULL,1,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(15,46,15,15,'其他','墙皮脱落',NULL,0,NULL,NULL,'2025-11-27 20:11:55',NULL,NULL,NULL),(16,32,1,1,'水电维修','test','http://localhost:8080/images/71eaf9e7-0427-4744-8860-be29471b94e2.jpg',1,NULL,NULL,'2025-11-28 10:06:11','2025-11-28 10:07:07',NULL,NULL);
/*!40000 ALTER TABLE `tb_repair` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_room`
--

DROP TABLE IF EXISTS `tb_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_room` (
  `room_id` bigint NOT NULL AUTO_INCREMENT,
  `building_id` bigint NOT NULL COMMENT '所属楼栋',
  `floor_number` int NOT NULL COMMENT '所在楼层',
  `room_number` varchar(10) NOT NULL COMMENT '宿舍号(如:101)',
  `category` varchar(32) DEFAULT '4人间' COMMENT '户型(4人间/6人间)',
  `capacity` int DEFAULT '4' COMMENT '总床位数',
  `occupied` int DEFAULT '0' COMMENT '已入住人数',
  `electricity_balance` decimal(10,2) DEFAULT '0.00' COMMENT '电费余额',
  `water_balance` decimal(10,2) DEFAULT '0.00' COMMENT '水费余额(备用)',
  `last_clean_score` int DEFAULT NULL COMMENT '上次卫生检查得分',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`room_id`),
  UNIQUE KEY `uk_build_room` (`building_id`,`room_number`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍房间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_room`
--

LOCK TABLES `tb_room` WRITE;
/*!40000 ALTER TABLE `tb_room` DISABLE KEYS */;
INSERT INTO `tb_room` VALUES (1,1,1,'101','4人间',4,1,310.00,0.00,NULL,'2025-11-27 20:11:55'),(2,2,1,'101','4人间',4,1,88.50,0.00,NULL,'2025-11-27 20:11:55'),(3,3,2,'201','6人间',6,1,200.00,0.00,NULL,'2025-11-27 20:11:55'),(4,4,2,'202','6人间',6,1,45.00,0.00,NULL,'2025-11-27 20:11:55'),(5,5,3,'301','2人间',2,1,120.00,0.00,NULL,'2025-11-27 20:11:55'),(6,6,3,'305','2人间',2,1,30.50,0.00,NULL,'2025-11-27 20:11:55'),(7,7,4,'401','单人间',1,1,500.00,0.00,NULL,'2025-11-27 20:11:55'),(8,8,4,'404','4人间',4,1,10.00,0.00,NULL,'2025-11-27 20:11:55'),(9,9,5,'501','4人间',4,1,66.66,0.00,NULL,'2025-11-27 20:11:55'),(10,10,5,'502','4人间',4,1,99.99,0.00,NULL,'2025-11-27 20:11:55'),(11,11,6,'601','4人间',4,1,15.00,0.00,NULL,'2025-11-27 20:11:55'),(12,12,1,'102','单人间',1,1,300.00,0.00,NULL,'2025-11-27 20:11:55'),(13,13,2,'205','2人间',2,1,180.00,0.00,NULL,'2025-11-27 20:11:55'),(14,14,1,'108','8人间',8,1,20.00,0.00,NULL,'2025-11-27 20:11:55'),(15,15,3,'308','4人间',4,1,110.00,0.00,NULL,'2025-11-27 20:11:55'),(16,1,1,'102','4人间',4,0,100.00,0.00,NULL,'2025-11-28 15:02:31'),(17,1,1,'103','4人间',4,0,100.00,0.00,NULL,'2025-11-28 15:02:31'),(18,1,2,'201','4人间',4,0,100.00,0.00,NULL,'2025-11-28 15:02:31'),(19,1,2,'202','4人间',4,0,100.00,0.00,NULL,'2025-11-28 15:02:31'),(20,1,2,'203','4人间',4,0,100.00,0.00,NULL,'2025-11-28 15:02:31'),(21,1,3,'301','4人间',4,0,100.00,0.00,NULL,'2025-11-28 15:02:31');
/*!40000 ALTER TABLE `tb_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_student`
--

DROP TABLE IF EXISTS `tb_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_student` (
  `student_id` bigint NOT NULL COMMENT '关联sys_user.user_id',
  `student_no` varchar(32) NOT NULL COMMENT '学号',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `gender` tinyint DEFAULT '1' COMMENT '性别: 1-男, 2-女',
  `college_name` varchar(64) DEFAULT NULL COMMENT '所属院系',
  `major_name` varchar(64) DEFAULT NULL COMMENT '专业',
  `class_name` varchar(64) DEFAULT NULL COMMENT '班级',
  `enrollment_year` int DEFAULT NULL COMMENT '入学年份',
  `counselor_id` bigint DEFAULT NULL COMMENT '所属辅导员ID',
  `building_id` bigint DEFAULT NULL COMMENT '所在楼栋ID',
  `room_id` bigint DEFAULT NULL COMMENT '所在宿舍ID',
  `bed_number` int DEFAULT NULL COMMENT '床位号',
  `emergency_contact` varchar(64) DEFAULT NULL COMMENT '紧急联系人',
  `emergency_phone` varchar(20) DEFAULT NULL COMMENT '紧急联系电话',
  `status` tinyint DEFAULT '1' COMMENT '学籍状态: 1-在校, 2-休学, 3-毕业',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `uk_student_no` (`student_no`),
  KEY `idx_counselor` (`counselor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_student`
--

LOCK TABLES `tb_student` WRITE;
/*!40000 ALTER TABLE `tb_student` DISABLE KEYS */;
INSERT INTO `tb_student` VALUES (32,'stu01','学生甲',1,'计算机学院','软件工程','1班',NULL,2,1,1,1,'甲父','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(33,'stu02','学生乙',1,'机械学院','机械制造','2班',NULL,3,2,2,1,'乙父','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(34,'stu03','学生丙',2,'外语学院','英语','3班',NULL,4,3,3,1,'丙母','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(35,'stu04','学生丁',2,'经管学院','会计','4班',NULL,5,4,4,1,'丁父','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(36,'stu05','学生戊',1,'艺术学院','设计','5班',NULL,6,5,5,1,'戊母','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(37,'stu06','学生己',2,'数学学院','统计','6班',NULL,7,6,6,1,'己父','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(38,'stu07','学生庚',1,'物理学院','应用物理','7班',NULL,8,7,7,1,'庚母','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(39,'stu08','学生辛',1,'化学学院','应用化学','8班',NULL,9,8,8,1,'辛父','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(40,'stu09','学生壬',1,'生科学院','生物技术','9班',NULL,10,9,9,1,'壬母','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(41,'stu10','学生癸',1,'法学院','法学','10班',NULL,11,10,10,1,'癸父','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(42,'stu11','学生子',1,'医学院','临床医学','11班',NULL,12,11,11,1,'子母','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(43,'stu12','学生丑',1,'建筑学院','建筑学','12班',NULL,13,12,12,1,'丑父','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(44,'stu13','学生寅',1,'土木学院','土木工程','13班',NULL,14,13,13,1,'寅母','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(45,'stu14','学生卯',1,'能动学院','能源动力','14班',NULL,15,14,14,1,'卯父','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(46,'stu15','学生辰',1,'信息学院','通信工程','15班',NULL,16,15,15,1,'辰母','110',1,'2025-11-27 20:11:55','2025-11-27 20:11:55'),(101,'2023101','张三丰',1,'计算机学院','软件工程','软件1班',NULL,2,1,1,2,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(102,'2023102','李寻欢',1,'计算机学院','软件工程','软件1班',NULL,2,1,1,3,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(103,'2023103','郭靖',1,'计算机学院','软件工程','软件1班',NULL,2,1,1,4,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(104,'2023104','黄蓉',2,'计算机学院','软件工程','软件1班',NULL,2,3,3,2,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(105,'2023105','杨过',1,'计算机学院','网络工程','网络2班',NULL,2,1,14,1,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(106,'2023106','小龙女',2,'计算机学院','网络工程','网络2班',NULL,2,3,3,3,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(107,'2023107','令狐冲',1,'计算机学院','网络工程','网络2班',NULL,2,1,14,2,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(108,'2023108','任盈盈',2,'计算机学院','信息安全','信安3班',NULL,2,3,3,4,NULL,NULL,2,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(109,'2023109','乔峰',1,'计算机学院','信息安全','信安3班',NULL,2,1,14,3,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(110,'2023110','段誉',1,'计算机学院','信息安全','信安3班',NULL,2,1,14,4,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(111,'2023111','虚竹',1,'计算机学院','物联网','物联4班',NULL,2,2,2,2,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(112,'2023112','王语嫣',2,'计算机学院','物联网','物联4班',NULL,2,4,4,2,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(113,'2023113','韦小宝',1,'计算机学院','物联网','物联4班',NULL,2,2,2,3,NULL,NULL,3,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(114,'2023114','阿珂',2,'计算机学院','数据科学','大数据5班',NULL,2,4,4,3,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(115,'2023115','周伯通',1,'计算机学院','数据科学','大数据5班',NULL,2,2,2,4,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(116,'2023116','洪七公',1,'计算机学院','数据科学','大数据5班',NULL,2,5,5,2,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(117,'2023117','欧阳锋',1,'计算机学院','人工智能','AI6班',NULL,2,5,5,3,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(118,'2023118','黄药师',1,'计算机学院','人工智能','AI6班',NULL,2,5,5,4,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(119,'2023119','一灯大师',1,'计算机学院','人工智能','AI6班',NULL,2,8,8,2,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56'),(120,'2023120','风清扬',1,'计算机学院','人工智能','AI6班',NULL,2,8,8,3,NULL,NULL,1,'2025-11-28 16:49:56','2025-11-28 16:49:56');
/*!40000 ALTER TABLE `tb_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_visitor`
--

DROP TABLE IF EXISTS `tb_visitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_visitor` (
  `visitor_id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint NOT NULL COMMENT '被访学生ID',
  `visitor_name` varchar(64) NOT NULL,
  `id_card` varchar(20) NOT NULL COMMENT '身份证号',
  `phone` varchar(20) NOT NULL,
  `reason` varchar(255) NOT NULL COMMENT '来访事由',
  `visit_start_time` datetime NOT NULL COMMENT '预约来访时间',
  `visit_end_time` datetime DEFAULT NULL COMMENT '实际离开时间',
  `status` tinyint DEFAULT '0' COMMENT '状态: 0-待审核, 1-已通过(未到), 2-已进入, 3-已离开, 4-驳回',
  `qr_code` varchar(500) DEFAULT NULL COMMENT '通行二维码内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`visitor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='访客记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_visitor`
--

LOCK TABLES `tb_visitor` WRITE;
/*!40000 ALTER TABLE `tb_visitor` DISABLE KEYS */;
INSERT INTO `tb_visitor` VALUES (1,32,'访客A','420101198001010001','13000000001','探望','2025-11-27 20:11:55',NULL,0,NULL,'2025-11-27 20:11:55'),(2,33,'访客B','420101198001010002','13000000002','送东西','2025-11-27 20:11:55',NULL,1,NULL,'2025-11-27 20:11:55'),(3,34,'访客C','420101198001010003','13000000003','家长','2025-11-27 20:11:55',NULL,2,NULL,'2025-11-27 20:11:55'),(4,35,'访客D','420101198001010004','13000000004','朋友','2025-11-27 20:11:55',NULL,3,NULL,'2025-11-27 20:11:55'),(5,36,'访客E','420101198001010005','13000000005','探望','2025-11-27 20:11:55',NULL,4,NULL,'2025-11-27 20:11:55'),(6,37,'访客F','420101198001010006','13000000006','送快递','2025-11-27 20:11:55',NULL,1,NULL,'2025-11-27 20:11:55'),(7,38,'访客G','420101198001010007','13000000007','修电脑','2025-11-27 20:11:55',NULL,1,NULL,'2025-11-27 20:11:55'),(8,39,'访客H','420101198001010008','13000000008','同学','2025-11-27 20:11:55',NULL,2,NULL,'2025-11-27 20:11:55'),(9,40,'访客I','420101198001010009','13000000009','亲戚','2025-11-27 20:11:55',NULL,3,NULL,'2025-11-27 20:11:55'),(10,41,'访客J','420101198001010010','13000000010','探望','2025-11-27 20:11:55',NULL,4,NULL,'2025-11-27 20:11:55'),(11,42,'访客K','420101198001010011','13000000011','送饭','2025-11-27 20:11:55',NULL,0,NULL,'2025-11-27 20:11:55'),(12,43,'访客L','420101198001010012','13000000012','家长','2025-11-27 20:11:55',NULL,1,NULL,'2025-11-27 20:11:55'),(13,44,'访客M','420101198001010013','13000000013','朋友','2025-11-27 20:11:55',NULL,2,NULL,'2025-11-27 20:11:55'),(14,45,'访客N','420101198001010014','13000000014','送书','2025-11-27 20:11:55',NULL,3,NULL,'2025-11-27 20:11:55'),(15,46,'访客O','420101198001010015','13000000015','探望','2025-11-27 20:11:55',NULL,4,NULL,'2025-11-27 20:11:55');
/*!40000 ALTER TABLE `tb_visitor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-05 20:58:28
