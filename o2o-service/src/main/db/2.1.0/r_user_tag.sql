CREATE TABLE `r_user_tag` (
  `idr_user_tag` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '�û�-��ǩ��ϵ������',
  `fid_user` bigint(20) DEFAULT NULL COMMENT '�û�id',
  `tag_type` smallint(2) DEFAULT NULL COMMENT '��ǩ����',
  `tag_name` varchar(20) NOT NULL COMMENT '��ǩ����',
  `status` smallint(1) DEFAULT '1' COMMENT '״̬0��ʧЧ1����Ч',
  PRIMARY KEY (`idr_user_tag`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
