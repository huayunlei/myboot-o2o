

insert  into `t_dictionary`(`idt_dic`,`dic_key`,`dic_value`) values (4,'ACTION_TYPE_HEADIMG','http://7vilis.com2.z0.glb.qiniucdn.com/banner.png');

/* Create table for free design */
CREATE TABLE `t_free_design` (
  `id` bigint(20) NOT NULL,
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `style` varchar(255) DEFAULT NULL COMMENT '风格',
  `budget` varchar(64) DEFAULT NULL COMMENT '全屋预算',
  `cityCode` varchar(6) DEFAULT NULL COMMENT '城市邮政编码',
  `city` varchar(64) DEFAULT NULL COMMENT '城市信息',
  `suitId` bigint(20) DEFAULT NULL COMMENT '套装id',
  `roomId` bigint(20) DEFAULT NULL COMMENT '空间id',
  `appellation` varchar(64) DEFAULT NULL COMMENT '用户称谓',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

