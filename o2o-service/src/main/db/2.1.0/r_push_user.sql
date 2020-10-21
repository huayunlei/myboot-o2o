/*Table structure for table `r_push_user` */

DROP TABLE IF EXISTS `r_push_user`;

CREATE TABLE `r_push_user` (
  `idr_push_user` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '推送-用户关系表主键',
  `fid_user` bigint(20) DEFAULT NULL COMMENT '用户id',
  `fid_push` bigint(20) DEFAULT NULL COMMENT '推送id',
  `status` smallint(1) DEFAULT '1' COMMENT '状态0：失效1：有效',
  PRIMARY KEY (`idr_push_user`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
