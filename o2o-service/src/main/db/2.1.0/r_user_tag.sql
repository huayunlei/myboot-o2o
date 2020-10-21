CREATE TABLE `r_user_tag` (
  `idr_user_tag` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户-标签关系表主键',
  `fid_user` bigint(20) DEFAULT NULL COMMENT '用户id',
  `tag_type` smallint(2) DEFAULT NULL COMMENT '标签类型',
  `tag_name` varchar(20) NOT NULL COMMENT '标签名称',
  `status` smallint(1) DEFAULT '1' COMMENT '状态0：失效1：有效',
  PRIMARY KEY (`idr_user_tag`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
