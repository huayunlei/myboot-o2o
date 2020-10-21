/*Table structure for table `t_tag` */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `idt_tag` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_name` varchar(100) DEFAULT NULL COMMENT '标签名',
  `status` smallint(1) DEFAULT '1' COMMENT '状态0：失效1：有效',
  `type` smallint(2) DEFAULT '0' COMMENT '标签类型：1:城市，2：版本',
  `tag_desc` varchar(20) DEFAULT NULL COMMENT '标签描述',
  PRIMARY KEY (`idt_tag`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `t_tag` */

insert  into `t_tag`(`idt_tag`,`tag_name`,`status`,`type`,`tag_desc`) values (1,'210000',1,2,'南京市'),(2,'100000',1,2,'北京市'),(3,'450000',1,2,'郑州市'),(4,'1.8.0',1,1,'版本1.8.0'),(5,'1.9.0',1,1,'版本1.9.0'),(6,'2.0.0',1,1,'版本2.0.0'),(7,'2.1.0',1,1,'版本2.1.0'),(8,'2.2.0',1,1,'版本2.2.0'),(9,'1',1,3,'性别男'),(10,'0',1,3,'性别女'),(11,'210000',1,0,'默认all');
