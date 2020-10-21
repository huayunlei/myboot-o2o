/*Table structure for table `t_tag` */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `idt_tag` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����',
  `tag_name` varchar(100) DEFAULT NULL COMMENT '��ǩ��',
  `status` smallint(1) DEFAULT '1' COMMENT '״̬0��ʧЧ1����Ч',
  `type` smallint(2) DEFAULT '0' COMMENT '��ǩ���ͣ�1:���У�2���汾',
  `tag_desc` varchar(20) DEFAULT NULL COMMENT '��ǩ����',
  PRIMARY KEY (`idt_tag`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `t_tag` */

insert  into `t_tag`(`idt_tag`,`tag_name`,`status`,`type`,`tag_desc`) values (1,'210000',1,2,'�Ͼ���'),(2,'100000',1,2,'������'),(3,'450000',1,2,'֣����'),(4,'1.8.0',1,1,'�汾1.8.0'),(5,'1.9.0',1,1,'�汾1.9.0'),(6,'2.0.0',1,1,'�汾2.0.0'),(7,'2.1.0',1,1,'�汾2.1.0'),(8,'2.2.0',1,1,'�汾2.2.0'),(9,'1',1,3,'�Ա���'),(10,'0',1,3,'�Ա�Ů'),(11,'210000',1,0,'Ĭ��all');
