/*Table structure for table `t_time_config` */

DROP TABLE IF EXISTS `t_time_config`;

CREATE TABLE `t_time_config` (
  `idt_time` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `day` smallint(3) NOT NULL DEFAULT '0' COMMENT '0今天，1明天，2后天，依此类推',
  `hour` smallint(2) NOT NULL DEFAULT '0' COMMENT '24小时，1表示上午1点，13表下午1点',
  `minute` smallint(2) NOT NULL DEFAULT '0' COMMENT '分钟',
  `second` smallint(2) NOT NULL DEFAULT '0' COMMENT '秒',
  PRIMARY KEY (`idt_time`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

/*Data for the table `t_time_config` */

insert  into `t_time_config`(`idt_time`,`day`,`hour`,`minute`,`second`) values (1,0,11,30,0),(2,0,12,0,0),(3,0,17,30,0),(4,0,18,0,0),(5,1,11,30,0);
