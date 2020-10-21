/*
SQLyog v10.2 
MySQL - 5.5.46-0ubuntu0.14.04.2 : Database - new_aijia
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`new_aijia` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `new_aijia`;

/*Table structure for table `t_recommend_key` */

DROP TABLE IF EXISTS `t_recommend_key`;

CREATE TABLE `t_recommend_key` (
  `key_id` int(11) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(128) NOT NULL,
  PRIMARY KEY (`key_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `t_recommend_key` */

insert  into `t_recommend_key`(`key_id`,`key_name`) values (1,'现代'),(2,'地中海'),(3,'美式'),(4,'中式'),(5,'欧式'),(6,'东南亚'),(7,'客厅'),(8,'主卧'),(9,'儿童房'),(10,'餐厅'),(11,'次卧'),(12,'书房');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
