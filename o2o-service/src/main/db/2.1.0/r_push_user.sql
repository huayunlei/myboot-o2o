/*Table structure for table `r_push_user` */

DROP TABLE IF EXISTS `r_push_user`;

CREATE TABLE `r_push_user` (
  `idr_push_user` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����-�û���ϵ������',
  `fid_user` bigint(20) DEFAULT NULL COMMENT '�û�id',
  `fid_push` bigint(20) DEFAULT NULL COMMENT '����id',
  `status` smallint(1) DEFAULT '1' COMMENT '״̬0��ʧЧ1����Ч',
  PRIMARY KEY (`idr_push_user`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
